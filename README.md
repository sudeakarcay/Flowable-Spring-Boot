# Hiring Process Automation (Flowable + Spring Boot)

This project implements an automated hiring workflow using **Flowable BPMN**, **Spring Boot**, **Quartz Scheduler**, and an **H2 in-memory database**.  
It focuses entirely on backend logic and can be fully tested using **Postman**.

---

## Requirements

Before running the project, ensure you have:

- **Java 17**
- **Maven 3.8+**
- *(Optional)* IntelliJ IDEA or any Java IDE
- *(Optional)* Lombok plugin installed in your IDE

Library versions used (from `pom.xml`):

- **Spring Boot:** 3.5.8  
- **Flowable BPMN:** 7.2.0  
- **Lombok:** 1.18.32  
- **Quartz:** Managed by Spring Boot  
- **H2 Database:** Managed by Spring Boot  

---

## Installation & Setup

Follow the steps below to run the project locally.

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/sudeakarcay/Flowable-Spring-Boot.git
cd Flowable-Spring-Boot
```
### 2️⃣ Build the Project
```bash
mvn clean install
```
### 3️⃣ Run the Application
```bash
mvn spring-boot:run
```
## H2 Console URL 

http://localhost:8080/h2-console

