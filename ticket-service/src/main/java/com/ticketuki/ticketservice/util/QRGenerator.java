package com.ticketuki.ticketservice.util;

import java.util.UUID;

/**
 * Utilidad para generar códigos QR para tickets
 */
public class QRGenerator {

    /**
     * Genera un código QR único basado en UUID
     * @return Código QR único
     */
    public static String generarCodigoQR() {
        return "QR-" + UUID.randomUUID().toString().substring(0, 16).toUpperCase();
    }

    /**
     * Genera un código QR con información específica del ticket
     * @param idTicket ID del ticket
     * @param ventaId ID de la venta
     * @return Código QR con información del ticket
     */
    public static String generarCodigoQRConInfo(Long idTicket, Long ventaId) {
        return String.format("QR-T%d-V%d-%s",
                idTicket,
                ventaId,
                UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }
}
