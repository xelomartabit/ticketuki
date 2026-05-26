package com.ticketuki.historialservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historial")
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long id_historial;

    @NotBlank(message = "La entidad es requerida")
    @Column(name = "entidad", length = 50)
    private String entidad;

    @NotNull(message = "El ID de entidad es requerido")
    @Column(name = "id_entidad")
    private Integer id_entidad;

    @NotBlank(message = "La acción es requerida")
    @Column(name = "accion", length = 50)
    private String accion;

    @Column(name = "usuario_id")
    private Integer usuario_id;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @Column(name = "cambios_anteriores", columnDefinition = "TEXT")
    private String cambios_anteriores;

    @Column(name = "cambios_nuevos", columnDefinition = "TEXT")
    private String cambios_nuevos;
}
