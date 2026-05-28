package com.ticketuki.estadoservice.controller;

import com.ticketuki.estadoservice.dto.EstadoRequestDTO;
import com.ticketuki.estadoservice.dto.EstadoResponseDTO;
import com.ticketuki.estadoservice.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class EstadoController {

    private final EstadoService estadoService;

    // --- EstadoEvento ---

    @GetMapping("/estadosEvento")
    public ResponseEntity<List<EstadoResponseDTO>> listarEstadosEvento() {
        return ResponseEntity.ok(estadoService.listarEstadosEvento());
    }

    @GetMapping("/estadosEvento/{id}")
    public ResponseEntity<EstadoResponseDTO> obtenerEstadoEvento(@PathVariable Long id) {
        return ResponseEntity.ok(estadoService.obtenerEstadoEvento(id));
    }

    @PostMapping("/estadosEvento")
    public ResponseEntity<EstadoResponseDTO> crearEstadoEvento(@Valid @RequestBody EstadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.crearEstadoEvento(dto));
    }

    @PutMapping("/estadosEvento/{id}")
    public ResponseEntity<EstadoResponseDTO> actualizarEstadoEvento(@PathVariable Long id, @Valid @RequestBody EstadoRequestDTO dto) {
        return ResponseEntity.ok(estadoService.actualizarEstadoEvento(id, dto));
    }

    @DeleteMapping("/estadosEvento/{id}")
    public ResponseEntity<Void> eliminarEstadoEvento(@PathVariable Long id) {
        estadoService.eliminarEstadoEvento(id);
        return ResponseEntity.noContent().build();
    }

    // --- EstadoVenta ---

    @GetMapping("/estadosVenta")
    public ResponseEntity<List<EstadoResponseDTO>> listarEstadosVenta() {
        return ResponseEntity.ok(estadoService.listarEstadosVenta());
    }

    @GetMapping("/estadosVenta/{id}")
    public ResponseEntity<EstadoResponseDTO> obtenerEstadoVenta(@PathVariable Long id) {
        return ResponseEntity.ok(estadoService.obtenerEstadoVenta(id));
    }

    @PostMapping("/estadosVenta")
    public ResponseEntity<EstadoResponseDTO> crearEstadoVenta(@Valid @RequestBody EstadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.crearEstadoVenta(dto));
    }

    @PutMapping("/estadosVenta/{id}")
    public ResponseEntity<EstadoResponseDTO> actualizarEstadoVenta(@PathVariable Long id, @Valid @RequestBody EstadoRequestDTO dto) {
        return ResponseEntity.ok(estadoService.actualizarEstadoVenta(id, dto));
    }

    @DeleteMapping("/estadosVenta/{id}")
    public ResponseEntity<Void> eliminarEstadoVenta(@PathVariable Long id) {
        estadoService.eliminarEstadoVenta(id);
        return ResponseEntity.noContent().build();
    }

    // --- EstadoTicket ---

    @GetMapping("/estadosTicket")
    public ResponseEntity<List<EstadoResponseDTO>> listarEstadosTicket() {
        return ResponseEntity.ok(estadoService.listarEstadosTicket());
    }

    @GetMapping("/estadosTicket/{id}")
    public ResponseEntity<EstadoResponseDTO> obtenerEstadoTicket(@PathVariable Long id) {
        return ResponseEntity.ok(estadoService.obtenerEstadoTicket(id));
    }

    @PostMapping("/estadosTicket")
    public ResponseEntity<EstadoResponseDTO> crearEstadoTicket(@Valid @RequestBody EstadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoService.crearEstadoTicket(dto));
    }

    @PutMapping("/estadosTicket/{id}")
    public ResponseEntity<EstadoResponseDTO> actualizarEstadoTicket(@PathVariable Long id, @Valid @RequestBody EstadoRequestDTO dto) {
        return ResponseEntity.ok(estadoService.actualizarEstadoTicket(id, dto));
    }

    @DeleteMapping("/estadosTicket/{id}")
    public ResponseEntity<Void> eliminarEstadoTicket(@PathVariable Long id) {
        estadoService.eliminarEstadoTicket(id);
        return ResponseEntity.noContent().build();
    }

    // --- Resumen general ---

    @GetMapping("/estados")
    public ResponseEntity<Map<String, List<EstadoResponseDTO>>> listarTodos() {
        return ResponseEntity.ok(Map.of(
                "evento", estadoService.listarEstadosEvento(),
                "venta", estadoService.listarEstadosVenta(),
                "ticket", estadoService.listarEstadosTicket()
        ));
    }
}
