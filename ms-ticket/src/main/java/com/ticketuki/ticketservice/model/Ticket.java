package com.ticketuki.ticketservice.model;

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
@Table(name = "ticket", uniqueConstraints = {
        @UniqueConstraint(name = "uq_asiento_evento_sector",
                columnNames = {"num_asiento", "evento_id_evento", "sector_id_sector"})
})
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long id_ticket;

    @NotBlank(message = "El código QR es requerido")
    @Column(name = "cod_qr", nullable = false, unique = true, length = 10)
    private String cod_qr;

    @NotNull(message = "El número de asiento es requerido")
    @Column(name = "num_asiento", nullable = false)
    private Integer num_asiento;

    @NotBlank(message = "El nombre del titular es requerido")
    @Column(name = "nombre_titular", nullable = false, length = 50)
    private String nombre_titular;

    @NotBlank(message = "El RUN del titular es requerido")
    @Column(name = "run_titular", nullable = false, length = 12)
    private String run_titular;

    @NotNull(message = "La fecha de emisión es requerida")
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fecha_emision;

    @Column(name = "venta_id_venta")
    private Long venta_id_venta;

    @Column(name = "estado_ticket_id_estado")
    private Long estado_ticket_id_estado;

    @Column(name = "evento_id_evento")
    private Long evento_id_evento;

    @Column(name = "sector_id_sector")
    private Long sector_id_sector;
}
