package com.ticketuki.ventaservice.service;

import com.ticketuki.ventaservice.dto.DetalleVentaDTO;
import com.ticketuki.ventaservice.dto.VentaDTO;
import com.ticketuki.ventaservice.model.DetalleVenta;
import com.ticketuki.ventaservice.model.Venta;
import com.ticketuki.ventaservice.repository.DetalleVentaRepository;
import com.ticketuki.ventaservice.exception.VentaNotFoundException;
import com.ticketuki.ventaservice.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    private VentaDTO toResponseDTO(Venta v) {
        return new VentaDTO(v.getId_venta(), v.getFecha_venta(), v.getMedio_pago(), v.getCod_autorizacion(), v.getEstado_venta_id_estado());
    }

    private DetalleVentaDTO mapDetalleToDTO(DetalleVenta d) {
        return new DetalleVentaDTO(d.getId_detalle(), d.getCantidad_ticket(), d.getPrecio_neto(), d.getPrecio_iva(), d.getPrecio_total(), d.getComision(), d.getUsuario_id_usuario(), d.getVenta_id_venta(), d.getSector_id_sector());
    }

    @Transactional
    public VentaDTO crearVenta(VentaDTO dto) {
        log.info("Creando venta");
        Venta venta = new Venta(null, LocalDate.now(), dto.getMedio_pago(), dto.getCod_autorizacion(), dto.getEstado_venta_id_estado());
        return toResponseDTO(ventaRepository.save(venta));
    }

    @Transactional
    public VentaDTO cambiarEstado(Long id, Long idEstado) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrada: " + id));
        venta.setEstado_venta_id_estado(idEstado);
        return toResponseDTO(ventaRepository.save(venta));
    }

    @Transactional(readOnly = true)
    public Optional<VentaDTO> obtenerVenta(Long id) {
        return ventaRepository.findById(id).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<VentaDTO> listarVentas() {
        return ventaRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VentaDTO> listarPorPeriodo(LocalDate inicio, LocalDate fin) {
        return ventaRepository.findByFecha_ventaBetween(inicio, fin).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public DetalleVentaDTO crearDetalle(DetalleVentaDTO dto) {
        DetalleVenta detalle = new DetalleVenta(null, dto.getCantidad_ticket(), dto.getPrecio_neto(), dto.getPrecio_iva(), dto.getPrecio_total(), dto.getComision(), dto.getUsuario_id_usuario(), dto.getVenta_id_venta(), dto.getSector_id_sector());
        return mapDetalleToDTO(detalleVentaRepository.save(detalle));
    }

    @Transactional(readOnly = true)
    public Optional<DetalleVentaDTO> obtenerDetalle(Long id) {
        return detalleVentaRepository.findById(id).map(this::mapDetalleToDTO);
    }

    @Transactional(readOnly = true)
    public List<DetalleVentaDTO> listarDetallesPorVenta(Long ventaId) {
        return detalleVentaRepository.findByVenta_id_venta(ventaId).stream().map(this::mapDetalleToDTO).collect(Collectors.toList());
    }
}
