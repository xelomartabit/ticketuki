package com.ticketuki.gateway.security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;

/**
 * Filtro global que valida el JWT en CADA petición que entra al gateway,
 * antes de enrutarla al microservicio destino. Centraliza la seguridad:
 * los microservicios internos no validan el token, confían en el gateway.
 */
@Slf4j
@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    // Rutas públicas que NO requieren token de autenticación.
    private static final List<String> RUTAS_PUBLICAS = List.of(
            "/auth/login"
    );

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 1. Las rutas públicas pasan sin validación
        if (esRutaPublica(path)) {
            return chain.filter(exchange);
        }

        // 2. Debe venir el header Authorization: Bearer <token>
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return rechazar(exchange, "Falta el token de autenticación");
        }

        String token = authHeader.substring(7);

        try {
            // 3. Validar firma y expiración del token
            Claims claims = jwtUtil.validarYObtenerClaims(token);

            // 4. Propagar el usuario autenticado a los microservicios en una cabecera
            ServerHttpRequest mutada = request.mutate()
                    .header("X-Usuario", claims.getSubject())
                    .build();

            // 5. Token válido → continuar el enrutamiento
            return chain.filter(exchange.mutate().request(mutada).build());

        } catch (Exception e) {
            log.warn("Token inválido en {}: {}", path, e.getMessage());
            return rechazar(exchange, "Token inválido o expirado");
        }
    }

    private boolean esRutaPublica(String path) {
        return RUTAS_PUBLICAS.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> rechazar(ServerWebExchange exchange, String motivo) {
        log.warn("Acceso rechazado: {}", motivo);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        // Se ejecuta antes que el enrutamiento del gateway
        return -1;
    }
}
