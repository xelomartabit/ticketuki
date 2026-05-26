CREATE TABLE direccion_recinto (
    id_direccion_recinto BIGINT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(50),
    region VARCHAR(50),
    comuna VARCHAR(50),
    num_calle INTEGER,
    referencia_acceso VARCHAR(100)
);

CREATE TABLE recinto (
    id_recinto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_recinto VARCHAR(50) NOT NULL,
    capacidad_total INTEGER NOT NULL,
    direccion_recinto_id_direccion BIGINT,
    FOREIGN KEY (direccion_recinto_id_direccion) REFERENCES direccion_recinto(id_direccion_recinto)
);

CREATE TABLE sector (
    id_sector BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_sector VARCHAR(25) NOT NULL,
    capacidad_sector INTEGER NOT NULL,
    precio_sector INTEGER NOT NULL,
    recinto_id_recinto BIGINT NOT NULL,
    FOREIGN KEY (recinto_id_recinto) REFERENCES recinto(id_recinto)
);
