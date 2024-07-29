# Selenium Testing Project

## Project Description and Purpose
This project automates the testing of the demo page available at [SeleniumBase Demo Page](https://seleniumbase.io/demo_page). It uses the Page Object Model (POM) design pattern for maintainability and readability.

## Instructions on How to Set Up and Run the Tests
### Prerequisites
- Java 11 or higher
- Maven
- Chrome browser
- ChromeDriver (matching the Chrome version)

### Setup
1. Clone the repository:
    ```sh
    git clone https://github.com/deveshwaripujari/SeleniumProject.git
    cd SeleniumProject
    ```

2. Install dependencies:
    ```sh
    mvn clean install
    ```

3. Run the tests:
    ```sh
    mvn test
    ```

## Dependencies
- Selenium WebDriver
- JUnit
- WebDriverManager

## Directory Structure
- `src/java/com/example/pages`: Contains Page Object Model classes.
- `src/java/com/example/tests`: Contains test cases.
- `src/test/resources`: Contains test resources - log4j2 configuration.



