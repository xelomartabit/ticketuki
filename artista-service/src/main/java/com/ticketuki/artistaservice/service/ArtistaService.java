package com.ticketuki.artistaservice.service;

import com.ticketuki.artistaservice.dto.ArtistaDTO;
import com.ticketuki.artistaservice.model.Artista;
import com.ticketuki.artistaservice.model.ArtistaEventoId;
import com.ticketuki.artistaservice.model.Artista_Evento;
import com.ticketuki.artistaservice.exception.ArtistaNotFoundException;
import com.ticketuki.artistaservice.repository.ArtistaEventoRepository;
import com.ticketuki.artistaservice.repository.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistaService {

    private final ArtistaRepository artistaRepository;
    private final ArtistaEventoRepository artistaEventoRepository;

    private ArtistaDTO toResponseDTO(Artista artista) {
        return new ArtistaDTO(artista.getId_artista(), artista.getNombre_artista(), artista.getGenero_artista(), artista.getRedes_sociales());
    }

    @Transactional
    public ArtistaDTO crearArtista(ArtistaDTO dto) {
        log.info("Creando artista: {}", dto.getNombre_artista());
        Artista artista = new Artista(null, dto.getNombre_artista(), dto.getGenero_artista(), dto.getRedes_sociales());
        return toResponseDTO(artistaRepository.save(artista));
    }

    @Transactional
    public ArtistaDTO actualizarArtista(Long id, ArtistaDTO dto) {
        log.info("Actualizando artista con ID: {}", id);
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new ArtistaNotFoundException("Artista no encontrado: " + id));
        artista.setNombre_artista(dto.getNombre_artista());
        artista.setGenero_artista(dto.getGenero_artista());
        artista.setRedes_sociales(dto.getRedes_sociales());
        return toResponseDTO(artistaRepository.save(artista));
    }

    @Transactional(readOnly = true)
    public ArtistaDTO obtenerArtista(Long id) {
        return toResponseDTO(artistaRepository.findById(id)
                .orElseThrow(() -> new ArtistaNotFoundException("Artista no encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<ArtistaDTO> listarArtistas() {
        return artistaRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void eliminarArtista(Long id) {
        if (!artistaRepository.existsById(id)) {
            throw new ArtistaNotFoundException("Artista no encontrado: " + id);
        }
        artistaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ArtistaDTO> listarPorGenero(String genero) {
        return artistaRepository.findByGenero_artista(genero).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void asociarArtistaEvento(Long idArtista, Long idEvento) {
        if (!artistaRepository.existsById(idArtista)) {
            throw new ArtistaNotFoundException("Artista no encontrado: " + idArtista);
        }
        artistaEventoRepository.save(new Artista_Evento(idArtista, idEvento));
    }

    @Transactional
    public void desasociarArtistaEvento(Long idArtista, Long idEvento) {
        artistaEventoRepository.deleteById(new ArtistaEventoId(idArtista, idEvento));
    }

    @Transactional(readOnly = true)
    public List<ArtistaDTO> obtenerArtistasPorEvento(Long idEvento) {
        return artistaEventoRepository.findByEvento_id_evento(idEvento).stream()
                .map(ae -> artistaRepository.findById(ae.getArtista_id_artista()).map(this::toResponseDTO).orElse(null))
                .filter(a -> a != null)
                .collect(Collectors.toList());
    }
}
