/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : shi_xun

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 13/11/2023 19:12:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
                         `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` (`id`, `password`, `email`, `name`) VALUES ('555', '333', '21301043@bjtu.edu.cn', '刘麦');
COMMIT;

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
                           `contentID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `cno` varchar(20) DEFAULT NULL,
                           `sno` varchar(20) DEFAULT NULL,
                           `homeworkID` varchar(20) DEFAULT NULL,
                           `content` blob,
                           `appeal_content` varchar(255) DEFAULT NULL,
                           `times` int DEFAULT NULL,
                           `score1` double DEFAULT NULL,
                           `score2` double DEFAULT NULL,
                           `score3` double DEFAULT NULL,
                           `score4` double DEFAULT NULL,
                           `score5` double DEFAULT NULL,
                           `score6` double DEFAULT NULL,
                           `score` double DEFAULT NULL,
                           `sumit_time` datetime DEFAULT NULL,
                           `is_submit` varchar(10) DEFAULT NULL,
                           PRIMARY KEY (`contentID`) USING BTREE,
                           KEY `content_cno` (`cno`),
                           KEY `content_sno` (`sno`),
                           KEY `content_homeworkID` (`homeworkID`),
                           CONSTRAINT `content_cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`),
                           CONSTRAINT `content_homeworkID` FOREIGN KEY (`homeworkID`) REFERENCES `homework` (`homeworkID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT `content_sno` FOREIGN KEY (`sno`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
                          `cno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `cname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `tno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          PRIMARY KEY (`cno`) USING BTREE,
                          KEY `course_tno` (`tno`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
BEGIN;
INSERT INTO `course` (`cno`, `cname`, `tno`) VALUES ('1001', '操作系统', '21001001');
INSERT INTO `course` (`cno`, `cname`, `tno`) VALUES ('1002', '机器学习', '21001002');
INSERT INTO `course` (`cno`, `cname`, `tno`) VALUES ('1003', '软件测试', '21001003');
INSERT INTO `course` (`cno`, `cname`, `tno`) VALUES ('1004', '项目运维', '21001004');
INSERT INTO `course` (`cno`, `cname`, `tno`) VALUES ('1005', '实训', '21001005');
COMMIT;

-- ----------------------------
-- Table structure for homework
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
                            `homeworkID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `cno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `submit_ddl` date DEFAULT NULL,
                            `content` blob,
                            `tno` varchar(20) DEFAULT NULL,
                            `score_ddl` date DEFAULT NULL,
                            PRIMARY KEY (`homeworkID`) USING BTREE,
                            KEY `hoemwork_cno` (`cno`),
                            KEY `homework_tno` (`tno`),
                            CONSTRAINT `hoemwork_cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`),
                            CONSTRAINT `homework_tno` FOREIGN KEY (`tno`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of homework
-- ----------------------------
BEGIN;
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10011', '1001', '实验1', '2023-10-31', 0x00, '21001001', '2023-11-02');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10012', '1001', '实验2', '2023-11-04', 0x00, '21001001', '2023-11-06');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10021', '1002', '实验1', '2023-11-01', 0x00, '21001002', '2023-11-03');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10022', '1002', '感知机', '2023-11-07', 0x00, '21001002', '2023-11-09');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10031', '1003', '黑盒测试', '2023-10-28', 0x00, '21001003', '2023-10-30');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10032', '1003', '测试', '2023-11-05', 0x00, '21001003', '2023-11-07');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10041', '1004', '课堂小测1', '2023-11-03', 0x00, '21001004', '2023-11-05');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10042', '1004', '课堂小测2', '2023-11-10', 0x00, '21001004', '2023-11-12');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10051', '1005', '实训', '2023-10-17', 0x00, '21001005', '2023-10-19');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10052', '1005', '实训', '2023-10-31', 0x00, '21001005', '2023-11-02');
INSERT INTO `homework` (`homeworkID`, `cno`, `name`, `submit_ddl`, `content`, `tno`, `score_ddl`) VALUES ('10053', '1005', '实训', '2023-11-10', 0x00, '21001005', '2023-11-12');
COMMIT;

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc` (
                      `sno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                      `cno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                      `score` int DEFAULT NULL,
                      PRIMARY KEY (`sno`,`cno`),
                      KEY `sc_cno` (`cno`),
                      CONSTRAINT `sc_cno` FOREIGN KEY (`cno`) REFERENCES `course` (`cno`),
                      CONSTRAINT `sc_sno` FOREIGN KEY (`sno`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sc
-- ----------------------------
BEGIN;
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301001', '1001', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301001', '1002', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301001', '1003', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301002', '1001', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301002', '1002', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301002', '1003', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301003', '1001', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301003', '1003', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301003', '1004', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301004', '1002', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301004', '1004', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301004', '1005', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301005', '1003', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301005', '1004', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301005', '1005', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301006', '1002', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301006', '1003', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301006', '1004', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301007', '1001', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301007', '1003', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301007', '1005', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301008', '1001', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301008', '1002', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301008', '1004', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301009', '1003', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301009', '1004', NULL);
INSERT INTO `sc` (`sno`, `cno`, `score`) VALUES ('21301009', '1005', NULL);
COMMIT;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
                         `sno` varchar(20) NOT NULL,
                         `contentID` varchar(20) NOT NULL,
                         `score` double DEFAULT NULL,
                         `time` datetime DEFAULT NULL,
                         `content` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`sno`,`contentID`),
                         KEY `score_contentID` (`contentID`),
                         CONSTRAINT `score_contentID` FOREIGN KEY (`contentID`) REFERENCES `content` (`contentID`),
                         CONSTRAINT `score_sno` FOREIGN KEY (`sno`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of score
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
                           `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `age` int DEFAULT NULL,
                           `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
BEGIN;
INSERT INTO `student` (`id`, `name`, `sex`, `email`, `age`, `password`) VALUES ('21301001', '张飞', '男', '2461172547@qq.com', 21, '4eFSt1KosQJzERsG+t3iJA==');
INSERT INTO `student` (`id`, `name`, `sex`, `email`, `age`, `password`) VALUES ('21301002', '关羽', '男', '2461172547@qq.com', 21, 'oh8hGbhmpv4aJIm8X+eIGQ==');
INSERT INTO `student` (`id`, `name`, `sex`, `email`, `age`, `password`) VALUES ('21301003', '赵云', '男', '2461172547@qq.com', 21, '4234PAyaZ76I3HdA5WY86g==');
INSERT INTO `student` (`id`, `name`, `sex`, `email`, `age`, `password`) VALUES ('21301004', '黄忠', '男', '2461172547@qq.com', 21, 'v9/Pmtd9hHPuZVtvyCV7Vg==');
INSERT INTO `student` (`id`, `name`, `sex`, `email`, `age`, `password`) VALUES ('21301005', '马超', '男', '2461172547@qq.com', 21, 'sXEeK3I7Mw+P8UrcXkxCLQ==');
INSERT INTO `student` (`id`, `name`, `sex`, `email`, `age`, `password`) VALUES ('21301006', '诸葛亮', '男', '2461172547@qq.com', 21, '+m8+7ZYRKnOb2819qIb6GA==');
INSERT INTO `student` (`id`, `name`, `sex`, `email`, `age`, `password`) VALUES ('21301007', '刘备', '男', '2461172547@qq.com', 21, '9lhHnRulVWDxug4+Kk3TOg==');
INSERT INTO `student` (`id`, `name`, `sex`, `email`, `age`, `password`) VALUES ('21301008', '刘禅', '男', '2461172547@qq.com', 21, '3oQU+/y/hcVhXyAdBOPNKg==');
INSERT INTO `student` (`id`, `name`, `sex`, `email`, `age`, `password`) VALUES ('21301009', '诸葛瞻', '男', '2461172547@qq.com', 21, 'CVGFB6ku7R4SM0SpL7aBkw==');
COMMIT;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
                           `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                           `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `age` int DEFAULT NULL,
                           `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `sex` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of teacher
-- ----------------------------
BEGIN;
INSERT INTO `teacher` (`id`, `name`, `age`, `email`, `password`, `sex`) VALUES ('21001001', '曹操', 35, '12345', 'kOXPXXRgOBthTT3tg1hFTQ==', '男');
INSERT INTO `teacher` (`id`, `name`, `age`, `email`, `password`, `sex`) VALUES ('21001002', '典韦', 35, '12345', 'LUiSrS2oC8115Htvew9RFA==', '男');
INSERT INTO `teacher` (`id`, `name`, `age`, `email`, `password`, `sex`) VALUES ('21001003', '许褚', 35, '12345', 'g010hnJmUtkj5nkQDpxDnw==', '男');
INSERT INTO `teacher` (`id`, `name`, `age`, `email`, `password`, `sex`) VALUES ('21001004', '郭嘉', 35, '12345', 'Q/7wqQvHICQgYEHGq3aESA==', '男');
INSERT INTO `teacher` (`id`, `name`, `age`, `email`, `password`, `sex`) VALUES ('21001005', '荀彧', 35, '12345', 'gbywccUL79N3Rj5w+iNYIg==', '男');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
