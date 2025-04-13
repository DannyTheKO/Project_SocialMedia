-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: project-social-media
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments`
(
    `comment_id` bigint       NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6)  NOT NULL,
    `post_id`    bigint       NOT NULL,
    `updated_at` datetime(6)  NOT NULL,
    `user_id`    bigint       NOT NULL,
    `content`    varchar(255) NOT NULL,
    PRIMARY KEY (`comment_id`),
    KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
    KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
    CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `comments`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends`
(
    `created_at`  datetime(6)    NOT NULL,
    `friend_id`   bigint         NOT NULL AUTO_INCREMENT,
    `user_id_1`   bigint         NOT NULL,
    `user_id_2`   bigint         NOT NULL,
    `status_enum` varbinary(255) NOT NULL,
    PRIMARY KEY (`friend_id`),
    KEY `FKe3n361loct6kbk8yahgmhfts6` (`user_id_1`),
    KEY `FK9g8ttc647821knlyegjucxbs0` (`user_id_2`),
    CONSTRAINT `FK9g8ttc647821knlyegjucxbs0` FOREIGN KEY (`user_id_2`) REFERENCES `users` (`user_id`),
    CONSTRAINT `FKe3n361loct6kbk8yahgmhfts6` FOREIGN KEY (`user_id_1`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `friends`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes`
(
    `comment_id` bigint DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    `like_id`    bigint      NOT NULL AUTO_INCREMENT,
    `post_id`    bigint DEFAULT NULL,
    `user_id`    bigint      NOT NULL,
    PRIMARY KEY (`like_id`),
    KEY `FKe4guax66lb963pf27kvm7ikik` (`comment_id`),
    KEY `FKry8tnr4x2vwemv2bb0h5hyl0x` (`post_id`),
    KEY `FKnvx9seeqqyy71bij291pwiwrg` (`user_id`),
    CONSTRAINT `FKe4guax66lb963pf27kvm7ikik` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`comment_id`),
    CONSTRAINT `FKnvx9seeqqyy71bij291pwiwrg` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `FKry8tnr4x2vwemv2bb0h5hyl0x` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `likes`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media`
(
    `media_id`       bigint NOT NULL AUTO_INCREMENT,
    `uploaded_date`  datetime(6)    DEFAULT NULL,
    `file_name`      varchar(255)   DEFAULT NULL,
    `url`            varchar(255)   DEFAULT NULL,
    `file_type_enum` varbinary(255) DEFAULT NULL,
    `file_path`      varchar(255)   DEFAULT NULL,
    `file_type`      varchar(255)   DEFAULT NULL,
    PRIMARY KEY (`media_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media`
    DISABLE KEYS */;
INSERT INTO `media`
VALUES (5, '2025-04-09 05:28:33.833690', 'twilight_clouds_2_0a10d1da-1aab-449c-84e1-d9ad8b0c127d.jpg', NULL, NULL,
        'gui\\src\\assets\\uploads\\posts\\5\\twilight_clouds_2_0a10d1da-1aab-449c-84e1-d9ad8b0c127d.jpg', 'Image'),
       (8, '2025-04-09 06:15:48.942002', 'Night_City_894f6fcb-5da5-4ccf-b710-d6011a679881.jpg', NULL, NULL,
        'gui\\src\\assets\\uploads\\users\\3\\Night_City_894f6fcb-5da5-4ccf-b710-d6011a679881.jpg', 'Image'),
       (9, '2025-04-09 06:15:49.077968', 'redlogo_b9dddffa-b634-44bd-b13a-2474946b73b6.jpg', NULL, NULL,
        'gui\\src\\assets\\uploads\\users\\3\\redlogo_b9dddffa-b634-44bd-b13a-2474946b73b6.jpg', 'Image'),
       (10, '2025-04-09 06:21:26.605446', 'anh6_0232899b-b75a-4122-881e-657d8abbf720.jpg', NULL, NULL,
        'gui\\src\\assets\\uploads\\users\\2\\anh6_0232899b-b75a-4122-881e-657d8abbf720.jpg', 'Image'),
       (11, '2025-04-09 06:21:26.652458', 'LOVE_28d6bf0c-cbc9-4e4e-9a11-2fdc322342f3.jpg', NULL, NULL,
        'gui\\src\\assets\\uploads\\users\\2\\LOVE_28d6bf0c-cbc9-4e4e-9a11-2fdc322342f3.jpg', 'Image'),
       (12, '2025-04-09 06:41:29.636676', 'trump4_f85fcc49-f438-4d4b-8bcc-2ca1446782af.jfif', NULL, NULL,
        'gui\\src\\assets\\uploads\\posts\\6\\trump4_f85fcc49-f438-4d4b-8bcc-2ca1446782af.jfif', 'Unknown'),
       (13, '2025-04-09 06:41:29.678109', 'trump_7cbf6949-9885-4136-b173-e25fa7df9c01.jfif', NULL, NULL,
        'gui\\src\\assets\\uploads\\posts\\6\\trump_7cbf6949-9885-4136-b173-e25fa7df9c01.jfif', 'Unknown'),
       (14, '2025-04-09 06:41:29.710919', 'trump2_71edb660-c94b-4bfc-9f6e-287fc8e19c13.jfif', NULL, NULL,
        'gui\\src\\assets\\uploads\\posts\\6\\trump2_71edb660-c94b-4bfc-9f6e-287fc8e19c13.jfif', 'Unknown'),
       (15, '2025-04-09 07:16:59.830046', 'students_c7bb2f48-c1e2-411d-a182-2a088c0c49f5.mp4', NULL, NULL,
        'gui\\src\\assets\\uploads\\posts\\7\\students_c7bb2f48-c1e2-411d-a182-2a088c0c49f5.mp4', 'Video');
/*!40000 ALTER TABLE `media`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media_association`
--

DROP TABLE IF EXISTS `media_association`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `media_association`
(
    `media_association_id` bigint       NOT NULL AUTO_INCREMENT,
    `media_id`             bigint DEFAULT NULL,
    `target_id`            bigint       NOT NULL,
    `target_type`          varchar(255) NOT NULL,
    PRIMARY KEY (`media_association_id`),
    KEY `FK2g6f4t27emckb1yiy03nnwl1v` (`media_id`),
    CONSTRAINT `FK2g6f4t27emckb1yiy03nnwl1v` FOREIGN KEY (`media_id`) REFERENCES `media` (`media_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media_association`
--

LOCK TABLES `media_association` WRITE;
/*!40000 ALTER TABLE `media_association`
    DISABLE KEYS */;
INSERT INTO `media_association`
VALUES (5, 5, 5, 'Post'),
       (8, 8, 3, 'ProfileImage'),
       (9, 9, 3, 'BannerImage'),
       (10, 10, 2, 'ProfileImage'),
       (11, 11, 2, 'BannerImage'),
       (12, 12, 6, 'Post'),
       (13, 13, 6, 'Post'),
       (14, 14, 6, 'Post'),
       (15, 15, 7, 'Post');
/*!40000 ALTER TABLE `media_association`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages`
(
    `read_status`      bit(1)       NOT NULL,
    `created_at`       datetime(6)  NOT NULL,
    `message_id`       bigint       NOT NULL AUTO_INCREMENT,
    `receiver_user_id` bigint DEFAULT NULL,
    `sender_user_id`   bigint DEFAULT NULL,
    `content`          varchar(255) NOT NULL,
    PRIMARY KEY (`message_id`),
    KEY `FKrx9kj3k3dqvmcfk4my12a98c3` (`receiver_user_id`),
    KEY `FKk4mpqp6gfuaelpcamqv01brkr` (`sender_user_id`),
    CONSTRAINT `FKk4mpqp6gfuaelpcamqv01brkr` FOREIGN KEY (`sender_user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `FKrx9kj3k3dqvmcfk4my12a98c3` FOREIGN KEY (`receiver_user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `messages`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts`
(
    `created_post`  datetime(6)  NOT NULL,
    `modified_post` datetime(6)  NOT NULL,
    `post_id`       bigint       NOT NULL AUTO_INCREMENT,
    `user_id`       bigint       NOT NULL,
    `content`       varchar(255) NOT NULL,
    PRIMARY KEY (`post_id`),
    KEY `FK5lidm6cqbc7u4xhqpxm898qme` (`user_id`),
    CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts`
    DISABLE KEYS */;
INSERT INTO `posts`
VALUES ('2025-04-08 06:10:48.131625', '2025-04-08 06:10:48.131625', 1, 2, 'Third Post'),
       ('2025-04-08 06:54:46.189350', '2025-04-08 06:54:46.190351', 2, 2, 'Third Post'),
       ('2025-04-08 07:54:13.894201', '2025-04-08 07:54:13.894201', 3, 3, 'Third Post'),
       ('2025-04-09 05:28:16.436330', '2025-04-09 05:28:16.437329', 4, 2, 'test post '),
       ('2025-04-09 05:28:33.748278', '2025-04-09 05:28:33.749279', 5, 2, 'test post '),
       ('2025-04-09 06:41:29.384412', '2025-04-09 06:41:29.384412', 6, 3, 'test post with many images '),
       ('2025-04-09 07:16:59.644842', '2025-04-09 07:16:59.646519', 7, 3, 'test post with videos');
/*!40000 ALTER TABLE `posts`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users`
(
    `birth_date`        datetime(6)  DEFAULT NULL,
    `created_at`        datetime(6)           NOT NULL,
    `last_login`        datetime(6)           NOT NULL,
    `user_id`           bigint                NOT NULL AUTO_INCREMENT,
    `banner_image_url`  varchar(255) DEFAULT NULL,
    `bio`               varchar(255) DEFAULT NULL,
    `email`             varchar(255)          NOT NULL,
    `first_name`        varchar(255) DEFAULT NULL,
    `last_name`         varchar(255) DEFAULT NULL,
    `password`          varchar(255)          NOT NULL,
    `profile_image_url` varchar(255) DEFAULT NULL,
    `username`          varchar(255)          NOT NULL,
    `user_role`         enum ('USER','ADMIN') NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users`
VALUES ('2025-04-05 00:00:00.000000', '2025-04-08 01:43:43.768860', '2025-04-08 01:43:43.768860', 2,
        'gui\\src\\assets\\uploads\\users\\2\\LOVE_28d6bf0c-cbc9-4e4e-9a11-2fdc322342f3.jpg', 'Hi',
        'tuanthai1712@gmail.com', 'Tuan', 'Thai', '123789',
        'gui\\src\\assets\\uploads\\users\\2\\anh6_0232899b-b75a-4122-881e-657d8abbf720.jpg', 'TTuan', 'USER'),
       ('2025-04-05 00:00:00.000000', '2025-04-08 07:53:41.783978', '2025-04-08 07:53:41.784978', 3,
        'gui\\src\\assets\\uploads\\users\\3\\redlogo_b9dddffa-b634-44bd-b13a-2474946b73b6.jpg', 'Hi',
        'tuanthai1712@gmail.com', 'Tuan', 'Thai', '123789',
        'gui\\src\\assets\\uploads\\users\\3\\Night_City_894f6fcb-5da5-4ccf-b710-d6011a679881.jpg', 'TTuan2', 'USER');
/*!40000 ALTER TABLE `users`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2025-04-12 15:27:01
