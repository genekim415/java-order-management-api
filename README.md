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