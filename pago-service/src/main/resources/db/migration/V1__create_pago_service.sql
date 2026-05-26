CREATE TABLE pago (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10,2) NOT NULL,
    medio_pago VARCHAR(50),
    cod_autorizacion VARCHAR(50) NOT NULL,
    timestamp DATE,
    estado VARCHAR(25),
    venta_id BIGINT,
    usuario_id BIGINT
);
