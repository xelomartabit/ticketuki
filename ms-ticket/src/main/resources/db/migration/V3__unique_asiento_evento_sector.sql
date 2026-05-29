-- Garantiza que no existan dos tickets para el mismo asiento en el mismo evento y sector
ALTER TABLE ticket ADD CONSTRAINT uq_asiento_evento_sector UNIQUE (num_asiento, evento_id_evento, sector_id_sector);
