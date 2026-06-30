package com.ticketuki.recintoservice.controller;

import com.ticketuki.recintoservice.dto.RecintoDTO;
import com.ticketuki.recintoservice.service.RecintoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/recintos")
@RequiredArgsConstructor
public class RecintoController {

    private final RecintoService recintoService;

    @GetMapping
    public ResponseEntity<List<RecintoDTO>> listarRecintos() {
        return ResponseEntity.ok(recintoService.listarRecintos());
    }

    @PostMapping
    public ResponseEntity<RecintoDTO> crearRecinto(@Valid @RequestBody RecintoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recintoService.crearRecinto(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecintoDTO> obtenerRecinto(@PathVariable Long id) {
        return ResponseEntity.ok(recintoService.obtenerRecinto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecintoDTO> actualizarRecinto(@PathVariable Long id, @Valid @RequestBody RecintoDTO dto) {
        return ResponseEntity.ok(recintoService.actualizarRecinto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecinto(@PathVariable Long id) {
        recintoService.eliminarRecinto(id);
        return ResponseEntity.noContent().build();
    }
}
