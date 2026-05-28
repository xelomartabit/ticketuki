package com.ticketuki.pagoservice.service;

import com.ticketuki.pagoservice.dto.PagoDTO;
import com.ticketuki.pagoservice.model.Pago;
import com.ticketuki.pagoservice.repository.PagoRepository;
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
public class PagoService {

    private final PagoRepository pagoRepository;

    private PagoDTO toResponseDTO(Pago p) {
        return new PagoDTO(p.getId_pago(), p.getMonto(), p.getMedio_pago(), p.getCod_autorizacion(),
                p.getTimestamp(), p.getEstado(), p.getVenta_id(), p.getUsuario_id());
    }

    @Transactional
    public PagoDTO procesarPago(PagoDTO dto) {
        log.info("Procesando pago para venta: {}", dto.getVenta_id());
        Pago p = new Pago(null, dto.getMonto(), dto.getMedio_pago(), dto.getCod_autorizacion(),
                LocalDate.now(), "Pendiente", dto.getVenta_id(), dto.getUsuario_id());
        return toResponseDTO(pagoRepository.save(p));
    }

    @Transactional(readOnly = true)
    public Optional<PagoDTO> obtenerPago(Long id) {
        return pagoRepository.findById(id).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> listarPorVenta(Long idVenta) {
        return pagoRepository.findByVenta_id(idVenta).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public Optional<PagoDTO> procesarReembolso(Long id) {
        return pagoRepository.findById(id).map(p -> {
            p.setEstado("Reembolsado");
            return toResponseDTO(pagoRepository.save(p));
        });
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> listarPorUsuario(Long idUsuario) {
        return pagoRepository.findByUsuario_id(idUsuario).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> listarPorEstado(String estado) {
        return pagoRepository.findByEstado(estado).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> listarPorPeriodo(LocalDate inicio, LocalDate fin) {
        return pagoRepository.findByTimestampBetween(inicio, fin).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
