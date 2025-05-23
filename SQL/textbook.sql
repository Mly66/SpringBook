/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80037
 Source Host           : localhost:3306
 Source Schema         : textbook

 Target Server Type    : MySQL
 Target Server Version : 80037
 File Encoding         : 65001

 Date: 23/05/2025 21:46:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apply_book
-- ----------------------------
DROP TABLE IF EXISTS `apply_book`;
CREATE TABLE `apply_book`  (
  `book_id` int NOT NULL,
  `user_id` int NOT NULL,
  `apply_date` datetime NOT NULL,
  `apply_count` int NULL DEFAULT 0,
  `apply_status` int NULL DEFAULT 0,
  `approver` int NULL DEFAULT NULL,
  `approve_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`, `user_id`) USING BTREE,
  INDEX `fk_apply_book_user`(`user_id` ASC) USING BTREE,
  INDEX `fk_apply_book_user_approver`(`approver` ASC) USING BTREE,
  CONSTRAINT `fk_apply_book_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_apply_book_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_apply_book_user_approver` FOREIGN KEY (`approver`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apply_book
-- ----------------------------
INSERT INTO `apply_book` VALUES (1, 1, '2025-05-19 00:00:00', 60, 3, 1, '2025-05-19 00:00:00');
INSERT INTO `apply_book` VALUES (1, 10, '2025-05-23 00:00:00', 3, 1, 1, '2025-05-23 00:00:00');
INSERT INTO `apply_book` VALUES (2, 1, '2022-07-28 00:00:00', 38, 1, 1, '2025-05-21 00:00:00');
INSERT INTO `apply_book` VALUES (3, 2, '2022-06-26 00:00:00', 2, 0, 1, '2022-12-05 00:00:00');
INSERT INTO `apply_book` VALUES (10, 10, '2025-05-23 00:00:00', 100, 2, 1, '2025-05-23 00:00:00');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `book_name` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `isbn` varchar(18) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `price` float NOT NULL DEFAULT 0,
  `pub_date` datetime NOT NULL,
  `warehousing_date` datetime NOT NULL,
  `author` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `inventory` int NOT NULL DEFAULT 0,
  `pub_id` int NULL DEFAULT 0,
  `course_id` int NULL DEFAULT 0,
  PRIMARY KEY (`book_id`) USING BTREE,
  INDEX `fk_book_publisher`(`pub_id` ASC) USING BTREE,
  INDEX `fk_book_course`(`course_id` ASC) USING BTREE,
  CONSTRAINT `fk_book_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_book_publisher` FOREIGN KEY (`pub_id`) REFERENCES `publisher` (`pub_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '数据结构教程(第2版)', '978-7-115-45789-7', 59.8, '2020-12-08 00:00:00', '2022-08-26 00:00:00', '宋立本', 129, 1, 2);
INSERT INTO `book` VALUES (2, '数据库系统原理', '978-7-115-45657-3', 48, '2021-03-12 00:00:00', '2022-08-26 00:00:00', '梁知敏', 200, 2, 3);
INSERT INTO `book` VALUES (3, 'C语言程序设计教程', '978-7-121-44449-7', 65, '2021-08-28 00:00:00', '2022-08-26 00:00:00', '宋立本', 300, 1, 4);
INSERT INTO `book` VALUES (4, '高等数学（上）', '978-7-121-54875-3', 65, '2020-05-18 00:00:00', '2022-08-26 00:00:00', '韓立', 400, 1, 1);
INSERT INTO `book` VALUES (6, 'Java编程思想', '9787111213826', 108, '2007-06-01 00:00:00', '2025-05-20 11:27:56', 'Bruce Eckel', 50, 1, 2);
INSERT INTO `book` VALUES (7, 'Java编程思想', '9787111213826', 108, '2007-06-01 00:00:00', '2025-05-20 11:29:10', 'Bruce Eckel', 50, 1, 2);
INSERT INTO `book` VALUES (8, '开机键进', '413534757453745', 999, '2025-05-06 00:00:00', '2025-05-23 08:33:47', '424', 2, NULL, NULL);
INSERT INTO `book` VALUES (9, 'Java编程思想', '9787111213826', 108, '2007-06-01 00:00:00', '2025-05-23 08:34:58', 'Bruce Eckel', 50, 1, 2);
INSERT INTO `book` VALUES (10, 'Java编程思想', '9787111213826', 108, '2007-06-01 00:00:00', '2025-05-23 08:35:33', 'Bruce Eckel', 51, 3, 2);
INSERT INTO `book` VALUES (11, 'Vue编程思想', '9787111213886', 108, '2007-06-01 00:00:00', '2025-05-23 09:17:00', 'Bruce Eckel', 50, 1, 2);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `period` int NULL DEFAULT 0,
  `credit` int NULL DEFAULT 0,
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '高等数学Ⅰ', 120, 9);
INSERT INTO `course` VALUES (2, '数据结构', 72, 4);
INSERT INTO `course` VALUES (3, '数据库原理与应用', 72, 4);
INSERT INTO `course` VALUES (4, '程序设计基础', 72, 4);
INSERT INTO `course` VALUES (6, 'JavaEE企业级应用开发', 72, 4);
INSERT INTO `course` VALUES (7, 'JavaEE企业级应用开发', 72, 4);

-- ----------------------------
-- Table structure for distribute_book
-- ----------------------------
DROP TABLE IF EXISTS `distribute_book`;
CREATE TABLE `distribute_book`  (
  `book_id` int NOT NULL,
  `user_id` int NOT NULL,
  `distribute_date` datetime NOT NULL,
  `distribute_count` int NULL DEFAULT 0,
  PRIMARY KEY (`book_id`, `user_id`) USING BTREE,
  INDEX `fk_user_book_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_book_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_book_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of distribute_book
-- ----------------------------
INSERT INTO `distribute_book` VALUES (1, 1, '2025-05-23 00:00:00', 241);
INSERT INTO `distribute_book` VALUES (2, 1, '2022-07-28 00:00:00', 38);
INSERT INTO `distribute_book` VALUES (3, 2, '2022-06-26 00:00:00', 2);

-- ----------------------------
-- Table structure for publisher
-- ----------------------------
DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher`  (
  `pub_id` int NOT NULL AUTO_INCREMENT,
  `pub_name` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `contacter` varchar(30) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `mobile` varchar(12) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  PRIMARY KEY (`pub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of publisher
-- ----------------------------
INSERT INTO `publisher` VALUES (1, '清华大学出版社', '李四', '13588889999');
INSERT INTO `publisher` VALUES (2, '电子工业出版社', '廖时飞', '13588887777');
INSERT INTO `publisher` VALUES (3, '人民邮电出版社', '宋凌枫', '13588886666');
INSERT INTO `publisher` VALUES (4, '人民教育出版社', '刘小纳', '13588885555');
INSERT INTO `publisher` VALUES (5, '商务印书馆', '赵文静', '13588884444');
INSERT INTO `publisher` VALUES (6, '高等教育出版社', '孙明哲', '13588883333');
INSERT INTO `publisher` VALUES (7, '机械工业出版社', '王建平', '13588882222');
INSERT INTO `publisher` VALUES (8, '北京大学出版社', '李晓华', '13588881111');
INSERT INTO `publisher` VALUES (10, '科学出版社', '张三', '13500000000');
INSERT INTO `publisher` VALUES (11, '科学出版社', '张三', '13500000000');
INSERT INTO `publisher` VALUES (12, '41444455', '6666', '15733435555');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '系统管理员');
INSERT INTO `role` VALUES (2, '业务管理员');
INSERT INTO `role` VALUES (3, '教师');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `login_name` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `password` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `user_name` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  `mobile` char(11) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `login_name`(`login_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '888888', '张安亮', '13535553658');
INSERT INTO `user` VALUES (2, 'szhou', '888888', '周远方', '13568975588');
INSERT INTO `user` VALUES (3, 'tfeng', '888888', '冯伟才', '13825479652');
INSERT INTO `user` VALUES (4, 'tlin', '888888', '林卫海', '13568975426');
INSERT INTO `user` VALUES (6, 'testuser', '888888', '测试用户', '13812345678');
INSERT INTO `user` VALUES (7, 'newuser', '888888', '新用户', '13900000000');
INSERT INTO `user` VALUES (9, 'testuser1', '888888', '测试用户', '13812345678');
INSERT INTO `user` VALUES (10, 'admin4', '888888', 'admin4', '17899996655');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `fk_user_role_role`(`role_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (1, 2);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (2, 3);
INSERT INTO `user_role` VALUES (3, 3);
INSERT INTO `user_role` VALUES (4, 3);
INSERT INTO `user_role` VALUES (6, 3);
INSERT INTO `user_role` VALUES (7, 3);
INSERT INTO `user_role` VALUES (9, 3);
INSERT INTO `user_role` VALUES (10, 3);

SET FOREIGN_KEY_CHECKS = 1;
