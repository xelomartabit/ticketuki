package com.ticketuki.artistaservice.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.evento.url:http://localhost:8003}")
    private String eventoUrl;

    // Usa el resolver DNS de la JVM (respeta /etc/hosts) en lugar del resolver
    // nativo de Netty, que en macOS no resuelve 'localhost' correctamente.
    private ReactorClientHttpConnector jvmDnsConnector() {
        HttpClient httpClient = HttpClient.create()
                .resolver(DefaultAddressResolverGroup.INSTANCE);
        return new ReactorClientHttpConnector(httpClient);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(eventoUrl)
                .clientConnector(jvmDnsConnector())
                .build();
    }
}
