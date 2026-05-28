package com.ticketuki.promocionservice.service;

import com.ticketuki.promocionservice.dto.PromocionDTO;
import com.ticketuki.promocionservice.model.Promocion;
import com.ticketuki.promocionservice.exception.PromocionNotFoundException;
import com.ticketuki.promocionservice.repository.PromocionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromocionService {

    private final PromocionRepository promocionRepository;

    private PromocionDTO toResponseDTO(Promocion p) {
        return new PromocionDTO(p.getId_promocion(), p.getEmpresa(), p.getDescuento(), p.getDescripcion(),
                p.getRestriccion(), p.getFecha_expiracion(), p.getFecha_inicio(), p.getDetalle_venta_id_detalle());
    }

    @Transactional
    public PromocionDTO crearPromocion(PromocionDTO dto) {
        log.info("Creando promoción de empresa: {}", dto.getEmpresa());
        Promocion p = new Promocion(null, dto.getEmpresa(), dto.getDescuento(), dto.getDescripcion(),
                dto.getRestriccion(), dto.getFecha_expiracion(), dto.getFecha_inicio(), dto.getDetalle_venta_id_detalle());
        return toResponseDTO(promocionRepository.save(p));
    }

    @Transactional
    public PromocionDTO actualizarPromocion(Long id, PromocionDTO dto) {
        Promocion p = promocionRepository.findById(id)
                .orElseThrow(() -> new PromocionNotFoundException("Promoción no encontrada: " + id));
        p.setEmpresa(dto.getEmpresa());
        p.setDescuento(dto.getDescuento());
        p.setDescripcion(dto.getDescripcion());
        p.setRestriccion(dto.getRestriccion());
        p.setFecha_expiracion(dto.getFecha_expiracion());
        p.setFecha_inicio(dto.getFecha_inicio());
        p.setDetalle_venta_id_detalle(dto.getDetalle_venta_id_detalle());
        return toResponseDTO(promocionRepository.save(p));
    }

    @Transactional(readOnly = true)
    public Optional<PromocionDTO> obtenerPromocion(Long id) {
        return promocionRepository.findById(id).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<PromocionDTO> listarPromociones() {
        return promocionRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromocionDTO> listarActivas() {
        return promocionRepository.findPromocionesActivas(LocalDate.now()).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromocionDTO> listarPorEmpresa(String empresa) {
        return promocionRepository.findByEmpresa(empresa).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void eliminarPromocion(Long id) {
        if (!promocionRepository.existsById(id)) {
            throw new PromocionNotFoundException("Promoción no encontrada: " + id);
        }
        promocionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PromocionDTO> obtenerPorDetalle(Long idDetalle) {
        return promocionRepository.findByDetalle_venta_id_detalle(idDetalle).map(this::toResponseDTO);
    }
}
