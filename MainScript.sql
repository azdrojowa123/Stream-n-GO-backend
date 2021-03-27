-- MySQL Script generated by MySQL Workbench
-- Sat Mar 27 12:34:51 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema XEVA
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `XEVA` ;

-- -----------------------------------------------------
-- Schema XEVA
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `XEVA` DEFAULT CHARACTER SET utf8 ;
USE `XEVA` ;

-- -----------------------------------------------------
-- Table `XEVA`.`Roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `XEVA`.`Roles` ;

CREATE TABLE IF NOT EXISTS `XEVA`.`Roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `XEVA`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `XEVA`.`Users` ;

CREATE TABLE IF NOT EXISTS `XEVA`.`Users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Role_id` INT NOT NULL,
  `Name` VARCHAR(225) NOT NULL,
  `Email` VARCHAR(225) NOT NULL,
  `Pwd` VARCHAR(225) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fkRole_id_idx` (`Role_id` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  CONSTRAINT `fkRole_id`
    FOREIGN KEY (`Role_id`)
    REFERENCES `XEVA`.`Roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `XEVA`.`Organizations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `XEVA`.`Organizations` ;

CREATE TABLE IF NOT EXISTS `XEVA`.`Organizations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(225) NOT NULL,
  `Country` VARCHAR(225) NOT NULL,
  `Province` VARCHAR(225) NOT NULL,
  `City` VARCHAR(225) NOT NULL,
  `Postal_code` VARCHAR(6) NOT NULL,
  `Street` VARCHAR(225) NOT NULL,
  `NIP` VARCHAR(12) NULL DEFAULT NULL,
  `Phone_number` INT(20) NOT NULL,
  `Email` VARCHAR(225) NOT NULL,
  `Web_page` VARCHAR(2000) NULL DEFAULT NULL,
  `Photo` VARCHAR(2000) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  UNIQUE INDEX `NIP_UNIQUE` (`NIP` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE,
  UNIQUE INDEX `Web_page_UNIQUE` (`Web_page`(1000) ASC) VISIBLE,
  UNIQUE INDEX `Phone_number_UNIQUE` (`Phone_number` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `XEVA`.`Events`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `XEVA`.`Events` ;

CREATE TABLE IF NOT EXISTS `XEVA`.`Events` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `User_id` INT NOT NULL,
  `Organization_id` INT NOT NULL,
  `Name` VARCHAR(225) NOT NULL,
  `Description` VARCHAR(3000) NOT NULL,
  `days_of_week` VARCHAR(225) NOT NULL,
  `Cyclical` TINYINT NOT NULL,
  `Mode` VARCHAR(225) NOT NULL,
  `Web_address` VARCHAR(2000) NOT NULL,
  `Tags` VARCHAR(225) NULL DEFAULT NULL,
  `Language` VARCHAR(225) NOT NULL,
  `Status` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fkOrganization_id_idx` (`Organization_id` ASC) VISIBLE,
  INDEX `fkUser_id_idx` (`User_id` ASC) VISIBLE,
  CONSTRAINT `fOrganization_id`
    FOREIGN KEY (`Organization_id`)
    REFERENCES `XEVA`.`Organizations` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fkUser_id`
    FOREIGN KEY (`User_id`)
    REFERENCES `XEVA`.`Users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `XEVA`.`Settings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `XEVA`.`Settings` ;

CREATE TABLE IF NOT EXISTS `XEVA`.`Settings` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `XEVA`.`Time_events`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `XEVA`.`Time_events` ;

CREATE TABLE IF NOT EXISTS `XEVA`.`Time_events` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Event_id` INT NOT NULL,
  `Start_date` DATETIME NOT NULL,
  `End_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fkEvent_id_idx` (`Event_id` ASC) VISIBLE,
  CONSTRAINT `fkEvent_id`
    FOREIGN KEY (`Event_id`)
    REFERENCES `XEVA`.`Events` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `XEVA`.`User_events`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `XEVA`.`User_events` ;

CREATE TABLE IF NOT EXISTS `XEVA`.`User_events` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `time_event_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fkUser_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fkTime_evet_id_idx` (`time_event_id` ASC) VISIBLE,
  CONSTRAINT `User_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `XEVA`.`Users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fkTime_evet_id`
    FOREIGN KEY (`time_event_id`)
    REFERENCES `XEVA`.`Time_events` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

INSERT INTO `Roles`(`Role_name`) VALUES ('User'), ('Organizer'), ('Admin'), ('GlobalAdmin');

INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('1','Cody Conway','nisi.nibh.lacinia@Aliquamauctor.ca','$2y$12$fu6z3Rh2L6w9kPj40x0.s.prXfFPQ/34/G7YuTO2MRxnUQaTjqvGa');
INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('3','Louis Hopper','diam@sed.org','$2y$12$CUdxYgEN46xEUEIonbHgYeqsAuVXAAOBwFOZzmkxgBJiRjQX9VJVG');
INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('2','Kennan Monroe','ipsum.sodales.purus@Ut.ca','$2y$12$xdYD10kyM4ts/mBe7rvI.u7vqcGxCuPnqKuksN.KLyxcxTbLEiwlK');
INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('2','Jordan Anthony','non.sollicitudin.a@blandit.org','$2y$12$nOUhTnKe7UF0uohc3EMFKeGDf9Z.N.eJx7KUDCn4WMnODADZiLO5.');
INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('4','Finn Walls','fringilla.purus.mauris@musProin.org','$2y$12$ij1zSTgndCaqiPFjI2MjRewX5aQz3y.3fiMw8yl6jk05n2bLANIry');
INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('1','Janusz Nosal','facilisis@parturientmontesnascetur.org','$2y$12$OexToj9LxBjQckeJRRzWrOcyF4uhZHTHCK1RuO1mTunB3NEtb5y8m');
INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('4','Eric Vaughn','tellus.imperdiet@consectetueradipiscing.org','$2y$12$Sn2C4wYcWPG3G6XfAApUsu6qni8jKx2n7h/1Ed3k/kQpaSkz.an3m');
INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('4','Cooper Todd','eleifend.vitae.erat@parturientmontes.com','$2y$12$PKe22VJ7fYN/dByrBnU7Beom9fs0h.CC/mAqNxXAvQUWLQLe.t.AW');
INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('1','Slade Glover','venenatis.vel@ipsumcursusvestibulum.org','$2y$12$TMuaNWEu0POiHYuJtkqJmu036M5pxnM8H9aopMyYMoeHMP3zeceHC');
INSERT INTO `Users` (`Role_id`,`Name`,`Email`,`Pwd`) VALUES ('4','Theodore Pierce','erat.nonummy.ultricies@erat.org','$2y$12$0nJQJJP7bhAv/ejqmUn4Xu4P3fwzfrAX0HbSfV9n1UEjn8brgLsBa');

INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Lavasoft','Somalia','DOR','Morgantown','S8Q8E3','Ap #510-9997 Eleifend Avenue','80756136504','109478284','eleifend.vitae@etlibero.ca','tempus mauris','at');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Adobe','Bulgaria','Westmorland','Spartanburg','T8R1G0','630 Arcu Street','81449321615','923723436','est.ac.mattis@Quisqueornare.org','mollis. Phasellus','penatibus et magnis');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Macromedia','Bulgaria','Gwent','New Haven','R3Z3H4','P.O. Box 622, 2497 Feugiat. St.','70187415284','094346729','eget.ipsum.Donec@est.com','blandit','lobortis risus. In');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Apple Systems','Honduras','WMD','New Madrid','Q5X6M2','Ap #219-1979 Pellentesque Rd.','31549154612','336853611','mollis.lectus.pede@inconsectetuer.com','tristique senectus et','nec, eleifend');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Finale','Yemen','Co. Durham','Fitchburg','R5L7E3','9591 A, St.','23683026943','158254912','magna.Phasellus@vitaenibh.ca','Vivamus','nibh sit');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Google','Papua New Guinea','SOM','Chelsea','F9I4K7','Ap #270-6628 Dolor Rd.','27735453633','661814781','iaculis.quis.pede@eratinconsectetuer.org','nisi magna','Aenean gravida');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Borland','Monaco','GSY','Sierra Vista','Z5R7G6','Ap #342-9791 Vulputate, Rd.','56581002663','478735224','magna.et@quis.ca','nec ante blandit','bibendum');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Microsoft','Kazakhstan','OFF','Charlotte','M0E3V5','478-4906 Semper Road','18016235184','118420942','nec.diam.Duis@semperetlacinia.com','vel turpis.','Nulla facilisis. Suspendisse');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Chami','Romania','LET','Beverly','O1N8Z8','P.O. Box 335, 5165 Eu Street','17353486827','184032843','semper@dictumeuplacerat.org','convallis erat,','dui,');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Sibelius','Guinea-bissau','Renfrewshire','Buffalo','L4I5F7','168-7452 Dolor Ave','60231737121','689936134','Donec.consectetuer.mauris@at.edu','amet metus.','gravida sit amet,');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Nokia','Finland','Kirkcudbrightshire','Belleville','D1F1T3','432-2542 Dolor Av.',null,'023185163','nokia.adipiscing.enim@orciluctuset.ca','purus. curts','id, erat.');
INSERT INTO `Organizations` (`Name`,`Country`,`Province`,`City`,`Postal_code`,`Street`,`NIP`,`Phone_number`,`Email`,`Web_page`,`Photo`) VALUES ('Comarch','Poland','Roxburghshire','Dana Point','F8N1I1','1556 Ut Road',null,'781275108','comarch.sed@ac.edu','Vivamus molestie','nec a,');

INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('3','1','justo. Proin','aliquet molestie','','0','sollicitudin','nec mauris','Cras vulputate velit eu sem.','German','0');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('9','9','tincidunt, neque','cursus purus. Nullam scelerisque neque sed sem egestas','"THURSDAY", "FRIDAY", "SUNDAY", "WEDNESDAY"','1','eu','nec, mollis','tempus non, lacinia at, iaculis quis, pede.','German','1');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('9','2','mi enim, condimentum','Curabitur consequat, lectus sit amet luctus vulputate,','"FRIDAY", "WEDNESDAY"','1','lorem','interdum libero dui','semper egestas, urna justo faucibus lectus, a sollicitudin orci','Polish','0');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('10','5','lobortis quam a','rutrum, justo. Praesent luctus. Curabitur egestas nunc','"TUESDAY", "WEDNESDAY"','0','Donec','luctus','orci lobortis augue scelerisque mollis. Phasellus libero mauris, aliquam eu,','Spanish','1');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('4','8','Nunc sed','mollis. Integer tincidunt aliquam','"THURSDAY", "TUESDAY"','0','in','amet','Sed neque. Sed eget lacus. Mauris non dui','Polish','1');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('4','11','porttitor tellus','neque. Nullam nisl.','"SATURDAY", "THURSDAY", "TUESDAY", "WEDNESDAY"','1','Cras','Quisque','eu tempor erat neque non','English','1');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('2','1','Quisque fringilla euismod','metus. In lorem. Donec elementum, lorem ut','','0','sed','interdum enim non','dolor sit amet, consectetuer adipiscing','English','0');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('8','11','placerat velit. Quisque','sit amet diam eu dolor egestas rhoncus. Proin','"FRIDAY"','1','nec','a,','mauris, rhoncus id, mollis nec, cursus','Spanish','0');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('1','8','risus. Donec','vulputate, risus a ultricies adipiscing, enim mi tempor lorem, eget','"WEDNESDAY"','0','neque','laoreet ipsum.','justo faucibus lectus, a sollicitudin orci sem','German','1');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('10','8','Integer in magna.','nulla magna, malesuada vel, convallis in, cursus','"WEDNESDAY", "MONDAY", "SUNDAY", "SATURDAY", "TUESDAY", "THURSDAY", "FRIDAY"','0','ullamcorper.','porttitor','magna tellus faucibus leo, in lobortis','English','1');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('9','11','luctus lobortis.','enim nec tempus scelerisque, lorem','"TUESDAY", "SATURDAY", "THURSDAY", "FRIDAY", "SUNDAY", "MONDAY"','1','convallis','eu,','nunc sed pede. Cum','German','0');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('9','11','est.','enim, condimentum eget, volutpat ornare, facilisis eget, ipsum. Donec','"WEDNESDAY", "MONDAY", "SATURDAY"','1','sodales','et','nascetur','German','0');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('7','9','enim mi tempor','Quisque tincidunt pede','"SUNDAY", "THURSDAY"','1','sem','per conubia','facilisis. Suspendisse commodo tincidunt nibh.','German','0');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('2','4','lectus quis massa.','fames ac turpis egestas. Fusce aliquet magna a','"MONDAY", "THURSDAY", "SATURDAY", "TUESDAY", "SUNDAY", "FRIDAY"','1','aliquet.','sodales nisi magna','dis','German','1');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('7','11','sit amet ultricies','accumsan interdum libero','"MONDAY", "SATURDAY", "FRIDAY", "THURSDAY", "SUNDAY", "TUESDAY"','1','quam','non','nulla. Donec non justo.','German','0');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('9','2','nec, cursus','nonummy ac, feugiat non, lobortis quis,','','1','arcu.','Mauris','nonummy','German','1');
INSERT INTO `Events` (`User_id`,`Organization_id`,`Name`,`Description`,`days_of_week`,`Cyclical`,`Mode`,`Web_address`,`Tags`,`Language`,`Status`) VALUES ('1','7','sit amet','tincidunt orci quis lectus. Nullam suscipit, est ac facilisis facilisis,','"WEDNESDAY"','1','pretium','sapien molestie orci','non, hendrerit id, ante. Nunc mauris sapien,','German','0');

INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('3','2021-09-05 16:15:48','2021-09-05 16:49:05');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('10','2021-07-14 18:56:14','2021-07-14 20:13:17');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('2','2021-08-26 07:04:55','2021-08-26 22:02:54');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('14','2020-10-28 11:46:39','2021-10-28 14:27:12');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('14','2022-02-06 05:29:26','2021-02-06 20:09:46');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('5','2021-02-16 15:02:09','2021-02-16 16:08:30');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('13','2021-08-29 10:46:13','2020-08-29 22:09:57');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('3','2021-05-17 03:43:29','2020-05-17 08:11:01');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('16','2020-12-12 01:47:34','2021-12-12 04:05:51');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('9','2020-10-16 09:43:32','2020-10-16 12:38:27');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('12','2021-04-05 22:08:59','2020-04-05 22:43:22');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('3','2022-02-04 08:21:38','2020-02-04 19:44:54');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('12','2020-04-13 00:15:04','2020-04-13 02:01:51');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('17','2020-11-20 08:08:35','2020-11-20 23:17:17');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('14','2021-09-13 11:08:03','2021-03-13 13:07:18');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('2','2021-11-18 14:14:54','2021-11-18 16:10:40');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('16','2020-09-26 12:12:33','2020-09-26 16:34:13');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('8','2021-05-27 02:17:24','2020-05-27 21:51:32');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('15','2020-04-03 18:04:46','2022-04-03 19:42:31');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('1','2021-04-28 20:48:37','2021-04-28 22:26:06');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('4','2021-10-01 01:25:17','2022-10-01 03:41:27');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('6','2021-03-15 13:26:56','2021-03-15 14:13:05');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('1','2020-08-18 12:47:38','2020-08-18 14:27:49');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('9','2021-06-23 19:03:42','2022-06-23 20:13:18');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('7','2020-04-19 12:23:12','2020-04-19 15:17:22');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('1','2022-02-22 07:13:52','2020-02-22 08:18:57');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('15','2020-03-23 09:01:33','2022-03-23 18:34:09');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('6','2020-06-15 12:23:05','2021-06-15 18:13:41');
INSERT INTO `time_events` (`Event_id`,`Start_date`,`End_date`) VALUES ('16','2021-02-06 06:19:23','2021-02-06 10:21:22');


INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('2','15');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('9','4');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('10','14');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('3','3');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('6','9');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('4','11');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('10','2');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('9','4');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('9','4');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('8','2');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('4','8');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('3','14');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('10','2');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('7','16');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('4','3');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('9','10');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('10','13');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('8','16');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('6','17');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('1','1');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('5','8');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('7','7');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('6','9');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('6','7');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('8','7');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('5','2');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('9','12');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('9','13');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('8','9');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('7','3');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('7','9');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('7','11');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('3','7');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('6','14');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('4','10');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('9','15');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('10','8');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('3','10');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('10','14');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('10','12');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('2','7');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('8','17');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('2','10');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('8','16');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('1','7');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('1','12');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('9','12');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('3','2');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('1','14');
INSERT INTO `user_events` (`User_id`,`Time_Event_id`) VALUES ('9','8');

delimiter //
CREATE DEFINER = CURRENT_USER TRIGGER `xeva`.`organizations_AFTER_DELETE` AFTER DELETE ON `organizations` FOR EACH ROW
BEGIN
	delete from `Events` where `Organization_id` = old.id;
END
//

CREATE DEFINER = CURRENT_USER TRIGGER `xeva`.`Events_AFTER_DELETE` AFTER DELETE ON `Events` FOR EACH ROW
BEGIN
	delete from `time_events` where `Event_id` = old.id;
END
//

CREATE DEFINER = CURRENT_USER TRIGGER `xeva`.`Time_Events_AFTER_DELETE` AFTER DELETE ON `Time_Events` FOR EACH ROW
BEGIN
	delete from `user_events` where `Time_Event_id` = old.id;
END
//

CREATE DEFINER = CURRENT_USER TRIGGER `xeva`.`users_AFTER_DELETE` AFTER DELETE ON `users` FOR EACH ROW
BEGIN
	delete from `events` where `user_id` = old.id;
    delete from `user_events` where `User_id` = old.id;
END
//

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;