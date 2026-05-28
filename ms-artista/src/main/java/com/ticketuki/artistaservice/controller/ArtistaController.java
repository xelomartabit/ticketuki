package com.ticketuki.artistaservice.controller;

import com.ticketuki.artistaservice.dto.ArtistaRequestDTO;
import com.ticketuki.artistaservice.dto.ArtistaResponseDTO;
import com.ticketuki.artistaservice.service.ArtistaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/artistas")
@RequiredArgsConstructor
public class ArtistaController {

    private final ArtistaService artistaService;

    @GetMapping
    public ResponseEntity<List<ArtistaResponseDTO>> listarArtistas() {
        return ResponseEntity.ok(artistaService.listarArtistas());
    }

    @PostMapping
    public ResponseEntity<ArtistaResponseDTO> crearArtista(@Valid @RequestBody ArtistaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artistaService.crearArtista(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> obtenerArtista(@PathVariable Long id) {
        return ResponseEntity.ok(artistaService.obtenerArtista(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> actualizarArtista(@PathVariable Long id, @Valid @RequestBody ArtistaRequestDTO dto) {
        return ResponseEntity.ok(artistaService.actualizarArtista(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArtista(@PathVariable Long id) {
        artistaService.eliminarArtista(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<ArtistaResponseDTO>> listarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(artistaService.listarPorGenero(genero));
    }

    @PostMapping("/{id}/evento/{idEvento}")
    public ResponseEntity<Void> asociarEvento(@PathVariable Long id, @PathVariable Long idEvento) {
        artistaService.asociarArtistaEvento(id, idEvento);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/evento/{idEvento}")
    public ResponseEntity<Void> desasociarEvento(@PathVariable Long id, @PathVariable Long idEvento) {
        artistaService.desasociarArtistaEvento(id, idEvento);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<ArtistaResponseDTO>> obtenerArtistasPorEvento(@PathVariable Long idEvento) {
        return ResponseEntity.ok(artistaService.obtenerArtistasPorEvento(idEvento));
    }
}
