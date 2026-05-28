package com.ticketuki.usuarioservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "direccion_usuario")
public class DireccionUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_direccion_usuario")
    private Long id_direccion_usuario;

    @Column(name = "calle", nullable = false, length = 50)
    private String calle;

    @Column(name = "region", nullable = false, length = 50)
    private String region;

    @Column(name = "comuna", nullable = false, length = 50)
    private String comuna;

    @Column(name = "num_calle", nullable = false)
    private Integer num_calle;
}
