package com.ticketuki.pagoservice.service;

import com.ticketuki.pagoservice.dto.PagoResponseDTO;
import com.ticketuki.pagoservice.exception.PagoNotFoundException;
import com.ticketuki.pagoservice.model.EstadoPago;
import com.ticketuki.pagoservice.model.MedioPago;
import com.ticketuki.pagoservice.model.Pago;
import com.ticketuki.pagoservice.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    private PagoService pagoService;

    @BeforeEach
    void setUp() {
        // funciones no usan los WebClient sino k se pasan null
        pagoService = new PagoService(pagoRepository, null, null, null, null);
    }

    private Pago pagoConEstado(EstadoPago estado) {
        return new Pago(1L, 25800L, MedioPago.CREDITO, "AUTH-9",
                LocalDateTime.now(), estado, 5L, 3L);
    }

    // ───────1: completar pago exitoso (transicion PENDIENTE A COMPLETADO)───────
    @Test
    void completarPago_enEstadoPendiente_pasaACompletado() {
        Pago pago = pagoConEstado(EstadoPago.PENDIENTE);
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        PagoResponseDTO res = pagoService.completarPago(1L);

        assertThat(res.getEstado()).isEqualTo(EstadoPago.COMPLETADO);
    }

    // ─────── 2: completar pago en estado invalido - IllegalState ───────
    @Test
    void completarPago_enEstadoNoPendiente_lanzaIllegalState() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoConEstado(EstadoPago.COMPLETADO)));

        assertThatThrownBy(() -> pagoService.completarPago(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Solo se puede completar un pago en estado PENDIENTE...");
    }

    // ───────3 reembolso no permitido (no COMPLETADO) - IllegalState───────
    @Test
    void procesarReembolso_pagoNoCompletado_lanzaIllegalState() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoConEstado(EstadoPago.PENDIENTE)));

        assertThatThrownBy(() -> pagoService.procesarReembolso(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("solo se puede reembolsar un pago en estado COMPLETADO...");
    }

    // ─────── 4 listar por periodo con fechas invertidas - IllegalArgument───────
    @Test
    void listarPorPeriodo_desdePosteriorAHasta_lanzaIllegalArgument() {
        LocalDate desde = LocalDate.of(2026, 6, 30);
        LocalDate hasta = LocalDate.of(2026, 1, 1);

        assertThatThrownBy(() -> pagoService.listarPorPeriodo(desde, hasta))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La fecha 'desde' no puede ser posterior a 'hasta'...");
    }
}
