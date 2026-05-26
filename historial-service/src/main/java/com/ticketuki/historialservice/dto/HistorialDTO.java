package com.ticketuki.historialservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialDTO {

    private Long id_historial;

    @NotBlank(message = "La entidad es requerida")
    private String entidad;

    @NotNull(message = "El ID de entidad es requerido")
    private Integer id_entidad;

    @NotBlank(message = "La acción es requerida")
    private String accion;

    private Integer usuario_id;

    private LocalDate timestamp;

    private String cambios_anteriores;

    private String cambios_nuevos;
}
