package com.ticketuki.ventaservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

    private Long id_venta;

    private LocalDate fecha_venta;

    @NotBlank(message = "El medio de pago es requerido")
    private String medio_pago;

    @NotNull(message = "El código de autorización es requerido")
    private Integer cod_autorizacion;

    private Long estado_venta_id_estado;
}
