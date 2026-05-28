package com.ticketuki.artistaservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.evento.url:http://localhost:8001}")
    private String eventoUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(eventoUrl)
                .build();
    }
}
