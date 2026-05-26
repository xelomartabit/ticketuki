package com.ticketuki.historialservice.controller;

import com.ticketuki.historialservice.dto.HistorialDTO;
import com.ticketuki.historialservice.service.HistorialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/historial")
@RequiredArgsConstructor
public class HistorialController {

    private final HistorialService historialService;

    @PostMapping
    public ResponseEntity<HistorialDTO> registrar(@Valid @RequestBody HistorialDTO dto) {
        return ResponseEntity.status(201).body(historialService.registrar(dto));
    }

    @GetMapping("/{idHistorial}")
    public ResponseEntity<HistorialDTO> obtenerHistorial(@PathVariable Long idHistorial) {
        return historialService.obtenerHistorial(idHistorial)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<HistorialDTO>> listarHistorial() {
        return ResponseEntity.ok(historialService.listarHistorial());
    }

    @GetMapping("/entidad/{entidad}/{idEntidad}")
    public ResponseEntity<List<HistorialDTO>> obtenerPorEntidad(
            @PathVariable String entidad, @PathVariable Integer idEntidad) {
        return ResponseEntity.ok(historialService.obtenerPorEntidad(entidad, idEntidad));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<HistorialDTO>> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(historialService.obtenerPorUsuario(idUsuario));
    }

    @GetMapping("/fecha/{inicio}/{fin}")
    public ResponseEntity<List<HistorialDTO>> obtenerPorPeriodo(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(historialService.obtenerPorPeriodo(inicio, fin));
    }
}
