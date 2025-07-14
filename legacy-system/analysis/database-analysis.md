# Database Schema Analysis

## Overview
Legacy banking system uses MySQL database with tables for users, accounts, transactions, and complaints.

## Core Tables

### users
- Stores customer, banker, and admin login credentials
- Contains user roles and authentication info
- Links to accounts via user_id

### accounts
- Customer account information
- Account numbers, balances, account types
- Links to users table

### transactions
- Transaction history for all accounts
- Includes transfers, deposits, withdrawals
- Transaction date, amount, type, status

### complaints
- Customer complaint tracking
- Stored in MongoDB (separate from MySQL)
- Complaint ID, user ID, description, status, date

## Key Relationships
- users (1) -> (N) accounts
- accounts (1) -> (N) transactions
- users (1) -> (N) complaints

## Security Considerations
- Passwords appear to be stored in plain text or weak encryption
- No apparent audit logging table
- Direct SQL queries in servlets (SQL injection risk)

## Migration Notes
- Need to migrate to PostgreSQL with proper constraints
- Implement proper password hashing (BCrypt)
- Add audit logging for compliance
- Consider partitioning transactions table for performance
