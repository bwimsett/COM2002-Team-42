#
# Table structure for table 'Address'
#

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
    `Plan` VARCHAR(50) NOT NULL,
    `Postcode` VARCHAR(10) NOT NULL,
    `HouseNumber` SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (`Plan`) REFERENCES `HealthcarePlan` (`Plan`),
    FOREIGN KEY (`Postcode`, `HouseNumber`) REFERENCES `Address` (`Postcode`, `HouseNumber`),
    PRIMARY KEY (`PatientID`)
);
INSERT INTO `Patient` (`PatientID`, `Title`, `Forename`, `Surname`, `DOB`, `PhoneNumber`, `Plan`, `Postcode`, `HouseNumber`) VALUES
  ('1', 'Mr', 'Man', 'Manning', '1973-12-30', '070000000', 'Full', 'AAAAAA', '1'),
  ('2', 'Mr', 'Mans', 'Mannings', '1975-12-30', '070000001', 'Minimum', 'AAAAAA', '1');
  
#
# Table structure for table 'StaffMember'
#

DROP TABLE IF EXISTS `StaffMember` CASCADE;
CREATE TABLE `StaffMember` (
    `ProffessionalID` SMALLINT UNSIGNED NOT NULL,
    `JobTitle` VARCHAR(30) NOT NULL,
    `Forename` VARCHAR(30) NOT NULL,
    `Surname` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`ProffessionalID`)
);
INSERT INTO `StaffMember` (`ProffessionalID`, `JobTitle`, `Forename`, `Surname`) VALUES
  ('1', 'Secretary', 'Sec', 'Tary'),
  ('2', 'Dentist', 'Den', 'Tist');
  
#
# Table structure for table 'Treatment'
#

DROP TABLE IF EXISTS `Treatment` CASCADE;
CREATE TABLE `Treatment` (
    `AppointmentType` VARCHAR(30) NOT NULL,
    `Cost` SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (`AppointmentType`)
);
INSERT INTO `Treatment` (`AppointmentType`, `Cost`) VALUES
  ('Check Up', '30'),
  ('Descaling', '50');
  
#
# Table structure for table 'Appointment'
#

DROP TABLE IF EXISTS `Appointment` CASCADE;
CREATE TABLE `Appointment` (
    `ProffessionalID` SMALLINT UNSIGNED NOT NULL,
    `AppointmentDate` DATE NOT NULL,
    `AppointmentStartTime` TIME NOT NULL,
    `AppointmentEndTime` TIME,
    `AppointmentType` VARCHAR(30) NOT NULL,
    `PatientID` SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (`ProffessionalID` , `AppointmentDate` , `AppointmentStartTime`),
    FOREIGN KEY (`PatientID`) REFERENCES `Patient` (`PatientID`),
    FOREIGN KEY (`AppointmentType`) REFERENCES `Treatment` (`AppointmentType`),
    FOREIGN KEY (`ProffessionalID`) REFERENCES `StaffMember` (`ProffessionalID`)
);
INSERT INTO `Appointment` (`ProffessionalID`, `AppointmentDate`, `AppointmentStartTime`, `AppointmentType`, `PatientID`) VALUES
  ('1', '2017-12-30', '12:30:00', 'Check Up', '1'),
  ('1', '2017-12-30', '14:30:00', 'Check Up', '2');