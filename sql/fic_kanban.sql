/*
 Navicat Premium Data Transfer

 Source Server         : tc_fic-kanban
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Schema         : fic_kanban

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 06/08/2022 19:34:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`  (
  `card_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '卡片id',
  `order_in_column` decimal(15, 9) NOT NULL COMMENT '列中顺序',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `column_id` bigint(20) NOT NULL COMMENT '列id',
  `kanban_id` bigint(20) NOT NULL COMMENT '看板id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `is_tagged` tinyint(1) UNSIGNED NOT NULL COMMENT '是否带tag 0没1有',
  `update_user` bigint(20) NOT NULL COMMENT '最后更新用户',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`card_id`) USING BTREE,
  INDEX `column_id`(`column_id`, `card_id`) USING BTREE,
  INDEX `kanban_id`(`kanban_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 436 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡片' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of card
-- ----------------------------

-- ----------------------------
-- Table structure for guide_template
-- ----------------------------
DROP TABLE IF EXISTS `guide_template`;
CREATE TABLE `guide_template`  (
  `index` int(11) NOT NULL COMMENT '顺序',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`index`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of guide_template
-- ----------------------------
INSERT INTO `guide_template` VALUES (1, 'kanban', '【模板】使用指南', '#5585c1');
INSERT INTO `guide_template` VALUES (2, 'column', '待讨论', NULL);
INSERT INTO `guide_template` VALUES (3, 'card', '试着点击卡片，即可进入卡片详情。\r\n\r\n卡片详情中可对卡片内容及标签进行修改编辑。\r\n\r\n修改完成后点击空白处或者关闭按钮即可保存。', NULL);
INSERT INTO `guide_template` VALUES (4, 'tag', '说明', '#6bf81b');
INSERT INTO `guide_template` VALUES (5, 'card', '功能aaa', NULL);
INSERT INTO `guide_template` VALUES (6, 'card', '功能bbb', NULL);
INSERT INTO `guide_template` VALUES (7, 'tag', '推迟', '#41ffcc');
INSERT INTO `guide_template` VALUES (8, 'column', '研发中', NULL);
INSERT INTO `guide_template` VALUES (9, 'card', '鼠标放在卡片上，卡片右上角会出现\"更多\"的图标，试着点击一下吧。\r\n\r\n在\"更多\"中，可以调整卡片上下顺序、把卡片流转到其他列、删除卡片。', NULL);
INSERT INTO `guide_template` VALUES (10, 'tag', '说明', '#6bf81b');
INSERT INTO `guide_template` VALUES (11, 'card', '功能ccc', NULL);
INSERT INTO `guide_template` VALUES (12, 'column', '已完成', NULL);
INSERT INTO `guide_template` VALUES (13, 'column', '更多说明', NULL);
INSERT INTO `guide_template` VALUES (14, 'card', '不仅卡片可以调整顺序，列也可以调整顺序哦。\r\n\r\n可以在列标题右边的\"更多\"图标找到该功能。', NULL);
INSERT INTO `guide_template` VALUES (15, 'tag', '说明', '#6bf81b');
INSERT INTO `guide_template` VALUES (16, 'card', '标签支持自定义颜色哦。', NULL);
INSERT INTO `guide_template` VALUES (17, 'tag', 'abc', '#8cddf6');
INSERT INTO `guide_template` VALUES (18, 'tag', 'def', '#3605ad');
INSERT INTO `guide_template` VALUES (19, 'card', '标签还支持无内容标签哦。', NULL);
INSERT INTO `guide_template` VALUES (20, 'tag', '', '#d82b0f');
INSERT INTO `guide_template` VALUES (21, 'tag', '', '#7dbd6f');

-- ----------------------------
-- Table structure for invitation
-- ----------------------------
DROP TABLE IF EXISTS `invitation`;
CREATE TABLE `invitation`  (
  `invitation_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '邀请id',
  `invited_user` bigint(20) NOT NULL COMMENT '受邀人',
  `send_user` bigint(20) NOT NULL COMMENT '发送用户',
  `kanban_id` bigint(20) NOT NULL COMMENT '看板id',
  `invitation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  `state` int(11) NOT NULL COMMENT '状态 1未处理 2邀请成功 3拒绝',
  PRIMARY KEY (`invitation_id`) USING BTREE,
  INDEX `invited_user`(`invited_user`, `invitation_time`) USING BTREE,
  INDEX `kanban_id`(`kanban_id`, `invited_user`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of invitation
-- ----------------------------

-- ----------------------------
-- Table structure for kanban
-- ----------------------------
DROP TABLE IF EXISTS `kanban`;
CREATE TABLE `kanban`  (
  `kanban_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '看板id',
  `owner_id` bigint(20) NOT NULL COMMENT '所有者id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '看板名称标题',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `color` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '颜色',
  `type` int(11) NOT NULL COMMENT '类型',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`kanban_id`) USING BTREE,
  INDEX `owner_id`(`owner_id`, `kanban_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '看板信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kanban
-- ----------------------------

-- ----------------------------
-- Table structure for kanban_column
-- ----------------------------
DROP TABLE IF EXISTS `kanban_column`;
CREATE TABLE `kanban_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `column_order` decimal(15, 9) NOT NULL,
  `column_title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `kanban_id` bigint(20) NOT NULL,
  `update_user` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`column_id`) USING BTREE,
  INDEX `kanban_id`(`kanban_id`, `column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 236 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kanban_column
-- ----------------------------

-- ----------------------------
-- Table structure for share_kanban
-- ----------------------------
DROP TABLE IF EXISTS `share_kanban`;
CREATE TABLE `share_kanban`  (
  `kanban_id` bigint(20) NOT NULL COMMENT '看板id',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `is_collect` tinyint(1) UNSIGNED NOT NULL COMMENT '是否收藏 0否 1是',
  `join_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  PRIMARY KEY (`kanban_id`, `userid`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '协作/分享信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of share_kanban
-- ----------------------------

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tag_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `card_id` bigint(20) NOT NULL COMMENT '卡片id',
  `type` int(11) NOT NULL COMMENT '类型',
  `color` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '颜色',
  `content` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `kanban_id` bigint(20) NOT NULL COMMENT '看板id',
  `create_user` bigint(20) NOT NULL COMMENT '哪个用户创建的',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE,
  INDEX `card_id`(`card_id`) USING BTREE,
  INDEX `kanban_id`(`kanban_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 328 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tag
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
