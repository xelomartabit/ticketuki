package com.ticketuki.eventoservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoRequestDTO {

    @NotBlank(message = "El nombre del evento es requerido")
    private String nombre_evento;

    @NotNull(message = "El aforo es requerido")
    @Positive(message = "El aforo debe ser positivo")
    private Integer aforo_evento;

    @NotNull(message = "La fecha es requerida")
    private LocalDate fecha_evento;

    private String descripcion;

    private Long estado_evento_id_estado;

    private Long recinto_id_recinto;
}
