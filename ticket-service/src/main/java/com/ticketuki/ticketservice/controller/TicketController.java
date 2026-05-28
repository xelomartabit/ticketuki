package com.ticketuki.ticketservice.controller;

import com.ticketuki.ticketservice.dto.TicketDTO;
import com.ticketuki.ticketservice.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDTO>> listarTickets() {
        return ResponseEntity.ok(ticketService.listarTickets());
    }

    @PostMapping
    public ResponseEntity<TicketDTO> crearTicket(@Valid @RequestBody TicketDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.crearTicket(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> obtenerTicket(@PathVariable Long id) {
        return ticketService.obtenerTicket(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<TicketDTO>> listarPorEvento(@PathVariable Long idEvento) {
        return ResponseEntity.ok(ticketService.listarPorEvento(idEvento));
    }

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<TicketDTO>> listarPorVenta(@PathVariable Long idVenta) {
        return ResponseEntity.ok(ticketService.listarPorVenta(idVenta));
    }

    @GetMapping("/sector/{idSector}")
    public ResponseEntity<List<TicketDTO>> listarPorSector(@PathVariable Long idSector) {
        return ResponseEntity.ok(ticketService.listarPorSector(idSector));
    }

    @PutMapping("/{id}/estado/{idEstado}")
    public ResponseEntity<TicketDTO> cambiarEstado(@PathVariable Long id, @PathVariable Long idEstado) {
        return ResponseEntity.ok(ticketService.cambiarEstado(id, idEstado));
    }

    @GetMapping("/qr/{codQR}")
    public ResponseEntity<TicketDTO> obtenerPorQR(@PathVariable String codQR) {
        return ticketService.obtenerPorQR(codQR)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
