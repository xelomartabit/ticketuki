package com.ticketuki.ventaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id_detalle;

    @NotNull
    @Column(name = "cantidad_ticket", nullable = false)
    private Integer cantidad_ticket;

    @NotNull
    @Column(name = "precio_neto", nullable = false)
    private Integer precio_neto;

    @NotNull
    @Column(name = "precio_iva", nullable = false)
    private Integer precio_iva;

    @NotNull
    @Column(name = "precio_total", nullable = false)
    private Integer precio_total;

    @NotNull
    @Column(name = "comision", nullable = false)
    private Integer comision;

    @Column(name = "usuario_id_usuario")
    private Long usuario_id_usuario;

    @Column(name = "venta_id_venta")
    private Long venta_id_venta;

    @Column(name = "sector_id_sector")
    private Long sector_id_sector;
}
