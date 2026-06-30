-- Recrea la tabla local estado_ticket (eliminada en V2) con los IDs explícitos
-- para que coincidan con las referencias ya guardadas en ticket.estado_ticket_id_estado
CREATE TABLE IF NOT EXISTS estado_ticket (
    id_estado_ticket     BIGINT      NOT NULL AUTO_INCREMENT,
    nombre_estado_ticket VARCHAR(25) NOT NULL UNIQUE,
    PRIMARY KEY (id_estado_ticket)
);

INSERT IGNORE INTO estado_ticket (id_estado_ticket, nombre_estado_ticket) VALUES
    (1, 'VALIDO'),
    (2, 'USADO'),
    (3, 'CANCELADO'),
    (4, 'TRANSFERIDO');

ALTER TABLE ticket
    ADD CONSTRAINT fk_ticket_estado_ticket
    FOREIGN KEY (estado_ticket_id_estado) REFERENCES estado_ticket(id_estado_ticket);
