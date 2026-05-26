package com.ticketuki.historialservice.repository;

import com.ticketuki.historialservice.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Long> {

    @Query("SELECT h FROM Historial h WHERE h.entidad = :entidad AND h.id_entidad = :idEntidad")
    List<Historial> findByEntidadAndId_entidad(@Param("entidad") String entidad, @Param("idEntidad") Integer idEntidad);

    @Query("SELECT h FROM Historial h WHERE h.usuario_id = :usuarioId")
    List<Historial> findByUsuario_id(@Param("usuarioId") Integer usuarioId);

    List<Historial> findByTimestampBetween(LocalDate inicio, LocalDate fin);
}
