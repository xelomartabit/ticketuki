package com.ticketuki.recintoservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "direccion_recinto")
public class DireccionRecinto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion_recinto")
    private Long id_direccion_recinto;

    @Column(length = 50)
    private String calle;

    @Column(length = 50)
    private String region;

    @Column(length = 50)
    private String comuna;

    @Column(name = "num_calle")
    private Integer num_calle;

    @Column(name = "referencia_acceso", length = 100)
    private String referencia_acceso;
}
