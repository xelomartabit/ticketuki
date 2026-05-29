package com.ticketuki.ticketservice.service;

import com.ticketuki.ticketservice.dto.EstadoTicketDTO;
import com.ticketuki.ticketservice.dto.TicketRequestDTO;
import com.ticketuki.ticketservice.dto.TicketResponseDTO;
import com.ticketuki.ticketservice.dto.TicketUpdateDTO;
import com.ticketuki.ticketservice.dto.TransferirTicketDTO;
import com.ticketuki.ticketservice.dto.ValidarTicketResponseDTO;
import com.ticketuki.ticketservice.exception.TicketNotFoundException;
import com.ticketuki.ticketservice.exception.TicketOperacionInvalidaException;
import com.ticketuki.ticketservice.model.Ticket;
import com.ticketuki.ticketservice.repository.TicketRepository;
import com.ticketuki.ticketservice.util.QRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Qualifier("estadoWebClient")
    private final WebClient estadoWebClient;

    @Qualifier("eventoWebClient")
    private final WebClient eventoWebClient;

    @Qualifier("recintoWebClient")
    private final WebClient recintoWebClient;

    @Qualifier("ventaWebClient")
    private final WebClient ventaWebClient;

    // Cache en memoria: se carga una sola vez al primer uso
    private volatile List<EstadoTicketDTO> estadosCache = null;

    // --- Helpers de mapeo ---

    private TicketResponseDTO toResponseDTO(Ticket ticket) {
        return new TicketResponseDTO(
                ticket.getId_ticket(),
                ticket.getCod_qr(),
                ticket.getNum_asiento(),
                ticket.getNombre_titular(),
                ticket.getRun_titular(),
                ticket.getFecha_emision(),
                ticket.getVenta_id_venta(),
                ticket.getEstado_ticket_id_estado(),
                ticket.getEvento_id_evento(),
                ticket.getSector_id_sector()
        );
    }

    // --- Cache de estados ---

    private List<EstadoTicketDTO> obtenerEstadosTicket() {
        if (estadosCache == null) {
            List<EstadoTicketDTO> estados = estadoWebClient.get()
                    .uri("/api/v1/estadosTicket")
                    .retrieve()
                    .bodyToFlux(EstadoTicketDTO.class)
                    .collectList()
                    .block();
            estadosCache = (estados != null) ? estados : List.of();
            log.info("Estados de ticket cargados en caché: {}", estadosCache.size());
        }
        return estadosCache;
    }

    private String obtenerNombreEstado(Long idEstado) {
        if (idEstado == null) return null;
        return obtenerEstadosTicket().stream()
                .filter(e -> e.getId().equals(idEstado))
                .map(EstadoTicketDTO::getNombre)
                .findFirst()
                .orElse(null);
    }

    private Long obtenerIdPorNombre(String fragmentoNombre) {
        return obtenerEstadosTicket().stream()
                .filter(e -> e.getNombre() != null &&
                        e.getNombre().toUpperCase().contains(fragmentoNombre.toUpperCase()))
                .findFirst()
                .map(EstadoTicketDTO::getId)
                .orElse(null);
    }

    private Long obtenerEstadoInicial() {
        Long id = obtenerIdPorNombre("VALID");
        if (id == null) {
            log.warn("No se encontró estado 'VÁLIDO' en ms-estado, el ticket se creará sin estado");
        }
        return id;
    }

    // --- Validaciones de estado para operaciones ---

    private void verificarModificable(Ticket ticket) {
        String nombre = obtenerNombreEstado(ticket.getEstado_ticket_id_estado());
        if (nombre == null) return;
        String n = nombre.toUpperCase();
        if (n.contains("USADO") || n.contains("CANCELADO")) {
            throw new TicketOperacionInvalidaException(
                    "No se puede modificar un ticket en estado '" + nombre + "'");
        }
    }

    private void verificarCambiableEstado(Ticket ticket) {
        String nombre = obtenerNombreEstado(ticket.getEstado_ticket_id_estado());
        if (nombre == null) return;
        String n = nombre.toUpperCase();
        if (n.contains("USADO") || n.contains("CANCELADO")) {
            throw new TicketOperacionInvalidaException(
                    "No se puede cambiar el estado de un ticket en estado '" + nombre + "'");
        }
    }

    private void verificarEliminable(Ticket ticket) {
        String nombre = obtenerNombreEstado(ticket.getEstado_ticket_id_estado());
        if (nombre == null) return;
        if (nombre.toUpperCase().contains("USADO")) {
            throw new TicketOperacionInvalidaException(
                    "No se puede eliminar un ticket en estado '" + nombre + "'");
        }
    }

    // --- Validaciones contra otros microservicios ---

    private void validarEstado(Long idEstado) {
        estadoWebClient.get()
                .uri("/api/v1/estadosTicket/{id}", idEstado)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.createException().map(e ->
                                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Estado de ticket no encontrado: " + idEstado)))
                .bodyToMono(EstadoTicketDTO.class)
                .block();
    }

    private void validarEvento(Long idEvento) {
        eventoWebClient.get()
                .uri("/eventos/{id}", idEvento)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.createException().map(e ->
                                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Evento no encontrado: " + idEvento)))
                .bodyToMono(Object.class)
                .block();
    }

    private void validarSector(Long idSector) {
        recintoWebClient.get()
                .uri("/sectores/{id}", idSector)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.createException().map(e ->
                                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Sector no encontrado: " + idSector)))
                .bodyToMono(Object.class)
                .block();
    }

    private void validarVenta(Long idVenta) {
        ventaWebClient.get()
                .uri("/ventas/{id}", idVenta)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.createException().map(e ->
                                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Venta no encontrada: " + idVenta)))
                .bodyToMono(Object.class)
                .block();
    }

    private void validarAsientoDisponible(Integer numAsiento, Long eventoId, Long sectorId) {
        if (ticketRepository.existsByNum_asientoAndEvento_id_eventoAndSector_id_sector(
                numAsiento, eventoId, sectorId)) {
            throw new TicketOperacionInvalidaException(
                    "El asiento " + numAsiento + " ya está ocupado en este evento y sector");
        }
    }

    // --- Operaciones CRUD ---

    @Transactional
    public TicketResponseDTO crearTicket(TicketRequestDTO dto) {
        log.info("Creando ticket para evento: {}", dto.getEvento_id_evento());
        validarEvento(dto.getEvento_id_evento());
        validarSector(dto.getSector_id_sector());
        if (dto.getVenta_id_venta() != null) {
            validarVenta(dto.getVenta_id_venta());
        }
        validarAsientoDisponible(dto.getNum_asiento(), dto.getEvento_id_evento(), dto.getSector_id_sector());
        Long estadoInicial = obtenerEstadoInicial();
        Ticket ticket = new Ticket(
                null,
                QRGenerator.generarCodigoQR(),
                dto.getNum_asiento(),
                dto.getNombre_titular(),
                dto.getRun_titular(),
                LocalDate.now(),
                dto.getVenta_id_venta(),
                estadoInicial,
                dto.getEvento_id_evento(),
                dto.getSector_id_sector()
        );
        // Reintento por colisión de cod_qr (probabilidad extremadamente baja)
        try {
            return toResponseDTO(ticketRepository.save(ticket));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            log.warn("Colisión de código QR al crear ticket, reintentando...");
            ticket.setCod_qr(QRGenerator.generarCodigoQR());
            return toResponseDTO(ticketRepository.save(ticket));
        }
    }

    @Transactional
    public TicketResponseDTO actualizarTicket(Long id, TicketUpdateDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket no encontrado: " + id));
        verificarModificable(ticket);
        ticket.setNombre_titular(dto.getNombre_titular());
        ticket.setRun_titular(dto.getRun_titular());
        return toResponseDTO(ticketRepository.save(ticket));
    }

    @Transactional
    public TicketResponseDTO cambiarEstado(Long id, Long idEstado) {
        validarEstado(idEstado);
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket no encontrado: " + id));
        verificarCambiableEstado(ticket);
        ticket.setEstado_ticket_id_estado(idEstado);
        return toResponseDTO(ticketRepository.save(ticket));
    }

    @Transactional
    public void eliminarTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket no encontrado: " + id));
        verificarEliminable(ticket);
        ticketRepository.deleteById(id);
    }

    @Transactional
    public ValidarTicketResponseDTO validarTicket(String codQR) {
        Ticket ticket = ticketRepository.findByCod_qr(codQR)
                .orElseThrow(() -> new TicketNotFoundException("Ticket no encontrado para QR: " + codQR));
        String nombreEstado = obtenerNombreEstado(ticket.getEstado_ticket_id_estado());
        if (nombreEstado == null || !nombreEstado.toUpperCase().contains("VALID")) {
            return new ValidarTicketResponseDTO(false,
                    "Ticket no válido. Estado actual: " + nombreEstado,
                    toResponseDTO(ticket));
        }
        Long idUsado = obtenerIdPorNombre("USADO");
        if (idUsado == null) {
            throw new TicketOperacionInvalidaException(
                    "No se puede marcar el ticket como usado: estado 'USADO' no disponible en ms-estado");
        }
        ticket.setEstado_ticket_id_estado(idUsado);
        return new ValidarTicketResponseDTO(true,
                "Ticket validado correctamente",
                toResponseDTO(ticketRepository.save(ticket)));
    }

    @Transactional
    public TicketResponseDTO transferirTicket(Long id, TransferirTicketDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket no encontrado: " + id));
        String nombreEstado = obtenerNombreEstado(ticket.getEstado_ticket_id_estado());
        if (nombreEstado == null || !nombreEstado.toUpperCase().contains("VALID")) {
            throw new TicketOperacionInvalidaException(
                    "Solo se pueden transferir tickets en estado 'VÁLIDO'. Estado actual: " + nombreEstado);
        }
        Long idTransferido = obtenerIdPorNombre("TRANSFERIDO");
        if (idTransferido == null) {
            throw new TicketOperacionInvalidaException(
                    "No se puede transferir el ticket: estado 'TRANSFERIDO' no disponible en ms-estado");
        }
        ticket.setNombre_titular(dto.getNombre_titular());
        ticket.setRun_titular(dto.getRun_titular());
        ticket.setEstado_ticket_id_estado(idTransferido);
        return toResponseDTO(ticketRepository.save(ticket));
    }

    // --- Consultas ---

    @Transactional(readOnly = true)
    public TicketResponseDTO obtenerTicket(Long id) {
        return ticketRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new TicketNotFoundException("Ticket no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public Optional<TicketResponseDTO> obtenerPorQR(String codQR) {
        return ticketRepository.findByCod_qr(codQR).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> listarTickets() {
        return ticketRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> listarPorEvento(Long eventoId) {
        return ticketRepository.findByEvento_id_evento(eventoId).stream()
                .map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> listarPorVenta(Long ventaId) {
        return ticketRepository.findByVenta_id_venta(ventaId).stream()
                .map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> listarPorSector(Long sectorId) {
        return ticketRepository.findBySector_id_sector(sectorId).stream()
                .map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDTO> listarPorRun(String run) {
        return ticketRepository.findByRun_titular(run).stream()
                .map(this::toResponseDTO).collect(Collectors.toList());
    }
}
