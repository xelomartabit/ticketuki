package com.ticketuki.ticketservice.service;

import com.ticketuki.ticketservice.dto.TicketDTO;
import com.ticketuki.ticketservice.model.Ticket;
import com.ticketuki.ticketservice.exception.TicketNotFoundException;
import com.ticketuki.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private TicketDTO toResponseDTO(Ticket ticket) {
        return new TicketDTO(ticket.getId_ticket(), ticket.getCod_qr(), ticket.getNum_asiento(),
                ticket.getNombre_titular(), ticket.getRun_titular(), ticket.getFecha_emision(),
                ticket.getVenta_id_venta(), ticket.getEstado_ticket_id_estado(),
                ticket.getEvento_id_evento(), ticket.getSector_id_sector());
    }

    private String generarCodQR() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    @Transactional
    public TicketDTO crearTicket(TicketDTO dto) {
        log.info("Creando ticket para evento: {}", dto.getEvento_id_evento());
        Ticket ticket = new Ticket(null, generarCodQR(), dto.getNum_asiento(),
                dto.getNombre_titular(), dto.getRun_titular(), LocalDate.now(),
                dto.getVenta_id_venta(), dto.getEstado_ticket_id_estado(),
                dto.getEvento_id_evento(), dto.getSector_id_sector());
        return toResponseDTO(ticketRepository.save(ticket));
    }

    @Transactional
    public TicketDTO cambiarEstado(Long id, Long idEstado) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket no encontrado: " + id));
        ticket.setEstado_ticket_id_estado(idEstado);
        return toResponseDTO(ticketRepository.save(ticket));
    }

    @Transactional(readOnly = true)
    public Optional<TicketDTO> obtenerTicket(Long id) {
        return ticketRepository.findById(id).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Optional<TicketDTO> obtenerPorQR(String codQR) {
        return ticketRepository.findByCod_qr(codQR).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> listarTickets() {
        return ticketRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> listarPorEvento(Long eventoId) {
        return ticketRepository.findByEvento_id_evento(eventoId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> listarPorVenta(Long ventaId) {
        return ticketRepository.findByVenta_id_venta(ventaId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> listarPorSector(Long sectorId) {
        return ticketRepository.findBySector_id_sector(sectorId).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
