CREATE TABLE estado_ticket (
    id_estado_ticket BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado_ticket VARCHAR(25)
);

CREATE TABLE ticket (
    id_ticket BIGINT AUTO_INCREMENT PRIMARY KEY,
    cod_qr VARCHAR(10) NOT NULL UNIQUE,
    num_asiento INTEGER NOT NULL,
    nombre_titular VARCHAR(50) NOT NULL,
    run_titular VARCHAR(12) NOT NULL,
    fecha_emision DATE NOT NULL,
    venta_id_venta BIGINT,
    estado_ticket_id_estado BIGINT,
    evento_id_evento BIGINT,
    sector_id_sector BIGINT,
    FOREIGN KEY (estado_ticket_id_estado) REFERENCES estado_ticket(id_estado_ticket)
);

INSERT INTO estado_ticket (nombre_estado_ticket) VALUES ('Válido'), ('Usado'), ('Cancelado'), ('Transferido');
