/*
 Navicat Premium Data Transfer

 Source Server         : localhost3306
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : workflow

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 06/11/2023 18:43:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for flow_definition
-- ----------------------------
DROP TABLE IF EXISTS `flow_definition`;
CREATE TABLE `flow_definition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '审批id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批名称',
  `director_max_level` int(11) NULL DEFAULT NULL COMMENT '审批主管最大层级',
  `definition_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批code',
  `company_id` int(11) NULL DEFAULT NULL COMMENT '企业id',
  `definition_user_id` int(11) NULL DEFAULT NULL COMMENT '流程定义人',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定义人昵称',
  `module_id` int(11) NULL DEFAULT NULL COMMENT '模块id',
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `status` bit(1) NULL DEFAULT b'0' COMMENT '是否开放（0未开启 1已开启）',
  `definition_node` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '模板审批节点JSON',
  `created_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted` bit(1) NULL DEFAULT b'0',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询CODE',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '审批流程定义表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_definition
-- ----------------------------

-- ----------------------------
-- Table structure for flow_definition_node
-- ----------------------------
DROP TABLE IF EXISTS `flow_definition_node`;
CREATE TABLE `flow_definition_node`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `definition_id` int(11) NULL DEFAULT NULL COMMENT '定义审批id',
  `node_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点code',
  `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父节点id',
  `parent_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点code',
  `type` int(11) NULL DEFAULT NULL COMMENT '发起人 1审批 2抄送 3条件 4路由',
  `priority_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '条件优先级',
  `settype` int(11) NULL DEFAULT NULL COMMENT '审批人设置 1指定成员 2主管 4发起人自选 5发起人自己 7连续多级主管',
  `select_mode` int(11) NULL DEFAULT NULL COMMENT '审批人数 1选一个人 2选多个人    ',
  `select_range` int(11) NULL DEFAULT NULL COMMENT '选择范围 1.全公司 2指定成员 2指定角色',
  `director_level` int(11) NULL DEFAULT NULL COMMENT '审批终点  最高层主管数',
  `examine_mode` int(11) NULL DEFAULT NULL COMMENT '多人审批时采用的审批方式 1依次审批 2会签',
  `no_hander_action` int(11) NULL DEFAULT NULL COMMENT '审批人为空时 1自动审批通过/不允许发起 2转交给审核管理员',
  `examine_end_director_level` int(11) NULL DEFAULT NULL COMMENT '审批终点 第n层主管',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除：0否，1是',
  `cc_self_select_flag` int(11) NULL DEFAULT NULL COMMENT '允许发起人自选抄送人',
  `created_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `sponsor_all_flag` bit(1) NULL DEFAULT NULL COMMENT '发起人是否所有人',
  `definition_sort` int(11) NOT NULL COMMENT '节点顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 198 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点定义' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_definition_node
-- ----------------------------

-- ----------------------------
-- Table structure for flow_instance
-- ----------------------------
DROP TABLE IF EXISTS `flow_instance`;
CREATE TABLE `flow_instance`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
  `instance_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实例代码',
  `definition_id` int(11) NULL DEFAULT NULL COMMENT '定义id',
  `definition_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定义代码',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '申请人用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人用户名称',
  `company_id` int(11) NULL DEFAULT NULL COMMENT '申请人企业id',
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人姓名',
  `module_id` int(11) NULL DEFAULT NULL COMMENT '模块id 配置表',
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `b_id` int(11) NULL DEFAULT NULL COMMENT '业务id',
  `deleted` bit(1) NULL DEFAULT b'0',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态：0创建,1进行中,2已完成,3已终止',
  `created_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询CODE',
  `biz_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '业务金额',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批内容',
  `b_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务记录查询CODE',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code`(`code`) USING BTREE,
  INDEX `idx_bcode`(`b_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 149 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程实例表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_instance
-- ----------------------------

-- ----------------------------
-- Table structure for flow_instance_node
-- ----------------------------
DROP TABLE IF EXISTS `flow_instance_node`;
CREATE TABLE `flow_instance_node`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `node_id` int(11) NULL DEFAULT NULL COMMENT '节点id',
  `node_parent_id` int(11) NULL DEFAULT NULL COMMENT '父节点id',
  `instance_id` int(11) NULL DEFAULT NULL COMMENT '实例id',
  `instance_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实例编码',
  `task_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务编码',
  `company_id` int(11) NULL DEFAULT NULL COMMENT '企业id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请人姓名',
  `def_node_id` int(11) NULL DEFAULT NULL COMMENT '定义节点id',
  `def_node_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定义节点名称',
  `action_status` int(11) NULL DEFAULT NULL COMMENT ' 状态：0待处理，1同意，2不同意等',
  `action_time` datetime(0) NULL DEFAULT NULL COMMENT '批阅时间',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批注',
  `created_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询code',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流程实例节点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_instance_node
-- ----------------------------

-- ----------------------------
-- Table structure for flow_module
-- ----------------------------
DROP TABLE IF EXISTS `flow_module`;
CREATE TABLE `flow_module`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询code',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `parent_id` int(11) NULL DEFAULT NULL,
  `module_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `rel_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联类的全路径',
  `deleted` bit(1) NULL DEFAULT b'0',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审批流模块配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_module
-- ----------------------------

-- ----------------------------
-- Table structure for flow_my_module
-- ----------------------------
DROP TABLE IF EXISTS `flow_my_module`;
CREATE TABLE `flow_my_module`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询code',
  `company_id` int(11) NULL DEFAULT NULL COMMENT '客户企业id',
  `module_id` int(11) NULL DEFAULT NULL COMMENT '模块id',
  `module_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `open` bit(1) NULL DEFAULT b'0' COMMENT '是否开启',
  `def_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定义名称',
  `definition_id` int(11) NULL DEFAULT NULL COMMENT '审批定义id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '描述',
  `flow_set` int(11) NULL DEFAULT 0 COMMENT '设置审批流状态 0未设置 1已设置 2已发布',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除：0否，1是',
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审批流模块企业配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_my_module
-- ----------------------------

-- ----------------------------
-- Table structure for flow_node_condition
-- ----------------------------
DROP TABLE IF EXISTS `flow_node_condition`;
CREATE TABLE `flow_node_condition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `column_id` int(11) NULL DEFAULT NULL COMMENT ' 发起人',
  `node_id` int(11) NULL DEFAULT NULL COMMENT '审批节点',
  `type` int(11) NULL DEFAULT NULL COMMENT '1 发起人 2其他',
  `opt_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型[\"\", \"<\", \">\", \"≤\", \"=\", \"≥\"]',
  `zdy1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '左侧自定义内容',
  `zdy2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '右侧自定义内容',
  `opt1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '左侧符号 < ≤',
  `opt2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '右侧符号 < ≤',
  `cloumn_db_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '条件字段名称',
  `column_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '条件字段类型',
  `show_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'checkBox多选 其他',
  `show_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '展示名',
  `fixed_down_box_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多选数组',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除：0否，1是',
  `created_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `user_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '审理条件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_node_condition
-- ----------------------------

-- ----------------------------
-- Table structure for flow_node_permission
-- ----------------------------
DROP TABLE IF EXISTS `flow_node_permission`;
CREATE TABLE `flow_node_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型1个人 3部门',
  `target_id` int(11) NULL DEFAULT NULL COMMENT '目标id（操作人id）',
  `definition_id` int(11) NULL DEFAULT NULL COMMENT '流程id',
  `created_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点发起人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_node_permission
-- ----------------------------

-- ----------------------------
-- Table structure for flow_node_user
-- ----------------------------
DROP TABLE IF EXISTS `flow_node_user`;
CREATE TABLE `flow_node_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `target_id` int(11) NULL DEFAULT NULL COMMENT '用户id或角色id或部门id',
  `company_id` int(11) NULL DEFAULT NULL COMMENT '企业id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型1指定人2角色3部门',
  `node_id` int(11) NULL DEFAULT NULL COMMENT '条件节点id',
  `user_type` int(11) NULL DEFAULT 0 COMMENT '用户类型 0审批人1发起人2抄送人',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除：0否，1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 156 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '节点操作人' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_node_user
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `province_regioncode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份区域代码',
  `city_regioncode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地市区域代码',
  `address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id,可多个逗号隔开',
  `company_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属企业id，如果存在多个用逗号隔开',
  `notify_message` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否通知系统消息 0：关 1：开',
  `notify_todo` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否通知待办任务 0：关 1：开',
  `confirmation_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改手机确认函',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `tenant_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '10001' COMMENT '租户编码',
  `update_pwd_time` datetime(0) NULL DEFAULT NULL COMMENT '用户修改密码时间',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询code',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_mobile`(`mobile`) USING BTREE,
  INDEX `idx_user_code`(`code`(191)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1177 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
