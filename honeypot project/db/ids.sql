-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.62 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for ids
CREATE DATABASE IF NOT EXISTS `ids` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ids`;

-- Dumping structure for table ids.userinform
CREATE TABLE IF NOT EXISTS `userinform` (
  `username` varchar(25) NOT NULL DEFAULT '',
  `password` varchar(25) DEFAULT NULL,
  `contact` varchar(25) DEFAULT NULL,
  `mail` varchar(35) DEFAULT NULL,
  `native` varchar(35) DEFAULT NULL,
  `dob` varchar(35) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table ids.userinform: ~6 rows (approximately)
DELETE FROM `userinform`;
/*!40000 ALTER TABLE `userinform` DISABLE KEYS */;
/*!40000 ALTER TABLE `userinform` ENABLE KEYS */;

-- Dumping structure for table ids.verificationcode
CREATE TABLE IF NOT EXISTS `verificationcode` (
  `username` varchar(35) NOT NULL DEFAULT '',
  `code` varchar(35) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table ids.verificationcode: ~5 rows (approximately)
DELETE FROM `verificationcode`;
/*!40000 ALTER TABLE `verificationcode` DISABLE KEYS */;
/*!40000 ALTER TABLE `verificationcode` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
