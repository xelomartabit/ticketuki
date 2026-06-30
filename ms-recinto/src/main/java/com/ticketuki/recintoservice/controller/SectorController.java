package com.ticketuki.recintoservice.controller;

import com.ticketuki.recintoservice.dto.SectorDTO;
import com.ticketuki.recintoservice.service.SectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sectores")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @PostMapping
    public ResponseEntity<SectorDTO> crearSector(@Valid @RequestBody SectorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorService.crearSector(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorDTO> obtenerSector(@PathVariable Long id) {
        return ResponseEntity.ok(sectorService.obtenerSector(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectorDTO> actualizarSector(@PathVariable Long id, @Valid @RequestBody SectorDTO dto) {
        return ResponseEntity.ok(sectorService.actualizarSector(id, dto));
    }

    @GetMapping("/recinto/{recintoId}")
    public ResponseEntity<List<SectorDTO>> listarPorRecinto(@PathVariable Long recintoId) {
        return ResponseEntity.ok(sectorService.listarPorRecinto(recintoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSector(@PathVariable Long id) {
        sectorService.eliminarSector(id);
        return ResponseEntity.noContent().build();
    }
}
