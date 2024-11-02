# Nbp-exchange App

Welcome to the Nbp-exchange App! This is a simple Spring Boot application that allows users to exchange US Dollars (USD) to Polish Zloty (PLN) and vice versa. The application is built with Java 17 and uses an H2 in-memory database for quick and easy data management.

## Features

- **Exchange Rates**: Fetch current exchange rates for USD to PLN.
- **Currency Conversion**: Convert amounts between USD and PLN.
- **In-memory Database**: Utilizes H2 for lightweight data storage.
- **RESTful API**: Interact with the application via a RESTful API.

## Prerequisites

- Java 17
- Maven

## Getting Started

### Clone the Repository

git clone https://github.com/pawloda/nbp-exchange.git

### Run the Application
Use Maven to build the application:<br>
mvn clean install

You can run the application using the following command:<br>
mvn spring-boot:run

The application will start on http://localhost:8080.

### API Endpoints
!!! IMPORTANT - use your generated UUID in the requests !!!

GET /api/accounts/$UUID$: Retrieve the account details.<br>
POST /api/accounts: Create a new account.<br>
GET /api/exchange/$UUID$: Convert an amount from USD to PLN or vice versa.<br>

Example Requests<br>
Get Account Details<br>
curl -X GET http://localhost:8080/api/account

Create Account<br>
curl -X POST http://localhost:8080/api/account \
-H "Content-Type: application/json" \
-d '{"name": "John", "surname": "Doe", "pln-balance": 100}'

Exchange Dollars to Pln<br>
curl -X GET http://localhost:8080/api/exchange?currency=dollars

Exchange Pln to Dollars<br>
curl -X GET http://localhost:8080/api/exchange?currency=plns

### Database
This application uses H2 as an in-memory database, which means that data will not persist after the application is stopped. For development purposes, this setup is quick and efficient.

### Testing
To run tests, use the following command:
mvn test

## Acknowledgments
Spring Boot - Framework for building web applications<br>
H2 Database - Lightweight in-memory database<br>
Maven - Build automation tool for Java
