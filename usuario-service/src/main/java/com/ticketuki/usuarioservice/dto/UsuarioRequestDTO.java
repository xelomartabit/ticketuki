package com.ticketuki.usuarioservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "RUN no puede estar vacío")
    @Pattern(regexp = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9kK]$",
            message = "Formato RUN inválido")
    private String run;

    @NotBlank(message = "Primer nombre es obligatorio")
    @Size(min = 2, max = 25, message = "Primer nombre debe tener entre 2 y 25 caracteres")
    private String p_nombre;

    @Size(max = 25, message = "Segundo nombre no puede exceder 25 caracteres")
    private String s_nombre;

    @NotBlank(message = "Apellido materno es obligatorio")
    @Size(max = 25, message = "Apellido materno no puede exceder 25 caracteres")
    private String a_materno;

    @NotBlank(message = "Apellido paterno es obligatorio")
    @Size(min = 2, max = 25, message = "Apellido paterno debe tener entre 2 y 25 caracteres")
    private String a_paterno;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @Valid
    @NotNull(message = "Dirección es obligatoria")
    private DireccionDTO direccion;
}
