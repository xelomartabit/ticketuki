package com.ticketuki.pagoservice.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

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

    // Usa el resolver DNS de la JVM (respeta /etc/hosts) en lugar del resolver
    // nativo de Netty, que en macOS no resuelve 'localhost' correctamente.
    private ReactorClientHttpConnector jvmDnsConnector() {
        HttpClient httpClient = HttpClient.create()
                .resolver(DefaultAddressResolverGroup.INSTANCE);
        return new ReactorClientHttpConnector(httpClient);
    }

    @Bean("ventaWebClient")
    public WebClient ventaWebClient() {
        return WebClient.builder().baseUrl(ventaUrl).clientConnector(jvmDnsConnector()).build();
    }

    @Bean("usuarioWebClient")
    public WebClient usuarioWebClient() {
        return WebClient.builder().baseUrl(usuarioUrl).clientConnector(jvmDnsConnector()).build();
    }

    @Bean("ticketWebClient")
    public WebClient ticketWebClient() {
        return WebClient.builder().baseUrl(ticketUrl).clientConnector(jvmDnsConnector()).build();
    }

    @Bean("estadoWebClient")
    public WebClient estadoWebClient() {
        return WebClient.builder().baseUrl(estadoUrl).clientConnector(jvmDnsConnector()).build();
    }
}
