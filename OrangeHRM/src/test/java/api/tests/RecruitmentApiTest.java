package api.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dtos.RecruitDTO;
import io.restassured.response.Response;

public class RecruitmentApiTest extends BaseApiTest {

	private RecruitDTO recruit;
	private SoftAssert sa;

	@BeforeClass
	public void setLocal() {
		sa = new SoftAssert();
		login();
		recruit = initRecruit();
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		sa.assertAll();
	}

	@AfterMethod
	public void tearDown() {
	}

	@Test(priority = -1, description = "Verify that Admin can add new candidate")
	public void TestAPIverifyAddingNewCandidate() {
		Response response = createNewRecruit(recruit);
		sa.assertEquals(response.getStatusCode(), 200);
		sa.assertEquals(getDataFromRecruitJsonResponse(response, "firstName"), recruit.getFirstName());
		sa.assertEquals(getDataFromRecruitJsonResponse(response, "middleName"), recruit.getMiddleName());
		sa.assertEquals(getDataFromRecruitJsonResponse(response, "lastName"), recruit.getLastName());
		sa.assertEquals(getDataFromRecruitJsonResponse(response, "email"), recruit.getEmail());
	}

	@Test(description = "Precondition: TestAPIverifyAddingNewCandidate() executed. Adding attachment is on different endpoint")
	public void TestAPIverifyAddingAttachmentToNewRecruit() {
		Response response = addAttachmentToNewRecruit(recruit.getId());
		sa.assertEquals(response.getStatusCode(), 200);
		sa.assertEquals(getDataFromRecruitJsonResponse(response, "candidateId"), recruit.getId());
	}

	
}
