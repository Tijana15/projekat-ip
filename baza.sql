CREATE DATABASE  IF NOT EXISTS `urban_motion` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `urban_motion`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: urban_motion
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `breakdown`
--

DROP TABLE IF EXISTS `breakdown`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `breakdown` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `breakdown_date_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `vehicle_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb01qph3xndijdefc3cclrm318` (`vehicle_id`),
  CONSTRAINT `FKb01qph3xndijdefc3cclrm318` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `breakdown`
--

LOCK TABLES `breakdown` WRITE;
/*!40000 ALTER TABLE `breakdown` DISABLE KEYS */;
INSERT INTO `breakdown` VALUES (1,'2023-10-26 10:00:00.000000','Motor se pregrijava i čuje se čudan zvuk.','CAR-001'),(3,'2025-08-06 11:30:00.000000','Ajmooouuuuuu','CAR-001'),(4,'2025-08-06 11:46:00.000000','Idemo da vidimo','CAR-002'),(5,'2025-08-09 12:29:00.000000','Ošlo vinklo nece','CAR-002');
/*!40000 ALTER TABLE `breakdown` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
  `description` varchar(255) DEFAULT NULL,
  `purchase_date` datetime(6) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKfugwdpykh9kb35q1quro44hrm` FOREIGN KEY (`id`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES ('Electric sedan with long range','2023-01-15 10:30:00.000000','CAR-001'),('Electric sedan with long rangeee','2023-04-14 22:00:00.000000','CAR-002'),('Very nice car. Also comfort.','2001-01-31 23:00:00.000000','CAR-003'),('Comfort for city rides along Vrbas river.','2025-06-30 20:00:00.000000','CAR-004');
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `avatar_picture` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `id_document` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  `blocked` bit(1) NOT NULL,
  `drivers_licence` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK4ai00u1wkkeysdaq92yicsf6e` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('4.png','dj@gmail.com','LK-001','066569874',4,_binary '\0','VD_001'),(NULL,'jovana@gmail.com','LK_002','066888555',7,_binary '\0','VD_002'),('8.png','lazendictijana22@gmail.com','LK_007','066874123',8,_binary '\0','VD_007'),('9.png','andrej.tomic@student.etf.unibl.org','LK_009','066555222',9,_binary '\0','VD_009');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ebike`
--

DROP TABLE IF EXISTS `ebike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ebike` (
  `max_range` int DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKgvp7htrgmjylpjr8t5m7kubni` FOREIGN KEY (`id`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ebike`
--

LOCK TABLES `ebike` WRITE;
/*!40000 ALTER TABLE `ebike` DISABLE KEYS */;
INSERT INTO `ebike` VALUES (40,'EBIKE-004'),(60,'EBIKE-005');
/*!40000 ALTER TABLE `ebike` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKd8il4lxw1wi74qh8b7uoy6e0a` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1),(2),(5),(6);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `escooter`
--

DROP TABLE IF EXISTS `escooter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `escooter` (
  `max_speed` int DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKls8elskue7ylodao8qdhsktxg` FOREIGN KEY (`id`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `escooter`
--

LOCK TABLES `escooter` WRITE;
/*!40000 ALTER TABLE `escooter` DISABLE KEYS */;
INSERT INTO `escooter` VALUES (27,'ESCOOTER-001'),(25,'SCOOTER-003');
/*!40000 ALTER TABLE `escooter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manufacturer`
--

DROP TABLE IF EXISTS `manufacturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufacturer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufacturer`
--

LOCK TABLES `manufacturer` WRITE;
/*!40000 ALTER TABLE `manufacturer` DISABLE KEYS */;
INSERT INTO `manufacturer` VALUES (6,'2500 Shingai, Iwata, Shizuoka 438-8501, Japan','+81-538-37-4250','info@yamaha-motor.co.jp','Yamaha Motor Company','+81-538-32-1103','Japan'),(7,'2-1-1 Minami-Aoyama, Minato-ku, Tokyo 107-8556, Japan','+81-3-5412-1515','contact@honda.co.jp','Honda Motor Co., Ltd.','+81-3-3423-1111','Japan'),(10,'BMW Headquarters, Petuelring 130, 80809 München','777777','bmw@business.com','BMW AG','+49 89 3820','Germany');
/*!40000 ALTER TABLE `manufacturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'Content1 of post1, something new about our fiction company ETFBL_IP','2025-08-13 07:38:54.893361','Title1 of post1'),(2,'New amazing content of fiction companyyy','2025-08-13 09:30:19.171865','Title2 of post2'),(3,'You love BMW, Xiaomi and Tesla? Check out now in our app!','2025-08-19 09:42:11.661274','New model of escooters available.');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` VALUES (2,'2025-10-10 09:31:00.000000','Neew description2 because our company is safe for rentals.','2025-10-14 21:31:00.000000','Promotion2'),(3,'2025-08-22 09:49:00.000000','Try out now in our client app.','2025-08-30 09:49:00.000000','ACTION! -10% on new models of escooters!');
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rental`
--

DROP TABLE IF EXISTS `rental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rental` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) DEFAULT NULL,
  `duration_seconds` int NOT NULL,
  `endx` double DEFAULT NULL,
  `endy` double DEFAULT NULL,
  `startx` double DEFAULT NULL,
  `starty` double DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  `vehicle_id` varchar(255) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfurpp295i3dhumquorur054dw` (`client_id`),
  KEY `FK6qohlnx46y05m5bykquuafobx` (`vehicle_id`),
  CONSTRAINT `FK6qohlnx46y05m5bykquuafobx` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`),
  CONSTRAINT `FKfurpp295i3dhumquorur054dw` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rental`
--

LOCK TABLES `rental` WRITE;
/*!40000 ALTER TABLE `rental` DISABLE KEYS */;
INSERT INTO `rental` VALUES (1,'2025-08-06 10:30:00.000000',250,44.7723,17.1897,44.767418,17.187272,4,'CAR-001',_binary '\0',2.75),(4,'2025-08-17 11:48:51.240585',72,44.75794630522955,17.200027726876506,44.77035,17.18965,4,'CAR-002',NULL,2.1),(9,'2025-08-18 11:36:35.509614',40,44.77258949150789,17.19679748435979,44.77122154679681,17.178650574992627,8,'ESCOOTER-001',_binary '\0',0.18000000000000002),(12,'2025-08-18 12:42:58.574484',124,44.78563008140567,17.195551967552504,44.75794630522955,17.200027726876506,8,'CAR-002',_binary '\0',3.6166666666666667),(13,'2025-08-18 14:34:52.880490',5,44.757212055745256,17.195056345003408,44.772184,17.19101,4,'CAR-001',_binary '\0',0.14583333333333334),(14,'2025-08-18 15:00:59.648061',46,44.779004668758546,17.205039898498484,44.77844501286178,17.17461748670855,4,'EBIKE-005',_binary '\0',0.9276666666666666),(15,'2025-08-18 18:31:29.787630',2,44.78369041355826,17.194862245253443,0,0,4,'CAR-003',_binary '\0',0.058333333333333334),(16,'2025-08-19 09:13:57.520819',51,44.77441537885147,17.188745464521617,44.77258949150789,17.19679748435979,9,'ESCOOTER-001',_binary '\0',0.22950000000000004),(17,'2025-08-19 09:15:27.519347',14,44.75338880828782,17.202252566010912,44.78369041355826,17.194862245253443,9,'CAR-003',_binary '\0',0.4083333333333333),(18,'2025-08-19 09:28:12.848398',99,44.7536159985264,17.178134878498323,44.779004668758546,17.205039898498484,4,'EBIKE-005',_binary '\0',1.9965);
/*!40000 ALTER TABLE `rental` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rental_price_config`
--

DROP TABLE IF EXISTS `rental_price_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rental_price_config` (
  `vehicle_type` enum('CAR','EBIKE','ESCOOTER') NOT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`vehicle_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rental_price_config`
--

LOCK TABLES `rental_price_config` WRITE;
/*!40000 ALTER TABLE `rental_price_config` DISABLE KEYS */;
INSERT INTO `rental_price_config` VALUES ('CAR',10),('EBIKE',1.21),('ESCOOTER',0.3);
/*!40000 ALTER TABLE `rental_price_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','CLIENT','MANAGEMENT','OPERATOR') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `drivers_licence` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Tijana','Lazendić','tijana','ADMIN','Tijana',NULL),(2,'Dejan','Janjić','dejan','OPERATOR','dejan',NULL),(4,'Dragana','Jović','dragana','CLIENT','dragana',NULL),(5,'Tamara','Kosovac','tamara','OPERATOR','tamara',NULL),(6,'Menadzer1','Menadzer1','menadzer','MANAGEMENT','menadzer1',NULL),(7,'Jovana','Jovanic','jovana','CLIENT','jovana',NULL),(8,'Marija','Djumic','marija','CLIENT','marija',NULL),(9,'Andrej','TomiÄ','toma','CLIENT','toma',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle` (
  `id` varchar(255) NOT NULL,
  `model` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `purchase_price` double DEFAULT NULL,
  `vehicle_state` enum('AVAILABLE','BROKEN','RENTED') DEFAULT NULL,
  `manufacturer_id` bigint DEFAULT NULL,
  `mapx` double DEFAULT NULL,
  `mapy` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc6y2tuc57qy6qi28dqjeerodl` (`manufacturer_id`),
  CONSTRAINT `FKc6y2tuc57qy6qi28dqjeerodl` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES ('CAR-001','Model 3','car.png',45000,'BROKEN',6,44.757212055745256,17.195056345003408),('CAR-002','Model 3','car1.png',45000,'BROKEN',7,44.78563008140567,17.195551967552504),('CAR-003','AJKULA','car.png',14500,'AVAILABLE',10,44.75338880828782,17.202252566010912),('CAR-004','XS589','car1.png',17800,'AVAILABLE',6,17.18913953782617,44.77039351668075),('EBIKE-004','SUV22','ebike.png',3100,'AVAILABLE',6,44.77123384496009,17.189284130248193),('EBIKE-005','Urban Cruiser','ebike1.png',1270,'AVAILABLE',10,44.7536159985264,17.178134878498323),('ESCOOTER-001','NMAX 155','escooter1.png',457,'AVAILABLE',6,44.77441537885147,17.188745464521617),('SCOOTER-003','Mi Electric Scooter Pro 2','escooter2.png',550,'AVAILABLE',10,44.78129958570501,17.171560427328938);
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-25 10:38:56
