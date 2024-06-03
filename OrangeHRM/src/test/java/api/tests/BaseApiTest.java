package api.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import config.CandidateFile;
import config.EmployeeFile;
import config.PropertiesFile;
import dtos.CandidateAttachmentDTO;
import dtos.CandidateDTO;
import dtos.VacancyDTO;
import dtos.employee.EmployeeDTO;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Utility;

public class BaseApiTest extends Utility {

	private static String token;
	
	private static Cookies cookies;
	protected static CandidateDTO candidate;
	protected static CandidateAttachmentDTO candidateAttachment;

	private static RequestSpecification request;
	
	public BaseApiTest() {
		this.token = "";
	}

	private static void startProcess() {
		RestAssured.baseURI = "https://opensource-demo.orangehrmlive.com/";
		request = RestAssured
				.given()
				.header("Content-Type", "application/json");		
	}

	private static Response acquireToken() {
		startProcess();
		Response response = given().get();
		cookies = response.getDetailedCookies();
		token = extractTokenFromHtml(response.asPrettyString());
		if(response.getStatusCode() != 200) {
			System.out.println("Failed to acquire token");
		}
		return response;
	}
	
	protected static Response login() {
		acquireToken();
		Response response = given()
							.cookies(cookies)
							.formParam("_token", token)
							.formParam("username", PropertiesFile.getUsername())
							.formParam("password", PropertiesFile.getPassword())
							.post("/web/index.php/auth/validate");
		
		cookies = response.getDetailedCookies();	//new cookie is saved
		if(response.getStatusCode() != 302) {
			System.out.println("Failed to login");
		}
		return response;
	}
	/**
	 * Due to frequent changes in localization settings on the site by other users, 
	 * this method reliably sets the date format to the one required for the existing tests. 
	 * Otherwise, the TestVerifyAddingNewCandidate test would fail in most cases due to date format issues.
	 */
	public static Response setLocalisation() {
		login();
		String location = "/web/index.php/api/v2/admin/localization";
		String payload = "{ \"language\": \"en_US\", \"dateFormat\": \"d-m-Y\" }";
		Response response = put(location, payload);
		System.out.println("Localisation is executed");
		return response;
	}
	
	public static Response getCandidate(int id) {
		String location = "/web/index.php/api/v2/recruitment/candidates/" + id;
		Response response = get(location);
		candidate = populateDTO(response, CandidateDTO.class);
		return response;
	}
		
	public static Response createNewCandidate(CandidateDTO cand) {
		String location = "/web/index.php/api/v2/recruitment/candidates";
		String payload = "{\r\n"
				+ "    \"firstName\": \"" + cand.getFirstName() + "\",\r\n"
				+ "    \"middleName\": \"" + cand.getMiddleName() + "\",\r\n"
				+ "    \"lastName\": \"" + cand.getLastName() + "\",\r\n"
				+ "    \"vacancyId\": " + cand.getVacancy().getId() + ",\r\n"
				+ "    \"email\": \"" + cand.getEmail() + "\",\r\n"
				+ "    \"contactNumber\": \"" + cand.getContactNumber() + "\",\r\n"
				+ "    \"keywords\": \"" + cand.getKeywords() + "\",\r\n"
				+ "    \"dateOfApplication\": \"" + cand.getDateOfApplication() + "\",\r\n"
				+ "    \"comment\": \"" + cand.getComment() + "\",\r\n"
				+ "    \"consentToKeepData\": " + cand.isConsentToKeepData() + "\r\n"
				+ "}";
		Response response = post(location, payload);
		cand.setId(Integer.parseInt(getDataFromJson(response, "data", "id")));
		CandidateFile.setApiId(getDataFromJson(response, "data", "id"));
		return response;
	}
	
	public static Response updateCandidate(CandidateDTO cand) {
		String location = "/web/index.php/api/v2/recruitment/candidates/" + cand.getId();
		String payload = "{\r\n"
				+ "    \"firstName\": \"" + cand.getFirstName() + "\",\r\n"
				+ "    \"middleName\": \"" + cand.getMiddleName() + "\",\r\n"
				+ "    \"lastName\": \"" + cand.getLastName() + "\",\r\n"
				+ "    \"vacancyId\": " + cand.getVacancy().getId() + ",\r\n"
				+ "    \"email\": \"" + cand.getEmail() + "\",\r\n"
				+ "    \"contactNumber\": \"" + cand.getContactNumber() + "\",\r\n"
				+ "    \"keywords\": \"" + cand.getKeywords() + "\",\r\n"
				+ "    \"dateOfApplication\": \"" + cand.getDateOfApplication() + "\",\r\n"
				+ "    \"comment\": \"" + cand.getComment() + "\",\r\n"
				+ "    \"consentToKeepData\": " + cand.isConsentToKeepData() + "\r\n"
				+ "}";
		Response response = put(location, payload);
		return response;
	}
	
	protected static Response addAttachmentToNewCandidate(int id) {
		String location = "/web/index.php/api/v2/recruitment/candidate/attachments";
		HashMap<String, Object> fileInfo = encodeFile("pdf");
		String payload = "{\r\n"
				+ "    \"candidateId\": " + id + ",\r\n"
				+ "    \"attachment\": {\r\n"
				+ "        \"name\": \"" + fileInfo.get("fileName") + "\",\r\n"
				+ "        \"type\": \"" + fileInfo.get("fileType") + "\",\r\n"
				+ "        \"size\": " + fileInfo.get("fileSize") + ",\r\n"
				+ "        \"base64\": \"" + fileInfo.get("base64String") + "\"\r\n"
				+ "    }\r\n"
				+ "}";
		Response response = post(location, payload);
		candidateAttachment = populateDTO(response, CandidateAttachmentDTO.class);
		return response;
	}
	
	private static String extractTokenFromHtml(String response) {
		Document doc = Jsoup.parse(response);
		Element token = doc.selectFirst("auth-login");
		return token.attr("token"); //.replace("\"", "");
	}

	protected static CandidateDTO initCandidate() {
		return new CandidateDTO(CandidateFile.getApiFirstname(), 
				CandidateFile.getApiMiddlename(), 
				CandidateFile.getApiLastname(), 
				CandidateFile.getApiEmail(), 
				CandidateFile.getApiContactNumber(), 
				CandidateFile.getApiNotes(), 
				CandidateFile.getApiKeywords(), 
				CandidateFile.getApiDateOfApplication(), 
				new VacancyDTO(Integer.parseInt(CandidateFile.getApiVacancyId())),
				true);
	}
	protected static EmployeeDTO initEmployee() {
		return new EmployeeDTO(
				EmployeeFile.getAPIempNumber(),
				EmployeeFile.getAPIfirstname(),
				EmployeeFile.getAPImiddlename(),
				EmployeeFile.getAPIlastnam());
	}

	private static String checkEmployeeId(String employeeId) {
		String location = "/web/index.php/api/v2/core/validation/unique?value=" + employeeId + "&entityName=Employee&attributeName=employeeId";
		Response response = get(location);
		return getDataFromJson(response, "data", "valid");
	}

	public static Response createNewEmployee(String employeeID, EmployeeDTO employee) {
		String location = "/web/index.php/api/v2/pim/employees";
		String isValidId = checkEmployeeId(employeeID);

		if(!isValidId.equals("true")) {
			createNewEmployee(incrementEmployeeId(employeeID), employee);
		}
		
		HashMap<String, Object> fileInfo = encodeFile("image");
		String payload = "{\r\n"
				+ "    \"empPicture\": {\r\n"
				+ "        \"base64\": \"" + fileInfo.get("base64String") + "\",\r\n"
				+ "        \"name\": \"" + fileInfo.get("fileName") + "\",\r\n"
				+ "        \"size\": " + fileInfo.get("fileSize") + ",\r\n"
				+ "        \"type\": \"" + fileInfo.get("fileType") + "\"\r\n"
				+ "    },\r\n"
				+ "    \"employeeId\": \"" + employeeID + "\",\r\n"
				+ "    \"firstName\": \"" + employee.getFirstName() + "\",\r\n"
				+ "    \"lastName\": \"" + employee.getLastName() + "\",\r\n"
				+ "    \"middleName\": \"" + employee.getMiddleName() + "\"\r\n"
				+ "}";
		Response response = post(location, payload);
		EmployeeFile.setAPIempNumber(getDataFromJson(response, "data", "empNumber"));
		employee.setEmpNumber(getDataFromJson(response, "data", "empNumber"));
		return response;
	}

	public static Response activateNewEmployee() {
		String location = "/web/index.php/api/v2/admin/users";
		String payload = "{\r\n"
				+ "    \"empNumber\": " + EmployeeFile.getAPIempNumber() + ",\r\n"
				+ "    \"password\": \"" + EmployeeFile.getAPIpassword() + "\",\r\n"
				+ "    \"status\": " + EmployeeFile.getAPIstatus() + ",\r\n"
				+ "    \"userRoleId\": " + EmployeeFile.getAPIuserRoleId() + ",\r\n"
				+ "    \"username\": \"" + EmployeeFile.getAPIusername() + "\"\r\n"
				+ "}";
		Response response = post(location, payload);
		EmployeeFile.setAPIusername(incrementEmployeeUsername(getDataFromJson(response, "data", "userName")));
		return response;
	}
	
	private static Response get(String location) {
		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.get(location);
		return response;
	}	
	
	private static Response put(String location, String payload) {
		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.put(location);
		return response;
	}	

	private static Response post(String location, String payload) {
		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.post(location);
		return response;
	}
}
