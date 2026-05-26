package com.ticketuki.recintoservice.service;

import com.ticketuki.recintoservice.dto.SectorDTO;
import com.ticketuki.recintoservice.model.Sector;
import com.ticketuki.recintoservice.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;

    private SectorDTO mapToDTO(Sector sector) {
        return new SectorDTO(sector.getId_sector(), sector.getNombre_sector(), sector.getCapacidad_sector(), sector.getPrecio_sector(), sector.getRecinto_id_recinto());
    }

    @Transactional
    public SectorDTO crearSector(SectorDTO dto) {
        log.info("Creando sector: {}", dto.getNombre_sector());
        Sector sector = new Sector(null, dto.getNombre_sector(), dto.getCapacidad_sector(), dto.getPrecio_sector(), dto.getRecinto_id_recinto());
        return mapToDTO(sectorRepository.save(sector));
    }

    @Transactional
    public SectorDTO actualizarSector(Long id, SectorDTO dto) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sector no encontrado: " + id));
        sector.setNombre_sector(dto.getNombre_sector());
        sector.setCapacidad_sector(dto.getCapacidad_sector());
        sector.setPrecio_sector(dto.getPrecio_sector());
        return mapToDTO(sectorRepository.save(sector));
    }

    @Transactional(readOnly = true)
    public Optional<SectorDTO> obtenerSector(Long id) {
        return sectorRepository.findById(id).map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public List<SectorDTO> listarPorRecinto(Long recintoId) {
        return sectorRepository.findByRecinto_id_recinto(recintoId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
