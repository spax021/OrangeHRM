# OrangeHRM

Self practice automation project: Java/testNG/Selenium
https://opensource-demo.orangehrmlive.com/web/index.php/auth/login

## Project Structure:

```
OrangeHRM
├── src/main/java
│ ├── config
│ │ └── PropertiesFile.java
│ ├── dtos
│ │ └── RecruitDTO.java
│ ├── elements
│ │ ├── DashboardPageElements.java
│ │ ├── LoginPageElements.java
│ │ ├── PopupsElements.java
│ │ └── RecruitmentPageElements.java
│ ├── pages
│ │ ├── BasePage.java
│ │ ├── DashboardPage.java
│ │ ├── LoginPage.java
│ │ ├── Popups.java
│ │ └── RecruitmentPage.java
├── src/main/resources
├── src/test/java
│ ├── api.tests
│ │ ├── BaseApiTest.java
│ │ └── RecruitmentApiTest.java
│ ├── uitests
│ │ ├── BaseTest.java
│ │ ├── DashboardTest.java
│ │ ├── LoginTest.java
│ │ └── RecruitmentTest.java
├── src/test/resources
│ └── application.properties
```

Each directory and file has a specific role in the project:

- **src/main/java**: Contains the main source code.
  - **config**: Configuration files and classes.
  - **dtos**: Data Transfer Objects.
  - **elements**: Page elements.
  - **pages**: Page object classes.

- **src/main/resources**: Contains resource files.

- **src/test/java**: Contains test source code.
  - **api.tests**: API test classes.
  - **uitests**: UI test classes.

- **src/test/resources**: Contains test resource files.
  - **application.properties**: Configuration for tests.

Test cases: TBD
