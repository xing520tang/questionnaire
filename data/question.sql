/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.53 : Database - question
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`question` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `question`;

/*Table structure for table `answers` */

DROP TABLE IF EXISTS `answers`;

CREATE TABLE `answers` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '答案id',
  `record_id` int(11) DEFAULT NULL COMMENT '该答案对应的记录id',
  `paper_id` int(11) DEFAULT NULL COMMENT '该答案对应的问卷id',
  `question_id` int(11) DEFAULT NULL COMMENT '该答案对应的问题id',
  `answer_no` text COMMENT '单选或多选答案编号',
  `answer_text` text COMMENT '简答题答案',
  `scores` int(11) DEFAULT '0' COMMENT '分数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

/*Table structure for table `papers` */

DROP TABLE IF EXISTS `papers`;

CREATE TABLE `papers` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问卷id',
  `author_id` int(11) DEFAULT NULL COMMENT '作者的用户id',
  `type` int(11) DEFAULT '0' COMMENT '问卷类型，0表示开放问卷，1带答案问卷',
  `title` varchar(256) DEFAULT NULL COMMENT '问卷标题',
  `sub_title` varchar(256) DEFAULT NULL COMMENT '副标题',
  `description` text COMMENT '问卷说明',
  `add_date` timestamp NULL DEFAULT NULL COMMENT '问卷创建日期',
  `start_date` timestamp NULL DEFAULT NULL COMMENT '开始日期',
  `end_date` timestamp NULL DEFAULT NULL COMMENT '回收日期',
  `time_limit` bigint(20) DEFAULT '0' COMMENT '答题时间限制',
  `remark` text COMMENT '备注',
  `img_name` varchar(256) DEFAULT 'akari.jpg' COMMENT '图片名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `paper_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `questions` */

DROP TABLE IF EXISTS `questions`;

CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题id',
  `paper_id` int(11) NOT NULL COMMENT '问题所属问卷id',
  `no` int(11) DEFAULT '0' COMMENT '题号',
  `type` int(11) DEFAULT '0' COMMENT '问题类型0单选1多选2简答',
  `description` text COMMENT '题目描述',
  `options_num` int(11) DEFAULT '0' COMMENT '选项个数',
  `options_info` text COMMENT '选项信息',
  `remark` text COMMENT '问题备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `question_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Table structure for table `records` */

DROP TABLE IF EXISTS `records`;

CREATE TABLE `records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `paper_id` int(11) DEFAULT NULL COMMENT '该记录对应的问卷id',
  `user_id` int(11) DEFAULT NULL COMMENT '答题者id',
  `answer_date` timestamp NULL DEFAULT NULL COMMENT '答题日期',
  `use_time` int(11) DEFAULT '0' COMMENT '所用时间',
  `scores` int(11) DEFAULT '0' COMMENT '分数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名，唯一',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '昵称',
  `user_type` tinyint(4) DEFAULT '1' COMMENT '用户类型1:普通用户',
  `password` varchar(256) DEFAULT NULL COMMENT '密码(哈希值)',
  `img_name` varchar(256) DEFAULT 'akari.jpg' COMMENT '头像名称',
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次登录时间',
  `register_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

insert into users(id, user_name, nick_name) values(0, 'niming', '匿名用户');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
