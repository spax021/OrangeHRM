package api.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import config.EmployeeFile;
import dtos.employee.EmployeeDTO;
import io.restassured.response.Response;

public class EmployeeApiTest extends BaseApiTest {

	private EmployeeDTO initEmployee;
	private SoftAssert sa;

	@BeforeClass
	public void setLocal() {
		sa = new SoftAssert();
		login();
		initEmployee = initEmployee();
	}

	@BeforeMethod
	public void setUp() {
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		sa.assertAll();
	}

	@Test(priority = -1, description = "Verify that Admin can add new employee")
	public void TestAPIverifyAddingNewEmployee() {
		Response response = createNewEmployee(EmployeeFile.getAPIemployeeId(), initEmployee);
		sa.assertEquals(response.getStatusCode(), 200);
		sa.assertEquals(getDataFromJson(response, "data", "firstName"), initEmployee.getFirstName());
		sa.assertEquals(getDataFromJson(response, "data", "middleName"), initEmployee.getMiddleName());
		sa.assertEquals(getDataFromJson(response, "data", "lastName"), initEmployee.getLastName());
		sa.assertEquals(getDataFromJson(response, "data", "terminationId"), "null");
	}
	
	@Test(priority = 0, description = "Verify that Admin can activate and add username and password to the new employee")
	public void TestAPIverifyActivatingNewEmployee() {
		Response response = activateNewEmployee();
		System.out.println(response.asPrettyString());
		
		
	}
	
	@Test(description = "")
	public void E2E() {
		TestAPIverifyAddingNewEmployee();
		TestAPIverifyActivatingNewEmployee();
	}
	
}
