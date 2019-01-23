-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.11-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for vehicle_reservation
DROP DATABASE IF EXISTS `vehicle_reservation`;
CREATE DATABASE IF NOT EXISTS `vehicle_reservation` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `vehicle_reservation`;

-- Dumping structure for table vehicle_reservation.company
DROP TABLE IF EXISTS `company`;
CREATE TABLE IF NOT EXISTS `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.company: ~5 rows (approximately)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `name`, `deleted`) VALUES
	(2, 'test3', 0),
	(3, 'Kompanija 2', 1),
	(4, 'drugaa', 0),
	(5, 'proba', 0),
	(6, 'blabla', 0);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.cost
DROP TABLE IF EXISTS `cost`;
CREATE TABLE IF NOT EXISTS `cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` decimal(8,2) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `description` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `cost_type_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `vehicle_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cost_cost_type1_idx` (`cost_type_id`),
  KEY `fk_cost_company1_idx` (`company_id`),
  KEY `fk_cost_vehicle1_idx` (`vehicle_id`),
  CONSTRAINT `fk_cost_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cost_cost_type1` FOREIGN KEY (`cost_type_id`) REFERENCES `cost_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cost_vehicle1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.cost: ~0 rows (approximately)
/*!40000 ALTER TABLE `cost` DISABLE KEYS */;
/*!40000 ALTER TABLE `cost` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.cost_type
DROP TABLE IF EXISTS `cost_type`;
CREATE TABLE IF NOT EXISTS `cost_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.cost_type: ~0 rows (approximately)
/*!40000 ALTER TABLE `cost_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `cost_type` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.fuel
DROP TABLE IF EXISTS `fuel`;
CREATE TABLE IF NOT EXISTS `fuel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.fuel: ~0 rows (approximately)
/*!40000 ALTER TABLE `fuel` DISABLE KEYS */;
/*!40000 ALTER TABLE `fuel` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.location
DROP TABLE IF EXISTS `location`;
CREATE TABLE IF NOT EXISTS `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_company_idx` (`company_id`),
  CONSTRAINT `fk_location_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.location: ~0 rows (approximately)
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
/*!40000 ALTER TABLE `location` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.logger
DROP TABLE IF EXISTS `logger`;
CREATE TABLE IF NOT EXISTS `logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(128) COLLATE utf8mb4_bin NOT NULL,
  `action_details` text COLLATE utf8mb4_bin NOT NULL,
  `table_name` varchar(128) COLLATE utf8mb4_bin NOT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `user_id` int(11) NOT NULL,
  `atomic` tinyint(4) NOT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_logger_user1_idx` (`user_id`),
  KEY `fk_logger_company1_idx` (`company_id`),
  CONSTRAINT `fk_logger_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_logger_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- Dumping data for table vehicle_reservation.logger: ~25 rows (approximately)
/*!40000 ALTER TABLE `logger` DISABLE KEYS */;
INSERT INTO `logger` (`id`, `action_type`, `action_details`, `table_name`, `created`, `user_id`, `atomic`, `company_id`) VALUES
	(91, 'create', 'Kreiran je novi entitet: Company{id=2, name=\'test\', deleted=0}.', 'Company', '2019-01-16 16:52:22', 1, 1, NULL),
	(92, 'create', 'Kreiran je novi entitet: Company{id=3, name=\'Kompanija 2\', deleted=0}.', 'Company', '2019-01-16 16:54:40', 1, 1, NULL),
	(93, 'delete', 'Obrisan je entitet: Company{id=3, name=\'Kompanija 2\', deleted=1}.', 'Company', '2019-01-16 17:45:28', 1, 1, NULL),
	(94, 'create', 'Kreiran je novi entitet: Company{id=4, name=\'nova\', deleted=0}.', 'Company', '2019-01-16 17:45:42', 1, 1, NULL),
	(95, 'create', 'Kreiran je novi entitet: Company{id=5, name=\'proba\', deleted=0}.', 'Company', '2019-01-16 17:54:58', 1, 1, NULL),
	(96, 'update', 'A&#x017E;uriran je entitet: Company{id=4, name=\'nova\', deleted=0} na novu vrijednost: Company{id=4, name=\'nova1\', deleted=0}.', 'Company', '2019-01-16 18:05:11', 1, 1, NULL),
	(97, 'update', 'A&#x017E;uriran je entitet: Company{id=2, name=\'test\', deleted=0} na novu vrijednost: Company{id=2, name=\'test3\', deleted=0}.', 'Company', '2019-01-16 18:17:43', 1, 1, NULL),
	(98, 'update', 'A&#x017E;uriran je entitet: Company{id=4, name=\'nova1\', deleted=0} na novu vrijednost: Company{id=4, name=\'\', deleted=0}.', 'Company', '2019-01-17 11:00:32', 1, 1, NULL),
	(99, 'update', 'A&#x017E;uriran je entitet: Company{id=4, name=\'\', deleted=0} na novu vrijednost: Company{id=4, name=\'druga\', deleted=0}.', 'Company', '2019-01-17 11:00:39', 1, 1, NULL),
	(100, 'update', 'A&#x017E;uriran je entitet: Company{id=4, name=\'druga\', deleted=0} na novu vrijednost: Company{id=4, name=\'drugaa\', deleted=0}.', 'Company', '2019-01-17 11:01:13', 1, 1, NULL),
	(102, 'create', 'Kreiran je novi entitet: User{id=3, username=\'null\', password=\'null\', firstName=\'null\', lastName=\'null\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-17 12:32:20.0, active=1, deleted=0, token=\'i8MBq60U7uqoc4FuFdODIRW48EtXaoZgjnQAfKBqAB3k2WstqTlsZvKseWXy2w1a\', companyId=2, roleId=2, notificationTypeId=2}.', 'User', '2019-01-17 12:32:20', 1, 1, NULL),
	(103, 'create', 'Kreiran je novi entitet: User{id=4, username=\'null\', password=\'null\', firstName=\'null\', lastName=\'null\', email=\'milanagluvic995@gmail.com\', registrationDate=2019-01-17 14:27:04.0, active=1, deleted=0, token=\'62PlkxYqpHHGp44LVuTBEpk8YPsNsG1U1sqbabbi027qQmvgIoGDz1YnIKrpHZw7\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-17 14:27:04', 1, 1, NULL),
	(104, 'create', 'Kreiran je novi entitet: User{id=5, username=\'null\', password=\'null\', firstName=\'null\', lastName=\'null\', email=\'milanagluvic995@gmail.com\', registrationDate=2019-01-17 14:33:55.0, active=1, deleted=0, token=\'T01S6ZUG3pCcdzHl6pu6Q82SqggYoQFmUTFWYN9BzIRggVMKjDbm18PYA6nXFCxM\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-17 14:33:55', 1, 1, NULL),
	(105, 'update', 'A&#x017E;uriran je entitet: User{id=1, username=\'superadmin\', password=\'964a5502faec7a27f63ab5f7bddbe1bd8a685616a90ffcba633b5ad404569bd8fed4693cc00474a4881f636f3831a3e5a36bda049c568a89cfe54b1285b0c13e\', firstName=\'Ime1\', lastName=\'Prezime1\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null} na novu vrijednost: User{id=1, username=\'superadmin\', password=\'null\', firstName=\'Ime1d\', lastName=\'d\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 13:56:21', 1, 1, NULL),
	(106, 'update', 'A&#x017E;uriran je entitet: User{id=1, username=\'superadmin\', password=\'964a5502faec7a27f63ab5f7bddbe1bd8a685616a90ffcba633b5ad404569bd8fed4693cc00474a4881f636f3831a3e5a36bda049c568a89cfe54b1285b0c13e\', firstName=\'Ime1d\', lastName=\'d\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null} na novu vrijednost: User{id=1, username=\'superadmin\', password=\'null\', firstName=\'Ime1d\', lastName=\'Prezime\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 13:56:48', 1, 1, NULL),
	(107, 'update', 'A&#x017E;uriran je entitet: User{id=1, username=\'superadmin\', password=\'964a5502faec7a27f63ab5f7bddbe1bd8a685616a90ffcba633b5ad404569bd8fed4693cc00474a4881f636f3831a3e5a36bda049c568a89cfe54b1285b0c13e\', firstName=\'Ime1d\', lastName=\'Prezime\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null} na novu vrijednost: User{id=1, username=\'superadmin\', password=\'null\', firstName=\'Ime\', lastName=\'Prezime\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 13:58:58', 1, 1, NULL),
	(108, 'update', 'A&#x017E;uriran je entitet: User{id=1, username=\'superadmin\', password=\'964a5502faec7a27f63ab5f7bddbe1bd8a685616a90ffcba633b5ad404569bd8fed4693cc00474a4881f636f3831a3e5a36bda049c568a89cfe54b1285b0c13e\', firstName=\'Ime\', lastName=\'Prezime\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null} na novu vrijednost: User{id=1, username=\'superadmin\', password=\'null\', firstName=\'Ime1\', lastName=\'Prezime1\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 14:13:12', 1, 1, NULL),
	(109, 'update', 'A&#x017E;uriran je entitet: User{id=1, username=\'superadmin\', password=\'964a5502faec7a27f63ab5f7bddbe1bd8a685616a90ffcba633b5ad404569bd8fed4693cc00474a4881f636f3831a3e5a36bda049c568a89cfe54b1285b0c13e\', firstName=\'Ime1\', lastName=\'Prezime1\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null} na novu vrijednost: User{id=1, username=\'superadmin\', password=\'null\', firstName=\'Ime\', lastName=\'Prezime\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 14:16:25', 1, 1, NULL),
	(110, 'update', 'A&#x017E;uriran je entitet: User{id=1, username=\'superadmin\', password=\'964a5502faec7a27f63ab5f7bddbe1bd8a685616a90ffcba633b5ad404569bd8fed4693cc00474a4881f636f3831a3e5a36bda049c568a89cfe54b1285b0c13e\', firstName=\'Ime\', lastName=\'Prezime\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null} na novu vrijednost: User{id=1, username=\'superadmin\', password=\'null\', firstName=\'Ime\', lastName=\'Prezime\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 14:16:37', 1, 1, NULL),
	(111, 'update', 'A&#x017E;uriran je entitet: User{id=1, username=\'superadmin\', password=\'964a5502faec7a27f63ab5f7bddbe1bd8a685616a90ffcba633b5ad404569bd8fed4693cc00474a4881f636f3831a3e5a36bda049c568a89cfe54b1285b0c13e\', firstName=\'Ime\', lastName=\'Prezime\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null} na novu vrijednost: User{id=1, username=\'superadmin\', password=\'null\', firstName=\'Ime1\', lastName=\'Prezime2\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 14:16:49', 1, 1, NULL),
	(112, 'update', 'A&#x017E;uriran je entitet: User{id=1, username=\'superadmin\', password=\'964a5502faec7a27f63ab5f7bddbe1bd8a685616a90ffcba633b5ad404569bd8fed4693cc00474a4881f636f3831a3e5a36bda049c568a89cfe54b1285b0c13e\', firstName=\'Ime1\', lastName=\'Prezime2\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null} na novu vrijednost: User{id=1, username=\'superadmin\', password=\'null\', firstName=\'Ime\', lastName=\'Prezime\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-11 23:41:25.0, active=1, deleted=0, token=\'null\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 14:19:29', 1, 1, NULL),
	(113, 'create', 'Kreiran je novi entitet: Company{id=6, name=\'blabla\', deleted=0}.', 'Company', '2019-01-22 14:23:10', 1, 1, NULL),
	(114, 'create', 'Kreiran je novi entitet: User{id=6, username=\'null\', password=\'null\', firstName=\'null\', lastName=\'null\', email=\'test@email.com\', registrationDate=2019-01-22 14:28:51.0, active=1, deleted=0, token=\'LNdhrDO6fZm3PNwZoX4dqiUhT5hHQm1Nb7hnA20uaIPtc2i0lN0pb44EYsa4ZMAq\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 14:28:51', 1, 1, NULL),
	(115, 'create', 'Kreiran je novi entitet: User{id=7, username=\'null\', password=\'null\', firstName=\'null\', lastName=\'null\', email=\'slogi_gajic@hotmail.com\', registrationDate=2019-01-22 14:29:08.0, active=1, deleted=0, token=\'TVZvk01rYOLyPc8Y8mcEBIT9ScS6zMKP84RJUDa7W2HQBeJZOQTgtSPnkFsf58Ib\', companyId=null, roleId=1, notificationTypeId=null}.', 'User', '2019-01-22 14:29:08', 1, 1, NULL),
	(116, 'create', 'Kreiran je novi entitet: User{id=8, username=\'null\', password=\'null\', firstName=\'null\', lastName=\'null\', email=\'dejan.gajic1110@gmail.com\', registrationDate=2019-01-22 14:32:02.0, active=1, deleted=0, token=\'KCYQyDgxjRXwgbvOZUpUgWAvHXaGrqZzyfX9IOVImTktaxij9P8HSzJe1Z1N6et6\', companyId=2, roleId=2, notificationTypeId=2}.', 'User', '2019-01-22 14:32:02', 1, 1, NULL);
/*!40000 ALTER TABLE `logger` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.manufacturer
DROP TABLE IF EXISTS `manufacturer`;
CREATE TABLE IF NOT EXISTS `manufacturer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.manufacturer: ~0 rows (approximately)
/*!40000 ALTER TABLE `manufacturer` DISABLE KEYS */;
/*!40000 ALTER TABLE `manufacturer` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.notification_type
DROP TABLE IF EXISTS `notification_type`;
CREATE TABLE IF NOT EXISTS `notification_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.notification_type: ~2 rows (approximately)
/*!40000 ALTER TABLE `notification_type` DISABLE KEYS */;
INSERT INTO `notification_type` (`id`, `name`) VALUES
	(1, 'Za lokaciju'),
	(2, 'Za kompaniju'),
	(3, 'Isključeno');
/*!40000 ALTER TABLE `notification_type` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.reservation
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `start_mileage` int(11) NOT NULL,
  `end_mileage` int(11) DEFAULT NULL,
  `direction` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `user_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reservation_user1_idx` (`user_id`),
  KEY `fk_reservation_company1_idx` (`company_id`),
  KEY `fk_reservation_vehicle1_idx` (`vehicle_id`),
  CONSTRAINT `fk_reservation_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_vehicle1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.reservation: ~0 rows (approximately)
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.role: ~2 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`) VALUES
	(1, 'Administrator sistema'),
	(2, 'Administrator komapnije'),
	(3, 'Regularni korisnik');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) COLLATE utf8_czech_ci DEFAULT NULL,
  `password` varchar(128) COLLATE utf8_czech_ci DEFAULT NULL,
  `first_name` varchar(128) COLLATE utf8_czech_ci DEFAULT NULL,
  `last_name` varchar(128) COLLATE utf8_czech_ci DEFAULT NULL,
  `email` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `registration_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `active` tinyint(1) NOT NULL DEFAULT 0,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `token` char(64) COLLATE utf8_czech_ci DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `notification_type_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_company1_idx` (`company_id`),
  KEY `fk_user_role1_idx` (`role_id`),
  KEY `fk_user_notification_type1_idx` (`notification_type_id`),
  KEY `fk_user_location1_idx` (`location_id`),
  CONSTRAINT `fk_user_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_notification_type1` FOREIGN KEY (`notification_type_id`) REFERENCES `notification_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.user: ~5 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `username`, `password`, `first_name`, `last_name`, `email`, `registration_date`, `active`, `deleted`, `token`, `company_id`, `role_id`, `notification_type_id`, `location_id`) VALUES
	(1, 'superadmin', '26201b15da89dff413c2f06b6662291765cb70e33bb1062f4d382bccf8036b8ab540e47515cb9b9b8ab243083df397a88d13a3f32a5dec6418b809d3fb4c5159', 'Ime', 'Prezime', 'dejan.gajic1110@gmail.com', '2019-01-11 23:41:25', 1, 0, NULL, NULL, 1, NULL, NULL),
	(5, NULL, NULL, NULL, NULL, 'milanagluvic995@gmail.com', '2019-01-17 14:33:55', 1, 0, 'T01S6ZUG3pCcdzHl6pu6Q82SqggYoQFmUTFWYN9BzIRggVMKjDbm18PYA6nXFCxM', NULL, 1, NULL, NULL),
	(6, NULL, NULL, NULL, NULL, 'test@email.com', '2019-01-22 14:28:51', 1, 0, 'LNdhrDO6fZm3PNwZoX4dqiUhT5hHQm1Nb7hnA20uaIPtc2i0lN0pb44EYsa4ZMAq', NULL, 1, NULL, NULL),
	(7, NULL, NULL, NULL, NULL, 'slogi_gajic@hotmail.com', '2019-01-22 14:29:08', 1, 0, 'TVZvk01rYOLyPc8Y8mcEBIT9ScS6zMKP84RJUDa7W2HQBeJZOQTgtSPnkFsf58Ib', NULL, 1, NULL, NULL),
	(8, NULL, NULL, NULL, NULL, 'dejan.gajic1110@gmail.com', '2019-01-22 14:32:02', 1, 0, 'KCYQyDgxjRXwgbvOZUpUgWAvHXaGrqZzyfX9IOVImTktaxij9P8HSzJe1Z1N6et6', 2, 2, 2, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table vehicle_reservation.vehicle
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE IF NOT EXISTS `vehicle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `registration` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `location_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `manufacturer_id` int(11) NOT NULL,
  `fuel_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vehicle_location1_idx` (`location_id`),
  KEY `fk_vehicle_company1_idx` (`company_id`),
  KEY `fk_vehicle_manufacturer1_idx` (`manufacturer_id`),
  KEY `fk_vehicle_fuel1_idx` (`fuel_id`),
  CONSTRAINT `fk_vehicle_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_fuel1` FOREIGN KEY (`fuel_id`) REFERENCES `fuel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_manufacturer1` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- Dumping data for table vehicle_reservation.vehicle: ~0 rows (approximately)
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
