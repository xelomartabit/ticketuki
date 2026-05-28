package com.ticketuki.artistaservice.repository;

import com.ticketuki.artistaservice.model.Artista;
import com.ticketuki.artistaservice.model.Artista_Evento;
import com.ticketuki.artistaservice.model.ArtistaEventoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtistaEventoRepository extends JpaRepository<Artista_Evento, ArtistaEventoId> {

    List<Artista_Evento> findByEvento_id_evento(Long evento_id_evento);

    List<Artista_Evento> findByArtista_id_artista(Long artista_id_artista);

    boolean existsByArtista_id_artistaAndEvento_id_evento(Long artista_id_artista, Long evento_id_evento);

    @Query("SELECT a FROM Artista a WHERE a.id_artista IN " +
           "(SELECT ae.artista_id_artista FROM Artista_Evento ae WHERE ae.evento_id_evento = :idEvento)")
    List<Artista> findArtistasByEventoId(@Param("idEvento") Long idEvento);
}
