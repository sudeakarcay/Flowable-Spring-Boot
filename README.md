Hiring Process Automation (Flowable + Spring Boot)

This project implements an automated Hiring Workflow using Flowable BPMN, Spring Boot, Quartz Scheduler, and H2 Database.
It focuses on backend logic only and can be fully tested via Postman.

Requirements

Before setting up the project, ensure you have:

Java 17

Maven 3.8+

(Optional) IntelliJ IDEA or any Java IDE

(Optional) Lombok plugin installed in your IDE

Library versions from pom.xml:
Spring Boot	3.5.8
Flowable BPMN	7.2.0
Lombok	1.18.32
Quartz	Managed by Spring Boot
H2 Database	Managed by Spring Boot


Installation & Setup

Follow the steps below to run the project locally:

1️⃣ Clone the Repository
git clone https://github.com/<your-username>/<your-repo>.git
cd <your-repo>

2️⃣ Build the Project

Use Maven to download all dependencies:

mvn clean install

3️⃣ Run the Application

Start the Spring Boot application:

mvn spring-boot:run

The application will start at:

http://localhost:8080

🗄️ Database (H2 In-Memory)

The project uses H2 in-memory database for development and testing.

H2 Console URL:

http://localhost:8080/h2-console


📁 BPMN Workflow

The BPMN process file is automatically deployed on startup:

src/main/resources/processes/hiring-process.bpmn20.xml


Flowable reads this file, creates the process definition, and handles execution.

📡 API Usage

Use Postman or any REST client to interact with the workflow.
