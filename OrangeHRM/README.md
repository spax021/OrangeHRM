# OrangeHRM

Self practice automation project: Java/testNG/Selenium/Rest Assured<br>
https://opensource-demo.orangehrmlive.com/web/index.php/auth/login<br>

WebDriverManager (Bony Garcia) for managing web drivers<br>
TestNG for running tests and taking screen shots when test fail<br>
Allure TBD<br>

Using Hard Assert for UI<br>
Using Soft Assert for API<br>

## Project Structure:

```
OrangeHRM
├── src/main/java
│ ├── config (used for reading values from properties files)
│ ├── dtos (Data Transfer Objects)
│ ├── elements (element locators on a page)
│ ├── pages (pages)
├── src/main/resources
├── src/test/java
│ ├── apiTests (API tests)
│ ├── common (used for common functions ex: screenshot listeners)
│ ├── uitests (UI test)
│ ├── utils (user for util methods ex: CRUD operations)
├── src/test/resources (resources folder)
```
  
## BaseTest classes

The `BaseTest.java` and `BaseApiTest.java` classes serves as the foundation for all test classes in the project. It contains common setup, teardown, and utility methods that are used across multiple test classes. This approach helps in reducing code duplication and ensures consistency across the tests.<br>
**Plan for future: ** `BaseTest.java` and `BaseApiTest.java` will be moved to Base package and additional base classes will be created which will correspond to existing TEST classes.

## Key Responsibilities:

1. **Setup and Teardown Methods**:
<p>   - **@BeforeMethod**: This method runs before each test method. It is responsible for initializing the WebDriver, setting browser properties, deleting cookies, maximizing the browser window, and navigating to the login URL.</p>
<p>   - **@AfterMethod**: This method runs after each test method. It ensures that the WebDriver instance is properly closed and cleaned up after each test to prevent memory leaks and other issues.</p>

2. **Utility Methods**:
<p>   - Common utility methods that are used across multiple test classes. For example, methods to take screenshots, handle common web interactions, or validate conditions.</p>


# UI Test Cases 

##### LoginTest.java

1. **TestLoginUser**: Verify that a user can log in to the system with valid credentials.
2. **TestLoginWithWrongCredentials**: Verify that a user cannot log in to the system with invalid credentials.

##### RecruitmentTest.java

1. **TestVerifyAddingNewCandidate**: Verify that an Admin can add a new candidate to the recruitment page.
2. **TestVerifySearchingCandidateByName**: Verify that an Admin can search for a candidate by name.
3. **TestVerifyEditingCandidate**: Verify that an Admin can edit a candidate's details.

##### DashboardTest.java

1. **TestVerifyUserCanCloseAboutPopup**: Verify that a user can close the "About" popup.
2. **TestVerifyAboutSection**: Verify the version information in the "About" section.
3. **TestLogoutUser**: Verify that a user can log out from the system.
   - **Description**: This test navigates to the user dashboard, clicks the logout button, and verifies that the user is logged out successfully.

# API Test Cases 

##### RecruitmentApiTest.java

1. **TestAPIverifyAddingNewCandidate**: Verify that Admin can add new candidate.
2. **TestAPIverifyAddingAttachmentToNewRecruit**: Adding attachment is on different endpoint.
3. **TestAPIverifyGetingNewCandidate**: Verify existance of a new Candidate.
4. **TestAPIverifyUpdateingNewCandidate**: Verify that admin can update new candidate.
5. **TestAPIVerifyShortlistNewCandidate**: Verify that admin can shortlist new candidate.
6. **TestAPIVerifyScheduleInterviewWithNewcandidate**: Verify that admin can schedule an interview with new candidate.
7. **TestAPImarkAsPassedIntervview**: Verify that admin can mark interview as passed for new candidate.
8. **TestAPIofferAJobToNewCandidate**: Verify that admin can offer a job to new candidate.
9. **TestAPIhireNewCandidate**: Verify that admin can hire new candidate.
10. **TestAPIverifyDeleteingExistingCandidate**: Verify that Admin can delete existing candidate.
11. **E2E**: Create new candidate > go through whole process > Hire


##### EmployeeApiTest.java

1. **TestAPIverifyAddingNewEmployee**: Verify that Admin can add new employee.
2. **TestAPIverifyActivatingNewEmployee**: Verify that Admin can activate new employee.
3. **TestAPIverifyDeleteingExistingEmployee**: Verify that Admin can delete existing employee.
4. **TestCreateAndActivateNewEmployee**: Verify that Admin can create and activate new employee.
