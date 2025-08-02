# Banking Application Modernization: Java JSP/Servlets to Spring Boot

### <em>Video Walkthrough: </em>https://youtu.be/wd9cKoyIxMw
<a href="https://youtu.be/wd9cKoyIxMw">
<img width="3446" height="1916" alt="banking_modernization_screenshot" src="https://github.com/user-attachments/assets/f004cea6-c020-4482-bdf5-e82e906c747b" />

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> **Incremental modernization of a legacy Java JSP/Servlets banking application to Spring Boot 3.x**

## Project Overview

This project demonstrates enterprise-level legacy system modernization using the Strangler Fig pattern to incrementally migrate a banking application from JSP/Servlets to Spring Boot 3.x. Rather than a risky "big bang" rewrite, this approach maintains business continuity while delivering continuous value through feature-by-feature migration.

### Why This Approach Matters

- **Zero business downtime** during modernization process  
- **Immediate security improvements** addressing legacy servlet vulnerabilities and security gaps
- **Risk mitigation** through feature flags and rollback capabilities
- **Continuous value delivery** rather than waiting months/years for completion

## Architecture & Migration Strategy

### Current Infrastructure

```
┌─────────────────────────────────────────────────────────────┐
│                    AWS EC2 Instance                         │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐    ┌─────────────────────────────────┐ │
│  │   Nginx Proxy   │    │     Migration Controller        │ │
│  │                 │    │   (Feature Flag Routing)        │ │
│  └─────────┬───────┘    └─────────────┬───────────────────┘ │
│            │                          │                     │
│  ┌─────────▼───────┐    ┌─────────────▼───────────────────┐ │
│  │ Legacy System   │    │    Modern System                │ │
│  │ JSP/Servlets    │    │    Spring Boot 3.x              │ │
│  │ Tomcat 10       │    │    Embedded Tomcat              │ │
│  └─────────────────┘    └─────────────────────────────────┘ │
├─────────────────────────────────────────────────────────────┤
│                     Database Layer                          │
│  ┌─────────────────┐              ┌─────────────────┐       │
│  │     MySQL       │     ────►    │   PostgreSQL    │       │
│  │  (Legacy Data)  │              │ (Modern Schema) │       │
│  └─────────────────┘              └─────────────────┘       │
└─────────────────────────────────────────────────────────────┘
```

### Migration Phases

#### Phase 1: Legacy System Analysis 
- **Deployed legacy JSP/Servlets application** to production environment
- **Operational analysis** through real-world usage and monitoring
- **Business logic documentation** through black-box and code analysis
- **Infrastructure challenges** identified and resolved

#### Phase 2: Database Migration 
- **MySQL to PostgreSQL migration** with zero-downtime strategy
- **Schema modernization** while maintaining data compatibility
- **Dual-write validation** to ensure data consistency
- **Performance optimization** for banking transaction loads

#### Phase 3: CI/CD Integration 
- **Jenkins pipeline integration** for automated testing and deployment
- **Feature flag infrastructure** for gradual traffic migration
- **Rollback automation** for risk mitigation
- **Monitoring and alerting** for migration health

#### Phase 4: Feature-by-Feature Migration 
- **Authentication system** (Spring Security + JWT)
- **Account management** (Balance inquiry, transaction history)
- **Fund transfers** (Internal and external bank transfers)
- **Complaint system** (Customer service and resolution tracking)

## Technical Implementation

### Legacy System (Reference Baseline)
- **Framework**: Traditional JSP/Servlets with manual MVC implementation
- **Application Server**: Apache Tomcat 9
- **Database**: MySQL 5.7 with banking transaction schema
- **Architecture**: Servlet-based request handling with JSP views and manual routing

### Modern System (Target Architecture)
- **Framework**: Spring Boot 3.x with Spring Security
- **Database**: PostgreSQL 15 with optimized schema design
- **API Design**: RESTful services with comprehensive validation
- **Security**: JWT authentication, SQL injection prevention, input sanitization
- **Testing**: Comprehensive unit, integration, and migration verification tests

### Key Technologies
```yaml
Backend:
  - Java 17 (LTS)
  - Spring Boot 3.x
  - Spring Security 6
  - Spring Data JPA
  - PostgreSQL 15
  - MongoDB (complaints system)

Frontend:
  - Thymeleaf templates
  - Modern responsive CSS
  - Vanilla JavaScript
  - Bootstrap 5

Infrastructure:
  - AWS EC2
  - Nginx (reverse proxy)
  - Jenkins (CI/CD)
  - Docker (containerization)
  - Feature flags (gradual migration)
```




