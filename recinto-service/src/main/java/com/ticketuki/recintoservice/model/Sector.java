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
@Table(name = "sector")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sector")
    private Long id_sector;

    @NotBlank
    @Column(name = "nombre_sector", nullable = false, length = 25)
    private String nombre_sector;

    @Column(name = "capacidad_sector", nullable = false)
    private Integer capacidad_sector;

    @Column(name = "precio_sector", nullable = false)
    private Integer precio_sector;

    @Column(name = "recinto_id_recinto", nullable = false)
    private Long recinto_id_recinto;
}
