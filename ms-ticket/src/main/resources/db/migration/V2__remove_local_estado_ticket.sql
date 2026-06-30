-- Eliminar FK de ticket hacia estado_ticket y luego la tabla local
ALTER TABLE ticket DROP FOREIGN KEY IF EXISTS `1`;
ALTER TABLE ticket DROP FOREIGN KEY IF EXISTS ticket_ibfk_1;
ALTER TABLE ticket DROP COLUMN IF EXISTS estado_ticket_id_estado;
DROP TABLE IF EXISTS estado_ticket;
