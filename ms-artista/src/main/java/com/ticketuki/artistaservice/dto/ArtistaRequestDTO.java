package com.ticketuki.artistaservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistaRequestDTO {

    @NotBlank(message = "El nombre del artista es requerido")
    private String nombre_artista;

    @NotBlank(message = "El género del artista es requerido")
    private String genero_artista;

    @NotBlank(message = "Las redes sociales son requeridas")
    private String redes_sociales;
}
