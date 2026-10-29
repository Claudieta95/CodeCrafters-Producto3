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