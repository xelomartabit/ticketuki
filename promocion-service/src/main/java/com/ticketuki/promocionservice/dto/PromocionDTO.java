package com.ticketuki.promocionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionDTO {

    private Long id_promocion;

    @NotBlank(message = "La empresa es requerida")
    private String empresa;

    @NotNull(message = "El descuento es requerido")
    private Integer descuento;

    @NotBlank(message = "La descripción es requerida")
    private String descripcion;

    @NotNull(message = "La restricción es requerida")
    private Integer restriccion;

    @NotNull(message = "La fecha de expiración es requerida")
    private LocalDate fecha_expiracion;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate fecha_inicio;

    private Long detalle_venta_id_detalle;
}
