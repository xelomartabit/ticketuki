package com.ticketuki.ventaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long id_venta;

    @NotNull(message = "La fecha de venta es requerida")
    @Column(name = "fecha_venta", nullable = false)
    private LocalDate fecha_venta;

    @NotBlank(message = "El medio de pago es requerido")
    @Column(name = "medio_pago", nullable = false, length = 10)
    private String medio_pago;

    @NotNull(message = "El código de autorización es requerido")
    @Column(name = "cod_autorizacion", nullable = false)
    private Integer cod_autorizacion;

    @Column(name = "estado_venta_id_estado")
    private Long estado_venta_id_estado;
}
