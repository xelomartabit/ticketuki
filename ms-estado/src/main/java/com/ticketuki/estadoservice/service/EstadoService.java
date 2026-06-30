package com.ticketuki.estadoservice.service;

import com.ticketuki.estadoservice.dto.EstadoRequestDTO;
import com.ticketuki.estadoservice.dto.EstadoResponseDTO;
import com.ticketuki.estadoservice.exception.EstadoDuplicadoException;
import com.ticketuki.estadoservice.exception.EstadoNotFoundException;
import com.ticketuki.estadoservice.model.Estado;
import com.ticketuki.estadoservice.model.EstadoTicket;
import com.ticketuki.estadoservice.model.EstadoVenta;
import com.ticketuki.estadoservice.repository.EstadoRepository;
import com.ticketuki.estadoservice.repository.EstadoTicketRepository;
import com.ticketuki.estadoservice.repository.EstadoVentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoEventoRepository;
    private final EstadoVentaRepository estadoVentaRepository;
    private final EstadoTicketRepository estadoTicketRepository;

    /**
     * Normaliza un nombre de estado: elimina tildes/diacríticos, recorta espacios
     * y convierte a mayúsculas. Así 'válido', 'Válido' y 'VÁLIDO' se tratan igual.
     */
    private String normalizar(String nombre) {
        if (nombre == null) return null;
        return Normalizer.normalize(nombre.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase();
    }

    // --- EstadoEvento ---

    @Transactional(readOnly = true)
    public List<EstadoResponseDTO> listarEstadosEvento() {
        log.info("Listando todos los estados de evento");
        return estadoEventoRepository.findAll().stream()
                .map(e -> new EstadoResponseDTO(e.getId_estado_evento(), e.getNombre_estado_evento()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadoResponseDTO obtenerEstadoEvento(Long id) {
        log.info("Buscando estado evento con id: {}", id);
        Estado e = estadoEventoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException("Estado evento no encontrado con id: " + id));
        return new EstadoResponseDTO(e.getId_estado_evento(), e.getNombre_estado_evento());
    }

    @Transactional
    public EstadoResponseDTO crearEstadoEvento(EstadoRequestDTO dto) {
        String nombre = normalizar(dto.getNombre());
        log.info("Creando estado evento con nombre: {}", nombre);
        if (estadoEventoRepository.findByNombre(nombre).isPresent()) {
            throw new EstadoDuplicadoException("Ya existe un estado evento con el nombre: " + nombre);
        }
        Estado e = estadoEventoRepository.save(new Estado(null, nombre));
        return new EstadoResponseDTO(e.getId_estado_evento(), e.getNombre_estado_evento());
    }

    @Transactional
    public EstadoResponseDTO actualizarEstadoEvento(Long id, EstadoRequestDTO dto) {
        String nombre = normalizar(dto.getNombre());
        Estado e = estadoEventoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException("Estado evento no encontrado con id: " + id));
        estadoEventoRepository.findByNombre(nombre)
                .filter(existing -> !existing.getId_estado_evento().equals(id))
                .ifPresent(existing -> { throw new EstadoDuplicadoException("Ya existe un estado evento con el nombre: " + nombre); });
        e.setNombre_estado_evento(nombre);
        return new EstadoResponseDTO(e.getId_estado_evento(), e.getNombre_estado_evento());
    }

    @Transactional
    public void eliminarEstadoEvento(Long id) {
        if (!estadoEventoRepository.existsById(id)) {
            throw new EstadoNotFoundException("Estado evento no encontrado con id: " + id);
        }
        estadoEventoRepository.deleteById(id);
    }

    // --- EstadoVenta ---

    @Transactional(readOnly = true)
    public List<EstadoResponseDTO> listarEstadosVenta() {
        log.info("Listando todos los estados de venta");
        return estadoVentaRepository.findAll().stream()
                .map(e -> new EstadoResponseDTO(e.getId_estado_venta(), e.getNombre_estado_venta()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadoResponseDTO obtenerEstadoVenta(Long id) {
        log.info("Buscando estado venta con id: {}", id);
        EstadoVenta e = estadoVentaRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException("Estado venta no encontrado con id: " + id));
        return new EstadoResponseDTO(e.getId_estado_venta(), e.getNombre_estado_venta());
    }

    @Transactional
    public EstadoResponseDTO crearEstadoVenta(EstadoRequestDTO dto) {
        String nombre = normalizar(dto.getNombre());
        log.info("Creando estado venta con nombre: {}", nombre);
        if (estadoVentaRepository.findByNombre(nombre).isPresent()) {
            throw new EstadoDuplicadoException("Ya existe un estado venta con el nombre: " + nombre);
        }
        EstadoVenta e = estadoVentaRepository.save(new EstadoVenta(null, nombre));
        return new EstadoResponseDTO(e.getId_estado_venta(), e.getNombre_estado_venta());
    }

    @Transactional
    public EstadoResponseDTO actualizarEstadoVenta(Long id, EstadoRequestDTO dto) {
        String nombre = normalizar(dto.getNombre());
        EstadoVenta e = estadoVentaRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException("Estado venta no encontrado con id: " + id));
        estadoVentaRepository.findByNombre(nombre)
                .filter(existing -> !existing.getId_estado_venta().equals(id))
                .ifPresent(existing -> { throw new EstadoDuplicadoException("Ya existe un estado venta con el nombre: " + nombre); });
        e.setNombre_estado_venta(nombre);
        return new EstadoResponseDTO(e.getId_estado_venta(), e.getNombre_estado_venta());
    }

    @Transactional
    public void eliminarEstadoVenta(Long id) {
        if (!estadoVentaRepository.existsById(id)) {
            throw new EstadoNotFoundException("Estado venta no encontrado con id: " + id);
        }
        estadoVentaRepository.deleteById(id);
    }

    // --- EstadoTicket ---

    @Transactional(readOnly = true)
    public List<EstadoResponseDTO> listarEstadosTicket() {
        log.info("Listando todos los estados de ticket");
        return estadoTicketRepository.findAll().stream()
                .map(e -> new EstadoResponseDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadoResponseDTO obtenerEstadoTicket(Long id) {
        log.info("Buscando estado ticket con id: {}", id);
        EstadoTicket e = estadoTicketRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException("Estado ticket no encontrado con id: " + id));
        return new EstadoResponseDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket());
    }

    @Transactional
    public EstadoResponseDTO crearEstadoTicket(EstadoRequestDTO dto) {
        String nombre = normalizar(dto.getNombre());
        log.info("Creando estado ticket con nombre: {}", nombre);
        if (estadoTicketRepository.findByNombre(nombre).isPresent()) {
            throw new EstadoDuplicadoException("Ya existe un estado ticket con el nombre: " + nombre);
        }
        EstadoTicket e = estadoTicketRepository.save(new EstadoTicket(null, nombre));
        return new EstadoResponseDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket());
    }

    @Transactional
    public EstadoResponseDTO actualizarEstadoTicket(Long id, EstadoRequestDTO dto) {
        String nombre = normalizar(dto.getNombre());
        log.info("Actualizando estado ticket con id: {}", id);
        EstadoTicket e = estadoTicketRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException("Estado ticket no encontrado con id: " + id));
        estadoTicketRepository.findByNombre(nombre)
                .filter(existing -> !existing.getId_estado_ticket().equals(id))
                .ifPresent(existing -> { throw new EstadoDuplicadoException("Ya existe un estado ticket con el nombre: " + nombre); });
        e.setNombre_estado_ticket(nombre);
        return new EstadoResponseDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket());
    }

    @Transactional
    public void eliminarEstadoTicket(Long id) {
        log.info("Eliminando estado ticket con id: {}", id);
        if (!estadoTicketRepository.existsById(id)) {
            throw new EstadoNotFoundException("Estado ticket no encontrado con id: " + id);
        }
        estadoTicketRepository.deleteById(id);
    }
}
