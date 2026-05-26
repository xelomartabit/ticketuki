CREATE TABLE direccion_usuario (
    id_direccion_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(50),
    region VARCHAR(50),
    comuna VARCHAR(50),
    num_calle INTEGER
);

CREATE TABLE usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    run VARCHAR(12) NOT NULL UNIQUE,
    p_nombre VARCHAR(25) NOT NULL,
    s_nombre VARCHAR(25),
    a_materno VARCHAR(25) NOT NULL,
    a_paterno VARCHAR(25) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    direccion_usuario_id_direccion BIGINT,
    FOREIGN KEY (direccion_usuario_id_direccion) REFERENCES direccion_usuario(id_direccion_usuario)
);
