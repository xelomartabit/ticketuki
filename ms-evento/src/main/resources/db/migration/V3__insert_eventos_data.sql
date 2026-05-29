-- Eliminar FK constraints hacia otros microservicios (estado_evento y recinto son BD externas)
-- En arquitectura de microservicios cada servicio gestiona su propia BD;
-- la integridad referencial entre servicios se garantiza a nivel de aplicación, no de BD.
ALTER TABLE evento DROP FOREIGN KEY IF EXISTS evento_ibfk_1;
ALTER TABLE evento DROP FOREIGN KEY IF EXISTS evento_ibfk_2;

-- Datos iniciales de eventos
INSERT INTO evento (id_evento, nombre_evento, aforo_evento, fecha_evento, descripcion, estado_evento_id_estado, recinto_id_recinto) VALUES
(1, 'Gorillaz The Mountain Tour', 100, '2026-06-15',
 'La icónica banda virtual británica Gorillaz llega con su esperado tour mundial. Una experiencia audiovisual única que combina música alternativa, hip-hop y arte digital con sus personajes animados en pantallas gigantes.',
 2, 1),

(2, 'Metallica 72 Seasons World Tour', 70, '2026-07-15',
 'Los reyes del heavy metal regresan con su gira mundial 72 Seasons. Más de cuatro décadas de carrera resumidas en un show de más de tres horas con pirotecnia, efectos especiales y los clásicos que los llevaron a la cima del metal.',
 1, 2),

(3, 'Daft Punk Alive 2026', 70, '2026-08-20',
 'El dúo de música electrónica más influyente de la historia vuelve a los escenarios con su icónica pirámide de luces LED. Una noche épica de electronic house, disco y funk que promete ser el evento del año.',
 1, 2),

(4, 'Justice Woman Worldwide Tour', 50, '2026-09-10',
 'El dúo francés de electrónica Justice presenta su gira Woman Worldwide, un espectáculo de sonido e iluminación diseñado para hacer vibrar cada rincón del recinto. Cross, Audio Video Disco y sus mejores hits en una sola noche.',
 1, 3);
