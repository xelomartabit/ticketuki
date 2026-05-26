package com.ticketuki.recintoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionRecintoDTO {
    private Long id_direccion_recinto;
    private String calle;
    private String region;
    private String comuna;
    private Integer num_calle;
    private String referencia_acceso;
}
