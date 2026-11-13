
USE codecrafters_producto3;

-- =========================================
-- TABLA: habitacion
-- =========================================

CREATE TABLE IF NOT EXISTS habitacion (
    id_habitacion INT NOT NULL AUTO_INCREMENT,
    numero INT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    capacidad INT NOT NULL,
    precio_noche DECIMAL(10,2) NOT NULL,
    id_hotel INT NOT NULL,

    PRIMARY KEY (id_habitacion),

    UNIQUE KEY uk_habitacion_hotel_numero (id_hotel, numero),

    CONSTRAINT fk_habitacion_hotel
        FOREIGN KEY (id_hotel)
        REFERENCES hotel(id_hotel)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT chk_habitacion_capacidad
        CHECK (capacidad > 0),

    CONSTRAINT chk_habitacion_precio
        CHECK (precio_noche >= 0)
) ENGINE=InnoDB;


-- =========================================
-- TABLA: reserva
-- =========================================

CREATE TABLE IF NOT EXISTS reserva (
    id_reserva INT NOT NULL AUTO_INCREMENT,
    fecha_entrada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    numero_personas INT NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'Confirmada',
    importe DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    id_cliente INT NOT NULL,

    PRIMARY KEY (id_reserva),

    CONSTRAINT fk_reserva_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES cliente(id_persona)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT chk_reserva_fechas
        CHECK (fecha_salida > fecha_entrada),

    CONSTRAINT chk_reserva_personas
        CHECK (numero_personas > 0),

    CONSTRAINT chk_reserva_importe
        CHECK (importe >= 0)
) ENGINE=InnoDB;


-- =========================================
-- TABLA: linea_reserva
-- =========================================

CREATE TABLE IF NOT EXISTS linea_reserva (
    id_reserva INT NOT NULL,
    id_habitacion INT NOT NULL,
    precio_noche DECIMAL(10,2) NOT NULL,

    PRIMARY KEY (id_reserva, id_habitacion),

    CONSTRAINT fk_linea_reserva_reserva
        FOREIGN KEY (id_reserva)
        REFERENCES reserva(id_reserva)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_linea_reserva_habitacion
        FOREIGN KEY (id_habitacion)
        REFERENCES habitacion(id_habitacion)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT chk_linea_reserva_precio
        CHECK (precio_noche >= 0)
) ENGINE=InnoDB;


-- =========================================
-- DATOS DE PRUEBA: habitaciones
-- =========================================

INSERT INTO habitacion
    (numero, categoria, capacidad, precio_noche, id_hotel)
VALUES
    (101, 'Individual', 1, 55.00, 1),
    (102, 'Doble',      2, 85.00, 1),
    (103, 'Triple',     3, 110.00, 1),
    (104, 'Suite',      4, 160.00, 1),

    (201, 'Individual', 1, 50.00, 2),
    (202, 'Doble',      2, 80.00, 2),
    (203, 'Triple',     3, 105.00, 2),

    (301, 'Individual', 1, 65.00, 3),
    (302, 'Doble',      2, 95.00, 3),
    (303, 'Suite',      4, 180.00, 3),

    (401, 'Individual', 1, 60.00, 4),
    (402, 'Doble',      2, 90.00, 4),
    (403, 'Triple',     3, 120.00, 4)

ON DUPLICATE KEY UPDATE
    categoria = VALUES(categoria),
    capacidad = VALUES(capacidad),
    precio_noche = VALUES(precio_noche);


-- =========================================
-- COMPROBACIONES
-- =========================================

SELECT * FROM habitacion ORDER BY id_hotel, numero;

SELECT * FROM reserva;

SELECT * FROM linea_reserva;
