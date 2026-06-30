-- Los estados de ticket ahora los gestiona ms-estado.
-- Se elimina la FK local y la tabla, el ID queda como referencia simple.
ALTER TABLE ticket DROP FOREIGN KEY IF EXISTS fk_ticket_estado_ticket;
DROP TABLE IF EXISTS estado_ticket;
