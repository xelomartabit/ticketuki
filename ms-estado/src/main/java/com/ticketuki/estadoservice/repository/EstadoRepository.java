package com.ticketuki.estadoservice.repository;

import com.ticketuki.estadoservice.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    @Query("SELECT e FROM Estado e WHERE e.nombre_estado_evento = :nombre")
    Optional<Estado> findByNombre(@Param("nombre") String nombre);
}
