package com.ticketuki.promocionservice.model;

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
@Table(name = "promocion")
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Long id_promocion;

    @NotBlank(message = "La empresa es requerida")
    @Column(name = "empresa", nullable = false, length = 25)
    private String empresa;

    @NotNull(message = "El descuento es requerido")
    @Column(name = "descuento", nullable = false)
    private Integer descuento;

    @NotBlank(message = "La descripción es requerida")
    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    @NotNull(message = "La restricción es requerida")
    @Column(name = "restriccion", nullable = false)
    private Integer restriccion;

    @NotNull(message = "La fecha de expiración es requerida")
    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDate fecha_expiracion;

    @NotNull(message = "La fecha de inicio es requerida")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fecha_inicio;

    @Column(name = "detalle_venta_id_detalle")
    private Long detalle_venta_id_detalle;
}
