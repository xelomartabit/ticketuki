package com.ticketuki.ventaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VentaServiceApplication {
    public static void main(String[] args) { SpringApplication.run(VentaServiceApplication.class, args); }
}
