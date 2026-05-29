package com.ticketuki.historialservice.dto;

import com.ticketuki.historialservice.model.AccionHistorial;
import com.ticketuki.historialservice.model.TipoEntidad;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialRequestDTO {

    @NotNull(message = "La entidad es requerida")
    private TipoEntidad entidad;

    @NotNull(message = "El ID de entidad es requerido")
    private Long id_entidad;

    @NotNull(message = "La acción es requerida")
    private AccionHistorial accion;

    private Long usuario_id;

    private String cambios_anteriores;

    private String cambios_nuevos;
}
