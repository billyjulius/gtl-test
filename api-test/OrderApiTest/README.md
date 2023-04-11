# GoTo Logistics Take Home Assignment

This project contains assignment to create automated API test for given instruction in the doc.

---

## How to Run
- Install Java 11+ and Maven
- Import the dependency with command 'mvn dependency:resolve'
- Run test with command 'mvn clean test'

---

## TechStack
- Programming Language Java
- Test Framework TestNG
- API automation library RestAssured
- Reporting Allure

Reason why I choose this combination:
- **Java Programming Language**: Java is a widely-used programming language, with a large community and excellent documentation. 
  It is also known for its stability and portability, making it an excellent choice for building reliable, cross-platform automation scripts. 
- **TestNG Test Framework**: TestNG is a popular test framework for Java, which provides many useful features such as test parallelization, flexible test configuration, and data-driven testing. 
  It also has a wide range of reporting options and integrations with other tools, making it easy to integrate with other parts of the development process.
- **RestAssured API automation library**: RestAssured is a popular Java-based library for API automation testing. 
  It provides an easy-to-use syntax for making HTTP requests, parsing responses, and validating results. 
  It also has many built-in features such as support for multiple authentication mechanisms, handling of cookies, and support for various content types.
- **Allure Reporting**: Allure is an open-source reporting framework that provides rich and interactive test reports. 
  It integrates well with TestNG, making it easy to generate detailed reports with just a few lines of code. It provides a comprehensive set of reports such as charts, graphs, and metrics, making it easy to analyze and share test results with stakeholders.

Another my consideration:
- In terms of test framework, personally I'm leaning to avoid BDD in API test scope. 
  Because it is just creating unnecessary layer for the test (step definitions, gherkin file) which make it more effort to maintain.
- For the reporting itself, for this assignment I'm decided to use Allure as it readable enough. 
  But personally, I preferred that the result is integrated with test management or other reporting tools that can save historical data and can make some visualization from it.
  
---

## Design Pattern

To be honest, I'm not confident enough with my knowledge about various pattern used in automation testing.
But I'll try to explain why I'm build this automation like this:

### 1. Singleton pattern in RestAssured 

The Singleton pattern is used to ensure that a class can only be instantiated once, and it provides a global point of access to that instance. 
In the context of RestAssured, the RestAssuredClient singleton ensures that there is only one instance of the RestAssured client throughout the test execution. 
This provides benefits such as:
 - Consistency: Since there is only one RestAssured client instance, all the test cases will use the same configuration and settings. This ensures consistency in the test results and makes it easier to troubleshoot issues.
 - Resource efficiency: Creating a new RestAssured client for each test case can be resource-intensive and time-consuming. By using a singleton pattern, we can reuse the same RestAssured client instance, reducing the overhead of creating a new instance for each test case.
 - Encapsulation: Using a singleton pattern encapsulates the RestAssured client instantiation and configuration logic in a single location. This makes it easier to maintain and modify the client configuration without affecting the test cases.

Overall, using the Singleton pattern for RestAssured client instantiation and configuration provides a more efficient, consistent, and maintainable approach to API automation testing.

### 2. Configuration Constants

Placing the API host or base URL in an environment variable provides a level of flexibility and maintainability to the API automation project.
By storing the API host or base URL in an environment variable, you can easily switch between different environments (e.g., development, staging, production) without modifying the code. 
This is particularly useful when you have multiple environments with different URLs but the same API endpoints.

The other things, is the centralized constants.
By placing the constants (for example the endpoint path `/processOrder`) to a single interface class, 
it ensures that the endpoint used across different test methods is consistent and centralized. 
This means that if there are changes in the endpoint, it only needs to be updated in one place, rather than having to update it in every test method that uses it.
This approach helps in reducing redundancy and minimizing the effort required for maintenance and updates.

### 3. Request body JSON in file

Placing the body request JSON to a file has several benefits:
- Separation of Concerns: Separating the request payload from the test code helps to keep the test code clean and concise. It also separates the concerns of writing test code and creating the request payload.
- Reusability: When the request payload is stored in a file, it can be reused in multiple test cases. This reduces the amount of duplicate code and ensures consistency across different tests that use the same payload.
- Readability: A JSON file is a structured data format that is easily readable and understandable. It makes it easier for developers and other stakeholders to understand the request payload and verify that it conforms to the API specification.
- Maintainability: Storing the request payload in a separate file simplifies the process of updating or modifying the request payload. Any changes to the payload can be made in the file, and the changes will be automatically reflected in all the test cases that use the file.

Overall, storing the request payload in a separate JSON file makes the test code more modular, reusable, and maintainable.

### 4. Assertion approach

The approach of checking the response structure with a JSON schema and then verifying the values based on business logic.

Firstly, checking the response structure against a JSON schema ensures that the response matches the expected format and structure. 
JSON schema defines the structure of the JSON document, including the required and optional properties, data types, and allowed values. 
By validating the response against a schema, we can catch any discrepancies between the actual response and the expected response.

Secondly, checking the values based on business logic ensures that the API is functioning correctly from a business perspective. 
This involves verifying that the data returned by the API meets the requirements of the business rules and logic. 
For example, if the API is supposed to return  updated order status, we mush check that the returned orders are in the correct status. 
This ensures that the API is providing the expected value to the users and meeting the business requirements.

By combining these two approaches, we can ensure that the API is working correctly both structurally and functionally. 
We can catch any issues related to the response structure and also verify that the response meets the business requirements. 
This approach ensures the quality of the API and helps to prevent issues in production.

---



  