package com.ticketuki.gateway.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.client.HttpClient;

@Configuration
public class HttpClientConfig {

    // El HttpClient interno del gateway usa por defecto el resolver DNS nativo de
    // Netty, que en macOS no respeta /etc/hosts y falla al resolver 'localhost'
    // (NXDOMAIN). Forzamos el resolver de la JVM, que sí lo resuelve.
    @Bean
    public HttpClientCustomizer jvmDnsResolverCustomizer() {
        return httpClient -> httpClient.resolver(DefaultAddressResolverGroup.INSTANCE);
    }
}
