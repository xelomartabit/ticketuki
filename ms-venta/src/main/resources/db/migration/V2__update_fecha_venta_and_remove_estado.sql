-- Cambiar fecha_venta de DATE a DATETIME para incluir hora
ALTER TABLE venta MODIFY COLUMN fecha_venta DATETIME NOT NULL;

-- Eliminar FK antes de borrar la tabla (si existe)
ALTER TABLE venta DROP FOREIGN KEY IF EXISTS venta_ibfk_1;
ALTER TABLE venta DROP INDEX IF EXISTS estado_venta_id_estado;

-- Eliminar tabla estado_venta local (los estados se gestionan en ms-estado)
DROP TABLE IF EXISTS estado_venta;
