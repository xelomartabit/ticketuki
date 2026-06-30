CREATE TABLE promocion (
    id_promocion BIGINT AUTO_INCREMENT PRIMARY KEY,
    empresa VARCHAR(25) NOT NULL,
    descuento INTEGER NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    restriccion INTEGER NOT NULL,
    fecha_expiracion DATE NOT NULL,
    fecha_inicio DATE NOT NULL,
    detalle_venta_id_detalle BIGINT
);
