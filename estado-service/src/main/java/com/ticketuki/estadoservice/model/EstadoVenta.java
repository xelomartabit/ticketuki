package com.ticketuki.estadoservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estado_venta")
public class EstadoVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_venta")
    private Long id_estado_venta;

    @NotBlank(message = "El nombre del estado es requerido")
    @Column(name = "nombre_estado_venta", length = 25)
    private String nombre_estado_venta;
}
