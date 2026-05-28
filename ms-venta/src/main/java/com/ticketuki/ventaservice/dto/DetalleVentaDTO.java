package com.ticketuki.ventaservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaDTO {

    private Long id_detalle;

    @NotNull(message = "La cantidad de tickets es requerida")
    private Integer cantidad_ticket;

    @NotNull(message = "El precio neto es requerido")
    private Integer precio_neto;

    @NotNull(message = "El precio IVA es requerido")
    private Integer precio_iva;

    @NotNull(message = "El precio total es requerido")
    private Integer precio_total;

    @NotNull(message = "La comisión es requerida")
    private Integer comision;

    @NotNull(message = "El id usuario es requerido")
    private Long usuario_id_usuario;

    @NotNull(message = "La id venta es requerida")
    private Long venta_id_venta;

    @NotNull(message = "El id sector es requerida")
    private Long sector_id_sector;
}
