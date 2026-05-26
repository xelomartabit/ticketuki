package com.ticketuki.estadoservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDTO {

    private Long id;

    @NotBlank(message = "El nombre del estado es requerido")
    private String nombre;
}
