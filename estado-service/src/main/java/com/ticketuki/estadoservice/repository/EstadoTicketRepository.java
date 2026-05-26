package com.ticketuki.estadoservice.repository;

import com.ticketuki.estadoservice.model.EstadoTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoTicketRepository extends JpaRepository<EstadoTicket, Long> {
}
