package com.ticketuki.ticketservice.repository;

import com.ticketuki.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByCod_qr(String cod_qr);

    List<Ticket> findByVenta_id_venta(Long venta_id_venta);

    List<Ticket> findByEvento_id_evento(Long evento_id_evento);

    List<Ticket> findBySector_id_sector(Long sector_id_sector);

    List<Ticket> findByRun_titular(String run_titular);

    boolean existsByNum_asientoAndEvento_id_eventoAndSector_id_sector(
            Integer num_asiento, Long evento_id_evento, Long sector_id_sector);
}
