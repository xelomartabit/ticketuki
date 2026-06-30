package com.ticketuki.estadoservice.repository;

import com.ticketuki.estadoservice.model.EstadoTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoTicketRepository extends JpaRepository<EstadoTicket, Long> {

    @Query("SELECT e FROM EstadoTicket e WHERE UPPER(e.nombre_estado_ticket) = UPPER(:nombre)")
    Optional<EstadoTicket> findByNombre(@Param("nombre") String nombre);
}
