package api.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import config.CandidateFile;
import dtos.RecruitDTO;
import io.restassured.response.Response;

public class RecruitmentApiTest extends BaseApiTest {

	private RecruitDTO initRecruit;
	private SoftAssert sa;

	@BeforeClass
	public void setLocal() {
		sa = new SoftAssert();
		login();
		initRecruit = initRecruit();
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
		Response response = createNewRecruit(initRecruit);
		sa.assertEquals(response.getStatusCode(), 200);
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(response, "firstName"), initRecruit.getFirstName());
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(response, "middleName"), initRecruit.getMiddleName());
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(response, "lastName"), initRecruit.getLastName());
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(response, "email"), initRecruit.getEmail());
	}

	@Test(priority = 0, description = "Precondition: TestAPIverifyAddingNewCandidate() executed. Adding attachment is on different endpoint")
	public void TestAPIverifyAddingAttachmentToNewRecruit() {
		Response response = addAttachmentToNewRecruit(Integer.parseInt(CandidateFile.getApiid()));  //initRecruit.getId()
		sa.assertEquals(response.getStatusCode(), 200);
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(response, "candidateId"), recrAttachment.getCandidateId());
		sa.assertEquals(getSpecificDataFromRecruitAttachmentJsonResponse(response, "fileName"), recrAttachment.getAttachment().getFileName());
	}

	@Test(description = "Precondition: TestAPIverifyAddingNewCandidate() executed.")
	public void TestAPIverifyUpdateingNewCandidate() {
		Response getResponse = getCandidate(initRecruit.getId());  //initRecruit.getId()
		sa.assertEquals(getResponse.getStatusCode(), 200);
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(getResponse, "firstName"), initRecruit.getFirstName());
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(getResponse, "lastName"), initRecruit.getLastName());
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(getResponse, "email"), initRecruit.getEmail());

		recruit.setFirstName("Marko");
		recruit.setLastName("Markovic");
		recruit.setEmail("ma.ma@example.com");
		
		Response setResponse = updateRecruit(recruit);
		sa.assertEquals(getResponse.getStatusCode(), 200);
		System.out.println(setResponse.asPrettyString());
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(setResponse, "firstName"), recruit.getFirstName());
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(setResponse, "lastName"), recruit.getLastName());
		sa.assertEquals(getSpecificDataFromRecruitJsonResponse(setResponse, "email"), recruit.getEmail());

	}

	
	
	
}
