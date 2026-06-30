CREATE TABLE venta (
    id_venta BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta DATE NOT NULL,
    medio_pago VARCHAR(10) NOT NULL,
    cod_autorizacion INTEGER NOT NULL,
    estado_venta_id_estado BIGINT,
    INDEX idx_estado (estado_venta_id_estado)
);

CREATE TABLE detalle_venta (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad_ticket INTEGER NOT NULL,
    precio_neto INTEGER NOT NULL,
    precio_iva INTEGER NOT NULL,
    precio_total INTEGER NOT NULL,
    comision INTEGER NOT NULL,
    usuario_id_usuario BIGINT,
    venta_id_venta BIGINT,
    sector_id_sector BIGINT,
    FOREIGN KEY (venta_id_venta) REFERENCES venta(id_venta)
);