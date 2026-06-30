package com.ticketuki.authservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario_auth")
public class UsuarioAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_auth")
    private Long id_usuario_auth;

    @NotBlank(message = "El username es requerido")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    // Contraseña almacenada como hash BCrypt (nunca en texto plano)
    @NotBlank(message = "El password es requerido")
    @Column(name = "password", nullable = false, length = 100)
    private String password;
}
