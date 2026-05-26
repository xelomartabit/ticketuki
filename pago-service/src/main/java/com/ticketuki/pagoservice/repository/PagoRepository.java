package com.ticketuki.pagoservice.repository;

import com.ticketuki.pagoservice.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query("SELECT p FROM Pago p WHERE p.venta_id = :ventaId")
    List<Pago> findByVenta_id(@Param("ventaId") Long ventaId);

    @Query("SELECT p FROM Pago p WHERE p.usuario_id = :usuarioId")
    List<Pago> findByUsuario_id(@Param("usuarioId") Long usuarioId);

    List<Pago> findByEstado(String estado);

    List<Pago> findByTimestampBetween(LocalDate inicio, LocalDate fin);
}
