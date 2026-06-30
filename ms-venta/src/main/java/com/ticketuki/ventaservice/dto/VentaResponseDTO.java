package com.ticketuki.ventaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {

    private Long id_venta;
    private LocalDateTime fecha_venta;
    private String medio_pago;
    private String cod_autorizacion;
    private Long monto_total;
    private EstadoVentaDTO estado_venta;
    private List<DetalleVentaResponseDTO> detalles;
}
