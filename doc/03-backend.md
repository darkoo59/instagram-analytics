
# Backend Documentation
This documentation provides an overview of the backend part of the application, which is developed using Clojure. The backend serves as the backbone of the system, handling data processing, API requests, and database interactions.

## Technology Stack
The backend part of the application utilizes a variety of libraries and tools to achieve its functionality:

- Clojure: A dynamic, functional programming language that runs on the Java Virtual Machine (JVM).
- Ring: A low-level web application library for Clojure.
- Compojure: A routing library for Ring, used to define and manage routes for HTTP requests.
- Migratus: A library for database schema migration in Clojure applications.
- SLF4J Log4j12: A logging framework for the JVM.
- Java JDBC: A Java library for interacting with databases.
- PostgreSQL: A powerful, open-source relational database system.
- Ring-JSON: A library for parsing and generating JSON content in Ring applications.
- Environ: A library for managing environment-specific configuration in Clojure applications.
- Hikari-CP: A high-performance JDBC connection pooling library for Clojure.
- Ring-Defaults: A library providing default middleware configurations for Ring applications.
- Ring-CORS: Middleware for handling Cross-Origin Resource Sharing (CORS) in Ring applications.
- Ring-JWT: Middleware for JSON Web Token (JWT) authentication in Ring applications.
- Buddy-Sign: A library for cryptographic signing in Clojure applications.
- HoneySQL: A SQL DSL (Domain-Specific Language) for Clojure.
- Ring-JWT-Middleware: Middleware for JWT authentication in Ring applications.
- Ring-Session-Timeout: Middleware for managing session timeouts in Ring applications.
- Next JDBC: A Clojure wrapper for JDBC that aims to be simple, powerful, and consistent.
- data.csv: A library for reading and writing CSV files in Clojure.
- Cheshire: A JSON encoding and decoding library for Clojure.
- Midje: A testing framework for Clojure.
- Ring-Mock: A library for mocking HTTP requests and responses in Ring applications.
## Project Structure
The backend part of the application follows a structured organization to maintain code clarity and modularity:

- resources/... Directory for migration files and csv files from instagram
- test/... Directory with tests that are testing backend functionalities
- src/...
  - config/: Contains configuration files for the application.
  - constant/: Houses constant values used throughout the application.
  - controller/: Contains pipeline responsible for handling HTTP requests and returning responses.
  - handler/: Houses handlers that calls service methods and formatting responses.
  - services/: Contains services responsible for business logic and interacting with the database (aldo this part with databased will move to query folder in future updates).
  - spec/: Contains Clojure Spec definitions for data validation.
  - utils/: Utility functions used throughout the application.
  - validations/: Contains validation functions for input data.
  - core.clj: The entry point for the application, responsible for setting up HTTP server and defining routes.
  
## Detailed Comments
  The codebase includes detailed comments for nearly every function, namespace, and variable. This practice enhances code readability and helps developers understand the logic and purpose of each component.

## Getting Started
To set up the backend part of the application locally, follow these steps:

#### Clone the Repository:

  ```sh
git clone https://github.com/your_username/your_repository.git
  ```

#### Navigate to the code directory:

  ```sh
cd instagram-analytics
  ```

#### Install dependencies:
  ```sh
lein deps
  ```
#### Run the Application:

- Ensure you have Java and Clojure installed on your system.
- Start the application using your preferred Clojure development tool or by running the following command:
```sh
lein run
```

#### Access the Backend APIs:
- Once the server starts, access the backend APIs via the defined routes.