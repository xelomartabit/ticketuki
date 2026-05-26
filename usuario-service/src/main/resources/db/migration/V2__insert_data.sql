-- DIRECCION
INSERT INTO direccion_usuario (calle, num_calle, comuna, region) VALUES
('Av. Irarrazaval', '3301', 'Ñuñoa','Region Metropolitana'),
('Antonio Varas', '666','Providencia','Region Metropolitana'),
('Av. Cristobal Colon','4330','Las Condes','Region Metropolitana');

-- USUARIOS
INSERT INTO usuario (run, p_nombre, s_nombre, a_paterno, a_materno, email, direccion_usuario_id_direccion) VALUES
('18.356.204-5', 'Marcelo','Andres','Martabit','Donoso','ma.martabit@duocuc.cl',1),
('12.345.678-9', 'Luz', 'Clara', 'Solar','Tesla','luz.solar@gmail.com',2),
('17.579.054-3', 'Paulina', 'Francisca', 'Gonzalez','Castro','pauli.gonzalezc@duocuc.cl',3);