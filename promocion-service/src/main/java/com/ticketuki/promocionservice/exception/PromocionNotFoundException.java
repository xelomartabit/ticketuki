package com.ticketuki.promocionservice.exception;

public class PromocionNotFoundException extends RuntimeException {
    public PromocionNotFoundException(String message) {
        super(message);
    }
}
