/*
 Navicat Premium Data Transfer

 Source Server         : tc_fic-user
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Schema         : fic_user

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 06/08/2022 19:34:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源地址',
  `desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`url`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('/api/hello', '测试');
INSERT INTO `resource` VALUES ('/user/currentUser', '用户信息');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色标识名',
  `role_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  PRIMARY KEY (`role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('evaluation', '评估版本用户（未填写注册信息）');
INSERT INTO `role` VALUES ('registered', '已注册的用户');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource`  (
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色',
  `resource_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源地址',
  PRIMARY KEY (`resource_url`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES ('registered', '/invitation');
INSERT INTO `role_resource` VALUES ('evaluation', '/kanban');
INSERT INTO `role_resource` VALUES ('registered', '/kanban');
INSERT INTO `role_resource` VALUES ('evaluation', '/kanban/card');
INSERT INTO `role_resource` VALUES ('registered', '/kanban/card');
INSERT INTO `role_resource` VALUES ('evaluation', '/kanban/card/order');
INSERT INTO `role_resource` VALUES ('registered', '/kanban/card/order');
INSERT INTO `role_resource` VALUES ('evaluation', '/kanban/card/transfer');
INSERT INTO `role_resource` VALUES ('registered', '/kanban/card/transfer');
INSERT INTO `role_resource` VALUES ('evaluation', '/kanban/collect');
INSERT INTO `role_resource` VALUES ('registered', '/kanban/collect');
INSERT INTO `role_resource` VALUES ('evaluation', '/kanban/column');
INSERT INTO `role_resource` VALUES ('registered', '/kanban/column');
INSERT INTO `role_resource` VALUES ('evaluation', '/kanban/column/order');
INSERT INTO `role_resource` VALUES ('registered', '/kanban/column/order');
INSERT INTO `role_resource` VALUES ('evaluation', '/kanban/content');
INSERT INTO `role_resource` VALUES ('registered', '/kanban/content');
INSERT INTO `role_resource` VALUES ('registered', '/kanban/share');
INSERT INTO `role_resource` VALUES ('evaluation', '/kanban/tag');
INSERT INTO `role_resource` VALUES ('registered', '/kanban/tag');
INSERT INTO `role_resource` VALUES ('evaluation', '/search');
INSERT INTO `role_resource` VALUES ('registered', '/search');
INSERT INTO `role_resource` VALUES ('evaluation', '/user/currentUser');
INSERT INTO `role_resource` VALUES ('registered', '/user/currentUser');
INSERT INTO `role_resource` VALUES ('registered', '/user/email');
INSERT INTO `role_resource` VALUES ('evaluation', '/user/info');
INSERT INTO `role_resource` VALUES ('registered', '/user/info');
INSERT INTO `role_resource` VALUES ('registered', '/user/info/avatar');
INSERT INTO `role_resource` VALUES ('registered', '/user/info/nickname');
INSERT INTO `role_resource` VALUES ('registered', '/user/password');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户登录名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `status` int(11) NOT NULL COMMENT '状态 1正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户登录信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户角色',
  PRIMARY KEY (`userid`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
