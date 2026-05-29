package com.ticketuki.pagoservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.venta.url:http://localhost:8006}")
    private String ventaUrl;

    @Value("${ms.usuario.url:http://localhost:8001}")
    private String usuarioUrl;

    @Value("${ms.ticket.url:http://localhost:8005}")
    private String ticketUrl;

    @Value("${ms.estado.url:http://localhost:8004}")
    private String estadoUrl;

    @Bean("ventaWebClient")
    public WebClient ventaWebClient() {
        return WebClient.builder().baseUrl(ventaUrl).build();
    }

    @Bean("usuarioWebClient")
    public WebClient usuarioWebClient() {
        return WebClient.builder().baseUrl(usuarioUrl).build();
    }

    @Bean("ticketWebClient")
    public WebClient ticketWebClient() {
        return WebClient.builder().baseUrl(ticketUrl).build();
    }

    @Bean("estadoWebClient")
    public WebClient estadoWebClient() {
        return WebClient.builder().baseUrl(estadoUrl).build();
    }
}
