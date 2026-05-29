package com.ticketuki.ventaservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.estado.url:http://localhost:8004}")
    private String estadoBaseUrl;

    @Value("${ms.promocion.url:http://localhost:8007}")
    private String promocionBaseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(estadoBaseUrl)
                .build();
    }

    @Bean("promocionWebClient")
    public WebClient promocionWebClient() {
        return WebClient.builder()
                .baseUrl(promocionBaseUrl)
                .build();
    }
}
