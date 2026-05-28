package com.ticketuki.estadoservice.repository;

import com.ticketuki.estadoservice.model.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoVentaRepository extends JpaRepository<EstadoVenta, Long> {

    @Query("SELECT e FROM EstadoVenta e WHERE e.nombre_estado_venta = :nombre")
    Optional<EstadoVenta> findByNombre(@Param("nombre") String nombre);
}
