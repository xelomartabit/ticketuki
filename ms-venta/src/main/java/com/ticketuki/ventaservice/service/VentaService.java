package com.ticketuki.ventaservice.service;

import com.ticketuki.ventaservice.dto.DetalleVentaResponseDTO;
import com.ticketuki.ventaservice.dto.EstadoVentaDTO;
import com.ticketuki.ventaservice.dto.VentaRequestDTO;
import com.ticketuki.ventaservice.dto.VentaResponseDTO;
import com.ticketuki.ventaservice.model.DetalleVenta;
import com.ticketuki.ventaservice.model.Venta;
import com.ticketuki.ventaservice.repository.DetalleVentaRepository;
import com.ticketuki.ventaservice.exception.VentaNotFoundException;
import com.ticketuki.ventaservice.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaService {

    private static final double PORCENTAJE_IVA      = 0.19;
    private static final double PORCENTAJE_COMISION  = 0.10;

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final WebClient webClient;

    // ── Helpers ──────────────────────────────────────────────────────────────

    private EstadoVentaDTO obtenerEstado(Long idEstado) {
        try {
            return webClient.get()
                    .uri("/estadosVenta/{id}", idEstado)
                    .retrieve()
                    .bodyToMono(EstadoVentaDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException("Estado de venta no encontrado: " + idEstado);
        } catch (Exception e) {
            log.warn("No se pudo consultar ms-estado, se retorna solo el id. Error: {}", e.getMessage());
            return new EstadoVentaDTO(idEstado, null);
        }
    }

    private DetalleVenta calcularDetalle(com.ticketuki.ventaservice.dto.DetalleVentaRequestDTO dto, Long ventaId) {
        int precioIva    = (int) Math.round(dto.getPrecio_neto() * PORCENTAJE_IVA);
        int comision     = (int) Math.round(dto.getPrecio_neto() * PORCENTAJE_COMISION);
        int precioTotal  = dto.getPrecio_neto() + precioIva + comision;
        return new DetalleVenta(null, dto.getCantidad_ticket(), dto.getPrecio_neto(),
                precioIva, precioTotal, comision,
                dto.getUsuario_id_usuario(), ventaId, dto.getSector_id_sector());
    }

    private DetalleVentaResponseDTO mapDetalleToDTO(DetalleVenta d) {
        return new DetalleVentaResponseDTO(d.getId_detalle(), d.getCantidad_ticket(),
                d.getPrecio_neto(), d.getPrecio_iva(), d.getPrecio_total(), d.getComision(),
                d.getUsuario_id_usuario(), d.getVenta_id_venta(), d.getSector_id_sector());
    }

    private VentaResponseDTO toResponseDTO(Venta v, List<DetalleVentaResponseDTO> detalles) {
        EstadoVentaDTO estado = obtenerEstado(v.getEstado_venta_id_estado());
        return new VentaResponseDTO(v.getId_venta(), v.getFecha_venta(), v.getMedio_pago(),
                v.getCod_autorizacion(), v.getMonto_total(), estado, detalles);
    }

    // ── Operaciones de venta ─────────────────────────────────────────────────

    @Transactional
    public VentaResponseDTO crearVenta(VentaRequestDTO dto) {
        log.info("Creando venta con medio de pago: {}", dto.getMedio_pago());

        // 1. Validar que el estado existe en ms-estado
        EstadoVentaDTO estado = obtenerEstado(dto.getEstado_venta_id_estado());

        // 2. Calcular detalles en memoria (IVA 19%, comisión 10%)
        List<DetalleVenta> detallesCalculados = dto.getDetalles().stream()
                .map(d -> calcularDetalle(d, null))
                .collect(Collectors.toList());

        // 3. Calcular monto total sumando precio_total de cada detalle
        int montoTotal = detallesCalculados.stream()
                .mapToInt(DetalleVenta::getPrecio_total)
                .sum();

        // 4. Guardar la venta con el monto calculado
        Venta venta = new Venta(null, LocalDateTime.now(), dto.getMedio_pago(),
                dto.getCod_autorizacion(), montoTotal, dto.getEstado_venta_id_estado(), null, null);
        Venta ventaGuardada = ventaRepository.save(venta);
        log.info("Venta creada con id: {}, monto total: {}", ventaGuardada.getId_venta(), montoTotal);

        // 5. Asociar detalles a la venta y guardarlos
        detallesCalculados.forEach(d -> d.setVenta_id_venta(ventaGuardada.getId_venta()));
        List<DetalleVenta> detallesGuardados = detalleVentaRepository.saveAll(detallesCalculados);

        // 6. Construir respuesta completa
        List<DetalleVentaResponseDTO> detallesDTO = detallesGuardados.stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());

        return new VentaResponseDTO(ventaGuardada.getId_venta(), ventaGuardada.getFecha_venta(),
                ventaGuardada.getMedio_pago(), ventaGuardada.getCod_autorizacion(),
                montoTotal, estado, detallesDTO);
    }

    @Transactional
    public VentaResponseDTO cambiarEstado(Long id, Long idEstado) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrada: " + id));
        EstadoVentaDTO estado = obtenerEstado(idEstado);
        venta.setEstado_venta_id_estado(idEstado);
        Venta guardada = ventaRepository.save(venta);
        return new VentaResponseDTO(guardada.getId_venta(), guardada.getFecha_venta(),
                guardada.getMedio_pago(), guardada.getCod_autorizacion(),
                guardada.getMonto_total(), estado, null);
    }

    @Transactional(readOnly = true)
    public Optional<VentaResponseDTO> obtenerVenta(Long id) {
        return ventaRepository.findById(id).map(v -> {
            List<DetalleVentaResponseDTO> detalles = detalleVentaRepository
                    .findByVenta_id_venta(v.getId_venta()).stream()
                    .map(this::mapDetalleToDTO)
                    .collect(Collectors.toList());
            return toResponseDTO(v, detalles);
        });
    }

    @Transactional(readOnly = true)
    public List<VentaResponseDTO> listarVentas() {
        return ventaRepository.findAll().stream()
                .map(v -> toResponseDTO(v, null))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VentaResponseDTO> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.findByFecha_ventaBetween(inicio, fin).stream()
                .map(v -> toResponseDTO(v, null))
                .collect(Collectors.toList());
    }

    // ── Operaciones de detalle ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Optional<DetalleVentaResponseDTO> obtenerDetalle(Long id) {
        return detalleVentaRepository.findById(id).map(this::mapDetalleToDTO);
    }

    @Transactional(readOnly = true)
    public List<DetalleVentaResponseDTO> listarDetallesPorVenta(Long ventaId) {
        return detalleVentaRepository.findByVenta_id_venta(ventaId).stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());
    }
}