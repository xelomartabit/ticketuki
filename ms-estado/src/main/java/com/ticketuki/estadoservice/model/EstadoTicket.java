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
@Table(name = "estado_ticket")
public class EstadoTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_ticket")
    private Long id_estado_ticket;

    @NotBlank(message = "El nombre del estado es requerido")
    @Column(name = "nombre_estado_ticket", length = 25, unique = true)
    private String nombre_estado_ticket;
}
