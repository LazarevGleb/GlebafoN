-- MySQL Workbench Forward Engineering
SET NAMES utf8;
SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_BACKSLASH_ESCAPES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema mobileoperator
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mobileoperator`;

-- -----------------------------------------------------
-- Schema mobileoperator
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mobileoperator`
  DEFAULT CHARACTER SET utf8;
USE `mobileoperator`;

-- -----------------------------------------------------
-- Table `mobileoperator`.`client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mobileoperator`.`client`;

CREATE TABLE IF NOT EXISTS `mobileoperator`.`client` (
  `ID`                     INT(3)                 NOT NULL AUTO_INCREMENT,
  `NAME`                   VARCHAR(45)            NOT NULL,
  `SURNAME`                VARCHAR(45)            NOT NULL,
  `BIRTHDAY`               VARCHAR(10)            NOT NULL,
  `ADDRESS`                VARCHAR(255)           NOT NULL,
  `EMAIL`                  VARCHAR(45) UNIQUE     NOT NULL,
  `PASSPORT_SERIES`        VARCHAR(45)            NOT NULL,
  `PASSPORT_NUMBER`        VARCHAR(45)            NOT NULL,
  `PASSPORT_ISSUED_BY`     VARCHAR(45)            NOT NULL,
  `PASSPORT_ISSUE_DATE`    VARCHAR(45)            NOT NULL,
  `PASSPORT_DIVISION_CODE` VARCHAR(45)            NOT NULL,
  `VALID` BIT DEFAULT 1,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `mobileoperator`.`contract`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mobileoperator`.`contract`;

CREATE TABLE IF NOT EXISTS `mobileoperator`.`contract` (
  `ID`                 INT(3)                                                       NOT NULL AUTO_INCREMENT,
  `NUMBER`             VARCHAR(45) UNIQUE                                           NOT NULL,
  `CONTRACT_TARIFF_ID` INT(3)                                                       ,
  `CONTRACT_CLIENT_ID` INT(3)                                                       NOT NULL,
  `BLOCKED`            ENUM ('UNBLOCKED', 'CLIENT_BLOCKED', 'MANAGER_BLOCKED')      NOT NULL,
  `PASSWORD`               VARCHAR(70),
  `BALANCE`                 DECIMAL(10,2) NOT NULL DEFAULT 0.0,
  `VALID` BIT DEFAULT 1,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `NUMBER_UNIQUE` (`NUMBER` ASC),
  INDEX `contract_tariff_idx` (`CONTRACT_TARIFF_ID` ASC),
  INDEX `contract_client_idx` (`CONTRACT_CLIENT_ID` ASC),
  CONSTRAINT `CONTRACT_TARIFF`
  FOREIGN KEY (`CONTRACT_TARIFF_ID`) REFERENCES tariff (`ID`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `CONTRACT_CLIENT`
  FOREIGN KEY (`CONTRACT_CLIENT_ID`) REFERENCES client (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `mobileoperator`.`addition`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mobileoperator`.`addition`;

CREATE TABLE IF NOT EXISTS `mobileoperator`.`addition` (
  `ID`              INT(3)                                   NOT NULL AUTO_INCREMENT,
  `NAME`            VARCHAR(45) UNIQUE                       NOT NULL,
  `PARAMETER`       ENUM ('SMS', 'MINUTES', 'INTERNET')      NOT NULL,
  `VALUE`           BIGINT(4)                                NOT NULL DEFAULT 0,
  `PRICE`           DECIMAL(10, 2) UNSIGNED                  NOT NULL,
  `ACTIVATION_COST` DECIMAL(10, 2) UNSIGNED                  NOT NULL,
  `VALID` BIT DEFAULT 1,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `NAME_UNIQUE` (`NAME` ASC)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mobileoperator`.`mandatoryOptions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mobileoperator`.`mandatoryOptions`;

CREATE TABLE IF NOT EXISTS `mobileoperator`.`mandatoryOptions` (
  `ID`              INT(3)                                   NOT NULL AUTO_INCREMENT,
  `MANDATORY_OPTION_1` INT(3) NOT NULL,
  `MANDATORY_OPTION_2` INT(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `MANDATORY_DEPENDENT` (`MANDATORY_OPTION_1` ASC,`MANDATORY_OPTION_2` ASC ),
  INDEX `mandatory_option_idx` (`MANDATORY_OPTION_1` ASC),
  CONSTRAINT `MANDATORY_OPTION`
  FOREIGN KEY (`MANDATORY_OPTION_1`)
  REFERENCES `mobileoperator`.`addition` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mobileoperator`.`incompatibleOptions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mobileoperator`.`incompatibleOptions`;

CREATE TABLE IF NOT EXISTS `mobileoperator`.`incompatibleOptions` (
  `ID`              INT(3)                                   NOT NULL AUTO_INCREMENT,
  `INCOMPATIBLE_OPTION_1` INT(3) NOT NULL,
  `INCOMPATIBLE_OPTION_2` INT(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `INCOMPATIBLE_DEPENDENT` (`INCOMPATIBLE_OPTION_1` ASC,`INCOMPATIBLE_OPTION_2` ASC ),
  INDEX `incompatible_option_idx` (`INCOMPATIBLE_OPTION_1` ASC),
  CONSTRAINT `INCOMPATIBLE_OPTION`
  FOREIGN KEY (`INCOMPATIBLE_OPTION_1`)
  REFERENCES `mobileoperator`.`addition` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mobileoperator`.`tariff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mobileoperator`.`tariff`;

CREATE TABLE IF NOT EXISTS `mobileoperator`.`tariff` (
  `ID`          INT(3)                    NOT NULL AUTO_INCREMENT,
  `NAME`        VARCHAR(45) UNIQUE        NOT NULL,
  `SMS`         BIGINT(4)                 NOT NULL DEFAULT 0,
  `MINUTES`     BIGINT(4)                 NOT NULL DEFAULT 0,
  `INTERNET_GB` BIGINT(2)                 NOT NULL DEFAULT 0,
  `DESCRIPTION` VARCHAR(255)              NOT NULL,
  `PRICE`       DECIMAL(10, 2) UNSIGNED   NOT NULL,
  `VALID` BIT DEFAULT 1,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `NAME_UNIQUE` (`NAME` ASC)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `mobileoperator`.`package`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mobileoperator`.`package`;

CREATE TABLE IF NOT EXISTS `mobileoperator`.`package` (
  `ID`                  INT(3) AUTO_INCREMENT,
  `PACKAGE_TARIFF_ID`   INT(3) NOT NULL,
  `PACKAGE_ADDITION_ID` INT(3) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `TARIFF_ADDITION` (`PACKAGE_TARIFF_ID` ASC, `PACKAGE_ADDITION_ID` ASC),
  INDEX `package_tariff_idx` (`PACKAGE_TARIFF_ID` ASC),
  INDEX `package_addition_idx` (`PACKAGE_ADDITION_ID` ASC),
  CONSTRAINT `PACKAGE_TARIFF`
  FOREIGN KEY (`PACKAGE_TARIFF_ID`)
  REFERENCES `mobileoperator`.`tariff` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PACKAGE_ADDITION`
  FOREIGN KEY (`PACKAGE_ADDITION_ID`)
  REFERENCES `mobileoperator`.`addition` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `mobileoperator`.`expense` Статья расходов
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mobileoperator`.`expense`;

CREATE TABLE IF NOT EXISTS `mobileoperator`.`expense` (
  `ID`                  INT(3) NOT NULL AUTO_INCREMENT,
  `EXPENSE_CONTRACT_ID` INT(3) NOT NULL,
  `EXPENSE_ADDITION_ID` INT(3) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `expense_contract_idx` (`EXPENSE_CONTRACT_ID` ASC),
  INDEX `expense_addition_idx` (`EXPENSE_ADDITION_ID` ASC),
  CONSTRAINT `EXPENSE_CONTRACT`
  FOREIGN KEY (`EXPENSE_CONTRACT_ID`)
  REFERENCES `mobileoperator`.`contract` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `EXPENSE_ADDITION`
  FOREIGN KEY (`EXPENSE_ADDITION_ID`)
  REFERENCES `mobileoperator`.`addition` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `client`
-- -----------------------------------------------------
START TRANSACTION;
USE `mobileoperator`;
INSERT INTO `client` (`ID`,
                      `NAME`,
                      `SURNAME`,
                      `BIRTHDAY`,
                      `ADDRESS`,
                      `EMAIL`,
                      `PASSPORT_SERIES`,
                      `PASSPORT_NUMBER`,
                      `PASSPORT_ISSUED_BY`,
                      `PASSPORT_ISSUE_DATE`,
                      `PASSPORT_DIVISION_CODE`)
VALUES (1,
        'Жека',
        'Игнатьев',
        '29.05.1994',
        'Санкт-Петербург',
        'ignat@mail.com',
        '0514',
        '300986',
        'УФМС России по Санкт-Петербургу',
        '27.06.2014',
        '268-090');
INSERT INTO `client` (`ID`,
                      `NAME`,
                      `SURNAME`,
                      `BIRTHDAY`,
                      `ADDRESS`,
                      `EMAIL`,
                      `PASSPORT_SERIES`,
                      `PASSPORT_NUMBER`,
                      `PASSPORT_ISSUED_BY`,
                      `PASSPORT_ISSUE_DATE`,
                      `PASSPORT_DIVISION_CODE`)
VALUES (2,
        'Даша',
        'Игнатьева',
        '01.02.1996',
        'Санкт-Петербург',
        'dashaignateva@rambler.ru',
        '0516',
        '767595',
        'УФМС России по Санкт-Петербургу',
        '12.03.2017',
        '286-666');
INSERT INTO `client` (`ID`,
                      `NAME`,
                      `SURNAME`,
                      `BIRTHDAY`,
                      `ADDRESS`,
                      `EMAIL`,
                      `PASSPORT_SERIES`,
                      `PASSPORT_NUMBER`,
                      `PASSPORT_ISSUED_BY`,
                      `PASSPORT_ISSUE_DATE`,
                      `PASSPORT_DIVISION_CODE`)
VALUES (3,
        'Gleb',
        'Lazarev',
        '06.12.1994',
        'Saint_petersburg, Lunacharskiy avenue, house 110',
        'lazarevgb@gmail.com',
        '0514',
        '225948',
        'UFMS Russia Primorskiy kray',
        '29.01.2015',
        '250-060');
INSERT INTO `client` (`ID`,
                      `NAME`,
                      `SURNAME`,
                      `BIRTHDAY`,
                      `ADDRESS`,
                      `EMAIL`,
                      `PASSPORT_SERIES`,
                      `PASSPORT_NUMBER`,
                      `PASSPORT_ISSUED_BY`,
                      `PASSPORT_ISSUE_DATE`,
                      `PASSPORT_DIVISION_CODE`)
VALUES (4,
        'Полина',
        'Лазарева',
        '24.07.1996',
        'Санкт-Петербург, ул. Шувалова, д. 76',
        'lazarevapb@gmail.com',
        '0516',
        '224667',
        'УФМС России по Приморскому краю',
        '29.06.2017',
        '250-060');
INSERT INTO `client` (`ID`,
                      `NAME`,
                      `SURNAME`,
                      `BIRTHDAY`,
                      `ADDRESS`,
                      `EMAIL`,
                      `PASSPORT_SERIES`,
                      `PASSPORT_NUMBER`,
                      `PASSPORT_ISSUED_BY`,
                      `PASSPORT_ISSUE_DATE`,
                      `PASSPORT_DIVISION_CODE`)
VALUES (5,
        'Ирина',
        'Лазарева',
        '14.10.1961',
        'Большой Камень',
        'lazarevaig@gmail.com',
        '0578',
        '267676',
        'УФМС России по Хабаровскому краю',
        '27.02.2010',
        '276-956');

COMMIT;

-- -----------------------------------------------------
-- Data for table `addition`
-- -----------------------------------------------------
START TRANSACTION;
USE `mobileoperator`;
INSERT INTO `addition` (ID, NAME, PARAMETER, VALUE, PRICE, ACTIVATION_COST)
VALUES (1, '100 extra sms', 'SMS', 100, 50, 50);
INSERT INTO `addition` (ID, NAME, PARAMETER, VALUE, PRICE, ACTIVATION_COST)
VALUES (2, '300 extra sms', 'SMS', 300, 100, 35);
INSERT INTO `addition` (ID, NAME, PARAMETER, VALUE, PRICE, ACTIVATION_COST)
VALUES (3, '150 extra minutes', 'MINUTES', 150, 350, 100);
INSERT INTO `addition` (ID, NAME, PARAMETER, VALUE, PRICE, ACTIVATION_COST)
VALUES (4, '400 extra minutes', 'MINUTES', 400, 600, 0);
INSERT INTO `addition` (ID, NAME, PARAMETER, VALUE, PRICE, ACTIVATION_COST)
VALUES (5, '1 extra Gb', 'INTERNET', 1, 200, 50);
INSERT INTO `addition` (ID, NAME, PARAMETER, VALUE, PRICE, ACTIVATION_COST)
VALUES (6, '3 extra Gb', 'INTERNET', 3, 550, 0);

COMMIT;

-- -----------------------------------------------------
-- Data for table `tariff`
-- -----------------------------------------------------
START TRANSACTION;
USE `mobileoperator`;
INSERT INTO `tariff` (ID, NAME, SMS, MINUTES, INTERNET_GB, DESCRIPTION, PRICE)
VALUES (1, 'Schooler', 100, 200, 3, '100 sms, 200 minutes, 3 Gb', 100.00);
INSERT INTO `tariff` (ID, NAME, SMS, MINUTES, INTERNET_GB, DESCRIPTION, PRICE)
VALUES (2, 'Student', 200, 350, 7, '200 sms, 350 minutes, 7 Gb', 350);
INSERT INTO `tariff` (ID, NAME, SMS, MINUTES, INTERNET_GB, DESCRIPTION, PRICE)
VALUES (3, 'Adult', 500, 500, 10, '500 sms, 500 minutes, 10 Gb', 550.00);
INSERT INTO `tariff` (ID, NAME, SMS, MINUTES, INTERNET_GB, DESCRIPTION, PRICE)
VALUES (4, 'Social net', 0, 100, 30, '100 minutes, 30 Gb', 600.00);
INSERT INTO `tariff` (ID, NAME, SMS, MINUTES, INTERNET_GB, DESCRIPTION, PRICE)
VALUES (5, 'USSR', 300, 400, 0, '300 sms, 400 minutes', 400.00);
INSERT INTO `tariff` (ID, NAME, SMS, MINUTES, INTERNET_GB, DESCRIPTION, PRICE)
VALUES (6, 'Call', 0, 700, 0, '700 minutes', 450.00);

COMMIT;

-- -----------------------------------------------------
-- Data for table `contract`
-- -----------------------------------------------------
START TRANSACTION;
USE `mobileoperator`;
INSERT INTO `contract` (ID, NUMBER, CONTRACT_TARIFF_ID, CONTRACT_CLIENT_ID, BLOCKED, PASSWORD, `BALANCE`)
VALUES (1, '+7-925-732-6169', 2, 3, 'UNBLOCKED', 'gleb12345', 0);
INSERT INTO `contract` (ID, NUMBER, CONTRACT_TARIFF_ID, CONTRACT_CLIENT_ID, BLOCKED, PASSWORD, `BALANCE`)
VALUES (2, '+7-925-252-6866', 3, 4, 'UNBLOCKED', 'polina12345', 0);
INSERT INTO `contract` (ID, NUMBER, CONTRACT_TARIFF_ID, CONTRACT_CLIENT_ID, BLOCKED, PASSWORD, `BALANCE`)
VALUES (3, '+7-925-194-8166', 1, 1, 'UNBLOCKED', 'zheka12345', 0);
INSERT INTO `contract` (ID, NUMBER, CONTRACT_TARIFF_ID, CONTRACT_CLIENT_ID, BLOCKED, PASSWORD, `BALANCE`)
VALUES (4, '+7-925-159-3317', 3, 2, 'UNBLOCKED', 'dasha12345', 0);
INSERT INTO `contract` (ID, NUMBER, CONTRACT_TARIFF_ID, CONTRACT_CLIENT_ID, BLOCKED, PASSWORD, `BALANCE`)
VALUES (5, '+7-925-702-1956', 2, 5, 'UNBLOCKED', 'mama12345', 0);

COMMIT;