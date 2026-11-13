-- MySQL dump 10.13  Distrib 8.4.13, for Win64 (x86_64)
--
-- Host: localhost    Database: codecrafters_producto3
-- ------------------------------------------------------
-- Server version	8.4.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `codecrafters_producto3`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `codecrafters_producto3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `codecrafters_producto3`;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_persona` int NOT NULL,
  PRIMARY KEY (`id_persona`),
  CONSTRAINT `fk_cliente_persona` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (2),(3),(5),(6);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `id_persona` int NOT NULL,
  `puesto` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_persona`),
  CONSTRAINT `fk_empleado_persona` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `habitacion`
--

DROP TABLE IF EXISTS `habitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `habitacion` (
  `id_habitacion` int NOT NULL AUTO_INCREMENT,
  `numero` int NOT NULL,
  `categoria` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `capacidad` int NOT NULL,
  `precio_noche` decimal(10,2) NOT NULL,
  `id_hotel` int NOT NULL,
  PRIMARY KEY (`id_habitacion`),
  UNIQUE KEY `uk_habitacion_hotel_numero` (`id_hotel`,`numero`),
  CONSTRAINT `fk_habitacion_hotel` FOREIGN KEY (`id_hotel`) REFERENCES `hotel` (`id_hotel`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_habitacion_capacidad` CHECK ((`capacidad` > 0)),
  CONSTRAINT `chk_habitacion_precio` CHECK ((`precio_noche` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `habitacion`
--

LOCK TABLES `habitacion` WRITE;
/*!40000 ALTER TABLE `habitacion` DISABLE KEYS */;
INSERT INTO `habitacion` VALUES (1,101,'Individual',1,55.00,1),(2,102,'Doble',2,85.00,1),(3,103,'Triple',3,110.00,1),(4,104,'Suite',4,160.00,1),(5,201,'Individual',1,50.00,2),(6,202,'Doble',2,80.00,2),(7,203,'Triple',3,105.00,2),(8,301,'Individual',1,65.00,3),(9,302,'Doble',2,95.00,3),(10,303,'Suite',4,180.00,3),(11,401,'Individual',1,60.00,4),(12,402,'Doble',2,90.00,4),(13,403,'Triple',3,120.00,4),(14,101,'Individual',1,55.00,3),(15,102,'Doble',2,80.00,3),(16,103,'Triple',3,110.00,3),(17,101,'Individual',1,50.00,4),(18,102,'Doble',2,75.00,4);
/*!40000 ALTER TABLE `habitacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel` (
  `id_hotel` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ciudad` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `estrellas` int NOT NULL,
  PRIMARY KEY (`id_hotel`),
  CONSTRAINT `chk_hotel_estrellas` CHECK ((`estrellas` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES (1,'Hotel Prueba Barcelona','Barcelona',4),(2,'Hotel Prueba Girona','Girona',3),(3,'Hotel Demo Madrid','Madrid',4),(4,'Hotel Demo Valencia','Valencia',3);
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `linea_reserva`
--

DROP TABLE IF EXISTS `linea_reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `linea_reserva` (
  `id_reserva` int NOT NULL,
  `id_habitacion` int NOT NULL,
  `precio_noche` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_reserva`,`id_habitacion`),
  KEY `fk_linea_reserva_habitacion` (`id_habitacion`),
  CONSTRAINT `fk_linea_reserva_habitacion` FOREIGN KEY (`id_habitacion`) REFERENCES `habitacion` (`id_habitacion`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_linea_reserva_reserva` FOREIGN KEY (`id_reserva`) REFERENCES `reserva` (`id_reserva`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chk_linea_reserva_precio` CHECK ((`precio_noche` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `linea_reserva`
--

LOCK TABLES `linea_reserva` WRITE;
/*!40000 ALTER TABLE `linea_reserva` DISABLE KEYS */;
INSERT INTO `linea_reserva` VALUES (1,2,85.00),(2,1,55.00),(3,6,80.00);
/*!40000 ALTER TABLE `linea_reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `id_persona` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `dni` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `telefono` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_persona`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (2,'Claudia','Maldonado','47570508E','cmaldonadoq@uoc.edu','685280175'),(3,'Claudia','Maldonado','DNI37697T','claudia@example.com','000000001'),(5,'Daniella','Alarcon','DNI50581R','daniella@example.com','000000002'),(6,'Marta','L�pez','TEST0003','marta@example.com','600000750');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva` (
  `id_reserva` int NOT NULL AUTO_INCREMENT,
  `fecha_entrada` date NOT NULL,
  `fecha_salida` date NOT NULL,
  `numero_personas` int NOT NULL,
  `estado` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Confirmada',
  `importe` decimal(10,2) NOT NULL DEFAULT '0.00',
  `id_cliente` int NOT NULL,
  PRIMARY KEY (`id_reserva`),
  KEY `fk_reserva_cliente` (`id_cliente`),
  CONSTRAINT `fk_reserva_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_persona`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_reserva_fechas` CHECK ((`fecha_salida` > `fecha_entrada`)),
  CONSTRAINT `chk_reserva_importe` CHECK ((`importe` >= 0)),
  CONSTRAINT `chk_reserva_personas` CHECK ((`numero_personas` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserva`
--

LOCK TABLES `reserva` WRITE;
/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
INSERT INTO `reserva` VALUES (1,'2026-11-12','2026-11-22',2,'Cancelada',850.00,6),(2,'2027-01-10','2027-01-12',1,'Confirmada',110.00,5),(3,'2026-11-16','2026-11-21',2,'Cancelada',400.00,5);
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'codecrafters_producto3'
--

--
-- Dumping routines for database 'codecrafters_producto3'
--
/*!50003 DROP PROCEDURE IF EXISTS `buscar_hoteles_por_ciudad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `buscar_hoteles_por_ciudad`(
    IN p_ciudad VARCHAR(100)
)
BEGIN
    SELECT id_hotel, nombre, ciudad, estrellas
    FROM hotel
    WHERE ciudad LIKE CONCAT('%', p_ciudad, '%')
    ORDER BY nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cancelar_reserva` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cancelar_reserva`(
    IN p_id_reserva INT,
    OUT p_filas_afectadas INT
)
BEGIN
    UPDATE reserva
    SET estado = 'Cancelada'
    WHERE id_reserva = p_id_reserva
      AND estado <> 'Cancelada';

    SET p_filas_afectadas = ROW_COUNT();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-13 19:37:20
