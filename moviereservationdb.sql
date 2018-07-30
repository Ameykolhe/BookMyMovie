-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: moviereservationdb
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookedseats`
--

DROP TABLE IF EXISTS `bookedseats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bookedseats` (
  `bookedSeatID` int(11) NOT NULL AUTO_INCREMENT,
  `bookingID` int(11) NOT NULL,
  `seatMapID` int(11) NOT NULL,
  PRIMARY KEY (`bookedSeatID`),
  KEY `bookingID` (`bookingID`),
  KEY `seatMapID` (`seatMapID`),
  CONSTRAINT `bookedseats_ibfk_1` FOREIGN KEY (`bookingID`) REFERENCES `bookinginfo` (`bookingid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bookedseats_ibfk_2` FOREIGN KEY (`seatMapID`) REFERENCES `seatmap` (`seatmapid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookedseats`
--

LOCK TABLES `bookedseats` WRITE;
/*!40000 ALTER TABLE `bookedseats` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookedseats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookinginfo`
--

DROP TABLE IF EXISTS `bookinginfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bookinginfo` (
  `bookingID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `scheduleID` int(11) NOT NULL,
  `totalSeats` int(11) NOT NULL,
  PRIMARY KEY (`bookingID`),
  KEY `userID` (`userID`),
  KEY `scheduleID` (`scheduleID`),
  CONSTRAINT `bookinginfo_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bookinginfo_ibfk_2` FOREIGN KEY (`scheduleID`) REFERENCES `schedule` (`scheduleid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookinginfo`
--

LOCK TABLES `bookinginfo` WRITE;
/*!40000 ALTER TABLE `bookinginfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookinginfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `city` (
  `cityID` int(10) NOT NULL AUTO_INCREMENT,
  `cityName` varchar(20) NOT NULL,
  PRIMARY KEY (`cityID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Pune'),(2,'Mumbai');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `movie` (
  `movieID` int(11) NOT NULL AUTO_INCREMENT,
  `movieName` varchar(30) NOT NULL,
  PRIMARY KEY (`movieID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `schedule` (
  `scheduleID` int(11) NOT NULL AUTO_INCREMENT,
  `showDate` date NOT NULL,
  `showTime` timestamp NOT NULL,
  `screenID` int(11) NOT NULL,
  `movieID` int(11) NOT NULL,
  PRIMARY KEY (`scheduleID`),
  KEY `screenID` (`screenID`),
  KEY `movieID` (`movieID`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`screenID`) REFERENCES `screen` (`screenid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`movieID`) REFERENCES `movie` (`movieid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `screen`
--

DROP TABLE IF EXISTS `screen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `screen` (
  `screenID` int(11) NOT NULL AUTO_INCREMENT,
  `theatreID` int(11) NOT NULL,
  `screenNum` int(11) NOT NULL,
  `capacity` int(11) NOT NULL,
  PRIMARY KEY (`screenID`),
  KEY `theatreID` (`theatreID`),
  CONSTRAINT `screen_ibfk_1` FOREIGN KEY (`theatreID`) REFERENCES `theatre` (`theatreid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `screen`
--

LOCK TABLES `screen` WRITE;
/*!40000 ALTER TABLE `screen` DISABLE KEYS */;
/*!40000 ALTER TABLE `screen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seatmap`
--

DROP TABLE IF EXISTS `seatmap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seatmap` (
  `seatMapID` int(11) NOT NULL AUTO_INCREMENT,
  `seatNO` int(11) NOT NULL,
  `screenID` int(11) NOT NULL,
  PRIMARY KEY (`seatMapID`),
  KEY `screenID` (`screenID`),
  CONSTRAINT `seatmap_ibfk_1` FOREIGN KEY (`screenID`) REFERENCES `screen` (`screenid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seatmap`
--

LOCK TABLES `seatmap` WRITE;
/*!40000 ALTER TABLE `seatmap` DISABLE KEYS */;
/*!40000 ALTER TABLE `seatmap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `theatre`
--

DROP TABLE IF EXISTS `theatre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `theatre` (
  `theatreID` int(11) NOT NULL AUTO_INCREMENT,
  `theatreName` varchar(50) NOT NULL,
  `ownerID` int(11) NOT NULL,
  `cityID` int(10) NOT NULL,
  `numOfScreens` int(11) NOT NULL,
  PRIMARY KEY (`theatreID`),
  KEY `cityID` (`cityID`),
  KEY `ownerID` (`ownerID`),
  CONSTRAINT `theatre_ibfk_1` FOREIGN KEY (`cityID`) REFERENCES `city` (`cityid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `theatre_ibfk_2` FOREIGN KEY (`ownerID`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `theatre`
--

LOCK TABLES `theatre` WRITE;
/*!40000 ALTER TABLE `theatre` DISABLE KEYS */;
/*!40000 ALTER TABLE `theatre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(40) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `userIndex` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (6,'ameykolhe@live.com','1379',1),(7,'kolheamey99@gmail.com','1379',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-30 22:52:16
