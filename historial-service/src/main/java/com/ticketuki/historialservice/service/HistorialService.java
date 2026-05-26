package com.ticketuki.historialservice.service;

import com.ticketuki.historialservice.dto.HistorialDTO;
import com.ticketuki.historialservice.model.Historial;
import com.ticketuki.historialservice.repository.HistorialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistorialService {

    private final HistorialRepository historialRepository;

    private HistorialDTO mapToDTO(Historial h) {
        return new HistorialDTO(h.getId_historial(), h.getEntidad(), h.getId_entidad(), h.getAccion(),
                h.getUsuario_id(), h.getTimestamp(), h.getCambios_anteriores(), h.getCambios_nuevos());
    }

    @Transactional
    public HistorialDTO registrar(HistorialDTO dto) {
        log.info("Registrando acción: {} en entidad: {}", dto.getAccion(), dto.getEntidad());
        Historial h = new Historial(null, dto.getEntidad(), dto.getId_entidad(), dto.getAccion(),
                dto.getUsuario_id(), LocalDate.now(), dto.getCambios_anteriores(), dto.getCambios_nuevos());
        return mapToDTO(historialRepository.save(h));
    }

    @Transactional(readOnly = true)
    public Optional<HistorialDTO> obtenerHistorial(Long id) {
        return historialRepository.findById(id).map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public List<HistorialDTO> listarHistorial() {
        return historialRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HistorialDTO> obtenerPorEntidad(String entidad, Integer idEntidad) {
        return historialRepository.findByEntidadAndId_entidad(entidad, idEntidad).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HistorialDTO> obtenerPorUsuario(Integer usuarioId) {
        return historialRepository.findByUsuario_id(usuarioId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HistorialDTO> obtenerPorPeriodo(LocalDate inicio, LocalDate fin) {
        return historialRepository.findByTimestampBetween(inicio, fin).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
