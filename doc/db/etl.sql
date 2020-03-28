/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : etl

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 28/03/2020 15:07:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for etl_action
-- ----------------------------
DROP TABLE IF EXISTS `etl_action`;
CREATE TABLE `etl_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作ID',
  `action` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作',
  `action_desc` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作描述',
  `default_check` tinyint(1) DEFAULT NULL COMMENT '操作检察',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of etl_action
-- ----------------------------
BEGIN;
INSERT INTO `etl_action` VALUES (1, '1', 'add', '新增', 0);
INSERT INTO `etl_action` VALUES (2, '2', 'query', '查询', 0);
INSERT INTO `etl_action` VALUES (3, '3', 'get', '详情', 0);
INSERT INTO `etl_action` VALUES (4, '4', 'update', '修改', 0);
INSERT INTO `etl_action` VALUES (5, '5', 'delete', '删除', 0);
INSERT INTO `etl_action` VALUES (6, '6', 'export', '导出', 0);
INSERT INTO `etl_action` VALUES (7, '7', 'import', '导入', 0);
COMMIT;

-- ----------------------------
-- Table structure for etl_action_permission
-- ----------------------------
DROP TABLE IF EXISTS `etl_action_permission`;
CREATE TABLE `etl_action_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作ID',
  `permission_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of etl_action_permission
-- ----------------------------
BEGIN;
INSERT INTO `etl_action_permission` VALUES (1, '1', 'dashboard');
INSERT INTO `etl_action_permission` VALUES (2, '2', 'dashboard');
INSERT INTO `etl_action_permission` VALUES (3, '3', 'dashboard');
INSERT INTO `etl_action_permission` VALUES (4, '4', 'dashboard');
INSERT INTO `etl_action_permission` VALUES (5, '5', 'dashboard');
INSERT INTO `etl_action_permission` VALUES (6, '6', 'dashboard');
INSERT INTO `etl_action_permission` VALUES (7, '7', 'dashboard');
INSERT INTO `etl_action_permission` VALUES (9, '1', 'form');
INSERT INTO `etl_action_permission` VALUES (10, '2', 'form');
INSERT INTO `etl_action_permission` VALUES (11, '3', 'form');
INSERT INTO `etl_action_permission` VALUES (12, '4', 'form');
INSERT INTO `etl_action_permission` VALUES (13, '5', 'form');
INSERT INTO `etl_action_permission` VALUES (14, '6', 'form');
INSERT INTO `etl_action_permission` VALUES (15, '7', 'form');
INSERT INTO `etl_action_permission` VALUES (16, '1', 'table');
INSERT INTO `etl_action_permission` VALUES (17, '2', 'table');
INSERT INTO `etl_action_permission` VALUES (18, '3', 'table');
INSERT INTO `etl_action_permission` VALUES (19, '4', 'table');
INSERT INTO `etl_action_permission` VALUES (20, '5', 'table');
INSERT INTO `etl_action_permission` VALUES (21, '6', 'table');
INSERT INTO `etl_action_permission` VALUES (22, '7', 'table');
COMMIT;

-- ----------------------------
-- Table structure for etl_cron
-- ----------------------------
DROP TABLE IF EXISTS `etl_cron`;
CREATE TABLE `etl_cron` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cron` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `cron_desc` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'cron描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of etl_cron
-- ----------------------------
BEGIN;
INSERT INTO `etl_cron` VALUES (1, '*/5 * * * * ?', '5秒执行一次');
INSERT INTO `etl_cron` VALUES (2, '0 0/1 * * * ? ', '1分钟执行一次');
INSERT INTO `etl_cron` VALUES (3, '0 0/2 * * * ?', '2分钟执行一次');
INSERT INTO `etl_cron` VALUES (4, '0 0/3 * * * ?', '3分钟执行一次');
INSERT INTO `etl_cron` VALUES (5, '0 0/5 * * * ?', '5分钟执行一次');
INSERT INTO `etl_cron` VALUES (6, '0 0/30 * * * ?', '30分钟执行一次');
INSERT INTO `etl_cron` VALUES (7, '0 0 0/1 * * ?', '1小时执行一次');
INSERT INTO `etl_cron` VALUES (8, '0 0 0/2 * * ?', '2小时执行一次');
INSERT INTO `etl_cron` VALUES (9, '0 0 0/3 * * ?', '3小时执行一次');
INSERT INTO `etl_cron` VALUES (10, '0 0 0 * * ?', '每日0点执行一次');
INSERT INTO `etl_cron` VALUES (11, '0 0 1 * * ?', '每日1点执行一次');
INSERT INTO `etl_cron` VALUES (12, '0 0 2 * * ?', '每日2点执行一次');
INSERT INTO `etl_cron` VALUES (13, '0 */15 * * * ?', '每15分钟执行一次');
COMMIT;

-- ----------------------------
-- Table structure for etl_permission
-- ----------------------------
DROP TABLE IF EXISTS `etl_permission`;
CREATE TABLE `etl_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限ID',
  `permission_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of etl_permission
-- ----------------------------
BEGIN;
INSERT INTO `etl_permission` VALUES (1, 'dashboard', '监控');
INSERT INTO `etl_permission` VALUES (2, 'form', '表单');
INSERT INTO `etl_permission` VALUES (3, 'table', '表格');
COMMIT;

-- ----------------------------
-- Table structure for etl_role
-- ----------------------------
DROP TABLE IF EXISTS `etl_role`;
CREATE TABLE `etl_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `role_desc` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户描述',
  `status` int(11) DEFAULT NULL COMMENT '角色状态',
  `creator_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建ID',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `deleted` int(11) DEFAULT NULL COMMENT '是否删除 0正常 1删除',
  `role_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of etl_role
-- ----------------------------
BEGIN;
INSERT INTO `etl_role` VALUES (1, '管理员', '拥有所有权限', 1, 'system', '2020-03-05 00:00:00', 1, 'admin');
COMMIT;

-- ----------------------------
-- Table structure for etl_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `etl_role_permission`;
CREATE TABLE `etl_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色ID',
  `permission_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of etl_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `etl_role_permission` VALUES (1, 'admin', 'dashboard');
INSERT INTO `etl_role_permission` VALUES (2, 'admin', 'form');
INSERT INTO `etl_role_permission` VALUES (3, 'admin', 'table');
COMMIT;

-- ----------------------------
-- Table structure for etl_user
-- ----------------------------
DROP TABLE IF EXISTS `etl_user`;
CREATE TABLE `etl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `username` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名称',
  `password` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `avatar` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `telephone` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话号码',
  `last_login_ip` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后一次登陆IP',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后一次登陆时间',
  `creator_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建Id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `merchant_code` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商号',
  `deleted` int(11) DEFAULT NULL COMMENT '是否删除 0正常 1删除',
  `role_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色ID',
  `access_token` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'token',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of etl_user
-- ----------------------------
BEGIN;
INSERT INTO `etl_user` VALUES (1, 'Kettle', 'admin', 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, '123456', NULL, NULL, NULL, NULL, NULL, 0, 'admin', 'c1a8347a53f349729b85d100297ff489');
COMMIT;

-- ----------------------------
-- Table structure for kettle_resource
-- ----------------------------
DROP TABLE IF EXISTS `kettle_resource`;
CREATE TABLE `kettle_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '转换或作业名称',
  `repository_directory` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源地址',
  `modified_user` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编辑者',
  `modified_date` datetime DEFAULT NULL COMMENT '编辑时间',
  `object_type` int(11) DEFAULT NULL COMMENT '资源类型1:转换 2:作业',
  `object_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `log_level` int(11) DEFAULT NULL COMMENT '日志等级',
  `kettle_params` text COLLATE utf8mb4_general_ci COMMENT '调度参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2247 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_group`;
CREATE TABLE `xxl_job_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行器名称',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of xxl_job_group
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_group` VALUES (11, 'kettle-job', 'kettle-job', 1, 0, '192.168.0.2:9998');
COMMIT;

-- ----------------------------
-- Table structure for xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_info`;
CREATE TABLE `xxl_job_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  `object_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'Kettle资源ID',
  `object_type` int(11) DEFAULT NULL COMMENT 'Kettle资源类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2701 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of xxl_job_lock
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');
COMMIT;

-- ----------------------------
-- Table structure for xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log`;
CREATE TABLE `xxl_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  `read_mark` int(11) DEFAULT '0' COMMENT '已读标记 0:默认 1:未读 2:已读',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `I_trigger_time` (`trigger_time`) USING BTREE,
  KEY `I_handle_code` (`handle_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for xxl_job_log_report
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_report`;
CREATE TABLE `xxl_job_log_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of xxl_job_log_report
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_log_report` VALUES (43, '2020-03-23 00:00:00', 0, 0, 23);
INSERT INTO `xxl_job_log_report` VALUES (44, '2020-03-22 00:00:00', 0, 0, 0);
INSERT INTO `xxl_job_log_report` VALUES (45, '2020-03-21 00:00:00', 0, 0, 0);
INSERT INTO `xxl_job_log_report` VALUES (46, '2020-03-28 00:00:00', 0, 0, 1);
INSERT INTO `xxl_job_log_report` VALUES (47, '2020-03-27 00:00:00', 0, 0, 0);
INSERT INTO `xxl_job_log_report` VALUES (48, '2020-03-26 00:00:00', 0, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_logglue`;
CREATE TABLE `xxl_job_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_registry`;
CREATE TABLE `xxl_job_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registry_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registry_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=290 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of xxl_job_registry
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_registry` VALUES (289, 'EXECUTOR', 'kettle-job', '192.168.0.2:9998', '2020-03-28 15:06:53');
COMMIT;

-- ----------------------------
-- Table structure for xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_user`;
CREATE TABLE `xxl_job_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `role` tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of xxl_job_user
-- ----------------------------
BEGIN;
INSERT INTO `xxl_job_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
