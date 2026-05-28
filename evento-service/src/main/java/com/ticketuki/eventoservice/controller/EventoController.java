package com.ticketuki.eventoservice.controller;

import com.ticketuki.eventoservice.dto.EventoDTO;
import com.ticketuki.eventoservice.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
@Slf4j
public class EventoController {
    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoDTO>> obtenerTodos() {
        return ResponseEntity.ok(eventoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtenerPorId(@PathVariable Long id) {
        return eventoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventoDTO> crear(@Valid @RequestBody EventoDTO eventoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.crear(eventoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EventoDTO eventoDTO) {
        if (eventoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventoService.actualizar(id, eventoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (eventoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<EventoDTO>> obtenerPorFecha(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(eventoService.obtenerPorFecha(fecha));
    }

    @GetMapping("/recinto/{recintoId}")
    public ResponseEntity<List<EventoDTO>> obtenerPorRecinto(@PathVariable Long recintoId) {
        return ResponseEntity.ok(eventoService.obtenerPorRecinto(recintoId));
    }

    @PutMapping("/{id}/estado/{idEstado}")
    public ResponseEntity<EventoDTO> cambiarEstado(@PathVariable Long id, @PathVariable Long idEstado) {
        return eventoService.cambiarEstado(id, idEstado)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
