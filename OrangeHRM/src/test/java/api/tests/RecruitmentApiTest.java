package api.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import config.CandidateFile;
import dtos.CandidateDTO;
import io.restassured.response.Response;

public class RecruitmentApiTest extends BaseApiTest {

	private CandidateDTO initCandidate;
	private SoftAssert sa;

	@BeforeClass
	public void setLocal() {
		sa = new SoftAssert();
		login();
		initCandidate = initCandidate();
	}

	@BeforeMethod
	public void setUp() {
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		sa.assertAll();
	}

	@Test(priority = -1, description = "Verify that Admin can add new candidate")
	public void TestAPIverifyAddingNewCandidate() {
		Response response = createNewCandidate(initCandidate);
		sa.assertEquals(response.getStatusCode(), 200);
		sa.assertEquals(getDataFromJson(response, "data", "firstName"), initCandidate.getFirstName());
		sa.assertEquals(getDataFromJson(response, "data", "middleName"), initCandidate.getMiddleName());
		sa.assertEquals(getDataFromJson(response, "data", "lastName"), initCandidate.getLastName());
		sa.assertEquals(getDataFromJson(response, "data", "email"), initCandidate.getEmail());
	}

	@Test(priority = 0, description = "Precondition: TestAPIverifyAddingNewCandidate() executed. Adding attachment is on different endpoint")
	public void TestAPIverifyAddingAttachmentToNewCandidate() {
		Response response = addAttachmentToNewCandidate(Integer.parseInt(CandidateFile.getApiid()));  //initCandidate.getId()
		sa.assertEquals(response.getStatusCode(), 200);
		sa.assertEquals(getDataFromJson(response, "data", "candidateId"), candidateAttachment.getCandidateId());
		sa.assertEquals(getDataFromJson(response, "data", "attachment", "fileName"), candidateAttachment.getAttachment().getFileName());
		sa.assertEquals(getDataFromJson(response, "data", "attachment", "fileType"), "application/pdf");
		sa.assertNotEquals(getDataFromJson(response, "data", "attachment", "fileSize"), "0");
		}

	@Test(description = "Precondition: TestAPIverifyAddingNewCandidate() executed.")
	public void TestAPIverifyUpdateingNewCandidate() {
		Response getResponse = getCandidate(initCandidate.getId());  //initCandidate.getId()
		sa.assertEquals(getResponse.getStatusCode(), 200);
		sa.assertEquals(getDataFromJson(getResponse, "data", "firstName"), initCandidate.getFirstName());
		sa.assertEquals(getDataFromJson(getResponse, "data", "lastName"), initCandidate.getLastName());
		sa.assertEquals(getDataFromJson(getResponse, "data", "email"), initCandidate.getEmail());

		candidate.setFirstName("Marko");
		candidate.setLastName("Markovic");
		candidate.setEmail("ma.ma@example.com");
		
		Response setResponse = updateCandidate(candidate);
		sa.assertEquals(getResponse.getStatusCode(), 200);
		sa.assertEquals(getDataFromJson(setResponse, "data", "firstName"), candidate.getFirstName());
		sa.assertEquals(getDataFromJson(setResponse, "data", "lastName"), candidate.getLastName());
		sa.assertEquals(getDataFromJson(setResponse, "data", "email"), candidate.getEmail());

	}

	@Test
	public void E2E() {
		TestAPIverifyAddingNewCandidate();
		TestAPIverifyAddingAttachmentToNewCandidate();
		TestAPIverifyUpdateingNewCandidate();
	}
	
	
}
