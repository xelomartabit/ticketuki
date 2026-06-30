-- Restaura la columna estado_ticket_id_estado en ticket como referencia simple (sin FK).
-- La FK local fue eliminada en V2 porque el catálogo de estados se delegó a ms-estado.
-- El valor sigue siendo el ID del estado en ms-estado, pero la integridad se valida en tiempo de ejecución.
ALTER TABLE ticket ADD COLUMN IF NOT EXISTS estado_ticket_id_estado BIGINT;
