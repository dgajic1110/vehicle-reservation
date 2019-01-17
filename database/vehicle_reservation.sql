-- MySQL Script generated by MySQL Workbench
-- Fri Jan  4 15:36:13 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema vehicle_reservation
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `vehicle_reservation` ;

-- -----------------------------------------------------
-- Schema vehicle_reservation
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `vehicle_reservation` DEFAULT CHARACTER SET utf8 ;
USE `vehicle_reservation` ;

-- -----------------------------------------------------
-- Table `vehicle_reservation`.`company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`company` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`company` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`cost_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`cost_type` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`cost_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`fuel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`fuel` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`fuel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`location` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`location` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  `lat` DOUBLE NOT NULL,
  `lng` DOUBLE NOT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT '0',
  `company_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_location_company_idx` (`company_id` ASC),
  CONSTRAINT `fk_location_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `vehicle_reservation`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`manufacturer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`manufacturer` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`manufacturer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`vehicle`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`vehicle` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`vehicle` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  `registration` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  `description` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NULL DEFAULT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `location_id` INT(11) NOT NULL,
  `company_id` INT(11) NOT NULL,
  `manufacturer_id` INT(11) NOT NULL,
  `fuel_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_vehicle_location1_idx` (`location_id` ASC),
  INDEX `fk_vehicle_company1_idx` (`company_id` ASC),
  INDEX `fk_vehicle_manufacturer1_idx` (`manufacturer_id` ASC),
  INDEX `fk_vehicle_fuel1_idx` (`fuel_id` ASC),
  CONSTRAINT `fk_vehicle_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `vehicle_reservation`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_fuel1`
    FOREIGN KEY (`fuel_id`)
    REFERENCES `vehicle_reservation`.`fuel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_location1`
    FOREIGN KEY (`location_id`)
    REFERENCES `vehicle_reservation`.`location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_vehicle_manufacturer1`
    FOREIGN KEY (`manufacturer_id`)
    REFERENCES `vehicle_reservation`.`manufacturer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`cost`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`cost` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`cost` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `value` DECIMAL(8,2) NOT NULL,
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NULL DEFAULT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT '0',
  `cost_type_id` INT(11) NOT NULL,
  `company_id` INT(11) NOT NULL,
  `vehicle_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cost_cost_type1_idx` (`cost_type_id` ASC),
  INDEX `fk_cost_company1_idx` (`company_id` ASC),
  INDEX `fk_cost_vehicle1_idx` (`vehicle_id` ASC),
  CONSTRAINT `fk_cost_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `vehicle_reservation`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cost_cost_type1`
    FOREIGN KEY (`cost_type_id`)
    REFERENCES `vehicle_reservation`.`cost_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cost_vehicle1`
    FOREIGN KEY (`vehicle_id`)
    REFERENCES `vehicle_reservation`.`vehicle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`notification_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`notification_type` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`notification_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`role` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`user` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NULL DEFAULT NULL,
  `password` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NULL DEFAULT NULL,
  `first_name` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NULL DEFAULT NULL,
  `last_name` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NULL DEFAULT NULL,
  `email` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  `registration_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` TINYINT(1) NOT NULL DEFAULT '0',
  `deleted` TINYINT(1) NOT NULL DEFAULT '0',
  `token` CHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NULL DEFAULT NULL,
  `company_id` INT(11) NULL DEFAULT NULL,
  `role_id` INT(11) NOT NULL,
  `notification_type_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_company1_idx` (`company_id` ASC),
  INDEX `fk_user_role1_idx` (`role_id` ASC),
  INDEX `fk_user_notification_type1_idx` (`notification_type_id` ASC),
  CONSTRAINT `fk_user_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `vehicle_reservation`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_notification_type1`
    FOREIGN KEY (`notification_type_id`)
    REFERENCES `vehicle_reservation`.`notification_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `vehicle_reservation`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`logger`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`logger` ;

CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`logger` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `action_type` VARCHAR(128) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL,
  `action_details` TEXT CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL,
  `table_name` VARCHAR(128) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` INT(11) NOT NULL,
  `atomic` TINYINT(4) NOT NULL,
  `company_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_logger_user1_idx` (`user_id` ASC),
  INDEX `fk_logger_company1_idx` (`company_id` ASC),
  CONSTRAINT `fk_logger_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `vehicle_reservation`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_logger_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `vehicle_reservation`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 91
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `vehicle_reservation`.`reservation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vehicle_reservation`.`reservation` ;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
CREATE TABLE IF NOT EXISTS `vehicle_reservation`.`reservation` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `start_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_date` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `start_mileage` INT(11) NOT NULL,
  `end_mileage` INT(11) NULL DEFAULT NULL,
  `direction` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_czech_ci' NOT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT '0',
  `user_id` INT(11) NOT NULL,
  `company_id` INT(11) NOT NULL,
  `vehicle_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_reservation_user1_idx` (`user_id` ASC),
  INDEX `fk_reservation_company1_idx` (`company_id` ASC),
  INDEX `fk_reservation_vehicle1_idx` (`vehicle_id` ASC),
  CONSTRAINT `fk_reservation_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `vehicle_reservation`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `vehicle_reservation`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_vehicle1`
    FOREIGN KEY (`vehicle_id`)
    REFERENCES `vehicle_reservation`.`vehicle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_czech_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
