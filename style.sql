/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50162
Source Host           : localhost:3306
Source Database       : style

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2014-06-03 18:22:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for com_qlp_style_app
-- ----------------------------
DROP TABLE IF EXISTS `com_qlp_style_app`;
CREATE TABLE `com_qlp_style_app` (
  `id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `visiable` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of com_qlp_style_app
-- ----------------------------
INSERT INTO `com_qlp_style_app` VALUES ('bbs_manager', '管理论坛的模块', '论坛管理', '3', '', 'ENABLE');
INSERT INTO `com_qlp_style_app` VALUES ('cms_manager', '管理新闻内容的模块', '内容管理', '2', null, 'ENABLE');
INSERT INTO `com_qlp_style_app` VALUES ('email_manager', '管理邮件的模块', '邮件管理', '4', null, 'ENABLE');
INSERT INTO `com_qlp_style_app` VALUES ('system_manager', '管理用户角色的模块', '系统管理', '1', 'user/index', 'ENABLE');

-- ----------------------------
-- Table structure for com_qlp_style_func
-- ----------------------------
DROP TABLE IF EXISTS `com_qlp_style_func`;
CREATE TABLE `com_qlp_style_func` (
  `id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `visiable` varchar(255) DEFAULT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_be6c8414f8b84338acd25066009` (`app_id`),
  CONSTRAINT `FK_be6c8414f8b84338acd25066009` FOREIGN KEY (`app_id`) REFERENCES `com_qlp_style_app` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of com_qlp_style_func
-- ----------------------------
INSERT INTO `com_qlp_style_func` VALUES ('class_manager', '管理站点下的新闻类别', '分类管理', '5', null, 'ENABLE', 'cms_manager');
INSERT INTO `com_qlp_style_func` VALUES ('inner_user_manager', '管理内部用户', '内部用户管理', '1', 'user/index/list/INNER', 'ENABLE', 'system_manager');
INSERT INTO `com_qlp_style_func` VALUES ('outer_user_manager', '管理注册用户', '注册用户管理', '2', 'user/index/list/OUTER', 'ENABLE', 'system_manager');
INSERT INTO `com_qlp_style_func` VALUES ('role_manager', '管理角色', '角色管理', '3', 'role/roleList', 'ENABLE', 'system_manager');
INSERT INTO `com_qlp_style_func` VALUES ('site_manager', '管理站点', '站点管理', '4', null, 'ENABLE', 'cms_manager');

-- ----------------------------
-- Table structure for com_qlp_style_role
-- ----------------------------
DROP TABLE IF EXISTS `com_qlp_style_role`;
CREATE TABLE `com_qlp_style_role` (
  `id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of com_qlp_style_role
-- ----------------------------
INSERT INTO `com_qlp_style_role` VALUES ('1', null, null, '拥有最高权限', '超级管理员', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_role` VALUES ('2', null, null, null, '普通管理员', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_role` VALUES ('3', null, null, null, '付费注册用户', 'ENABLE', 'OUTER');
INSERT INTO `com_qlp_style_role` VALUES ('4', null, null, null, '普通注册用户', 'ENABLE', 'OUTER');

-- ----------------------------
-- Table structure for com_qlp_style_role_app
-- ----------------------------
DROP TABLE IF EXISTS `com_qlp_style_role_app`;
CREATE TABLE `com_qlp_style_role_app` (
  `role_id` varchar(255) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  KEY `FK_8b98110f6e624c8f9465632bc56` (`app_id`),
  KEY `FK_3f1bb1cb61fb4bc1a7d24710c2d` (`role_id`),
  CONSTRAINT `FK_3f1bb1cb61fb4bc1a7d24710c2d` FOREIGN KEY (`role_id`) REFERENCES `com_qlp_style_role` (`id`),
  CONSTRAINT `FK_8b98110f6e624c8f9465632bc56` FOREIGN KEY (`app_id`) REFERENCES `com_qlp_style_app` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of com_qlp_style_role_app
-- ----------------------------
INSERT INTO `com_qlp_style_role_app` VALUES ('1', 'bbs_manager');
INSERT INTO `com_qlp_style_role_app` VALUES ('1', 'cms_manager');
INSERT INTO `com_qlp_style_role_app` VALUES ('1', 'email_manager');
INSERT INTO `com_qlp_style_role_app` VALUES ('1', 'system_manager');

-- ----------------------------
-- Table structure for com_qlp_style_user
-- ----------------------------
DROP TABLE IF EXISTS `com_qlp_style_user`;
CREATE TABLE `com_qlp_style_user` (
  `id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_num` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of com_qlp_style_user
-- ----------------------------
INSERT INTO `com_qlp_style_user` VALUES ('1', '2014-05-29 12:46:00', null, '湖北', '2014-06-17 11:26:45', 'zhangsan@163.com', '1', 'root', '张三', '1', '13333', '男', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('10', '2014-05-20 12:53:25', null, '浙江', '2014-06-11 11:26:52', 'test3', null, 'test3', '燕十三', '1', '22222', '男', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('11', '2014-06-03 15:16:28', null, '江西', '2014-06-03 15:16:52', 'xingshisi@163.com', null, 'xingshisi', '辛十四', '1', '33333', '女', 'ENABLE', 'OUTER');
INSERT INTO `com_qlp_style_user` VALUES ('2', '2014-05-15 12:52:53', null, '山东', '2014-06-04 11:26:55', 'wangwu', '1', 'wangwu', '王五', '1', '343234', '女', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('3', '2014-05-23 12:52:58', null, '北京', '2014-06-18 11:26:59', 'zhaoliu', null, 'zhaoliu', '赵六', '1', '2323423', '女', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('4', '2014-04-30 12:53:03', null, '台湾', '2014-06-25 11:27:02', 'chenqi', null, 'chenqi', '陈七', '1', '3241234123', '女', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('5', '2014-05-14 12:53:06', null, '上海', '2014-06-26 11:27:06', 'zhuba', null, 'zhuba', '朱八', '1', '342341', '男', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('6', '2014-05-06 12:53:10', null, '深圳', '2014-06-18 11:27:10', 'zhoujiu', null, 'zhoujiu', '周九', '1', '44433', '男', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('7', '2014-05-28 12:53:14', null, '重庆', '2014-06-25 11:27:13', 'test1', null, 'test1', '吴十', '1', '33', '男', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('8', '2014-05-22 12:53:18', null, '成都', '2014-06-18 11:27:18', 'test2', null, 'test2', '萧十一', '1', '7777', '男', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('8195d7ad-f1e7-4bea-b016-337d0a6ff089', '2014-05-14 17:58:40', null, '福建', '2014-06-19 11:27:23', 'lisi@163.com', '1', 'admin', '李四', '111111', '6577', '女', 'ENABLE', 'INNER');
INSERT INTO `com_qlp_style_user` VALUES ('9', '2014-05-30 12:53:22', null, '江苏', '2014-06-10 11:27:27', 'test3', null, 'test3', '贺十二', '1', '7777', '男', 'ENABLE', 'INNER');

-- ----------------------------
-- Table structure for com_qlp_style_user_role
-- ----------------------------
DROP TABLE IF EXISTS `com_qlp_style_user_role`;
CREATE TABLE `com_qlp_style_user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  KEY `FK_10f9760445b046299bb20b15d00` (`role_id`),
  KEY `FK_354f5bb8830b42ee86344beeffc` (`user_id`),
  CONSTRAINT `FK_354f5bb8830b42ee86344beeffc` FOREIGN KEY (`user_id`) REFERENCES `com_qlp_style_user` (`id`),
  CONSTRAINT `FK_10f9760445b046299bb20b15d00` FOREIGN KEY (`role_id`) REFERENCES `com_qlp_style_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of com_qlp_style_user_role
-- ----------------------------
INSERT INTO `com_qlp_style_user_role` VALUES ('1', '1');
INSERT INTO `com_qlp_style_user_role` VALUES ('8195d7ad-f1e7-4bea-b016-337d0a6ff089', '1');
INSERT INTO `com_qlp_style_user_role` VALUES ('10', '2');
INSERT INTO `com_qlp_style_user_role` VALUES ('2', '2');
INSERT INTO `com_qlp_style_user_role` VALUES ('3', '2');
INSERT INTO `com_qlp_style_user_role` VALUES ('4', '2');
INSERT INTO `com_qlp_style_user_role` VALUES ('5', '2');
INSERT INTO `com_qlp_style_user_role` VALUES ('6', '2');
INSERT INTO `com_qlp_style_user_role` VALUES ('7', '1');
INSERT INTO `com_qlp_style_user_role` VALUES ('7', '2');
INSERT INTO `com_qlp_style_user_role` VALUES ('8', '2');
INSERT INTO `com_qlp_style_user_role` VALUES ('9', '2');
INSERT INTO `com_qlp_style_user_role` VALUES ('11', '3');
