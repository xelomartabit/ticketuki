package com.ticketuki.usuarioservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {
    private String calle;
    private String region;
    private String comuna;
    private Integer num_calle;
}
