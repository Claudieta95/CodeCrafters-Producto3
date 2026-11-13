USE codecrafters_producto3;

DROP PROCEDURE IF EXISTS cancelar_reserva;

DELIMITER $$

CREATE PROCEDURE cancelar_reserva(
    IN p_id_reserva INT,
    OUT p_filas_afectadas INT
)
BEGIN
    UPDATE reserva
    SET estado = 'Cancelada'
    WHERE id_reserva = p_id_reserva
      AND estado <> 'Cancelada';

    SET p_filas_afectadas = ROW_COUNT();
END$$

DELIMITER ;
