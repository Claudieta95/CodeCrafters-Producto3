USE codecrafters_producto3;

CREATE TABLE IF NOT EXISTS empleado (
    id_persona INT PRIMARY KEY,
    puesto VARCHAR(100) NOT NULL,
    CONSTRAINT fk_empleado_persona
        FOREIGN KEY (id_persona)
        REFERENCES persona(id_persona)
        ON DELETE CASCADE
) ENGINE=InnoDB;
