CREATE TABLE IF NOT EXISTS direccionUsuario (
    id_direccion_usuario INT PRIMARY KEY AUTO_INCREMENT,
    calle VARCHAR(100) NOT NULL,
    num_calle INT NOT NULL,
    comuna VARCHAR(50) NOT NULL,
    region VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    run VARCHAR(12) NOT NULL UNIQUE,
    p_nombre VARCHAR(25) NOT NULL,
    s_nombre VARCHAR(25),
    a_paterno VARCHAR(25) NOT NULL,
    a_materno VARCHAR(25) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    id_direccion_usuario INT NOT NULL,
    FOREIGN KEY (id_direccion_usuario) REFERENCES direccionUsuario(id_direccion_usuario) ON DELETE CASCADE
);