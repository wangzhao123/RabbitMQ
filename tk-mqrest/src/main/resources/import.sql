/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.21 : Database - message
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`message` /*!40100 DEFAULT CHARACTER SET utf8 */;

/*Table structure for table `connectinfor` */

DROP TABLE IF EXISTS `connectinfor`;

CREATE TABLE `connectinfor` (
  `id` varchar(50) NOT NULL,
  `state` int(2) DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL,
  `groupname` varchar(100) DEFAULT NULL,
  `containerid` varchar(100) DEFAULT NULL COMMENT '容器ID',
  `applyperson` varchar(100) DEFAULT NULL COMMENT '申请人',
  `contactinfo` varchar(100) DEFAULT NULL COMMENT '联系方式',
  `secret` varchar(100) DEFAULT NULL,
  `configid` varchar(100) DEFAULT NULL COMMENT '回调配置ID',
  `sysid` varchar(100) DEFAULT NULL COMMENT '系统ID',
  `queueid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tk_msg_error` */

DROP TABLE IF EXISTS `tk_msg_error`;

CREATE TABLE `tk_msg_error` (
  `id` varchar(50) NOT NULL,
  `errorcode` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `syscode` varchar(100) DEFAULT NULL,
  `exchange` varchar(100) DEFAULT NULL,
  `messageid` varchar(100) DEFAULT NULL,
  `body` varchar(5000) DEFAULT NULL,
  `destinationsystemcode` varchar(100) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tk_sys_config` */

DROP TABLE IF EXISTS `tk_sys_config`;

CREATE TABLE `tk_sys_config` (
  `id` varchar(50) NOT NULL,
  `callbackurl` varchar(100) DEFAULT NULL,
  `sysid` varchar(100) DEFAULT NULL,
  `callbackparam` varchar(100) DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `configname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tk_sys_infomation` */

DROP TABLE IF EXISTS `tk_sys_infomation`;

CREATE TABLE `tk_sys_infomation` (
  `id` varchar(100) NOT NULL,
  `syscode` varchar(100) DEFAULT NULL,
  `sysname` varchar(100) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createuser` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tk_sys_queue` */

DROP TABLE IF EXISTS `tk_sys_queue`;

CREATE TABLE `tk_sys_queue` (
  `id` varchar(50) NOT NULL,
  `queuename` varchar(100) DEFAULT NULL,
  `topicid` varchar(50) DEFAULT NULL,
  `createuser` varchar(100) DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `info` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tk_sys_queue_system` */

DROP TABLE IF EXISTS `tk_sys_queue_system`;

CREATE TABLE `tk_sys_queue_system` (
  `id` varchar(50) NOT NULL,
  `queueid` varchar(50) DEFAULT NULL,
  `sysid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tk_sys_topic` */

DROP TABLE IF EXISTS `tk_sys_topic`;

CREATE TABLE `tk_sys_topic` (
  `id` varchar(100) NOT NULL,
  `topicname` varchar(100) DEFAULT NULL,
  `topiccode` varchar(100) DEFAULT NULL,
  `createuser` varchar(100) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `info` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
