package com.ticketuki.artistaservice.service;

import com.ticketuki.artistaservice.dto.ArtistaRequestDTO;
import com.ticketuki.artistaservice.dto.ArtistaResponseDTO;
import com.ticketuki.artistaservice.exception.ArtistaEventoDuplicadoException;
import com.ticketuki.artistaservice.exception.ArtistaNotFoundException;
import com.ticketuki.artistaservice.model.Artista;
import com.ticketuki.artistaservice.repository.ArtistaEventoRepository;
import com.ticketuki.artistaservice.repository.ArtistaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistaServiceTest {

    @Mock
    private ArtistaRepository artistaRepository;

    @Mock
    private ArtistaEventoRepository artistaEventoRepository;

    @InjectMocks
    private ArtistaService artistaService;

    // ───────────── 1crear exitoso ─────────────
    @Test
    void crearArtista_conDatosValidos_devuelveDTO() {
        ArtistaRequestDTO req = new ArtistaRequestDTO("Mon Laferte", "Pop", "@monlaferte");
        Artista guardado = new Artista(1L, "Mon Laferte", "Pop", "@monlaferte");
        when(artistaRepository.save(any(Artista.class))).thenReturn(guardado);

        ArtistaResponseDTO res = artistaService.crearArtista(req);

        assertThat(res.getId_artista()).isEqualTo(1L);
        assertThat(res.getNombre_artista()).isEqualTo("Mon Laferte");
        assertThat(res.getGenero_artista()).isEqualTo("Pop");
    }

    // ───────────── 2: obtener inexistente (error) ─────────────
    @Test
    void obtenerArtista_inexistente_lanzaNotFound() {
        when(artistaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> artistaService.obtenerArtista(99L))
                .isInstanceOf(ArtistaNotFoundException.class)
                .hasMessage("Artista no encontrado: 99");
    }

    // ───────────── 3 asociacion duplicada (error de negocio) ─────────────
    @Test
    void asociarArtistaEvento_yaAsociado_lanzaDuplicado() {
        when(artistaRepository.existsById(1L)).thenReturn(true);
        when(artistaEventoRepository
                .existsByArtista_id_artistaAndEvento_id_evento(1L, 5L)).thenReturn(true);

        assertThatThrownBy(() -> artistaService.asociarArtistaEvento(1L, 5L))
                .isInstanceOf(ArtistaEventoDuplicadoException.class)
                .hasMessage("El artista 1 ya está asociado al evento 5");
    }

    // ───────────── 4: filtrado por genero ─────────────
    @Test
    void listarPorGenero_devuelveSoloDelGenero() {
        when(artistaRepository.findByGenero_artista("Rock"))
                .thenReturn(List.of(new Artista(2L, "Los Bunkers", "Rock", "@bunkers")));

        List<ArtistaResponseDTO> res = artistaService.listarPorGenero("Rock");

        assertThat(res).hasSize(1);
        assertThat(res.get(0).getGenero_artista()).isEqualTo("Rock");
    }
}
