package com.ticketuki.ventaservice.repository;

import com.ticketuki.ventaservice.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    @Query("SELECT d FROM DetalleVenta d WHERE d.venta_id_venta = :ventaId")
    List<DetalleVenta> findByVenta_id_venta(@Param("ventaId") Long ventaId);
}
