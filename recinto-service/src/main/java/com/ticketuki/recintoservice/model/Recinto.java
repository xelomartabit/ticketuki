package com.ticketuki.recintoservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recinto")
public class Recinto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recinto")
    private Long id_recinto;

    @NotBlank
    @Column(name = "nombre_recinto", nullable = false, length = 50)
    private String nombre_recinto;

    @Column(name = "capacidad_total", nullable = false)
    private Integer capacidad_total;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_recinto_id_direccion")
    private DireccionRecinto direccion;
}
