USE codecrafters_producto3;

DROP PROCEDURE IF EXISTS buscar_hoteles_por_ciudad;

DELIMITER $$

CREATE PROCEDURE buscar_hoteles_por_ciudad(
    IN p_ciudad VARCHAR(100)
)
BEGIN
    SELECT id_hotel, nombre, ciudad, estrellas
    FROM hotel
    WHERE ciudad LIKE CONCAT('%', p_ciudad, '%')
    ORDER BY nombre;
END $$

DELIMITER ;