package com.ticketuki.pagoservice.model;

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
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id_pago;

    @NotNull(message = "El monto es requerido")
    @Column(name = "monto", nullable = false)
    private java.math.BigDecimal monto;

    @Column(name = "medio_pago", length = 50)
    private String medio_pago;

    @NotBlank(message = "El código de autorización es requerido")
    @Column(name = "cod_autorizacion", nullable = false, length = 50)
    private String cod_autorizacion;

    @NotNull(message = "El timestamp es requerido")
    @Column(name = "timestamp", nullable = false)
    private LocalDate timestamp;

    @Column(name = "estado", length = 25)
    private String estado;

    @Column(name = "venta_id")
    private Long venta_id;

    @Column(name = "usuario_id")
    private Long usuario_id;
}
