package com.ticketuki.ventaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaResponseDTO {

    private Long id_detalle;
    private Integer cantidad_ticket;
    private Integer precio_neto;
    private Integer precio_iva;
    private Integer precio_total;
    private Integer comision;
    private Long usuario_id_usuario;
    private Long venta_id_venta;
    private Long sector_id_sector;
}
