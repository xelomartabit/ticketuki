-- DIRECCION_RECINTO
INSERT INTO direccion_recinto (id_direccion_recinto, calle, region, comuna, num_calle, referencia_acceso) VALUES
(1,'Av. Grecia','Region Metropolitana','Nunoa',2001,'Sin referencia'),
(2,'Av. Beauchef','Region Metropolitana','Santiago',1204,'Sin referencia'),
(3,'Av. Manuel Montt','Region Metropolitana','Providencia',032,'Sin referencia');

-- RECINTO
INSERT INTO recinto (id_recinto, nombre_recinto, capacidad_total, direccion_recinto_id_direccion) VALUES
(1,'Estadio Nacional',100,1),
(2,'Movistar Arena',70,2),
(3,'Nescafe de las Artes',50,3);

-- SECTOR
INSERT INTO sector (id_sector, nombre_sector, capacidad_sector, precio_sector, recinto_id_recinto) VALUES
(1,'Cancha Estadio Nacional',70,50000,1),
(2,'Platea Estadio Nacional',30,70000,1),
(3,'Cancha Movistar Arena',50,75000,2),
(4,'Platea Movistar Arena',20,100000,2),
(5,'Platea Nescafe de las Artes',30,30000,3),
(6,'Palco Nescafe de las Artes',20,45000,3);

