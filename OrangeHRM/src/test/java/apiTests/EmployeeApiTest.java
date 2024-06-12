package apiTests;

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
	
	@Test(priority = 0, description = "Precodndition: TestAPIverifyAddingNewEmployee() / Verify that Admin can activate and add username and password to the new employee")
	public void TestAPIverifyActivatingNewEmployee() {
		Response response = activateNewEmployee(employee.getEmpNumber());
		sa.assertEquals(response.getStatusCode(), 200);
		sa.assertEquals(getDataFromJson(response, "data", "status"), "true");
		sa.assertEquals(getDataFromJson(response, "data", "employee", "empNumber"), String.valueOf(employee.getEmpNumber()));
		sa.assertEquals(getDataFromJson(response, "data", "employee", "employeeId"), employee.getEmployeeId());
		sa.assertEquals(getDataFromJson(response, "data", "employee", "firstName"), employee.getFirstName());
		sa.assertEquals(getDataFromJson(response, "data", "employee", "lastName"), employee.getLastName());
		sa.assertEquals(getDataFromJson(response, "data", "userRole", "id"), String.valueOf(userRole.getId()));
		sa.assertEquals(getDataFromJson(response, "data", "userRole", "name"), userRole.getName());
	}
	
	
	
//	@Test(priority = 10, description = "Precodndition: TestAPIverifyAddingNewEmployee() / Verify that Admin can delete existing employee")
	public void TestAPIverifyDeleteingExistingEmployee() {
		int[] ids = {employee.getEmpNumber()};
		Response response = deleteEmployee(ids);
		sa.assertEquals(response.getStatusCode(), 200);
		String[] deletedIds = getDataFromJson(response, "data").substring(1, getDataFromJson(response, "data").length() - 1).split(",");
		for(int i = 0; i < deletedIds.length; i++) {
			sa.assertEquals(deletedIds[i], String.valueOf(ids[i]));
		}
	}
	
	@Test(description = "")
	public void TestCreateAndActivateNewEmployee() {
		TestAPIverifyAddingNewEmployee();
		TestAPIverifyActivatingNewEmployee();
	}
	
	@Test(description = "")
	public void E2E() {
		TestAPIverifyAddingNewEmployee();
		TestAPIverifyActivatingNewEmployee();
		TestAPIverifyDeleteingExistingEmployee();
	}
	
}
