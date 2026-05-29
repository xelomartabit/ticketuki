package com.ticketuki.ticketservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.estado.url:http://localhost:8004}")
    private String estadoUrl;

    @Value("${ms.evento.url:http://localhost:8003}")
    private String eventoUrl;

    @Value("${ms.recinto.url:http://localhost:8008}")
    private String recintoUrl;

    @Value("${ms.venta.url:http://localhost:8006}")
    private String ventaUrl;

    @Bean("estadoWebClient")
    public WebClient estadoWebClient() {
        return WebClient.builder().baseUrl(estadoUrl).build();
    }

    @Bean("eventoWebClient")
    public WebClient eventoWebClient() {
        return WebClient.builder().baseUrl(eventoUrl).build();
    }

    @Bean("recintoWebClient")
    public WebClient recintoWebClient() {
        return WebClient.builder().baseUrl(recintoUrl).build();
    }

    @Bean("ventaWebClient")
    public WebClient ventaWebClient() {
        return WebClient.builder().baseUrl(ventaUrl).build();
    }
}
