package com.ticketuki.historialservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "entidad", length = 50, nullable = false)
    private TipoEntidad entidad;

    @Column(name = "id_entidad", nullable = false)
    private Long id_entidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "accion", length = 50, nullable = false)
    private AccionHistorial accion;

    @Column(name = "usuario_id")
    private Long usuario_id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "cambios_anteriores", columnDefinition = "TEXT")
    private String cambios_anteriores;

    @Column(name = "cambios_nuevos", columnDefinition = "TEXT")
    private String cambios_nuevos;
}
