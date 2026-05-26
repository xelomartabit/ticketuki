package com.ticketuki.recintoservice.repository;

import com.ticketuki.recintoservice.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    @Query("SELECT s FROM Sector s WHERE s.recinto_id_recinto = :recintoId")
    List<Sector> findByRecinto_id_recinto(@Param("recintoId") Long recintoId);
}
