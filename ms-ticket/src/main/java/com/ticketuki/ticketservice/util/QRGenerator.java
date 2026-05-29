package com.ticketuki.ticketservice.util;

import java.util.UUID;

public class QRGenerator {

    public static String generarCodigoQR() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }
}
