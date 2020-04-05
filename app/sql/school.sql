/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : school

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 05/04/2020 21:17:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for allocate
-- ----------------------------
DROP TABLE IF EXISTS `allocate`;
CREATE TABLE `allocate`  (
  `class_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `course_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `teacher_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`class_id`, `course_id`) USING BTREE,
  INDEX `PK_allocate_teacher`(`teacher_id`) USING BTREE,
  INDEX `PK_allocate_score`(`course_id`) USING BTREE,
  CONSTRAINT `PK_allocate_score` FOREIGN KEY (`course_id`) REFERENCES `course` (`c_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `PK_allocate_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`t_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of allocate
-- ----------------------------
INSERT INTO `allocate` VALUES ('1031', '3-245', '804');
INSERT INTO `allocate` VALUES ('1031', '3-105', '825');
INSERT INTO `allocate` VALUES ('1033', '3-105', '825');
INSERT INTO `allocate` VALUES ('1033', '6-166', '856');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `c_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `c_name` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('3-105', '计算机导论');
INSERT INTO `course` VALUES ('3-245', '操作系统');
INSERT INTO `course` VALUES ('6-166', '数字电路');
INSERT INTO `course` VALUES ('9-888', '高等数学');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `s_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `c_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mark` float(255, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`s_id`, `c_id`) USING BTREE,
  INDEX `PK_score_course`(`c_id`) USING BTREE,
  CONSTRAINT `PK_score_course` FOREIGN KEY (`c_id`) REFERENCES `course` (`c_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `PK_score_student` FOREIGN KEY (`s_id`) REFERENCES `student` (`s_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('101', '3-105', 64);
INSERT INTO `score` VALUES ('101', '6-166', 85);
INSERT INTO `score` VALUES ('103', '3-105', 92);
INSERT INTO `score` VALUES ('103', '3-245', 86);
INSERT INTO `score` VALUES ('105', '3-105', 88);
INSERT INTO `score` VALUES ('105', '3-245', 75);
INSERT INTO `score` VALUES ('107', '3-105', 91);
INSERT INTO `score` VALUES ('107', '6-166', 79);
INSERT INTO `score` VALUES ('108', '3-105', 78);
INSERT INTO `score` VALUES ('108', '6-166', NULL);
INSERT INTO `score` VALUES ('109', '3-105', 76);
INSERT INTO `score` VALUES ('109', '3-245', 68);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `s_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `s_name` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_sex` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_birth` datetime(6) NULL DEFAULT NULL,
  `s_class` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`s_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('101', '李军', '男', '1992-02-20 00:00:00.000000', '1033');
INSERT INTO `student` VALUES ('103', '陆君', '男', '1991-06-03 00:00:00.000000', '1031');
INSERT INTO `student` VALUES ('105', '匡明', '男', '1990-10-02 00:00:00.000000', '1031');
INSERT INTO `student` VALUES ('107', '王丽', '女', '1992-01-23 00:00:00.000000', '1033');
INSERT INTO `student` VALUES ('108', '曾华', '男', '1991-09-01 00:00:00.000000', '1033');
INSERT INTO `student` VALUES ('109', '王芳', '女', '1992-02-10 00:00:00.000000', '1031');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `t_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `t_name` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `t_sex` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `t_birth` datetime(6) NULL DEFAULT NULL,
  `t_job` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `t_depart` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('804', '李诚', '男', '1958-12-02 00:00:00.000000', '教授', '计算机系');
INSERT INTO `teacher` VALUES ('825', '王萍', '女', '1972-05-05 00:00:00.000000', '讲师', '计算机系');
INSERT INTO `teacher` VALUES ('831', '刘冰', '男', '1977-08-14 00:00:00.000000', '助教', '电子工程系');
INSERT INTO `teacher` VALUES ('856', '张旭', '男', '1969-03-12 00:00:00.000000', '教授', '电子工程系');

SET FOREIGN_KEY_CHECKS = 1;
