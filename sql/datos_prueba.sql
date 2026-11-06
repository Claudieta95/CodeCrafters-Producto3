USE codecrafters_producto3;

-- ==========================================
-- DATOS DE PRUEBA: HOTELES
-- ==========================================

INSERT INTO hotel (nombre, ciudad, estrellas)
SELECT 'Hotel Demo Madrid', 'Madrid', 4
WHERE NOT EXISTS (
    SELECT 1 FROM hotel
    WHERE nombre = 'Hotel Demo Madrid'
      AND ciudad = 'Madrid'
);

INSERT INTO hotel (nombre, ciudad, estrellas)
SELECT 'Hotel Demo Valencia', 'Valencia', 3
WHERE NOT EXISTS (
    SELECT 1 FROM hotel
    WHERE nombre = 'Hotel Demo Valencia'
      AND ciudad = 'Valencia'
);

-- ==========================================
-- DATOS DE PRUEBA: PERSONAS Y CLIENTES
-- ==========================================

INSERT INTO persona (nombre, apellido, dni, email, telefono)
VALUES (
    'Claudia',
    'Maldonado',
    'DNI37697T',
    'claudia@example.com',
    '000000001'
)
ON DUPLICATE KEY UPDATE id_persona = id_persona;

INSERT INTO cliente (id_persona)
SELECT p.id_persona
FROM persona AS p
WHERE p.dni = 'DNI37697T'
  AND NOT EXISTS (
      SELECT 1
      FROM cliente AS c
      WHERE c.id_persona = p.id_persona
  );

INSERT INTO persona (nombre, apellido, dni, email, telefono)
VALUES (
    'Daniella',
    'Alarcon',
    'DNI50581R',
    'daniella@example.com',
    '000000002'
)
ON DUPLICATE KEY UPDATE id_persona = id_persona;

INSERT INTO cliente (id_persona)
SELECT p.id_persona
FROM persona AS p
WHERE p.dni = 'DNI50581R'
  AND NOT EXISTS (
      SELECT 1
      FROM cliente AS c
      WHERE c.id_persona = p.id_persona
  );

  -- ==========================================
-- DATOS DE PRUEBA: HABITACIONES
-- ==========================================

-- Habitaciones del Hotel Demo Madrid

INSERT INTO habitacion (numero, categoria, capacidad, precio_noche, id_hotel)
SELECT 101, 'Individual', 1, 55.00, h.id_hotel
FROM hotel h
WHERE h.nombre = 'Hotel Demo Madrid'
  AND h.ciudad = 'Madrid'
  AND NOT EXISTS (
      SELECT 1
      FROM habitacion ha
      WHERE ha.id_hotel = h.id_hotel
        AND ha.numero = 101
  );

INSERT INTO habitacion (numero, categoria, capacidad, precio_noche, id_hotel)
SELECT 102, 'Doble', 2, 80.00, h.id_hotel
FROM hotel h
WHERE h.nombre = 'Hotel Demo Madrid'
  AND h.ciudad = 'Madrid'
  AND NOT EXISTS (
      SELECT 1
      FROM habitacion ha
      WHERE ha.id_hotel = h.id_hotel
        AND ha.numero = 102
  );

INSERT INTO habitacion (numero, categoria, capacidad, precio_noche, id_hotel)
SELECT 103, 'Triple', 3, 110.00, h.id_hotel
FROM hotel h
WHERE h.nombre = 'Hotel Demo Madrid'
  AND h.ciudad = 'Madrid'
  AND NOT EXISTS (
      SELECT 1
      FROM habitacion ha
      WHERE ha.id_hotel = h.id_hotel
        AND ha.numero = 103
  );

-- Habitaciones del Hotel Demo Valencia

INSERT INTO habitacion (numero, categoria, capacidad, precio_noche, id_hotel)
SELECT 101, 'Individual', 1, 50.00, h.id_hotel
FROM hotel h
WHERE h.nombre = 'Hotel Demo Valencia'
  AND h.ciudad = 'Valencia'
  AND NOT EXISTS (
      SELECT 1
      FROM habitacion ha
      WHERE ha.id_hotel = h.id_hotel
        AND ha.numero = 101
  );

INSERT INTO habitacion (numero, categoria, capacidad, precio_noche, id_hotel)
SELECT 102, 'Doble', 2, 75.00, h.id_hotel
FROM hotel h
WHERE h.nombre = 'Hotel Demo Valencia'
  AND h.ciudad = 'Valencia'
  AND NOT EXISTS (
      SELECT 1
      FROM habitacion ha
      WHERE ha.id_hotel = h.id_hotel
        AND ha.numero = 102
  );