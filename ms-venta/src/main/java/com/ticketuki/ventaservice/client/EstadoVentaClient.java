package com.ticketuki.ventaservice.client;

import com.ticketuki.ventaservice.dto.EstadoVentaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class EstadoVentaClient {

    private final WebClient webClient;

    @Cacheable(value = "estadosVenta", key = "#idEstado")
    public EstadoVentaDTO obtenerEstado(Long idEstado) {
        EstadoVentaDTO estado;
        try {
            estado = webClient.get()
                    .uri("/estadosVenta/{id}", idEstado)
                    .retrieve()
                    .bodyToMono(EstadoVentaDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException("Estado de venta no encontrado: " + idEstado);
        } catch (Exception e) {
            log.warn("No se pudo consultar ms-estado para id {}: {}", idEstado, e.getMessage());
            return new EstadoVentaDTO(idEstado, null);
        }
        if (estado == null) throw new IllegalArgumentException("Estado de venta no encontrado: " + idEstado);
        return estado;
    }
}
