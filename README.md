# 3Ci - Java Take Home Interview

Implement a small Spring Boot service to manage orders.

## Entity

- Order
  * productName (String, required)
  * quantity (int, ≥1)
  * unitPrice (double, ≥0)
  * totalPrice (derived: quantity * unitPrice)


## Functional Requirements

- API Functional Requirements
  * Create an order
  * Return a list of all orders
  * Return and order by ID

## Database
The solution must leverage a database.  It is acceptable for the data to only live for the duration of the running application.

## Testing

Write tests which cover your implementation of the functional requirements

## Technical Requirements
- Order Entity:
  * productName (required)
  * quantity (≥1)
  * unitPrice (≥0)
  * totalPrice (calculated)
- Validation: Use Bean Validation annotations
- Error Handling: Proper HTTP status codes and error responses
- Code Quality: Clean, readable, well-structured code


Return appropriate error for invalid input

## Guidelines

- Focus on clarity, correctness and testability
- Timebox:  Aim for ~1.5-3 hours
- You are allowed to use AI



## Instructions
- clone https://github.com/genekim415/java-order-management-api.git
- execute java --version and mvn --version. Java 17 or higher and Maven is required to run application
- mvn clean compile
- mvn test
- navigate to src/main/resources. Copy order-management-api.postman_collection.json and import into Postman.
- run mvn spring-boot:run. When the app is started, test data is created and persisted into an in-memory database.
- execute Postman requests.
- to verify crud operations, open a browser and navigate to: http://localhost:8080/h2-console.
- ensure tha JDBC URL is jdbc:h2:mem:ordersdb