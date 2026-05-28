package com.ticketuki.eventoservice.repository;

import com.ticketuki.eventoservice.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByFechaEvento(LocalDate fechaEvento);

    List<Evento> findByRecintoIdRecinto(Long recintoIdRecinto);
}
