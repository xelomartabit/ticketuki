package com.ticketuki.eventoservice.service;

import com.ticketuki.eventoservice.dto.EventoDTO;
import com.ticketuki.eventoservice.model.Evento;
import com.ticketuki.eventoservice.exception.EventoNotFoundException;
import com.ticketuki.eventoservice.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventoService {
    private final EventoRepository eventoRepository;

    private EventoDTO toResponseDTO(Evento evento) {
        return new EventoDTO(evento.getId_evento(), evento.getNombre_evento(),
            evento.getAforo_evento(), evento.getFecha_evento(), evento.getDescripcion(),
            evento.getEstado_evento_id_estado(), evento.getRecinto_id_recinto());
    }

    public List<EventoDTO> obtenerTodos() {
        log.info("Obteniendo todos los eventos");
        return eventoRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public Optional<EventoDTO> obtenerPorId(Long id) {
        log.info("Obteniendo evento por id: {}", id);
        return eventoRepository.findById(id).map(this::toResponseDTO);
    }

    public EventoDTO crear(EventoDTO eventoDTO) {
        log.info("Creando evento: {}", eventoDTO.getNombre_evento());
        Evento evento = new Evento(null, eventoDTO.getNombre_evento(), eventoDTO.getAforo_evento(),
            eventoDTO.getFecha_evento(), eventoDTO.getDescripcion(),
            eventoDTO.getEstado_evento_id_estado(), eventoDTO.getRecinto_id_recinto());
        return toResponseDTO(eventoRepository.save(evento));
    }

    public EventoDTO actualizar(Long id, EventoDTO eventoDTO) {
        log.info("Actualizando evento: {}", id);
        Evento evento = eventoRepository.findById(id)
            .orElseThrow(() -> new EventoNotFoundException("Evento no encontrado: " + id));
        evento.setNombre_evento(eventoDTO.getNombre_evento());
        evento.setAforo_evento(eventoDTO.getAforo_evento());
        evento.setFecha_evento(eventoDTO.getFecha_evento());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setEstado_evento_id_estado(eventoDTO.getEstado_evento_id_estado());
        evento.setRecinto_id_recinto(eventoDTO.getRecinto_id_recinto());
        return toResponseDTO(eventoRepository.save(evento));
    }

    public void eliminar(Long id) {
        log.info("Eliminando evento: {}", id);
        eventoRepository.deleteById(id);
    }

    public List<EventoDTO> obtenerPorFecha(LocalDate fecha) {
        log.info("Buscando eventos por fecha: {}", fecha);
        return eventoRepository.findByFecha(fecha).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<EventoDTO> obtenerPorRecinto(Long recintoId) {
        log.info("Buscando eventos por recinto: {}", recintoId);
        return eventoRepository.findByRecinto(recintoId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public Optional<EventoDTO> cambiarEstado(Long id, Long idEstado) {
        log.info("Cambiando estado del evento {} a {}", id, idEstado);
        return eventoRepository.findById(id).map(evento -> {
            evento.setEstado_evento_id_estado(idEstado);
            return toResponseDTO(eventoRepository.save(evento));
        });
    }
}
