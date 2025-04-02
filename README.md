# json_validation_peeking_pi_demo


**Requirements for JSON Validator**

    The program should take two JSON inputs:
    
    Schema (defines the expected structure and constraints)
    
    Data (the JSON object to validate)
    
    Implement a custom validation function that checks if the JSON object conforms to the schema
    
    If the JSON object is valid, return "Valid JSON"; otherwise, return a detailed error message
    
    Bonus features - 
    Support nested objects and arrays
    Implement custom validation rules (e.g., regex patterns for strings)

**Implementation - **
    Developed a Spring Boot REST API for schema-based JSON validation without external libraries.
    
    Implemented nested object handling, regex validation, and type checks (string, integer, boolean, array, object).
    
    Ensured detailed error reporting and optimized performance for real-time validation.
    
    Tested API using Postman for reliability.
    
    Tech Stack: Java, Spring Boot, Lombok, Postman

Tested Request body - 

{
  "schema":{
  "type": "object",
  "properties": {
    "name": { "type": "string", "pattern": "^[A-Z][a-z]+$" },
    "age": { "type": "integer", "minimum": 18, "maximum": 60 },
    "email": { "type": "string", "pattern": "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$" },
    "isEmployed": { "type": "boolean" },
    "skills": {
      "type": "array",
      "items": { "type": "string" }
    },
    "address": {
      "type": "object",
      "properties": {
        "street": { "type": "string" },
        "zip": { "type": "integer", "minimum": 10000, "maximum": 99999 }
      },
      "required": ["street", "zip"]
    }
  },
  "required": ["name", "age", "email", "address"]
},
  "data": {
  "name": "Sanjana",
  "age": 25,
  "email": "sanjana@example.com",
  "isEmployed": true,
  "skills": ["Java", "Spring Boot"],
  "address": {
    "street": "123 Main St",
    "zip": 12345

  }
}
}


Response - 
Valid JSON


ss  - 
![image](https://github.com/user-attachments/assets/e87fe697-5a9f-4639-bce9-213a11bcb3fe)



Invalid request - 
{
  "schema":{
  "type": "object",
  "properties": {
    "name": { "type": "string", "pattern": "^[A-Z][a-z]+$" },
    "age": { "type": "integer", "minimum": 18, "maximum": 60 },
    "email": { "type": "string", "pattern": "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$" },
    "isEmployed": { "type": "boolean" },
    "skills": {
      "type": "array",
      "items": { "type": "string" }
    },
    "address": {
      "type": "object",
      "properties": {
        "street": { "type": "string" },
        "zip": { "type": "integer", "minimum": 10000, "maximum": 99999 }
      },
      "required": ["street", "zip"]
    }
  },
  "required": ["name", "age", "email", "address"]
},
  "data": {
  "name": "Sanjana",
  "age": 16,
  "email": "sanjana.com",
  "isEmployed": "yes",
  "skills": "Java",
  "address": {
    "zip": 999
  }
}
}


Response - 
Error: data.age should be >= 18, data.email does not match pattern email^[^@\s]+@[^@\s]+\.[^@\s]+$, data.isEmployed should be a boolean, data.skills should be an array, Missing required field: data.street, data.zip should be >= 10000


ss - 
![image](https://github.com/user-attachments/assets/f376ac21-d2c1-46d1-9600-167f06e9ba97)

**Requirements for Pi Search**
    input - numeric string.
    
    determine whether this string appears in the first 1 million and 1 billion digits of Pi.
    
    If found, return the starting index (0-based) where the sequence appears in Pi.
    
    If not found, return -1.

  **Implementation - **
    Developed a REST API to search for numeric sequences in a precomputed Pi file (1M+ digits) using efficient stream-based search.
    
    Implemented Knuth-Morris-Pratt (KMP) algorithm for optimized time complexity (O(n + m)).
    
    Utilized BufferedReader (1MB chunks) for low memory usage and efficient file handling from static/ resources.
    
    Ensured scalability and maintainability by following MVC architecture (Controller-Service separation).
    Tech Stack: Java, Spring Boot, Lombok, Postman


Response and ss - 
![image](https://github.com/user-attachments/assets/c7bc26ff-9c60-482a-bbef-927d0aeec491)


