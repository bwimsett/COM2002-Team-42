DROP TABLE IF EXISTS `Appointment` CASCADE;
DROP TABLE IF EXISTS `Treatment` CASCADE;
DROP TABLE IF EXISTS `StaffMember` CASCADE;
DROP TABLE IF EXISTS `Patient` CASCADE;
DROP TABLE IF EXISTS `HealthcarePlan` CASCADE;
DROP TABLE IF EXISTS `CareLevel` CASCADE;
DROP TABLE IF EXISTS `Address` CASCADE;
DROP TABLE IF EXISTS `Reciept` CASCADE;

#
# Table structure for table 'Address'
#
CREATE TABLE `Address` (
    `Postcode` VARCHAR(10) NOT NULL,
    `HouseNumber` SMALLINT UNSIGNED NOT NULL,
    `Street` VARCHAR(50) NOT NULL,
    `District` VARCHAR(50) NOT NULL,
    `City` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`Postcode` , `HouseNumber`)
);
INSERT INTO `Address` (`Postcode`, `HouseNumber`, `Street`, `District`, `City`) VALUES
  ('AAAAAA', '1', 'Street1', 'District1', 'City1'),
  ('AAAAAA', '2', 'Street1', 'District1', 'City1'),
  ('BBBBBB', '2', 'Street2', 'District1', 'City1');

#
# Table structure for table 'HealthcarePlan'
#
CREATE TABLE `HealthcarePlan` (
    `Plan` VARCHAR(50) NOT NULL,
    `Price` SMALLINT UNSIGNED NOT NULL,
    `Coverage` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`Plan`)
);
INSERT INTO `HealthcarePlan` (`Plan`, `Price`, `Coverage`) VALUES
  ('None', '0', '0 Check Up, 0 Hygiene, 0 Repairs'),
  ('NHS free plan', '0', '2 Check Up, 2 Hygiene, 6 Repairs'),
  ('Maintenance', '15', '2 Check Up, 2 Hygiene, 0 Repairs'),
  ('Oral health plan', '21', '2 Check Up, 4 Hygiene, 0 Repairs'),
  ('Dental repair plan', '36', '2 Check Up, 4 Hygiene, 2 Repairs');

#
# Table structure for table 'Patient'
#
CREATE TABLE `Patient` (
    `PatientID` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Title` VARCHAR(10) NOT NULL,
    `Forename` VARCHAR(30) NOT NULL,
    `Surname` VARCHAR(30) NOT NULL,
    `DOB` DATE NOT NULL,
    `PhoneNumber` VARCHAR(15) NOT NULL,
    `Credit` SMALLINT NOT NULL,
    `Plan` VARCHAR(50) NOT NULL,
    `Postcode` VARCHAR(10) NOT NULL,
    `HouseNumber` SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (`Plan`) REFERENCES `HealthcarePlan` (`Plan`),
    FOREIGN KEY (`Postcode`, `HouseNumber`) REFERENCES `Address` (`Postcode`, `HouseNumber`),
    PRIMARY KEY (`PatientID`)
);
INSERT INTO `Patient` (`PatientID`, `Title`, `Forename`, `Surname`, `DOB`, `PhoneNumber`, `Credit`, `Plan`, `Postcode`, `HouseNumber`) VALUES
  ('1', 'Mr', 'Man', 'Manning', '1973-12-30', '070000000', '70', 'Dental repair plan', 'AAAAAA', '1'),
  ('2', 'Mr', 'Mans', 'Mannings', '1975-12-30', '070000001', '-50', 'None', 'AAAAAA', '1');

#
# Table structure for table 'StaffMember'
#
CREATE TABLE `StaffMember` (
    `ProfessionalID` SMALLINT UNSIGNED NOT NULL,
    `JobTitle` VARCHAR(30) NOT NULL,
    `Forename` VARCHAR(30) NOT NULL,
    `Surname` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`ProfessionalID`)
);
INSERT INTO `StaffMember` (`ProfessionalID`, `JobTitle`, `Forename`, `Surname`) VALUES
  ('1', 'Secretary', 'Sec', 'Tary'),
  ('2', 'Hygienist', 'Hi', 'Gen'),
  ('3', 'Dentist', 'Den', 'Tist');

#
# Table structure for table 'Treatment'
#
CREATE TABLE `Treatment` (
    `AppointmentType` VARCHAR(30) NOT NULL,
    `Cost` SMALLINT UNSIGNED,
    `Duration` SMALLINT UNSIGNED NOT NULL,
    `JobTitle` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`AppointmentType`)
);
INSERT INTO `Treatment` (`AppointmentType`, `Cost`, `Duration`, `JobTitle`) VALUES
  ('Check Up', '45', '20', 'Dentist'),
  ('Cleaning', '45', '20', 'Hygienist'),
  ('Silver Amalgam', '90', '60', 'Dentist'),
  ('Resin Filling', '150', '60', 'Dentist'),
  ('Gold Crown', '500', '60', 'Dentist');

#
# Table structure for table 'Appointment'
#
CREATE TABLE `Appointment` (
    `ProfessionalID` SMALLINT UNSIGNED NOT NULL,
    `AppointmentDate` DATE NOT NULL,
    `AppointmentStartTime` TIME NOT NULL,
    `AppointmentEndTime` TIME,
    `AppointmentCost` SMALLINT UNSIGNED,
    `AppointmentType` VARCHAR(30) NOT NULL,
    `Completed` TINYINT NOT NULL DEFAULT '0',
    `PatientID` SMALLINT UNSIGNED,
    FOREIGN KEY (`PatientID`) REFERENCES `Patient` (`PatientID`),
    FOREIGN KEY (`AppointmentType`) REFERENCES `Treatment` (`AppointmentType`),
    FOREIGN KEY (`ProfessionalID`) REFERENCES `StaffMember` (`ProfessionalID`),
    PRIMARY KEY (`ProfessionalID` , `AppointmentDate` , `AppointmentStartTime`)
);
INSERT INTO `Appointment` (`ProfessionalID`, `AppointmentDate`, `AppointmentStartTime`, `AppointmentType`, `PatientID`) VALUES
  ('3', '2017-12-30', '12:30:00', 'Check Up', '1'),
  ('3', '2017-12-30', '14:30:00', 'Check Up', '2');

#
# Table structure for table 'Reciept'
#
CREATE TABLE `Reciept` (
    `Time` DATETIME NOT NULL,
    `PatientID` SMALLINT UNSIGNED NOT NULL,
    `Check Up` TINYINT UNSIGNED NOT NULL,
    `Check Up Plan` TINYINT UNSIGNED NOT NULL,
    `Hygienist` TINYINT UNSIGNED NOT NULL,
    `Hygienist Plan` TINYINT UNSIGNED NOT NULL,
    `Repair:Silver Amalgam` TINYINT UNSIGNED NOT NULL,
    `Repair:Silver Amalgam Plan` TINYINT UNSIGNED NOT NULL,
    `Repair:Resin Filling` TINYINT UNSIGNED NOT NULL,
    `Repair:Resin Filling Plan` TINYINT UNSIGNED NOT NULL,
    `Repair:Gold Crown` TINYINT UNSIGNED NOT NULL,
    `Repair:Gold Crown Plan` TINYINT UNSIGNED NOT NULL,
    `Total Cost` SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (`PatientID`) REFERENCES `Patient` (`PatientID`),
    PRIMARY KEY (`Time`, `PatientID`)
);
INSERT INTO `Reciept` (`Time`, `PatientID`,`Check Up`,`Check Up Plan`,`Hygienist`,`Hygienist Plan`,`Repair:Silver Amalgam`,`Repair:Silver Amalgam Plan`,`Repair:Resin Filling`,`Repair:Resin Filling Plan`,`Repair:Gold Crown`,`Repair:Gold Crown Plan`, `Total Cost`) VALUES
  ('2017-11-27 12:30:00', '1', '1', '1', '0', '0', '0', '0', '1', '0', '0', '0', '150');