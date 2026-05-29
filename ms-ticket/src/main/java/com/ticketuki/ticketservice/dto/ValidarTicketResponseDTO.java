package com.ticketuki.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidarTicketResponseDTO {

    private boolean valido;
    private String mensaje;
    private TicketResponseDTO ticket;
}
