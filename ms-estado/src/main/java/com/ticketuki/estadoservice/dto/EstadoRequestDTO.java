package com.ticketuki.estadoservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoRequestDTO {

    @NotBlank(message = "El nombre del estado es requerido")
    @Size(max = 25, message = "El nombre del estado no puede superar los 25 caracteres")
    private String nombre;
}
