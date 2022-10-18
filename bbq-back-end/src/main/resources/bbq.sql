/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : bbq

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 10/10/2022 19:17:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bbq_article_type
-- ----------------------------
DROP TABLE IF EXISTS `bbq_article_type`;
CREATE TABLE `bbq_article_type`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'name',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'desc',
  `ref_count` bigint(11) NOT NULL DEFAULT 0 COMMENT 'Number of times cited',
  `scope` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'action scope',
  `is_delete` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Deletion (0: not deleted, 1: deleted)',
  `create_time` datetime NOT NULL COMMENT 'record create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'record update time',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'article type table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bbq_comments
-- ----------------------------
DROP TABLE IF EXISTS `bbq_comments`;
CREATE TABLE `bbq_comments`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `user_id` bigint(11) NOT NULL COMMENT 'comment user id',
  `reply_id` bigint(11) NULL DEFAULT NULL COMMENT 'Commented ID',
  `reply_reply_id` bigint(11) NULL DEFAULT NULL COMMENT 'Secondary commented ID',
  `posts_id` bigint(11) NOT NULL COMMENT 'Post ID',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'comment content',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Deletion (0: not deleted, 1: deleted)',
  `create_time` datetime NOT NULL COMMENT 'record create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'record update time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_uid_pid`(`user_id`, `posts_id`) USING BTREE,
  INDEX `idx_postsid`(`posts_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'post comment table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bbq_follow
-- ----------------------------
DROP TABLE IF EXISTS `bbq_follow`;
CREATE TABLE `bbq_follow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `followed` bigint(11) NOT NULL COMMENT 'Concerned person',
  `follower` bigint(11) NOT NULL COMMENT 'Pay attention to people',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Deletion (0: not deleted, 1: deleted)',
  `create_time` datetime NOT NULL COMMENT 'record create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'record update time',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_followed_follower`(`followed`, `follower`) USING BTREE,
  INDEX `idx_follower`(`follower`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'user follow table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bbq_message
-- ----------------------------
DROP TABLE IF EXISTS `bbq_message`;
CREATE TABLE `bbq_message`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `channel` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'send channel',
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'message type',
  `read` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'is read',
  `sender` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sender',
  `receiver` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'receiver',
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'send title',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'send content',
  `is_delete` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Deletion (0: not deleted, 1: deleted)',
  `create_time` datetime NOT NULL COMMENT 'record create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'record update time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sender`(`sender`) USING BTREE,
  INDEX `idx_receiver_type`(`receiver`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'send message table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bbq_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `bbq_operate_log`;
CREATE TABLE `bbq_operate_log`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'operate type',
  `operator_id` bigint(11) NOT NULL COMMENT 'operator id',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'content',
  `is_delete` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Deletion (0: not deleted, 1: deleted)',
  `create_time` datetime NOT NULL COMMENT 'record create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'record update time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_operator_id`(`operator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'operate log table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bbq_posts
-- ----------------------------
DROP TABLE IF EXISTS `bbq_posts`;
CREATE TABLE `bbq_posts`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `audit_state` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'audit state',
  `category` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'category',
  `author_id` bigint(11) NOT NULL COMMENT 'author id',
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'title',
  `content_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'content type',
  `markdown_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'md content',
  `html_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'html content',
  `views` bigint(11) NOT NULL DEFAULT 0 COMMENT 'article views',
  `approvals` bigint(11) NOT NULL DEFAULT 0 COMMENT 'Liked/collected amount',
  `comments` bigint(11) NOT NULL DEFAULT 0 COMMENT 'Comment quantity',
  `type_id` bigint(11) NOT NULL DEFAULT 0 COMMENT 'Article type ID',
  `head_img` varchar(8192) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Head picture of article',
  `official` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'official',
  `top` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'sticky',
  `sort` int(4) NOT NULL DEFAULT 1000 COMMENT 'sort',
  `marrow` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'marrow',
  `is_delete` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Deletion (0: not deleted, 1: deleted)',
  `create_time` datetime NOT NULL COMMENT 'record create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'record update time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_author`(`author_id`) USING BTREE,
  INDEX `idx_category_state_views`(`category`, `audit_state`, `views`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'posts table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bbq_tag
-- ----------------------------
DROP TABLE IF EXISTS `bbq_tag`;
CREATE TABLE `bbq_tag`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'group name',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'name',
  `description` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'desc',
  `ref_count` bigint(11) NOT NULL DEFAULT 0 COMMENT 'ref count',
  `is_delete` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Deletion (0: not deleted, 1: deleted)',
  `create_time` datetime NOT NULL COMMENT 'record create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'record update time',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'lable table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bbq_user
-- ----------------------------
DROP TABLE IF EXISTS `bbq_user`;
CREATE TABLE `bbq_user`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'user email',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'username',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'password',
  `role` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'role',
  `state` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'state',
  `sex` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'sex',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Personal image',
  `signature` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Personal profile',
  `is_delete` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Deletion (0: not deleted, 1: deleted)',
  `create_time` datetime NOT NULL COMMENT 'record create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'record update time',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'user info table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bbq_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `bbq_user_login_log`;
CREATE TABLE `bbq_user_login_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT 'login user id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'login user name',
  `user_role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'login user role',
  `login_time` datetime NULL DEFAULT NULL COMMENT 'user login time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
