INSERT IGNORE INTO artista (id_artista, nombre_artista, genero_artista, redes_sociales) VALUES
(1, 'Metallica',  'Metal',       'https://www.instagram.com/metallica'),
(2, 'Daft Punk',  'Electronica', 'https://www.instagram.com/daftpunk'),
(3, 'Justice',    'Electronica', 'https://www.instagram.com/etjusticepourtous'),
(4, 'Gorillaz',   'Alternativo', 'https://www.instagram.com/gorillaz');

INSERT IGNORE INTO artista_evento (artista_id_artista, evento_id_evento) VALUES
(4, 1),
(1, 2),
(2, 3),
(3, 4);
