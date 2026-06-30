-- Normalizar estado_ticket a mayúsculas sin tildes
UPDATE estado_ticket SET nombre_estado_ticket = 'VALIDO'      WHERE nombre_estado_ticket IN ('Válido',    'VÁLIDO',    'valido',    'Valido');
UPDATE estado_ticket SET nombre_estado_ticket = 'USADO'       WHERE nombre_estado_ticket IN ('Usado',     'USADO',     'usado');
UPDATE estado_ticket SET nombre_estado_ticket = 'CANCELADO'   WHERE nombre_estado_ticket IN ('Cancelado', 'CANCELADO', 'cancelado');
UPDATE estado_ticket SET nombre_estado_ticket = 'TRANSFERIDO' WHERE nombre_estado_ticket IN ('Transferido','TRANSFERIDO','transferido');

-- Normalizar estado_venta a mayúsculas sin tildes
UPDATE estado_venta SET nombre_estado_venta = 'PENDIENTE'   WHERE nombre_estado_venta IN ('Pendiente',   'PENDIENTE',   'pendiente');
UPDATE estado_venta SET nombre_estado_venta = 'CONFIRMADA'  WHERE nombre_estado_venta IN ('Confirmada',  'CONFIRMADA',  'confirmada');
UPDATE estado_venta SET nombre_estado_venta = 'CANCELADA'   WHERE nombre_estado_venta IN ('Cancelada',   'CANCELADA',   'cancelada');
UPDATE estado_venta SET nombre_estado_venta = 'REEMBOLSADA' WHERE nombre_estado_venta IN ('Reembolsada', 'REEMBOLSADA', 'reembolsada');
UPDATE estado_venta SET nombre_estado_venta = 'ANULADA'     WHERE nombre_estado_venta IN ('Anulada',     'anulada');

-- Normalizar estado_evento a mayúsculas sin tildes
UPDATE estado_evento SET nombre_estado_evento = 'AGENDADO'   WHERE nombre_estado_evento IN ('Agendado',  'AGENDADO',  'agendado');
UPDATE estado_evento SET nombre_estado_evento = 'DISPONIBLE' WHERE nombre_estado_evento IN ('En Venta',  'EN VENTA',  'en venta',  'DISPONIBLE', 'Disponible', 'disponible');
UPDATE estado_evento SET nombre_estado_evento = 'CANCELADO'  WHERE nombre_estado_evento IN ('Cancelado', 'CANCELADO', 'cancelado');
UPDATE estado_evento SET nombre_estado_evento = 'FINALIZADO' WHERE nombre_estado_evento IN ('Finalizado','FINALIZADO','finalizado');
