-- PostgreSQL Schema for Banking System
-- Converted from MySQL dump_bk.sql
-- Target Database: banking_system (lowercase for PostgreSQL compatibility)

-- Create database (commented for manual creation)
-- CREATE DATABASE banking_system;
-- \connect banking_system;

-- Drop tables if they exist (for clean slate)
DROP TABLE IF EXISTS holdtranscations CASCADE;
DROP TABLE IF EXISTS transaction CASCADE;
DROP TABLE IF EXISTS complaint CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS banker CASCADE;
DROP TABLE IF EXISTS administrator CASCADE;

-- Administrator table
CREATE TABLE administrator (
    fname VARCHAR(40),
    lname VARCHAR(40),
    userid VARCHAR(40) NOT NULL,
    pword VARCHAR(40),
    PRIMARY KEY (userid)
);

-- Banker table
CREATE TABLE banker (
    fname VARCHAR(40),
    lname VARCHAR(40),
    dob VARCHAR(40),
    userid VARCHAR(40) NOT NULL,
    pword VARCHAR(40),
    gender VARCHAR(40),
    PRIMARY KEY (userid)
);

-- Customer table
CREATE TABLE customer (
    fname VARCHAR(40),
    lname VARCHAR(40),
    dob VARCHAR(40),
    userid VARCHAR(40),
    pword VARCHAR(40),
    actno VARCHAR(40) NOT NULL,
    gender VARCHAR(40),
    balance DECIMAL(10,0),
    addressline1 VARCHAR(150) NOT NULL,
    addressline2 VARCHAR(150) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip INTEGER NOT NULL,
    PRIMARY KEY (actno)
);

-- Complaint table
CREATE TABLE complaint (
    complaint_no INTEGER,
    actno VARCHAR(40),
    complaint_date DATE,
    subject VARCHAR(40),
    description VARCHAR(40),
    status VARCHAR(40),
    closed VARCHAR(40),
    complaintid SERIAL NOT NULL,
    PRIMARY KEY (complaintid)
);

-- Transaction table
CREATE TABLE transaction (
    tranid SERIAL NOT NULL,
    fromactno VARCHAR(50),
    toactno VARCHAR(50) NOT NULL,
    trandate TIMESTAMP,
    trandesc VARCHAR(40),
    transtatus VARCHAR(40),
    remark VARCHAR(40),
    amount INTEGER NOT NULL,
    amountaction VARCHAR(50) NOT NULL,
    PRIMARY KEY (tranid)
);

-- Hold transactions table
CREATE TABLE holdtranscations (
    holdtranid SERIAL NOT NULL,
    tranid INTEGER,
    fromactno VARCHAR(40),
    toactno VARCHAR(40),
    requesttime TIMESTAMP,
    approvetime TIMESTAMP,
    aprrovedby VARCHAR(50),
    approvestatus VARCHAR(20),
    PRIMARY KEY (holdtranid)
);

-- Insert sample data
INSERT INTO administrator VALUES ('Admin','Test','admin','admin');

INSERT INTO banker VALUES ('Banker','Test','09/03/1988','banker','urvi','MALE');

INSERT INTO customer VALUES 
    ('Pallu','Yaji','1994-03-25','admin','test',' 1700120011','male',1000000000,'2901 S king dr','Apt 1418','chicago','Illinois',60616),
    ('Adi','Yaji','1994-03-25','Adi','yaji','1700120011','MALE',21550,'2901 S king dr','Apt 1418','chicago','Illinois',60616),
    ('Megha','Tatti','1995-04-13','megha','tt','1700120033','FEMALE',30639,'2901 S king dr','Apt 1418','chicago','Illinois',60616),
    ('Rakshith','Amarnath','1995-03-19','Rakshith','megha','1700120043','MALE',199980000,'3001 S King Dr','Apt 0217','Chicago','Illinois',60616);

INSERT INTO complaint (complaint_no, actno, complaint_date, subject, description, status, closed, complaintid) VALUES 
    (1013,'199980560','2018-12-01','test','test1','Open','False',4),
    (1011,'199980560','2018-12-01','123456','345324dfsdf','Open','False',5),
    (1012,'199980560','2018-12-01','UI not working','Some issues','Open','False',6);

-- Reset the sequence for complaint table to continue from where we left off
SELECT setval('complaint_complaintid_seq', 6);

INSERT INTO transaction (tranid, fromactno, toactno, trandate, trandesc, transtatus, remark, amount, amountaction) VALUES 
    (1,'1700120011','1700120033','2018-12-01 16:30:18','Funds Transfer With in the Bank','pass','Funds transferred successfully',24,'debit'),
    (2,'1700120033','1700120011','2018-12-01 16:30:18','Funds Transfer With in the Bank','pass','Funds transferred successfully',24,'credit'),
    (3,'1700120011','1700120033','2018-12-01 16:30:57','Funds Transfer With in the Bank','pass','Funds transferred successfully',50,'debit'),
    (4,'1700120033','1700120011','2018-12-01 16:30:57','Funds Transfer With in the Bank','pass','Funds transferred successfully',50,'credit'),
    (5,'1700120011','51242356121','2018-12-01 16:31:53','Funds Transfer to other Bank','progressing','Funds transfer in progress',10,'debit'),
    (6,'1700120043','51556121231','2018-12-01 16:32:47','Funds Transfer to other Bank','pass','This transaction is Approved  by  banker',500,'debit'),
    (7,'1700120043','1700120011','2018-12-01 16:33:05','Funds Transfer With in the Bank','pass','Funds transferred successfully',5000,'debit'),
    (8,'1700120011','1700120043','2018-12-01 16:33:05','Funds Transfer With in the Bank','pass','Funds transferred successfully',5000,'credit'),
    (9,'1700120011','1700120011','2018-12-01 16:36:31','Add Customer Transcation','pass','okay',16000,'credit'),
    (10,'1700120043','1700120033','2018-12-01 17:14:59','Funds Transfer With in the Bank','pass','Funds transferred successfully',15000,'debit'),
    (11,'1700120033','1700120043','2018-12-01 17:14:59','Funds Transfer With in the Bank','pass','Funds transferred successfully',15000,'credit'),
    (12,'1700120043','4521235451','2018-12-01 17:15:42','Funds Transfer to other Bank','pass','This transaction is Approved  by  banker',560,'debit'),
    (13,'1700120043','5151312131123123','2018-12-01 17:16:00','Funds Transfer to other Bank','fail','transaction is DisApproved  by  banker',1000,'debit');

-- Reset the sequence for transaction table
SELECT setval('transaction_tranid_seq', 13);

-- Create indexes for better performance (PostgreSQL best practices)
CREATE INDEX idx_customer_userid ON customer(userid);
CREATE INDEX idx_transaction_fromactno ON transaction(fromactno);
CREATE INDEX idx_transaction_toactno ON transaction(toactno);
CREATE INDEX idx_transaction_trandate ON transaction(trandate);
CREATE INDEX idx_complaint_actno ON complaint(actno);

-- Verify data
SELECT 'Administrator count:' as table_name, count(*) FROM administrator
UNION ALL
SELECT 'Banker count:' as table_name, count(*) FROM banker
UNION ALL
SELECT 'Customer count:' as table_name, count(*) FROM customer
UNION ALL
SELECT 'Complaint count:' as table_name, count(*) FROM complaint
UNION ALL
SELECT 'Transaction count:' as table_name, count(*) FROM transaction;