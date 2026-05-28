-- Cambiar fecha_venta de DATE a DATETIME para incluir hora
ALTER TABLE venta MODIFY COLUMN fecha_venta DATETIME NOT NULL;

-- Eliminar FK antes de borrar la tabla
ALTER TABLE venta DROP FOREIGN KEY venta_ibfk_1;

-- Eliminar tabla estado_venta local (los estados se gestionan en ms-estado)
DROP TABLE estado_venta;
