/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : template

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-04-10 18:15:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` varchar(32) NOT NULL COMMENT '部门主键',
  `name` varchar(255) NOT NULL COMMENT '部门名称',
  `short_name` varchar(255) DEFAULT NULL COMMENT '部门简称',
  `grade` int(1) DEFAULT NULL COMMENT '部门等级',
  `pid` varchar(32) NOT NULL COMMENT '父级部门',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(32) NOT NULL COMMENT ' 创建用户',
  `status` int(1) NOT NULL COMMENT '部门状态，0：禁用；1：启动',
  `sort_order` tinyint(255) DEFAULT NULL COMMENT '排序',
  `primary_person` varchar(255) DEFAULT NULL COMMENT '主要负责人',
  `deputy_person` varchar(255) DEFAULT NULL COMMENT '副负责人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES ('1', '杭漫', '杭漫', '1', '0', '2019-04-09 11:28:03', 'bb9c79a5fa051d533b9d38f388281f9b', '1', null, '', '', '1');
INSERT INTO `sys_department` VALUES ('7c67ecc6bc48f6f3fee684b82c583b6e', '杭漫技术部', null, null, '1', '2019-04-08 14:21:36', 'bb9c79a5fa051d533b9d38f388281f9b', '1', null, null, null, '1111');
INSERT INTO `sys_department` VALUES ('834be7b5e103b369818979d590ecb471', '漫画编辑组', '漫画编辑', '1', '8042b4ba82311f336f787336f708f875', '2019-04-08 19:16:51', 'bb9c79a5fa051d533b9d38f388281f9b', '1', null, 'bda24425e9772f4aa85e4e0ae38d77d7', 'bb9c79a5fa051d533b9d38f388281f9b', '1');
INSERT INTO `sys_department` VALUES ('83a583dc56101ff078c67ad9d69446fe', 'UI设计', 'UI', '1', '7c67ecc6bc48f6f3fee684b82c583b6e', '2019-04-09 11:21:00', 'bb9c79a5fa051d533b9d38f388281f9b', '0', null, 'bda24425e9772f4aa85e4e0ae38d77d7', 'bda24425e9772f4aa85e4e0ae38d77d7', '1');
INSERT INTO `sys_department` VALUES ('a8ece4290d3fc366bd2cd26d115adeda', '前端开发', '前端', '2', '7c67ecc6bc48f6f3fee684b82c583b6e', '2019-04-09 09:34:13', 'bb9c79a5fa051d533b9d38f388281f9b', '1', null, 'bb9c79a5fa051d533b9d38f388281f9b', 'bda24425e9772f4aa85e4e0ae38d77d7', '0');
INSERT INTO `sys_department` VALUES ('b7b91ef14fc3143a51b8469cf9fd235e', '后台java开发', null, null, '7c67ecc6bc48f6f3fee684b82c583b6e', '2019-04-08 14:29:37', 'bb9c79a5fa051d533b9d38f388281f9b', '1', null, null, null, '2');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '菜单名称',
  `path` varchar(255) DEFAULT NULL COMMENT '菜单路径',
  `pid` varchar(32) NOT NULL COMMENT '父级菜单，本身是根节点的话填0',
  `bool_isleaf` bit(1) NOT NULL COMMENT '是否叶子节点',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1f494825a954aec217107e516f92aa32', '用户管理', '/user/userListUI', 'f7d1d541865dc8b307b8ddcd8368f78d', '', '用户管理', '', '2019-03-07 10:23:33');
INSERT INTO `sys_menu` VALUES ('2ce32eb886ba79c2f92c183ab94fea32', '百度', 'http://www.baidu.com', 'fe836b2ea452fe765e2d3caa77f92329', '', '百度', 'am-icon-home', '2019-02-28 17:24:55');
INSERT INTO `sys_menu` VALUES ('4493b30c83dc66727c44b0c5043ff314', '角色管理', '/role/roleListUI', 'f7d1d541865dc8b307b8ddcd8368f78d', '', '', '', '2019-03-08 15:14:49');
INSERT INTO `sys_menu` VALUES ('4b1742f38e7c210ec28f51a331a28432', '部门管理', '/department/departmentListUI', 'f7d1d541865dc8b307b8ddcd8368f78d', '', '', '', '2019-04-08 12:04:16');
INSERT INTO `sys_menu` VALUES ('6778fa04df7d174396ca17bacd9ca712', '功能菜单管理', '/menu/menuListUI', 'f7d1d541865dc8b307b8ddcd8368f78d', '', '功能菜单管理', 'am-icon-cog', '2019-02-27 16:06:15');
INSERT INTO `sys_menu` VALUES ('7009f1e3559abfe9756b5322eefb3772', '角色菜单管理', '/roleMenu/roleMenuListUI', 'f7d1d541865dc8b307b8ddcd8368f78d', '', '', '', '2019-03-18 19:53:32');
INSERT INTO `sys_menu` VALUES ('f7d1d541865dc8b307b8ddcd8368f78d', '系统管理', '', '0', '\0', '系统管理', 'fa-gear', '2019-03-06 16:57:56');
INSERT INTO `sys_menu` VALUES ('fe836b2ea452fe765e2d3caa77f92329', '第三方网站', null, '0', '\0', '第三方网站', 'fa-share-alt', '2019-02-28 17:25:00');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `status` int(1) NOT NULL COMMENT '角色状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('11a852abf1e9e4a7fa92dc9bf52f008c', '管理员', '1', '一般管理权限', '2019-03-12 16:21:29');
INSERT INTO `sys_role` VALUES ('42b1985906a65369dde5102585e913ba', '技术', '1', '程序员', '2019-03-12 16:21:53');
INSERT INTO `sys_role` VALUES ('5883e03a76c8fcdcfe0671f07ed1a362', '超级管理员', '1', '超级管理权限', '2019-03-01 17:42:27');
INSERT INTO `sys_role` VALUES ('70d59927bb7ae94c2639cf2a6e5d2543', 'ces', '1', 'ccc', '2019-04-10 16:09:02');
INSERT INTO `sys_role` VALUES ('cf35e8d577d3266a0438de85afcfc0e3', '普通用户', '1', '超级普通权限', '2019-03-01 14:08:44');
INSERT INTO `sys_role` VALUES ('e45f8ee5f6f083b725933146d9325c32', 'ss', '1', 'ss试试', '2019-03-18 20:21:42');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `ID` varchar(32) NOT NULL COMMENT 'PK 主键',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单id',
  `create_time` datetime DEFAULT NULL COMMENT '创建/关联时间',
  `create_user` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单权限表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('36507070ffeedc3efb511f6c81d16ef5', '11a852abf1e9e4a7fa92dc9bf52f008c', '08690f23405f7b1f957a4d6b78355b6a', '2019-03-28 17:17:22', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_role_menu` VALUES ('41d8098e2ba5b2702da497aaa1221223', '5883e03a76c8fcdcfe0671f07ed1a362', 'f7d1d541865dc8b307b8ddcd8368f78d', '2019-04-10 16:20:47', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_role_menu` VALUES ('480ffe54c986d9b101db63f13212e199', 'b8bd45ecd3cee358a78079a8a70b5e2a', '08690f23405f7b1f957a4d6b78355b6a', '2019-04-08 10:48:54', 'bb9c79a5fa051d533b9d38f388281f9b');
INSERT INTO `sys_role_menu` VALUES ('5144f2038e5e7cdc2d2b96b1eb833e22', '5883e03a76c8fcdcfe0671f07ed1a362', '1f494825a954aec217107e516f92aa32', '2019-04-10 16:20:47', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_role_menu` VALUES ('5db7f626733baa53e3683c48c4b57216', '5883e03a76c8fcdcfe0671f07ed1a362', '6778fa04df7d174396ca17bacd9ca712', '2019-04-10 16:20:47', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_role_menu` VALUES ('79ac0dbb2c019ece41053ffe71398fab', '5883e03a76c8fcdcfe0671f07ed1a362', '4b1742f38e7c210ec28f51a331a28432', '2019-04-10 16:20:47', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_role_menu` VALUES ('7f0ec373b1063700e2c61415571bba24', '42b1985906a65369dde5102585e913ba', '2ce32eb886ba79c2f92c183ab94fea32', '2019-04-10 16:25:29', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_role_menu` VALUES ('9119b6629cb1682275888935dc55df7c', '5883e03a76c8fcdcfe0671f07ed1a362', '7009f1e3559abfe9756b5322eefb3772', '2019-04-10 16:20:47', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_role_menu` VALUES ('dd906d4a021dc8263e57ce9477cd4ee6', '5883e03a76c8fcdcfe0671f07ed1a362', '4493b30c83dc66727c44b0c5043ff314', '2019-04-10 16:20:47', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_role_menu` VALUES ('ecd49b1c36a7d71c87aae6711e27fbcc', '42b1985906a65369dde5102585e913ba', 'fe836b2ea452fe765e2d3caa77f92329', '2019-04-10 16:25:29', 'ede2f8bf37ce31902837c4a0a26cf3fa');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '用户名',
  `login_name` varchar(255) NOT NULL COMMENT '登录名',
  `pass_word` varchar(255) DEFAULT NULL COMMENT '密码',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `login_count` varchar(255) DEFAULT NULL COMMENT '登录次数',
  `status` int(1) DEFAULT NULL COMMENT '状态，0：禁用；1：启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('9057dac62516991187374d1ef7ce0202', 'ceshi', 'user', '3646244727133308a0dc2ae6d5c8e07d1f91fcd521e7ba652ac0b4e49ef447afe71c6d6bec7ab29b6dc5e425a26dd85718bb65a2a366c493f1ca033a6f7bef45', '13587444444', '1111@qq.com', '2019-04-10 16:29:38', '2019-04-10 16:29:38', '0', '1');
INSERT INTO `sys_user` VALUES ('ede2f8bf37ce31902837c4a0a26cf3fa', 'admin', 'admin', '4133459596169be9d9290c30528a7cb9871943ae4252bef155fbd5d979f946f01323b2b65a3f2a8972a452210e2bca43db028e3e09d331dc7e3c21719bb95fcf', '13511001100', '123@qq.com', '2019-03-20 15:06:51', '2019-03-20 15:06:51', '0', '1');

-- ----------------------------
-- Table structure for sys_user_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `department_id` varchar(32) NOT NULL COMMENT '部门ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(32) NOT NULL COMMENT '创建用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户部门关联表';

-- ----------------------------
-- Records of sys_user_department
-- ----------------------------
INSERT INTO `sys_user_department` VALUES ('29805e2032f575e36f5ed323f1a53969', '2c212f905966f164df7ee9c5692f90ba', '1', '2019-04-10 16:25:20', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_department` VALUES ('3ce298b7286960f1156a35bb28cef738', '19082bda74424f0dbd94906f7cdee6c8', '1', '2019-04-09 17:18:58', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_department` VALUES ('d3f1c56d0aa26541bfe275a3d9e81db7', '19082bda74424f0dbd94906f7cdee6c8', '7c67ecc6bc48f6f3fee684b82c583b6e', '2019-04-09 17:18:58', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_department` VALUES ('e40b02585dbe57948bd5484b3ea1b58e', '9057dac62516991187374d1ef7ce0202', '1', '2019-04-10 16:29:38', 'ede2f8bf37ce31902837c4a0a26cf3fa');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `ID` varchar(32) NOT NULL COMMENT 'PK 主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建/关联时间',
  `create_user` varchar(32) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('0876f75f205fbb900fdd729e1f96977b', '2c212f905966f164df7ee9c5692f90ba', '42b1985906a65369dde5102585e913ba', '2019-04-10 16:25:20', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_role` VALUES ('400f6c31d457cb4c8baf0b99f079c291', '2e411a8b92538c98c113d486fc2382f8', 'cf35e8d577d3266a0438de85afcfc0e3', '2019-03-21 15:21:47', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_role` VALUES ('46f128a7415ea2a2fdc679f8b584b8e3', '4e0aedbe773eda9755bb6ce927e586fd', '11a852abf1e9e4a7fa92dc9bf52f008c', '2019-03-21 15:16:35', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_role` VALUES ('501d3c2dc41af33a6d0de090f94eebde', '3ae62e040aaa5ac3f62a7e96e7198233', '11a852abf1e9e4a7fa92dc9bf52f008c', '2019-03-15 20:22:21', 'bb9c79a5fa051d533b9d38f388281f9b');
INSERT INTO `sys_user_role` VALUES ('5ff6641f93bded8fc4e2aa4fca6cb2a3', '19082bda74424f0dbd94906f7cdee6c8', '5883e03a76c8fcdcfe0671f07ed1a362', '2019-04-09 17:18:58', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_role` VALUES ('68cf82319540e795facc0f60c83937e2', 'bb9c79a5fa051d533b9d38f388281f9b', '5883e03a76c8fcdcfe0671f07ed1a362', '2019-03-08 11:21:42', '');
INSERT INTO `sys_user_role` VALUES ('8215b5d813d25713b39cfeafeba718d2', '19082bda74424f0dbd94906f7cdee6c8', 'b8bd45ecd3cee358a78079a8a70b5e2a', '2019-04-09 17:18:58', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_role` VALUES ('9717f6eb3c395b7dd2321cbf511aafa6', '19082bda74424f0dbd94906f7cdee6c8', '42b1985906a65369dde5102585e913ba', '2019-04-09 17:18:58', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_role` VALUES ('9da54f1bcda54031e719c2423ba716be', 'a1f0ed80f120a79ec64b1f0f3eefd8dd', '11a852abf1e9e4a7fa92dc9bf52f008c', '2019-03-15 20:20:01', 'bb9c79a5fa051d533b9d38f388281f9b');
INSERT INTO `sys_user_role` VALUES ('a2174fdce7d5028510e5143f392bc73e', '9057dac62516991187374d1ef7ce0202', '42b1985906a65369dde5102585e913ba', '2019-04-10 16:29:38', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_role` VALUES ('a36a5b6b24121f6aa41301400594d163', '2e411a8b92538c98c113d486fc2382f8', '42b1985906a65369dde5102585e913ba', '2019-03-21 15:21:47', 'ede2f8bf37ce31902837c4a0a26cf3fa');
INSERT INTO `sys_user_role` VALUES ('aa60bb1175564722141d09aeeddd3902', 'ede2f8bf37ce31902837c4a0a26cf3fa', '5883e03a76c8fcdcfe0671f07ed1a362', '2019-03-20 15:06:52', 'bb9c79a5fa051d533b9d38f388281f9b');
INSERT INTO `sys_user_role` VALUES ('f611ebe4e21b41c13928e9417aa802e5', '4e0aedbe773eda9755bb6ce927e586fd', '5883e03a76c8fcdcfe0671f07ed1a362', '2019-03-21 15:16:35', 'ede2f8bf37ce31902837c4a0a26cf3fa');
