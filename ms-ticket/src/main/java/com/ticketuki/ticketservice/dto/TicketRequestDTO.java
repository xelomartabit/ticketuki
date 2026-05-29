package com.ticketuki.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {

    @NotNull(message = "El número de asiento es requerido")
    private Integer num_asiento;

    @NotBlank(message = "El nombre del titular es requerido")
    private String nombre_titular;

    @NotBlank(message = "El RUN del titular es requerido")
    @Pattern(regexp = "\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dKk]", message = "Formato de RUN inválido (ej: 12.345.678-9)")
    private String run_titular;

    private Long venta_id_venta;

    @NotNull(message = "El evento es requerido")
    private Long evento_id_evento;

    @NotNull(message = "El sector es requerido")
    private Long sector_id_sector;
}
