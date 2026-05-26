package com.ticketuki.pagoservice.controller;

import com.ticketuki.pagoservice.dto.PagoDTO;
import com.ticketuki.pagoservice.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoDTO> procesarPago(@Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.status(201).body(pagoService.procesarPago(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPago(@PathVariable Long id) {
        return pagoService.obtenerPago(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<PagoDTO>> listarPorVenta(@PathVariable Long idVenta) {
        return ResponseEntity.ok(pagoService.listarPorVenta(idVenta));
    }

    @PutMapping("/{id}/reembolso")
    public ResponseEntity<PagoDTO> procesarReembolso(@PathVariable Long id) {
        return pagoService.procesarReembolso(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PagoDTO>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(pagoService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoDTO>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pagoService.listarPorEstado(estado));
    }

    @GetMapping("/fecha/{inicio}/{fin}")
    public ResponseEntity<List<PagoDTO>> listarPorPeriodo(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(pagoService.listarPorPeriodo(inicio, fin));
    }
}
