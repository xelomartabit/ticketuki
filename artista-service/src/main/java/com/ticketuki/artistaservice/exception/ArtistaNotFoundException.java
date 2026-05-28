package com.ticketuki.artistaservice.exception;

public class ArtistaNotFoundException extends RuntimeException {
    public ArtistaNotFoundException(String message) {
        super(message);
    }
}
