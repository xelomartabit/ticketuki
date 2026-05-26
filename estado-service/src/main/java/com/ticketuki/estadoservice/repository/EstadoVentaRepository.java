package com.ticketuki.estadoservice.repository;

import com.ticketuki.estadoservice.model.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoVentaRepository extends JpaRepository<EstadoVenta, Long> {
}
