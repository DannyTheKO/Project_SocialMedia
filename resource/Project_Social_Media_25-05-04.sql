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
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (4,'haha','2025-04-29 11:18:47.348760','2025-04-29 11:18:47.348760',1,1),(5,'awd','2025-04-29 11:19:41.052343','2025-04-29 11:19:41.052343',1,1),(6,'ăd','2025-04-29 11:25:10.152820','2025-04-29 11:25:10.152820',1,1),(7,'ẵ','2025-04-29 11:25:13.912124','2025-04-29 11:25:13.912124',1,1),(8,'ăd','2025-04-29 11:27:05.601253','2025-04-29 11:27:05.601253',1,1),(9,'','2025-04-29 11:38:24.987505','2025-04-29 11:38:24.987505',1,1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `like_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `comment_id` bigint DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`like_id`),
  KEY `FKe4guax66lb963pf27kvm7ikik` (`comment_id`),
  KEY `FKry8tnr4x2vwemv2bb0h5hyl0x` (`post_id`),
  KEY `FKnvx9seeqqyy71bij291pwiwrg` (`user_id`),
  CONSTRAINT `FKe4guax66lb963pf27kvm7ikik` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`comment_id`),
  CONSTRAINT `FKnvx9seeqqyy71bij291pwiwrg` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKry8tnr4x2vwemv2bb0h5hyl0x` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (22,'2025-05-03 15:14:55.494577',NULL,1,2),(23,'2025-05-03 15:15:45.485539',NULL,2,2),(24,'2025-05-03 15:15:56.084447',NULL,2,1);
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
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  `uploaded_date` datetime(6) NOT NULL,
  PRIMARY KEY (`media_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
INSERT INTO `media` VALUES (1,'bai1_d982dbf5-e15a-4c4e-9040-506d77c753ff.png','gui\\src\\Assets\\uploads\\posts\\1\\comments\\9\\bai1_d982dbf5-e15a-4c4e-9040-506d77c753ff.png','Image','2025-04-29 11:38:25.234135'),(2,'tks_2820143d-181d-47de-96a4-b86deefe4a0f.jpg','gui\\src\\Assets\\uploads\\users\\1\\tks_2820143d-181d-47de-96a4-b86deefe4a0f.jpg','Image','2025-04-30 12:17:10.612791'),(3,'migration_rate_96ee5af9-4548-4e61-a837-0428ddf08744.png','gui\\src\\Assets\\uploads\\posts\\1\\migration_rate_96ee5af9-4548-4e61-a837-0428ddf08744.png','Image','2025-04-30 12:17:50.960810'),(4,'logotruong_88a8d715-444c-4f50-8029-9110f5966d8d.jpg','gui\\src\\Assets\\uploads\\posts\\2\\logotruong_88a8d715-444c-4f50-8029-9110f5966d8d.jpg','Image','2025-05-03 15:15:22.664823');
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
  `target_id` bigint NOT NULL,
  `target_type` varchar(255) NOT NULL,
  `media_id` bigint DEFAULT NULL,
  PRIMARY KEY (`media_association_id`),
  KEY `FK2g6f4t27emckb1yiy03nnwl1v` (`media_id`),
  CONSTRAINT `FK2g6f4t27emckb1yiy03nnwl1v` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_association`
--

LOCK TABLES `media_association` WRITE;
/*!40000 ALTER TABLE `media_association` DISABLE KEYS */;
INSERT INTO `media_association` VALUES (1,9,'Comment',1),(2,1,'BannerImage',2),(3,1,'Post',3),(4,2,'Post',4);
/*!40000 ALTER TABLE `media_association` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,'test đã bình luận bài viết của bạn','2025-04-29 11:08:57.838476',_binary '\0',1,3,1,1),(2,'test đã bình luận bài viết của bạn','2025-04-29 11:09:14.623191',_binary '\0',2,3,1,1),(3,'test đã bình luận bài viết của bạn','2025-04-29 11:09:19.630395',_binary '\0',3,3,1,1),(4,'test đã bình luận bài viết của bạn','2025-04-29 11:18:47.363762',_binary '\0',4,3,1,1),(5,'test đã bình luận bài viết của bạn','2025-04-29 11:19:41.062739',_binary '\0',5,3,1,1),(6,'test đã bình luận bài viết của bạn','2025-04-29 11:25:10.165485',_binary '\0',6,3,1,1),(7,'test đã bình luận bài viết của bạn','2025-04-29 11:25:13.922112',_binary '\0',7,3,1,1),(8,'test đã bình luận bài viết của bạn','2025-04-29 11:27:05.610144',_binary '\0',8,3,1,1),(9,'test đã bình luận bài viết của bạn','2025-04-29 11:38:25.279693',_binary '\0',9,3,1,1),(10,'test đã gửi yêu cầu kết bạn','2025-04-29 14:41:37.127806',_binary '\0',1,0,2,1),(11,'test đã thích bài viết của bạn','2025-05-01 15:05:02.195531',_binary '\0',1,1,1,1),(12,'danny đã thích bài viết của bạn','2025-05-01 15:36:57.173463',_binary '\0',1,1,1,2),(13,'test đã thích bài viết của bạn','2025-05-01 15:39:52.327387',_binary '\0',1,1,1,1),(14,'danny đã thích bài viết của bạn','2025-05-01 15:40:47.110062',_binary '\0',1,1,1,2),(15,'test đã thích bài viết của bạn','2025-05-01 16:30:17.645898',_binary '\0',1,1,1,1),(16,'danny đã thích bài viết của bạn','2025-05-01 16:31:32.730762',_binary '\0',1,1,1,2),(17,'test đã thích bài viết của bạn','2025-05-01 16:32:29.426467',_binary '\0',1,1,1,1),(18,'danny đã thích bài viết của bạn','2025-05-01 16:32:52.726343',_binary '\0',1,1,1,2),(19,'test đã thích bài viết của bạn','2025-05-01 16:35:27.109198',_binary '\0',1,1,1,1),(20,'test đã thích bài viết của bạn','2025-05-01 16:37:49.939941',_binary '\0',1,1,1,1),(21,'test đã thích bài viết của bạn','2025-05-01 16:38:32.365103',_binary '\0',1,1,1,1),(22,'test đã thích bài viết của bạn','2025-05-01 16:39:46.694126',_binary '\0',1,1,1,1),(23,'danny đã thích bài viết của bạn','2025-05-01 16:40:11.096187',_binary '\0',1,1,1,2),(24,'danny đã thích bài viết của bạn','2025-05-01 16:45:05.532099',_binary '\0',1,1,1,2),(25,'test đã thích bài viết của bạn','2025-05-01 16:45:13.850320',_binary '\0',1,1,1,1),(26,'test đã thích bài viết của bạn','2025-05-01 16:46:09.264903',_binary '\0',1,1,1,1),(27,'test đã thích bài viết của bạn','2025-05-01 16:46:12.178976',_binary '\0',1,1,1,1),(28,'test đã thích bài viết của bạn','2025-05-01 16:46:18.927180',_binary '\0',1,1,1,1),(29,'test đã thích bài viết của bạn','2025-05-01 16:46:20.038096',_binary '\0',1,1,1,1),(30,'test đã thích bài viết của bạn','2025-05-01 16:46:22.232014',_binary '\0',1,1,1,1),(31,'test đã thích bài viết của bạn','2025-05-01 16:46:38.127745',_binary '\0',1,1,1,1),(32,'test đã gửi yêu cầu kết bạn','2025-05-02 16:59:51.729405',_binary '\0',2,0,2,1),(33,'danny đã thích bài viết của bạn','2025-05-03 15:14:55.511594',_binary '\0',1,1,1,2),(34,'danny đã thích bài viết của bạn','2025-05-03 15:15:45.496706',_binary '\0',2,1,2,2),(35,'test đã thích bài viết của bạn','2025-05-03 15:15:56.105968',_binary '\0',2,1,2,1),(36,'test đã gửi yêu cầu kết bạn','2025-05-03 15:59:50.195877',_binary '\0',3,0,2,1),(37,'test đã gửi yêu cầu kết bạn','2025-05-03 16:09:13.693250',_binary '\0',4,0,2,1),(38,'test đã gửi yêu cầu kết bạn','2025-05-03 16:10:18.623971',_binary '\0',5,0,2,1),(39,'test đã gửi yêu cầu kết bạn','2025-05-03 16:11:11.064081',_binary '\0',6,0,2,1),(40,'test đã gửi yêu cầu kết bạn','2025-05-03 16:18:31.084747',_binary '\0',7,0,2,1),(41,'test đã gửi yêu cầu kết bạn','2025-05-03 16:32:32.152216',_binary '\0',8,0,2,1),(42,'test đã gửi yêu cầu kết bạn','2025-05-03 16:33:56.850643',_binary '\0',9,0,2,1),(43,'test đã gửi yêu cầu kết bạn','2025-05-03 16:37:39.652409',_binary '\0',10,0,2,1),(44,'test đã gửi yêu cầu kết bạn','2025-05-03 16:38:11.264871',_binary '\0',11,0,2,1),(45,'test đã gửi yêu cầu kết bạn','2025-05-03 16:41:04.708447',_binary '\0',12,0,2,1),(46,'test đã gửi yêu cầu kết bạn','2025-05-03 16:46:09.093706',_binary '\0',13,0,2,1),(47,'test đã gửi yêu cầu kết bạn','2025-05-03 16:46:15.751541',_binary '\0',14,0,2,1),(48,'test đã gửi yêu cầu kết bạn','2025-05-03 16:46:29.794489',_binary '\0',15,0,2,1),(49,'test đã gửi yêu cầu kết bạn','2025-05-03 16:47:05.819625',_binary '\0',16,0,2,1),(50,'test đã gửi yêu cầu kết bạn','2025-05-03 16:55:02.870543',_binary '\0',17,0,2,1),(51,'test đã gửi yêu cầu kết bạn','2025-05-03 16:55:12.153412',_binary '\0',18,0,2,1),(52,'test đã gửi yêu cầu kết bạn','2025-05-03 16:59:50.570815',_binary '\0',19,0,2,1),(53,'test đã gửi yêu cầu kết bạn','2025-05-03 16:59:57.001211',_binary '\0',20,0,2,1),(54,'test đã gửi yêu cầu kết bạn','2025-05-03 17:24:37.545094',_binary '\0',21,0,2,1),(55,'test đã gửi yêu cầu kết bạn','2025-05-03 17:34:10.911292',_binary '\0',22,0,2,1),(56,'test đã gửi yêu cầu kết bạn','2025-05-03 17:34:14.491816',_binary '\0',23,0,2,1),(57,'test đã gửi yêu cầu kết bạn','2025-05-03 17:36:04.216174',_binary '\0',24,0,2,1),(58,'test đã gửi yêu cầu kết bạn','2025-05-03 17:37:36.202125',_binary '\0',25,0,2,1);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_post` datetime(6) NOT NULL,
  `modified_post` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FK5lidm6cqbc7u4xhqpxm898qme` (`user_id`),
  CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'haha2','2025-04-29 11:08:37.880835','2025-04-29 11:08:37.880835',1),(2,'SGU\r\n','2025-05-03 15:15:22.600597','2025-05-03 15:15:22.600597',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=457 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (454,'2025-05-04 05:44:16.290529','2025-06-03 05:44:16.290529','afe7940f-0309-4879-8677-6486da8b6463',1),(456,'2025-05-04 05:44:47.151238','2025-06-03 05:44:47.151238','4710fb27-8c91-4510-8291-be13e32d208f',2);
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
  `status` enum('BLOCKED','FRIENDS','PENDING') NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id_1` bigint NOT NULL,
  `user_id_2` bigint NOT NULL,
  PRIMARY KEY (`relationship_id`),
  KEY `idx_users` (`user_id_1`,`user_id_2`),
  KEY `idx_user1_status` (`user_id_1`,`status`),
  KEY `FKk7y46upeo3h8h2dvdmrlsfimj` (`user_id_2`),
  CONSTRAINT `FKk7y46upeo3h8h2dvdmrlsfimj` FOREIGN KEY (`user_id_2`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKmlv8lpp7npdrb0oe58igjnamx` FOREIGN KEY (`user_id_1`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationships`
--

LOCK TABLES `relationships` WRITE;
/*!40000 ALTER TABLE `relationships` DISABLE KEYS */;
INSERT INTO `relationships` VALUES (33,'2025-05-04 05:44:34.937767','FRIENDS','2025-05-04 05:44:51.176519',1,2);
/*!40000 ALTER TABLE `relationships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `banner_image_url` varchar(255) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `birth_date` datetime(6) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_login` datetime(6) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `profile_image_url` varchar(255) DEFAULT NULL,
  `user_role` enum('ADMIN','USER') NOT NULL,
  `user_state` enum('ACTIVE','SUSPENDED') NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'gui\\src\\Assets\\uploads\\users\\1\\tks_2820143d-181d-47de-96a4-b86deefe4a0f.jpg',NULL,'2004-12-17 00:00:00.000000','2025-04-29 11:08:27.485350','','Thái','2025-05-04 05:44:16.258523','Tuấn','123',NULL,'USER','ACTIVE','test'),(2,NULL,NULL,'2011-11-11 00:00:00.000000','2025-04-29 12:03:13.900173','','Danny','2025-05-04 05:44:47.126230','KO','ko',NULL,'USER','ACTIVE','danny');
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

-- Dump completed on 2025-05-04  5:48:28
