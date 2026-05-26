package com.ticketuki.ticketservice.repository;

import com.ticketuki.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.cod_qr = :codQR")
    Optional<Ticket> findByCod_qr(@Param("codQR") String codQR);

    @Query("SELECT t FROM Ticket t WHERE t.venta_id_venta = :ventaId")
    List<Ticket> findByVenta_id_venta(@Param("ventaId") Long ventaId);

    @Query("SELECT t FROM Ticket t WHERE t.evento_id_evento = :eventoId")
    List<Ticket> findByEvento_id_evento(@Param("eventoId") Long eventoId);

    @Query("SELECT t FROM Ticket t WHERE t.sector_id_sector = :sectorId")
    List<Ticket> findBySector_id_sector(@Param("sectorId") Long sectorId);
}
