# Dump
of table user
#
------------------------------------------------------------

USE `DLOG-DB`;

#
Create User Table
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER`
(
  `Uid` INT
(10) AUTO_INCREMENT,
  `Email` VARCHAR
(128) DEFAULT NULL,
  `Password` VARCHAR
(128) DEFAULT NULL,
   `Nickname` VARCHAR
(24),
   `Role` int
(2) DEFAULT 0, # 0 : 유저 1 : 관리자
  `Create_date` DATETIME DEFAULT CURRENT_TIMESTAMP
(),
  PRIMARY KEY
(`Uid`),
  UNIQUE KEY `user_idx_unique_email`
(`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;


# User test data input
INSERT INTO USER
  (Email, Password, Nickname, Role)
VALUES('admin@ssafy.com', 'admin', 'admin', 1);
INSERT INTO USER
  (Email, Password, Nickname)
VALUES('user@ssafy.com', 'user', 'user');

# Check User Table
SELECT *
FROM USER;

#
Create Post Table
DROP TABLE IF EXISTS `POST`;
CREATE TABLE `POST`
(
  `Pid` INT
(10) AUTO_INCREMENT,
  `Writer` INT
(10), 
  `Title` VARCHAR
(128) DEFAULT NULL,
  `Contents` VARCHAR
(5000) DEFAULT NULL,
   `Hit` INT
(10) DEFAULT 0,
   `Is_Hidden` BOOLEAN DEFAULT FALSE,
   `Is_Complete` BOOLEAN DEFAULT FALSE,
  `Create_date` DATETIME DEFAULT CURRENT_TIMESTAMP
(),
  PRIMARY KEY
(`Pid`),
  FOREIGN KEY
(`Writer`) REFERENCES USER
(`Uid`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;


# Post test data input
INSERT INTO POST
  (Writer, Title, Contents)
VALUES(1, 'Test Title', 'Test Contents');

# Check User Table
SELECT *
FROM POST;

