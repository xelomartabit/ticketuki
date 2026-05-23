package com.api.usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="DireccionUsuario")

public class DireccionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion_usuario")
    private Integer id_direccion_usuario;

    @Column(name = "calle", nullable = false, length = 100)
    private String calle;

    @Column(name = "num_calle", nullable = false)
    private Integer num_calle;

    @Column(name = "comuna", nullable = false, length = 50)
    private String comuna;

    @Column(name = "region", nullable = false, length = 50)
    private String region;
}