package apiTests;

import java.util.List;

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
//		setHiringManager();
	}

	@BeforeMethod
	public void setUp() {
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		sa.assertAll();
	}

	@Test(priority = -1, description = "")
	public void TestAPILogout() {
		Response response = logout();
		sa.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 0, description = "Verify that Admin can add new candidate.")
	public void TestAPIverifyAddingNewCandidate() {
		Response response = createNewCandidate(initCandidate);
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPIverifyAddingNewCandidate");
		sa.assertEquals(getDataFromJson(response, "data", "firstName"), initCandidate.getFirstName());
		sa.assertEquals(getDataFromJson(response, "data", "middleName"), initCandidate.getMiddleName());
		sa.assertEquals(getDataFromJson(response, "data", "lastName"), initCandidate.getLastName());
		sa.assertEquals(getDataFromJson(response, "data", "email"), initCandidate.getEmail());
	}

	@Test(priority = 1, description = "Precondition: TestAPIverifyAddingNewCandidate() executed. Adding attachment is on different endpoint.")
	public void TestAPIverifyAddingAttachmentToNewCandidate() {
		Response response = addAttachmentToNewCandidate(Integer.parseInt(CandidateFile.getApiid()));
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPIverifyAddingAttachmentToNewCandidate");
		sa.assertEquals(getDataFromJson(response, "data", "candidateId"), candidateAttachment.getCandidateId());
		sa.assertEquals(getDataFromJson(response, "data", "attachment", "fileName"), candidateAttachment.getAttachment().getFileName());
		sa.assertEquals(getDataFromJson(response, "data", "attachment", "fileType"), "application/pdf");
		sa.assertNotEquals(getDataFromJson(response, "data", "attachment", "fileSize"), "0");
	}

	@Test(priority = 2, description = "Verify existance of a new Candidate.")
	public void TestAPIverifyGetingNewCandidate() {
		Response response = getCandidate(candidate.getId());  //candidate.getId()
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPIverifyGetingNewCandidate");
		sa.assertEquals(getDataFromJson(response, "data", "id"), String.valueOf(candidate.getId()));
		sa.assertEquals(getDataFromJson(response, "data", "firstName"), candidate.getFirstName());
		sa.assertEquals(getDataFromJson(response, "data", "lastName"), candidate.getLastName());
		sa.assertEquals(getDataFromJson(response, "data", "email"), candidate.getEmail());
		sa.assertEquals(getDataFromJson(response, "data", "vacancy", "id"), String.valueOf(initCandidate.getVacancy().getId()));
	}
	
	@Test(priority = 10, description = "Verify that admin can update new candidate.")
	public void TestAPIverifyUpdateingNewCandidate() {
		candidate.setFirstName("Marko");
		candidate.setLastName("Markovic");
		candidate.setEmail("ma.ma@example.com");
		
		Response response = updateCandidate(candidate);
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPIverifyUpdateingNewCandidate");
		sa.assertEquals(getDataFromJson(response, "data", "firstName"), candidate.getFirstName());
		sa.assertEquals(getDataFromJson(response, "data", "lastName"), candidate.getLastName());
		sa.assertEquals(getDataFromJson(response, "data", "email"), candidate.getEmail());
	}
	
	@Test(priority = 3, description = "Verify that admin can shortlist new candidate.")
	public void TestAPIVerifyShortlistNewCandidate() {
		Response response = shortlistNewCandidate(candidate.getId());  //candidate.getId()
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPIVerifyShortlistNewCandidate");
		sa.assertEquals(getDataFromJson(response, "data", "candidate", "id"), String.valueOf(candidate.getId()));
		sa.assertEquals(getDataFromJson(response, "data", "candidate", "firstName"), candidate.getFirstName());
		sa.assertEquals(getDataFromJson(response, "data", "candidate", "lastName"), candidate.getLastName());
		
		sa.assertEquals(getDataFromJson(response, "data", "vacancy", "id"), String.valueOf(candidate.getVacancy().getId()));
		sa.assertEquals(getDataFromJson(response, "data", "vacancy", "hiringManager", "empNumber"), String.valueOf(candidate.getVacancy().getHiringManager().getId()));
		sa.assertEquals(getDataFromJson(response, "data", "vacancy", "hiringManager", "firstName"), candidate.getVacancy().getHiringManager().getFirstName());
		sa.assertEquals(getDataFromJson(response, "data", "vacancy", "hiringManager", "lastName"), candidate.getVacancy().getHiringManager().getLastName());
		
		sa.assertEquals(getDataFromJson(response, "data", "action", "id"), "2");
		sa.assertEquals(getDataFromJson(response, "data", "action", "label"), "Shortlisted");
		
	}
	
	private String interviewId = "";
	
	@Test(priority = 4, description = "Verify that admin can schedule an interview with new candidate.")
	public void TestAPIVerifyScheduleInterviewWithNewcandidate() {
		int[] empIds = {69};
		String title = "QA Lead";
		String date = "2024-07-20";
		String time = "14:00";
		Response response = scheduleInterviewWithNewCandidate(candidate.getId(), title, date, time, empIds);
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPIVerifyScheduleInterviewWithNewcandidate");
		
		sa.assertEquals(getDataFromJson(response, "data", "candidate", "id"), String.valueOf(candidate.getId()));
		sa.assertEquals(getDataFromJson(response, "data", "candidate", "firstName"), candidate.getFirstName());
		sa.assertEquals(getDataFromJson(response, "data", "candidate", "lastName"), candidate.getLastName());
		
		sa.assertEquals(getDataFromJson(response, "data", "vacancy", "id"), String.valueOf(candidate.getVacancy().getId()));
		
		sa.assertEquals(getDataFromJson(response, "data", "interviewDate"), date);
		sa.assertEquals(getDataFromJson(response, "data", "interviewTime"), time);
		
		List<String> interviewIds = getinterviewIdsFromJson(response, "data", "interviewers", "empNumber");
		for(int i = 0; i < interviewIds.size(); i++) {
			sa.assertEquals(interviewIds.get(i), String.valueOf(empIds[i]));
		}
		interviewId = getDataFromJson(response, "data", "id");
	}
	
	@Test(priority = 5, description = "Verify that admin can mark interview as passed for new candidate.")
	public void TestAPImarkAsPassedIntervview() {
		Response response = markInterviewAsPassed(candidate.getId(), interviewId);
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPImarkAsPassedIntervview");
		sa.assertEquals(getDataFromJson(response, "data", "candidate", "id"), String.valueOf(candidate.getId()));
		sa.assertEquals(getDataFromJson(response, "data", "action", "label"), "Interview Passed");
	}
	
	@Test(priority = 6, description = "Verify that admin can offer a job to new candidate.")
	public void TestAPIofferAJobToNewCandidate() {
		Response response = jobOffer(candidate.getId());
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPIofferAJob");
		sa.assertEquals(getDataFromJson(response, "data", "candidate", "id"), String.valueOf(candidate.getId()));
		sa.assertEquals(getDataFromJson(response, "data", "action", "label"), "Job Offered");
	}
	
	@Test(priority = 7, description = "Verify that admin can hire new candidate.")
	public void TestAPIhireNewCandidate() {
		Response response = hireCandidate(candidate.getId());
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPIhireNewCandidate");
		sa.assertEquals(getDataFromJson(response, "data", "candidate", "id"), String.valueOf(candidate.getId()));
		sa.assertEquals(getDataFromJson(response, "data", "action", "label"), "Hired");
	}
	
	@Test(priority = 10, description = "Verify that Admin can delete existing candidate.")
	public void TestAPIverifyDeleteingExistingCandidate() {
		int[] ids = {86};  //candidate.getId()
		Response response = deleteCandidate(ids);
		sa.assertEquals(response.getStatusCode(), 200, "Failed at TestAPIverifyDeleteingExistingCandidate");
		String[] deletedIds = getDataFromJson(response, "data").substring(1, getDataFromJson(response, "data").length() - 1).split(",");
		for(int i = 0; i < deletedIds.length; i++) {
			sa.assertEquals(deletedIds[i], String.valueOf(ids[i]));
		}
	}
	
	
	@Test
	public void mondoGet() {
//		System.out.println(readData("Candidate", 91));
		
		List<CandidateDTO> candidates = readData("Candidate", CandidateDTO.class);
		for (CandidateDTO candidate : candidates) {
			System.out.println(candidate);
		}
		
		
	}
	
	@Test
	public void mondoCreate() {
//		setHiringManager();
		Response response = getCandidate(123);
		System.out.println(response.asPrettyString());
		insertData("Candidate", candidate);
	}
	
	@Test
	public void mondoUpdate() {
		Response response = getCandidate(91);
		System.out.println(response.asPrettyString());
		updateData("Candidate", candidate);
	}
	


	@Test
	public void E2E() {
		setHiringManager();
		TestAPIverifyAddingNewCandidate();
		TestAPIverifyAddingAttachmentToNewCandidate();
		TestAPIverifyGetingNewCandidate();
//		TestAPIverifyUpdateingNewCandidate();
//		TestAPIverifyDeleteingExistingCandidate();
		TestAPIVerifyShortlistNewCandidate();
		TestAPIVerifyScheduleInterviewWithNewcandidate();
		TestAPImarkAsPassedIntervview();
		TestAPIofferAJobToNewCandidate();
		TestAPIhireNewCandidate();
	}
	
	
}
