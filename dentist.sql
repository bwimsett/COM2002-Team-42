DROP TABLE IF EXISTS `Appointment` CASCADE;
DROP TABLE IF EXISTS `Treatment` CASCADE;
DROP TABLE IF EXISTS `StaffMember` CASCADE;
DROP TABLE IF EXISTS `Patient` CASCADE;
DROP TABLE IF EXISTS `HealthcarePlan` CASCADE;
DROP TABLE IF EXISTS `Address` CASCADE;

DROP TABLE IF EXISTS `Address` CASCADE;
CREATE TABLE `Address` (
    `Postcode` VARCHAR(10) NOT NULL,
    `HouseNumber` SMALLINT UNSIGNED NOT NULL,
    `Street` VARCHAR(50) NOT NULL,
    `District` VARCHAR(50) NOT NULL,
    `City` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`Postcode` , `HouseNumber`)
);
INSERT INTO `Address` (`Postcode`, `HouseNumber`, `Street`, `District`, `City`) VALUES
  ('AAAAAA', '2', 'Street1', 'District1', 'City1'),
  ('AAAAAA', '1', 'Street1', 'District1', 'City1');

#
# Table structure for table 'HealthcarePlan'
#

DROP TABLE IF EXISTS `HealthcarePlan` CASCADE;
CREATE TABLE `HealthcarePlan` (
    `Plan` VARCHAR(50) NOT NULL,
    `Price` SMALLINT UNSIGNED NOT NULL,
    `Level` TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (`Plan`)
);
INSERT INTO `HealthcarePlan` (`Plan`, `Price`, `Level`) VALUES
  ('Full', '250', '5'),
  ('Minimum', '50', '1');

#
# Table structure for table 'Patient'
#

DROP TABLE IF EXISTS `Patient` CASCADE;
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
  ('1', 'Mr', 'Man', 'Manning', '1973-12-30', '070000000', '70', 'Full', 'AAAAAA', '1'),
  ('2', 'Mr', 'Mans', 'Mannings', '1975-12-30', '070000001', '-50', 'Minimum', 'AAAAAA', '1');

#
# Table structure for table 'StaffMember'
#

DROP TABLE IF EXISTS `StaffMember` CASCADE;
CREATE TABLE `StaffMember` (
    `ProfessionalID` SMALLINT UNSIGNED NOT NULL,
    `JobTitle` VARCHAR(30) NOT NULL,
    `Forename` VARCHAR(30) NOT NULL,
    `Surname` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`ProfessionalID`)
);
INSERT INTO `StaffMember` (`ProfessionalID`, `JobTitle`, `Forename`, `Surname`) VALUES
  ('1', 'Secretary', 'Sec', 'Tary'),
  ('2', 'Hygenist', 'Hi', 'Gen'),
  ('3', 'Dentist', 'Den', 'Tist');

#
# Table structure for table 'Treatment'
#

DROP TABLE IF EXISTS `Treatment` CASCADE;
CREATE TABLE `Treatment` (
    `AppointmentType` VARCHAR(30) NOT NULL,
    `Cost` SMALLINT UNSIGNED,
    `Duration` SMALLINT UNSIGNED NOT NULL,
    `Practitioner` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`AppointmentType`)
);
INSERT INTO `Treatment` (`AppointmentType`, `Cost`, `Duration`, `Practitioner`) VALUES
  ('Check Up', '30', '20', 'Dentist'),
  ('Cleaning', '50', '20', 'Hygenist'),
  ('Treatment', '40', '60', 'Dentist');

#
# Table structure for table 'Appointment'
#

DROP TABLE IF EXISTS `Appointment` CASCADE;
CREATE TABLE `Appointment` (
    `ProfessionalID` SMALLINT UNSIGNED NOT NULL,
    `AppointmentDate` DATE NOT NULL,
    `AppointmentStartTime` TIME NOT NULL,
    `AppointmentEndTime` TIME,
    `AppointmentType` VARCHAR(30) NOT NULL,
    `PatientID` SMALLINT UNSIGNED,
    PRIMARY KEY (`ProfessionalID` , `AppointmentDate` , `AppointmentStartTime`),
    FOREIGN KEY (`PatientID`) REFERENCES `Patient` (`PatientID`),
    FOREIGN KEY (`AppointmentType`) REFERENCES `Treatment` (`AppointmentType`),
    FOREIGN KEY (`ProfessionalID`) REFERENCES `StaffMember` (`ProfessionalID`)
);
INSERT INTO `Appointment` (`ProfessionalID`, `AppointmentDate`, `AppointmentStartTime`, `AppointmentType`, `PatientID`) VALUES
  ('3', '2017-12-30', '12:30:00', 'Check Up', '1'),
  ('3', '2017-12-30', '14:30:00', 'Check Up', '2');