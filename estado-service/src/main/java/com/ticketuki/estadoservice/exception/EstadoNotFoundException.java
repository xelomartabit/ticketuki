package com.ticketuki.estadoservice.exception;

public class EstadoNotFoundException extends RuntimeException {
    public EstadoNotFoundException(String message) {
        super(message);
    }
}
