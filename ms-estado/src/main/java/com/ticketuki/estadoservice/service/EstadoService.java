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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoEventoRepository;
    private final EstadoVentaRepository estadoVentaRepository;
    private final EstadoTicketRepository estadoTicketRepository;

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
                .orElseThrow(() -> {
                    log.warn("Estado evento no encontrado con id: {}", id);
                    return new EstadoNotFoundException("Estado evento no encontrado con id: " + id);
                });
        return new EstadoResponseDTO(e.getId_estado_evento(), e.getNombre_estado_evento());
    }

    @Transactional
    public EstadoResponseDTO crearEstadoEvento(EstadoRequestDTO dto) {
        String nombre = dto.getNombre().trim().toUpperCase();
        log.info("Creando estado evento con nombre: {}", nombre);
        if (estadoEventoRepository.findByNombre(nombre).isPresent()) {
            log.warn("Estado evento duplicado: {}", nombre);
            throw new EstadoDuplicadoException("Ya existe un estado evento con el nombre: " + nombre);
        }
        Estado e = estadoEventoRepository.save(new Estado(null, nombre));
        log.info("Estado evento creado exitosamente con ID: {}", e.getId_estado_evento());
        return new EstadoResponseDTO(e.getId_estado_evento(), e.getNombre_estado_evento());
    }

    @Transactional
    public EstadoResponseDTO actualizarEstadoEvento(Long id, EstadoRequestDTO dto) {
        String nombre = dto.getNombre().trim().toUpperCase();
        log.info("Actualizando estado evento con id: {}", id);
        Estado e = estadoEventoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Estado evento no encontrado para actualizar: {}", id);
                    return new EstadoNotFoundException("Estado evento no encontrado con id: " + id);
                });
        estadoEventoRepository.findByNombre(nombre)
                .filter(existing -> !existing.getId_estado_evento().equals(id))
                .ifPresent(existing -> { throw new EstadoDuplicadoException("Ya existe un estado evento con el nombre: " + nombre); });
        e.setNombre_estado_evento(nombre);
        log.info("Estado evento actualizado exitosamente: {}", id);
        return new EstadoResponseDTO(e.getId_estado_evento(), e.getNombre_estado_evento());
    }

    @Transactional
    public void eliminarEstadoEvento(Long id) {
        log.info("Eliminando estado evento con id: {}", id);
        if (!estadoEventoRepository.existsById(id)) {
            log.warn("Estado evento no encontrado para eliminar: {}", id);
            throw new EstadoNotFoundException("Estado evento no encontrado con id: " + id);
        }
        estadoEventoRepository.deleteById(id);
        log.info("Estado evento eliminado exitosamente: {}", id);
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
                .orElseThrow(() -> {
                    log.warn("Estado venta no encontrado con id: {}", id);
                    return new EstadoNotFoundException("Estado venta no encontrado con id: " + id);
                });
        return new EstadoResponseDTO(e.getId_estado_venta(), e.getNombre_estado_venta());
    }

    @Transactional
    public EstadoResponseDTO crearEstadoVenta(EstadoRequestDTO dto) {
        String nombre = dto.getNombre().trim().toUpperCase();
        log.info("Creando estado venta con nombre: {}", nombre);
        if (estadoVentaRepository.findByNombre(nombre).isPresent()) {
            log.warn("Estado venta duplicado: {}", nombre);
            throw new EstadoDuplicadoException("Ya existe un estado venta con el nombre: " + nombre);
        }
        EstadoVenta e = estadoVentaRepository.save(new EstadoVenta(null, nombre));
        log.info("Estado venta creado exitosamente con ID: {}", e.getId_estado_venta());
        return new EstadoResponseDTO(e.getId_estado_venta(), e.getNombre_estado_venta());
    }

    @Transactional
    public EstadoResponseDTO actualizarEstadoVenta(Long id, EstadoRequestDTO dto) {
        String nombre = dto.getNombre().trim().toUpperCase();
        log.info("Actualizando estado venta con id: {}", id);
        EstadoVenta e = estadoVentaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Estado venta no encontrado para actualizar: {}", id);
                    return new EstadoNotFoundException("Estado venta no encontrado con id: " + id);
                });
        estadoVentaRepository.findByNombre(nombre)
                .filter(existing -> !existing.getId_estado_venta().equals(id))
                .ifPresent(existing -> { throw new EstadoDuplicadoException("Ya existe un estado venta con el nombre: " + nombre); });
        e.setNombre_estado_venta(nombre);
        log.info("Estado venta actualizado exitosamente: {}", id);
        return new EstadoResponseDTO(e.getId_estado_venta(), e.getNombre_estado_venta());
    }

    @Transactional
    public void eliminarEstadoVenta(Long id) {
        log.info("Eliminando estado venta con id: {}", id);
        if (!estadoVentaRepository.existsById(id)) {
            log.warn("Estado venta no encontrado para eliminar: {}", id);
            throw new EstadoNotFoundException("Estado venta no encontrado con id: " + id);
        }
        estadoVentaRepository.deleteById(id);
        log.info("Estado venta eliminado exitosamente: {}", id);
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
                .orElseThrow(() -> {
                    log.warn("Estado ticket no encontrado con id: {}", id);
                    return new EstadoNotFoundException("Estado ticket no encontrado con id: " + id);
                });
        return new EstadoResponseDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket());
    }

    @Transactional
    public EstadoResponseDTO crearEstadoTicket(EstadoRequestDTO dto) {
        String nombre = dto.getNombre().trim().toUpperCase();
        log.info("Creando estado ticket con nombre: {}", nombre);
        if (estadoTicketRepository.findByNombre(nombre).isPresent()) {
            log.warn("Estado ticket duplicado: {}", nombre);
            throw new EstadoDuplicadoException("Ya existe un estado ticket con el nombre: " + nombre);
        }
        EstadoTicket e = estadoTicketRepository.save(new EstadoTicket(null, nombre));
        log.info("Estado ticket creado exitosamente con ID: {}", e.getId_estado_ticket());
        return new EstadoResponseDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket());
    }

    @Transactional
    public EstadoResponseDTO actualizarEstadoTicket(Long id, EstadoRequestDTO dto) {
        String nombre = dto.getNombre().trim().toUpperCase();
        log.info("Actualizando estado ticket con id: {}", id);
        EstadoTicket e = estadoTicketRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Estado ticket no encontrado para actualizar: {}", id);
                    return new EstadoNotFoundException("Estado ticket no encontrado con id: " + id);
                });
        estadoTicketRepository.findByNombre(nombre)
                .filter(existing -> !existing.getId_estado_ticket().equals(id))
                .ifPresent(existing -> { throw new EstadoDuplicadoException("Ya existe un estado ticket con el nombre: " + nombre); });
        e.setNombre_estado_ticket(nombre);
        log.info("Estado ticket actualizado exitosamente: {}", id);
        return new EstadoResponseDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket());
    }

    @Transactional
    public void eliminarEstadoTicket(Long id) {
        log.info("Eliminando estado ticket con id: {}", id);
        if (!estadoTicketRepository.existsById(id)) {
            log.warn("Estado ticket no encontrado para eliminar: {}", id);
            throw new EstadoNotFoundException("Estado ticket no encontrado con id: " + id);
        }
        estadoTicketRepository.deleteById(id);
        log.info("Estado ticket eliminado exitosamente: {}", id);
    }
}
