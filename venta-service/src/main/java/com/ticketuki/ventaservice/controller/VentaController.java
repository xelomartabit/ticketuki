package com.ticketuki.ventaservice.controller;

import com.ticketuki.ventaservice.dto.DetalleVentaDTO;
import com.ticketuki.ventaservice.dto.VentaDTO;
import com.ticketuki.ventaservice.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VentaController {

    private final VentaService ventaService;

    @GetMapping("/ventas")
    public ResponseEntity<List<VentaDTO>> listarVentas() {
        return ResponseEntity.ok(ventaService.listarVentas());
    }

    @PostMapping("/ventas")
    public ResponseEntity<VentaDTO> crearVenta(@Valid @RequestBody VentaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.crearVenta(dto));
    }

    @GetMapping("/ventas/{id}")
    public ResponseEntity<VentaDTO> obtenerVenta(@PathVariable Long id) {
        return ventaService.obtenerVenta(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/ventas/{id}/estado/{idEstado}")
    public ResponseEntity<VentaDTO> cambiarEstado(@PathVariable Long id, @PathVariable Long idEstado) {
        return ResponseEntity.ok(ventaService.cambiarEstado(id, idEstado));
    }

    @GetMapping("/ventas/fecha/{fechaInicio}/{fechaFin}")
    public ResponseEntity<List<VentaDTO>> listarPorPeriodo(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(ventaService.listarPorPeriodo(fechaInicio, fechaFin));
    }

    @PostMapping("/detalleVenta")
    public ResponseEntity<DetalleVentaDTO> crearDetalle(@Valid @RequestBody DetalleVentaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.crearDetalle(dto));
    }

    @GetMapping("/detalleVenta/{id}")
    public ResponseEntity<DetalleVentaDTO> obtenerDetalle(@PathVariable Long id) {
        return ventaService.obtenerDetalle(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/detalleVenta/venta/{idVenta}")
    public ResponseEntity<List<DetalleVentaDTO>> listarDetallesPorVenta(@PathVariable Long idVenta) {
        return ResponseEntity.ok(ventaService.listarDetallesPorVenta(idVenta));
    }
}
