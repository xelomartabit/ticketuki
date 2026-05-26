package com.ticketuki.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id_ticket;

    private String cod_qr;

    @NotNull(message = "El número de asiento es requerido")
    private Integer num_asiento;

    @NotBlank(message = "El nombre del titular es requerido")
    private String nombre_titular;

    @NotBlank(message = "El RUN del titular es requerido")
    private String run_titular;

    private LocalDate fecha_emision;

    private Long venta_id_venta;

    private Long estado_ticket_id_estado;

    @NotNull(message = "El evento es requerido")
    private Long evento_id_evento;

    @NotNull(message = "El sector es requerido")
    private Long sector_id_sector;
}
