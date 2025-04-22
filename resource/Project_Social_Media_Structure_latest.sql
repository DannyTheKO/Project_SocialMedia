-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: project-social-media
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `post_id` bigint NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends` (
  `addressee_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `friend_id` bigint NOT NULL AUTO_INCREMENT,
  `requester_id` bigint NOT NULL,
  `status_enum` varbinary(255) NOT NULL,
  PRIMARY KEY (`friend_id`),
  KEY `FKgn2yj6q74b04ske4dgv7igdev` (`addressee_id`),
  KEY `FK5mwylmmw7ot92jog0004x3gn1` (`requester_id`),
  CONSTRAINT `FK5mwylmmw7ot92jog0004x3gn1` FOREIGN KEY (`requester_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKgn2yj6q74b04ske4dgv7igdev` FOREIGN KEY (`addressee_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends_request`
--

DROP TABLE IF EXISTS `friends_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends_request` (
  `friend_request_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `status` enum('ACCEPTED','PENDING','REJECTED') NOT NULL,
  `from_user_id` bigint NOT NULL,
  `to_user_id` bigint NOT NULL,
  PRIMARY KEY (`friend_request_id`),
  KEY `idx_from_to` (`from_user_id`,`to_user_id`),
  KEY `idx_to_user_status` (`to_user_id`,`status`),
  CONSTRAINT `FKb0j1hrtktb1m8bbrwbo9wpti6` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKgestqiy5d0nf5vii1leg71yln` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends_request`
--

LOCK TABLES `friends_request` WRITE;
/*!40000 ALTER TABLE `friends_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `friends_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `comment_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `like_id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`like_id`),
  KEY `FKe4guax66lb963pf27kvm7ikik` (`comment_id`),
  KEY `FKry8tnr4x2vwemv2bb0h5hyl0x` (`post_id`),
  KEY `FKnvx9seeqqyy71bij291pwiwrg` (`user_id`),
  CONSTRAINT `FKe4guax66lb963pf27kvm7ikik` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`comment_id`),
  CONSTRAINT `FKnvx9seeqqyy71bij291pwiwrg` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKry8tnr4x2vwemv2bb0h5hyl0x` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media` (
  `media_id` bigint NOT NULL AUTO_INCREMENT,
  `uploaded_date` datetime(6) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`media_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
INSERT INTO `media` VALUES (5,'2025-04-21 16:28:30.929012','Screenshot (490)_0a76447b-48b6-47f8-af05-858d6cfc1270.png','gui\\src\\Assets\\uploads\\posts\\7\\Screenshot (490)_0a76447b-48b6-47f8-af05-858d6cfc1270.png','Image'),(6,'2025-04-21 16:48:52.531208','Screenshot (490)_ba4dd35f-b1ca-4a97-bd1c-dd0dc72d237c.png','gui\\src\\Assets\\uploads\\posts\\8\\Screenshot (490)_ba4dd35f-b1ca-4a97-bd1c-dd0dc72d237c.png','Image'),(7,'2025-04-21 17:07:51.694706','Screenshot (755)_478b0dba-f3a4-49fc-9d80-8aa93fc8f661.png','gui\\src\\Assets\\uploads\\posts\\9\\Screenshot (755)_478b0dba-f3a4-49fc-9d80-8aa93fc8f661.png','Image');
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_association`
--

DROP TABLE IF EXISTS `media_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_association` (
  `media_association_id` bigint NOT NULL AUTO_INCREMENT,
  `media_id` bigint DEFAULT NULL,
  `target_id` bigint NOT NULL,
  `target_type` varchar(255) NOT NULL,
  PRIMARY KEY (`media_association_id`),
  KEY `FK2g6f4t27emckb1yiy03nnwl1v` (`media_id`),
  CONSTRAINT `FK2g6f4t27emckb1yiy03nnwl1v` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_association`
--

LOCK TABLES `media_association` WRITE;
/*!40000 ALTER TABLE `media_association` DISABLE KEYS */;
INSERT INTO `media_association` VALUES (5,5,7,'Post'),(6,6,8,'Post'),(7,7,9,'Post');
/*!40000 ALTER TABLE `media_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `read_status` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `message_id` bigint NOT NULL AUTO_INCREMENT,
  `receiver_user_id` bigint DEFAULT NULL,
  `sender_user_id` bigint DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `FKrx9kj3k3dqvmcfk4my12a98c3` (`receiver_user_id`),
  KEY `FKk4mpqp6gfuaelpcamqv01brkr` (`sender_user_id`),
  CONSTRAINT `FKk4mpqp6gfuaelpcamqv01brkr` FOREIGN KEY (`sender_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKrx9kj3k3dqvmcfk4my12a98c3` FOREIGN KEY (`receiver_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `is_read` bit(1) NOT NULL,
  `related_id` bigint DEFAULT NULL,
  `type` tinyint NOT NULL,
  `receiver_id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdammjl0v5xfaegi926ugx6254` (`receiver_id`),
  KEY `FKrg0atx075rr68et2rqrh34qwj` (`sender_id`),
  CONSTRAINT `FKdammjl0v5xfaegi926ugx6254` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKrg0atx075rr68et2rqrh34qwj` FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `notification_chk_1` CHECK ((`type` between 0 and 4))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `created_post` datetime(6) NOT NULL,
  `modified_post` datetime(6) NOT NULL,
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FK5lidm6cqbc7u4xhqpxm898qme` (`user_id`),
  CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES ('2025-04-21 16:06:00.561447','2025-04-21 16:06:00.561447',2,6,'abc'),('2025-04-21 16:07:47.265395','2025-04-21 16:07:47.265395',3,6,'avc'),('2025-04-21 16:11:05.746064','2025-04-21 16:11:05.746064',4,6,'avc'),('2025-04-21 16:14:31.308566','2025-04-21 16:14:31.308566',5,6,'Lichj hojc'),('2025-04-21 16:17:13.265197','2025-04-21 16:17:13.265197',6,6,'lich hoc test'),('2025-04-21 16:28:30.828811','2025-04-21 16:28:30.828811',7,6,'lich hoc'),('2025-04-21 16:48:52.416781','2025-04-21 16:48:52.416781',8,6,'lich hoc thong bao'),('2025-04-21 17:07:51.678704','2025-04-21 17:07:51.678704',9,6,'code');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `expiry_date` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr4k4edos30bx9neoq81mdvwph` (`token`),
  KEY `FKjtx87i0jvq2svedphegvdwcuy` (`user_id`),
  CONSTRAINT `FKjtx87i0jvq2svedphegvdwcuy` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (13,'2025-04-21 17:07:42.788008','2025-05-21 17:07:42.787008','603783be-bc0c-4415-9117-e805fe7832c4',6);
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relationships`
--

DROP TABLE IF EXISTS `relationships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relationships` (
  `relationship_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `status` enum('BLOCKED','FRIENDS') NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id_1` bigint NOT NULL,
  `user_id_2` bigint NOT NULL,
  PRIMARY KEY (`relationship_id`),
  KEY `idx_users` (`user_id_1`,`user_id_2`),
  KEY `idx_user1_status` (`user_id_1`,`status`),
  KEY `FKk7y46upeo3h8h2dvdmrlsfimj` (`user_id_2`),
  CONSTRAINT `FKk7y46upeo3h8h2dvdmrlsfimj` FOREIGN KEY (`user_id_2`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKmlv8lpp7npdrb0oe58igjnamx` FOREIGN KEY (`user_id_1`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationships`
--

LOCK TABLES `relationships` WRITE;
/*!40000 ALTER TABLE `relationships` DISABLE KEYS */;
/*!40000 ALTER TABLE `relationships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `birth_date` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `last_login` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `banner_image_url` varchar(255) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `profile_image_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `user_role` enum('USER','ADMIN') NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('2023-10-15 00:00:00.000000','2025-04-21 16:01:23.716497','2025-04-21 17:07:42.653451',6,NULL,NULL,'test@example.com','Test','User','password123',NULL,'testuser','USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-21 17:10:02
