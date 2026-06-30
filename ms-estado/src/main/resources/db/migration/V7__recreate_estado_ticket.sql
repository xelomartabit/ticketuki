-- Reactiva la gestión de estado_ticket en ms-estado.
-- V6 la había eliminado cuando se delegó a ms-ticket;
-- ahora ms-estado vuelve a ser la fuente de verdad para estos estados.
CREATE TABLE IF NOT EXISTS estado_ticket (
    id_estado_ticket     BIGINT      NOT NULL AUTO_INCREMENT,
    nombre_estado_ticket VARCHAR(25) NOT NULL UNIQUE,
    PRIMARY KEY (id_estado_ticket)
);

INSERT IGNORE INTO estado_ticket (nombre_estado_ticket) VALUES
    ('VALIDO'),
    ('USADO'),
    ('CANCELADO'),
    ('TRANSFERIDO');
