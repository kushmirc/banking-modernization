-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: BNS
-- ------------------------------------------------------
-- Server version	5.7.12-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `FNAME` varchar(40) DEFAULT NULL,
  `LNAME` varchar(40) DEFAULT NULL,
  `USERID` varchar(40) NOT NULL,
  `PWORD` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES ('Admin','Test','admin','admin');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banker`
--

DROP TABLE IF EXISTS `banker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `banker` (
  `FNAME` varchar(40) DEFAULT NULL,
  `LNAME` varchar(40) DEFAULT NULL,
  `DOB` varchar(40) DEFAULT NULL,
  `USERID` varchar(40) NOT NULL,
  `PWORD` varchar(40) DEFAULT NULL,
  `GENDER` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banker`
--

LOCK TABLES `banker` WRITE;
/*!40000 ALTER TABLE `banker` DISABLE KEYS */;
INSERT INTO `banker` VALUES ('Banker','Test','09/03/1988','banker','urvi','MALE');
/*!40000 ALTER TABLE `banker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaint` (
  `COMPLAINT_NO` int(11) DEFAULT NULL,
  `ACTNO` varchar(40) DEFAULT NULL,
  `COMPLAINT_DATE` date DEFAULT NULL,
  `SUBJECT` varchar(40) DEFAULT NULL,
  `DESCRIPTION` varchar(40) DEFAULT NULL,
  `STATUS` varchar(40) DEFAULT NULL,
  `CLOSED` varchar(40) DEFAULT NULL,
  `complaintID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`complaintID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint`
--

LOCK TABLES `complaint` WRITE;
/*!40000 ALTER TABLE `complaint` DISABLE KEYS */;
INSERT INTO `complaint` VALUES (1013,'199980560','2018-12-01','test','test1','Open','False',4),(1011,'199980560','2018-12-01','123456','345324dfsdf','Open','False',5),(1012,'199980560','2018-12-01','UI not working','Some issues','Open','False',6);
/*!40000 ALTER TABLE `complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `FNAME` varchar(40) DEFAULT NULL,
  `LNAME` varchar(40) DEFAULT NULL,
  `DOB` varchar(40) DEFAULT NULL,
  `USERID` varchar(40) DEFAULT NULL,
  `PWORD` varchar(40) DEFAULT NULL,
  `ACTNO` varchar(40) NOT NULL,
  `GENDER` varchar(40) DEFAULT NULL,
  `BALANCE` decimal(10,0) DEFAULT NULL,
  `addressline1` varchar(150) NOT NULL,
  `addressline2` varchar(150) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `zip` int(10) NOT NULL,
  PRIMARY KEY (`ACTNO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('Pallu','Yaji','1994-03-25','admin','test',' 1700120011','male',1000000000,'2901 S king dr','Apt 1418','chicago','Illinois',60616),('Adi','Yaji','1994-03-25','Adi','yaji','1700120011','MALE',21550,'2901 S king dr','Apt 1418','chicago','Illinois',60616),('Megha','Tatti','1995-04-13','megha','tt','1700120033','FEMALE',30639,'2901 S king dr','Apt 1418','chicago','Illinois',60616),('Rakshith','Amarnath','1995-03-19','Rakshith','megha','1700120043','MALE',199980000,'3001 S King Dr','Apt 0217','Chicago','Illinois',60616);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holdtranscations`
--

DROP TABLE IF EXISTS `holdtranscations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `holdtranscations` (
  `holdtranid` int(11) NOT NULL AUTO_INCREMENT,
  `TRANID` int(11) DEFAULT NULL,
  `fromactno` varchar(40) DEFAULT NULL,
  `toactno` varchar(40) DEFAULT NULL,
  `requesttime` datetime DEFAULT NULL,
  `approvetime` datetime DEFAULT NULL,
  `aprrovedby` varchar(50) DEFAULT NULL,
  `approveStatus` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`holdtranid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holdtranscations`
--

LOCK TABLES `holdtranscations` WRITE;
/*!40000 ALTER TABLE `holdtranscations` DISABLE KEYS */;
/*!40000 ALTER TABLE `holdtranscations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `TRANID` int(11) NOT NULL AUTO_INCREMENT,
  `fromactno` varchar(50) DEFAULT NULL,
  `toactno` varchar(50) NOT NULL,
  `trandate` datetime DEFAULT NULL,
  `TRANDESC` varchar(40) DEFAULT NULL,
  `TRANSTATUS` varchar(40) DEFAULT NULL,
  `REMARK` varchar(40) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  `amountaction` varchar(50) NOT NULL,
  PRIMARY KEY (`TRANID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,'1700120011','1700120033','2018-12-01 16:30:18','Funds Transfer With in the Bank','pass','Funds transferred successfully',24,'debit'),(2,'1700120033','1700120011','2018-12-01 16:30:18','Funds Transfer With in the Bank','pass','Funds transferred successfully',24,'credit'),(3,'1700120011','1700120033','2018-12-01 16:30:57','Funds Transfer With in the Bank','pass','Funds transferred successfully',50,'debit'),(4,'1700120033','1700120011','2018-12-01 16:30:57','Funds Transfer With in the Bank','pass','Funds transferred successfully',50,'credit'),(5,'1700120011','51242356121','2018-12-01 16:31:53','Funds Transfer to other Bank','progressing','Funds transfer in progress',10,'debit'),(6,'1700120043','51556121231','2018-12-01 16:32:47','Funds Transfer to other Bank','pass','This transaction is Approved  by  banker',500,'debit'),(7,'1700120043','1700120011','2018-12-01 16:33:05','Funds Transfer With in the Bank','pass','Funds transferred successfully',5000,'debit'),(8,'1700120011','1700120043','2018-12-01 16:33:05','Funds Transfer With in the Bank','pass','Funds transferred successfully',5000,'credit'),(9,'1700120011','1700120011','2018-12-01 16:36:31','Add Customer Transcation','pass','okay',16000,'credit'),(10,'1700120043','1700120033','2018-12-01 17:14:59','Funds Transfer With in the Bank','pass','Funds transferred successfully',15000,'debit'),(11,'1700120033','1700120043','2018-12-01 17:14:59','Funds Transfer With in the Bank','pass','Funds transferred successfully',15000,'credit'),(12,'1700120043','4521235451','2018-12-01 17:15:42','Funds Transfer to other Bank','pass','This transaction is Approved  by  banker',560,'debit'),(13,'1700120043','5151312131123123','2018-12-01 17:16:00','Funds Transfer to other Bank','fail','transaction is DisApproved  by  banker',1000,'debit');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-01 21:09:06
