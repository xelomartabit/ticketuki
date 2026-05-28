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
@Table(name = "estado_evento")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_evento")
    private Long id_estado_evento;

    @NotBlank(message = "El nombre del estado es requerido")
    @Column(name = "nombre_estado_evento", length = 25, unique = true)
    private String nombre_estado_evento;
}
