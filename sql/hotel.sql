USE codecrafters_producto3;

CREATE TABLE IF NOT EXISTS hotel (
    id_hotel INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    estrellas INT NOT NULL,
    CONSTRAINT chk_hotel_estrellas
        CHECK (estrellas BETWEEN 1 AND 5)
) ENGINE=InnoDB;
