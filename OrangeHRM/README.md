# OrangeHRM

Self practice automation project: Java/testNG/Selenium/Rest Assured<br>
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
  
  
## BaseTest classes

The `BaseTest.java` and `BaseApiTest.java` classes serves as the foundation for all test classes in the project. It contains common setup, teardown, and utility methods that are used across multiple test classes. This approach helps in reducing code duplication and ensures consistency across the tests.

## Key Responsibilities:

1. **Setup and Teardown Methods**:
<p>   - **@BeforeMethod**: This method runs before each test method. It is responsible for initializing the WebDriver, setting browser properties, deleting cookies, maximizing the browser window, and navigating to the login URL.</p>
<p>   - **@AfterMethod**: This method runs after each test method. It ensures that the WebDriver instance is properly closed and cleaned up after each test to prevent memory leaks and other issues.</p>

2. **Utility Methods**:
<p>   - Common utility methods that are used across multiple test classes. For example, methods to take screenshots, handle common web interactions, or validate conditions.</p>


# Test Cases

##### LoginTest.java

1. **TestLoginInUser**: Verify that a user can log in to the system with valid credentials.
2. **TestLoginInWithWrongCredentials**: Verify that a user cannot log in to the system with invalid credentials.

##### RecruitmentTest.java

1. **TestVerifyAddingNewCandidate**: Verify that an Admin can add a new candidate to the recruitment page.
2. **TestVerifySearchingCandidateByName**: Verify that an Admin can search for a candidate by name.
3. **TestVerifyEditingCandidate**: Verify that an Admin can edit a candidate's details.

##### DashboardTest.java

1. **TestVerifyUserCanCloseAboutPopup**: Verify that a user can close the "About" popup.
2. **TestVerifyAboutSection**: Verify the version information in the "About" section.
3. **TestLogoutUser**: Verify that a user can log out from the system.
   - **Description**: This test navigates to the user dashboard, clicks the logout button, and verifies that the user is logged out successfully.

##### RecruitmentApiTest.java

1. **TestAPIverifyAddingNewCandidate**: Verify that Admin can add a new candidate via API.
2. **TestAPIverifyAddingAttachmentToNewRecruit**: Verify that an attachment can be added to a new recruit via API.