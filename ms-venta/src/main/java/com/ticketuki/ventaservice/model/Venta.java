package com.ticketuki.ventaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;

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
    private LocalDateTime fecha_venta;

    @NotBlank(message = "El medio de pago es requerido")
    @Column(name = "medio_pago", nullable = false, length = 50)
    private String medio_pago;

    @NotBlank(message = "El código de autorización es requerido")
    @Size(max = 50, message = "El código de autorización no puede superar los 50 caracteres")
    @Column(name = "cod_autorizacion", nullable = false, length = 50)
    private String cod_autorizacion;

    @NotNull(message = "El monto total es requerido")
    @Column(name = "monto_total", nullable = false)
    private Long monto_total;

    @Column(name = "estado_venta_id_estado")
    private Long estado_venta_id_estado;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated_at;
}
