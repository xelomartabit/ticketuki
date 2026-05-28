package com.ticketuki.promocionservice.controller;

import com.ticketuki.promocionservice.dto.PromocionDTO;
import com.ticketuki.promocionservice.service.PromocionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/promociones")
@RequiredArgsConstructor
@Slf4j
public class PromocionController {

    private final PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<PromocionDTO>> listarPromociones() {
        return ResponseEntity.ok(promocionService.listarPromociones());
    }

    @PostMapping
    public ResponseEntity<PromocionDTO> crearPromocion(@Valid @RequestBody PromocionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.crearPromocion(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromocionDTO> obtenerPromocion(@PathVariable Long id) {
        return promocionService.obtenerPromocion(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromocionDTO> actualizarPromocion(@PathVariable Long id, @Valid @RequestBody PromocionDTO dto) {
        return ResponseEntity.ok(promocionService.actualizarPromocion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable Long id) {
        promocionService.eliminarPromocion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activas")
    public ResponseEntity<List<PromocionDTO>> listarActivas() {
        return ResponseEntity.ok(promocionService.listarActivas());
    }

    @GetMapping("/empresa/{empresa}")
    public ResponseEntity<List<PromocionDTO>> listarPorEmpresa(@PathVariable String empresa) {
        return ResponseEntity.ok(promocionService.listarPorEmpresa(empresa));
    }

    @GetMapping("/detalle/{idDetalle}")
    public ResponseEntity<PromocionDTO> obtenerPorDetalle(@PathVariable Long idDetalle) {
        return promocionService.obtenerPorDetalle(idDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
