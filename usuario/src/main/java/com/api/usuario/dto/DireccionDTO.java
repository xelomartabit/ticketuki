package com.api.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DireccionDTO {

    @NotBlank(message = "Calle es obligatoria")
    @Size(min = 5, max = 100, message = "Calle debe tener entre 5 y 100 caracteres")
    private String calle;

    @NotNull(message = "Número de calle es obligatorio")
    @Positive(message = "Número de calle debe ser positivo")
    private Integer num_calle;

    @NotBlank(message = "Comuna es obligatoria")
    @Size(min = 3, max = 50, message = "Comuna debe tener entre 3 y 50 caracteres")
    private String comuna;

    @NotBlank(message = "Región es obligatoria")
    @Size(min = 3, max = 50, message = "Región debe tener entre 3 y 50 caracteres")
    private String region;
}