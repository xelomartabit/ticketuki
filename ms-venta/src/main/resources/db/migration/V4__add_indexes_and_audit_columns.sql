-- Auditoría: timestamps automáticos en venta
ALTER TABLE venta
    ADD COLUMN created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Índices para mejorar performance de búsquedas frecuentes
CREATE INDEX idx_venta_fecha       ON venta (fecha_venta);
CREATE INDEX idx_venta_estado      ON venta (estado_venta_id_estado);
CREATE INDEX idx_detalle_venta_id  ON detalle_venta (venta_id_venta);
