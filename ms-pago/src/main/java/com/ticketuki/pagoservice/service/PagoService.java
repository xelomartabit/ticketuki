package com.ticketuki.pagoservice.service;

import com.ticketuki.pagoservice.dto.EstadoResumenDTO;
import com.ticketuki.pagoservice.dto.PagoRequestDTO;
import com.ticketuki.pagoservice.dto.PagoResponseDTO;
import com.ticketuki.pagoservice.dto.TicketResumenDTO;
import com.ticketuki.pagoservice.exception.PagoNotFoundException;
import com.ticketuki.pagoservice.model.EstadoPago;
import com.ticketuki.pagoservice.model.Pago;
import com.ticketuki.pagoservice.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient ventaWebClient;
    private final WebClient usuarioWebClient;
    private final WebClient ticketWebClient;
    private final WebClient estadoWebClient;

    public PagoService(PagoRepository pagoRepository,
                       @Qualifier("ventaWebClient") WebClient ventaWebClient,
                       @Qualifier("usuarioWebClient") WebClient usuarioWebClient,
                       @Qualifier("ticketWebClient") WebClient ticketWebClient,
                       @Qualifier("estadoWebClient") WebClient estadoWebClient) {
        this.pagoRepository = pagoRepository;
        this.ventaWebClient = ventaWebClient;
        this.usuarioWebClient = usuarioWebClient;
        this.ticketWebClient = ticketWebClient;
        this.estadoWebClient = estadoWebClient;
    }

    // Busca el ID de un estado de ticket por fragmento de nombre (ej. "CANCELADO")
    private Long obtenerIdEstadoTicket(String fragmento) {
        try {
            List<EstadoResumenDTO> estados = estadoWebClient.get()
                    .uri("/api/v1/estadosTicket")
                    .retrieve()
                    .bodyToFlux(EstadoResumenDTO.class)
                    .collectList()
                    .block();
            if (estados == null) return null;
            return estados.stream()
                    .filter(e -> e.getNombre() != null && e.getNombre().toUpperCase().contains(fragmento.toUpperCase()))
                    .map(EstadoResumenDTO::getId)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            log.warn("No se pudo obtener estado de ticket '{}': {}", fragmento, e.getMessage());
            return null;
        }
    }

    // Busca el ID de un estado de venta por fragmento de nombre (ej. "ANULADA")
    private Long obtenerIdEstadoVenta(String fragmento) {
        try {
            List<EstadoResumenDTO> estados = estadoWebClient.get()
                    .uri("/api/v1/estadosVenta")
                    .retrieve()
                    .bodyToFlux(EstadoResumenDTO.class)
                    .collectList()
                    .block();
            if (estados == null) return null;
            return estados.stream()
                    .filter(e -> e.getNombre() != null && e.getNombre().toUpperCase().contains(fragmento.toUpperCase()))
                    .map(EstadoResumenDTO::getId)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            log.warn("No se pudo obtener estado de venta '{}': {}", fragmento, e.getMessage());
            return null;
        }
    }

    private PagoResponseDTO toResponseDTO(Pago p) {
        return new PagoResponseDTO(p.getId_pago(), p.getMonto(), p.getMedio_pago(),
                p.getCod_autorizacion(), p.getTimestamp(), p.getEstado(),
                p.getVenta_id(), p.getUsuario_id());
    }

    private void validarVentaExiste(Long ventaId) {
        try {
            ventaWebClient.get()
                    .uri("/api/v1/ventas/{id}", ventaId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> Mono.error(new IllegalArgumentException("Venta no encontrada con id: " + ventaId)))
                    .bodyToMono(Void.class)
                    .block();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.warn("No se pudo validar venta {}: {}", ventaId, e.getMessage());
        }
    }

    private void validarUsuarioExiste(Long usuarioId) {
        try {
            usuarioWebClient.get()
                    .uri("/api/v1/usuarios/{id}", usuarioId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> Mono.error(new IllegalArgumentException("Usuario no encontrado con id: " + usuarioId)))
                    .bodyToMono(Void.class)
                    .block();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.warn("No se pudo validar usuario {}: {}", usuarioId, e.getMessage());
        }
    }

    private void cancelarTicketsDeLaVenta(Long ventaId) {
        Long idEstadoTicketCancelado = obtenerIdEstadoTicket("CANCELADO");
        if (idEstadoTicketCancelado == null) {
            log.warn("No se encontró estado 'CANCELADO' para tickets, se omite la cancelación");
            return;
        }
        try {
            List<TicketResumenDTO> tickets = ticketWebClient.get()
                    .uri("/api/v1/tickets/venta/{ventaId}", ventaId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<TicketResumenDTO>>() {})
                    .block();

            if (tickets == null || tickets.isEmpty()) {
                log.warn("No se encontraron tickets para la venta {}", ventaId);
                return;
            }

            for (TicketResumenDTO ticket : tickets) {
                try {
                    ticketWebClient.put()
                            .uri("/api/v1/tickets/{id}/estado/{idEstado}", ticket.getId_ticket(), idEstadoTicketCancelado)
                            .retrieve()
                            .bodyToMono(Void.class)
                            .block();
                    log.info("Ticket {} cancelado", ticket.getId_ticket());
                } catch (Exception e) {
                    log.warn("No se pudo cancelar ticket {}: {}", ticket.getId_ticket(), e.getMessage());
                }
            }
        } catch (Exception e) {
            log.warn("No se pudo obtener tickets de la venta {}: {}", ventaId, e.getMessage());
        }
    }

    private void anularVenta(Long ventaId) {
        Long idEstadoVentaAnulada = obtenerIdEstadoVenta("ANULADA");
        if (idEstadoVentaAnulada == null) {
            log.warn("No se encontró estado 'ANULADA' para ventas, se omite la anulación");
            return;
        }
        try {
            ventaWebClient.put()
                    .uri("/api/v1/ventas/{id}/estado/{idEstado}", ventaId, idEstadoVentaAnulada)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            log.info("Venta {} anulada", ventaId);
        } catch (Exception e) {
            log.warn("No se pudo anular la venta {}: {}", ventaId, e.getMessage());
        }
    }

    @Transactional
    public PagoResponseDTO procesarPago(PagoRequestDTO dto) {
        log.info("Procesando pago para venta: {}", dto.getVenta_id());
        validarVentaExiste(dto.getVenta_id());
        validarUsuarioExiste(dto.getUsuario_id());
        Pago p = new Pago(null, dto.getMonto(), dto.getMedio_pago(), dto.getCod_autorizacion(),
                LocalDateTime.now(), EstadoPago.PENDIENTE, dto.getVenta_id(), dto.getUsuario_id());
        return toResponseDTO(pagoRepository.save(p));
    }

    @Transactional(readOnly = true)
    public PagoResponseDTO obtenerPago(Long id) {
        return pagoRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPorVenta(Long idVenta) {
        return pagoRepository.findByVenta_id(idVenta).stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public PagoResponseDTO completarPago(Long id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado con id: " + id));
        if (p.getEstado() != EstadoPago.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo se puede completar un pago en estado PENDIENTE. Estado actual: " + p.getEstado());
        }
        p.setEstado(EstadoPago.COMPLETADO);
        return toResponseDTO(pagoRepository.save(p));
    }

    // Fase 1: actualiza solo el estado del pago en BD (se confirma antes de las llamadas HTTP)
    @Transactional
    protected Pago marcarPagoReembolsado(Long id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado con id: " + id));
        if (p.getEstado() != EstadoPago.COMPLETADO) {
            throw new IllegalStateException(
                    "Solo se puede reembolsar un pago en estado COMPLETADO. Estado actual: " + p.getEstado());
        }
        p.setEstado(EstadoPago.REEMBOLSADO);
        return pagoRepository.save(p);
    }

    // Fase 2: coordina la cancelación de tickets y anulación de venta FUERA de la transacción de BD
    public PagoResponseDTO procesarReembolso(Long id) {
        Pago saved = marcarPagoReembolsado(id);
        log.info("Reembolso aprobado para pago {}. Coordinando cancelación de tickets y venta {}",
                id, saved.getVenta_id());
        cancelarTicketsDeLaVenta(saved.getVenta_id());
        anularVenta(saved.getVenta_id());
        return toResponseDTO(saved);
    }

    @Transactional
    public PagoResponseDTO cancelarPago(Long id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado con id: " + id));
        if (p.getEstado() != EstadoPago.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo se puede cancelar un pago en estado PENDIENTE. Estado actual: " + p.getEstado());
        }
        p.setEstado(EstadoPago.CANCELADO);
        return toResponseDTO(pagoRepository.save(p));
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPorUsuario(Long idUsuario) {
        return pagoRepository.findByUsuario_id(idUsuario).stream().map(this::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPorEstado(EstadoPago estado) {
        return pagoRepository.findByEstado(estado).stream().map(this::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPorPeriodo(LocalDate desde, LocalDate hasta) {
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha 'desde' no puede ser posterior a 'hasta'");
        }
        return pagoRepository.findByTimestampBetween(
                desde.atStartOfDay(), hasta.atTime(23, 59, 59)
        ).stream().map(this::toResponseDTO).toList();
    }
}
