package com.ticketuki.estadoservice.controller;

import com.ticketuki.estadoservice.dto.EstadoDTO;
import com.ticketuki.estadoservice.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EstadoController {

    private final EstadoService estadoService;

    // EstadoEvento
    @GetMapping("/estadosEvento")
    public ResponseEntity<List<EstadoDTO>> listarEstadosEvento() {
        return ResponseEntity.ok(estadoService.listarEstadosEvento());
    }

    @GetMapping("/estadosEvento/{id}")
    public ResponseEntity<EstadoDTO> obtenerEstadoEvento(@PathVariable Long id) {
        return estadoService.obtenerEstadoEvento(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/estadosEvento")
    public ResponseEntity<EstadoDTO> crearEstadoEvento(@Valid @RequestBody EstadoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.crearEstadoEvento(dto));
    }

    // EstadoVenta
    @GetMapping("/estadosVenta")
    public ResponseEntity<List<EstadoDTO>> listarEstadosVenta() {
        return ResponseEntity.ok(estadoService.listarEstadosVenta());
    }

    @GetMapping("/estadosVenta/{id}")
    public ResponseEntity<EstadoDTO> obtenerEstadoVenta(@PathVariable Long id) {
        return estadoService.obtenerEstadoVenta(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/estadosVenta")
    public ResponseEntity<EstadoDTO> crearEstadoVenta(@Valid @RequestBody EstadoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.crearEstadoVenta(dto));
    }

    // EstadoTicket
    @GetMapping("/estadosTicket")
    public ResponseEntity<List<EstadoDTO>> listarEstadosTicket() {
        return ResponseEntity.ok(estadoService.listarEstadosTicket());
    }

    @GetMapping("/estadosTicket/{id}")
    public ResponseEntity<EstadoDTO> obtenerEstadoTicket(@PathVariable Long id) {
        return estadoService.obtenerEstadoTicket(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/estadosTicket")
    public ResponseEntity<EstadoDTO> crearEstadoTicket(@Valid @RequestBody EstadoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.crearEstadoTicket(dto));
    }

    // Todos los estados (resumen)
    @GetMapping("/estados")
    public ResponseEntity<java.util.Map<String, List<EstadoDTO>>> listarTodos() {
        return ResponseEntity.ok(java.util.Map.of(
                "evento", estadoService.listarEstadosEvento(),
                "venta", estadoService.listarEstadosVenta(),
                "ticket", estadoService.listarEstadosTicket()
        ));
    }
}
