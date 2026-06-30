-- Renombrar estado_evento 'EN VENTA' a 'DISPONIBLE'
UPDATE estado_evento SET nombre_estado_evento = 'DISPONIBLE'
WHERE UPPER(nombre_estado_evento) IN ('EN VENTA', 'EN_VENTA', 'DISPONIBLE');
