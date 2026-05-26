CREATE TABLE historial (
    id_historial BIGINT AUTO_INCREMENT PRIMARY KEY,
    entidad VARCHAR(50),
    id_entidad INTEGER,
    accion VARCHAR(50),
    usuario_id INTEGER,
    timestamp DATE,
    cambios_anteriores TEXT,
    cambios_nuevos TEXT
);
