package com.ticketuki.artistaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistaResponseDTO {

    private Long id_artista;
    private String nombre_artista;
    private String genero_artista;
    private String redes_sociales;
}
