package com.ticketuki.pagoservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long id_pago;

    @NotNull(message = "El monto es requerido")
    private BigDecimal monto;

    private String medio_pago;

    @NotBlank(message = "El código de autorización es requerido")
    private String cod_autorizacion;

    private LocalDate timestamp;

    private String estado;

    private Long venta_id;

    private Long usuario_id;
}
