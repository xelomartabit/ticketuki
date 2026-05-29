-- Eliminar FK y tabla local de estado_ticket (los estados pasan a gestionarse por ms-estado)
ALTER TABLE ticket DROP FOREIGN KEY IF EXISTS ticket_ibfk_1;
DROP TABLE IF EXISTS estado_ticket;
