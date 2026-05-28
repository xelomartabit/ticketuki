CREATE TABLE evento (
    id_evento BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_evento VARCHAR(100) NOT NULL,
    aforo_evento INTEGER NOT NULL,
    fecha_evento DATE NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    estado_evento_id_estado BIGINT,
    recinto_id_recinto BIGINT,
--    INDEX idx_nombre (nombre_evento),
--    INDEX idx_fecha (fecha_evento),
    FOREIGN KEY (estado_evento_id_estado) REFERENCES estado_evento(id_estado_evento)
);