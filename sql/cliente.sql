USE codecrafters_producto3;

CREATE TABLE IF NOT EXISTS cliente (
    id_persona INT PRIMARY KEY,
    CONSTRAINT fk_cliente_persona
        FOREIGN KEY (id_persona)
        REFERENCES persona(id_persona)
        ON DELETE CASCADE
) ENGINE=InnoDB;
