package com.ticketuki.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferirTicketDTO {

    @NotBlank(message = "El nombre del nuevo titular es requerido")
    private String nombre_titular;

    @NotBlank(message = "El RUN del nuevo titular es requerido")
    @Pattern(regexp = "\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dKk]", message = "Formato de RUN inválido (ej: 12.345.678-9)")
    private String run_titular;
}
