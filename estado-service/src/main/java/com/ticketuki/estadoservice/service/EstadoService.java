package com.ticketuki.estadoservice.service;

import com.ticketuki.estadoservice.dto.EstadoDTO;
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
import java.util.Optional;
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
    public List<EstadoDTO> listarEstadosEvento() {
        return estadoEventoRepository.findAll().stream()
                .map(e -> new EstadoDTO(e.getId_estado_evento(), e.getNombre_estado_evento()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<EstadoDTO> obtenerEstadoEvento(Long id) {
        return estadoEventoRepository.findById(id)
                .map(e -> new EstadoDTO(e.getId_estado_evento(), e.getNombre_estado_evento()));
    }

    @Transactional
    public EstadoDTO crearEstadoEvento(EstadoDTO dto) {
        Estado e = estadoEventoRepository.save(new Estado(null, dto.getNombre()));
        return new EstadoDTO(e.getId_estado_evento(), e.getNombre_estado_evento());
    }

    // --- EstadoVenta ---
    @Transactional(readOnly = true)
    public List<EstadoDTO> listarEstadosVenta() {
        return estadoVentaRepository.findAll().stream()
                .map(e -> new EstadoDTO(e.getId_estado_venta(), e.getNombre_estado_venta()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<EstadoDTO> obtenerEstadoVenta(Long id) {
        return estadoVentaRepository.findById(id)
                .map(e -> new EstadoDTO(e.getId_estado_venta(), e.getNombre_estado_venta()));
    }

    @Transactional
    public EstadoDTO crearEstadoVenta(EstadoDTO dto) {
        EstadoVenta e = estadoVentaRepository.save(new EstadoVenta(null, dto.getNombre()));
        return new EstadoDTO(e.getId_estado_venta(), e.getNombre_estado_venta());
    }

    // --- EstadoTicket ---
    @Transactional(readOnly = true)
    public List<EstadoDTO> listarEstadosTicket() {
        return estadoTicketRepository.findAll().stream()
                .map(e -> new EstadoDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<EstadoDTO> obtenerEstadoTicket(Long id) {
        return estadoTicketRepository.findById(id)
                .map(e -> new EstadoDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket()));
    }

    @Transactional
    public EstadoDTO crearEstadoTicket(EstadoDTO dto) {
        EstadoTicket e = estadoTicketRepository.save(new EstadoTicket(null, dto.getNombre()));
        return new EstadoDTO(e.getId_estado_ticket(), e.getNombre_estado_ticket());
    }
}
