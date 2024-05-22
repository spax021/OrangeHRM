package api.tests;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.CandidateFile;
import config.PropertiesFile;
import dtos.RecruitAttachmentDTO;
import dtos.RecruitDTO;
import dtos.StatusDTO;
import dtos.VacancyDTO;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseApiTest {

	private static String token;
	
	private static String base64String = "";
	private static long fileSize = 0;
	
	private static Cookies cookies;
	protected static RecruitDTO recruit;
	protected static RecruitAttachmentDTO recrAttachment;

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
		String payload = "{ \"language\": \"en_US\", \"dateFormat\": \"d-m-Y\" }";
		
		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.put("/web/index.php/api/v2/admin/localization");
		
		if(response.getStatusCode() != 200) {
			System.out.println("Failed to set localisation");
			System.out.println(response.asPrettyString());
		}
		System.out.println("Localisation is executed");
		return response;
	}
	
	public static Response getCandidate(int id) {
		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.get("/web/index.php/api/v2/recruitment/candidates/" + id);
		
		if(response.getStatusCode() != 200) {
			System.out.println("Failed to fetch candidate");
			System.out.println(response.asPrettyString());
		}
		
		getAllDataFromRecruitJsonResponse(response);
		return response;
	}
		
	public static Response createNewRecruit(RecruitDTO recr) {
		String payload = "{\r\n"
				+ "    \"firstName\": \"" + recr.getFirstName() + "\",\r\n"
				+ "    \"middleName\": \"" + recr.getMiddleName() + "\",\r\n"
				+ "    \"lastName\": \"" + recr.getLastName() + "\",\r\n"
				+ "    \"vacancyId\": " + recr.getVacancy().getId() + ",\r\n"
				+ "    \"email\": \"" + recr.getEmail() + "\",\r\n"
				+ "    \"contactNumber\": \"" + recr.getContactNumber() + "\",\r\n"
				+ "    \"keywords\": \"" + recr.getKeywords() + "\",\r\n"
				+ "    \"dateOfApplication\": \"" + recr.getDateOfApplication() + "\",\r\n"
				+ "    \"comment\": \"" + recr.getComment() + "\",\r\n"
				+ "    \"consentToKeepData\": " + recr.isConsentToKeepData() + "\r\n"
				+ "}";
		
		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.post("/web/index.php/api/v2/recruitment/candidates");
		
		if(response.getStatusCode() != 200) {
			System.out.println("Failed to create new candidate");
			System.out.println(response.asPrettyString());
		}
		
		recr.setId(Integer.parseInt(getSpecificDataFromRecruitJsonResponse(response, "id")));
		CandidateFile.setApiId(getSpecificDataFromRecruitJsonResponse(response, "id"));
		return response;
	}
	
	public static Response updateRecruit(RecruitDTO recr) {
		String payload = "{\r\n"
				+ "    \"firstName\": \"" + recr.getFirstName() + "\",\r\n"
				+ "    \"middleName\": \"" + recr.getMiddleName() + "\",\r\n"
				+ "    \"lastName\": \"" + recr.getLastName() + "\",\r\n"
				+ "    \"vacancyId\": " + recr.getVacancy().getId() + ",\r\n"
				+ "    \"email\": \"" + recr.getEmail() + "\",\r\n"
				+ "    \"contactNumber\": \"" + recr.getContactNumber() + "\",\r\n"
				+ "    \"keywords\": \"" + recr.getKeywords() + "\",\r\n"
				+ "    \"dateOfApplication\": \"" + recr.getDateOfApplication() + "\",\r\n"
				+ "    \"comment\": \"" + recr.getComment() + "\",\r\n"
				+ "    \"consentToKeepData\": " + recr.isConsentToKeepData() + "\r\n"
				+ "}";
		
		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.put("/web/index.php/api/v2/recruitment/candidates/" + recr.getId());
		
		if(response.getStatusCode() != 200) {
			System.out.println("Failed to update recruit");
			System.out.println(response.asPrettyString());
		}
		
		return response;
	}
	
	protected static Response addAttachmentToNewRecruit(int id) {
		encodePdfFile();
		String payload = "{\r\n"
				+ "    \"candidateId\": " + id + ",\r\n"
				+ "    \"attachment\": {\r\n"
				+ "        \"name\": \"mock-cv.pdf\",\r\n"
				+ "        \"type\": \"application/pdf\",\r\n"
				+ "        \"size\": " + fileSize + ",\r\n"
				+ "        \"base64\": \"" + base64String + "\"\r\n"
				+ "    }\r\n"
				+ "}";

		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.post("/web/index.php/api/v2/recruitment/candidate/attachments");

		if(response.getStatusCode() != 200) {
			System.out.println("Failed to attach resume");
			System.out.println(response.asPrettyString());
		}
		getAllDataFromRecruitAttachmentJsonResponse(response);
		return response;
	}
	
	private static void encodePdfFile() {
        try {
            File pdfFile = new File(CandidateFile.getApiResume());
            byte[] fileContent = FileUtils.readFileToByteArray(pdfFile);

            base64String = Base64.getEncoder().encodeToString(fileContent);
            fileSize = pdfFile.length();
           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	protected static String getSpecificDataFromRecruitJsonResponse(Response response, String value) {
		ObjectMapper obj = new ObjectMapper();
		String extractedValue = "";
		try {
			JsonNode data = obj.readTree(response.asPrettyString());
			JsonNode dataNode = data.get("data");
			extractedValue = dataNode.get(value).asText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return extractedValue;
	}

	protected static void getAllDataFromRecruitJsonResponse(Response response) {
		ObjectMapper obj = new ObjectMapper();
		try {
			JsonNode data = obj.readTree(response.asPrettyString());
			JsonNode dataNode = data.get("data");
			recruit = obj.treeToValue(dataNode, RecruitDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static String getSpecificDataFromRecruitAttachmentJsonResponse(Response response, String value) {
		ObjectMapper obj = new ObjectMapper();
		String extractedValue = "";
		try {
			JsonNode data = obj.readTree(response.asPrettyString());
			JsonNode dataNode = data.get("data");
			JsonNode attachmentNode = dataNode.get("attachment");
			extractedValue = attachmentNode.get(value).asText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return extractedValue;
	}

	protected static void getAllDataFromRecruitAttachmentJsonResponse(Response response) {
		ObjectMapper obj = new ObjectMapper();
		try {
			JsonNode data = obj.readTree(response.asPrettyString());
			JsonNode dataNode = data.get("data");
			recrAttachment = obj.treeToValue(dataNode, RecruitAttachmentDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String extractTokenFromHtml(String response) {
		Document doc = Jsoup.parse(response);
		Element token = doc.selectFirst("auth-login");
		return token.attr("token"); //.replace("\"", "");
	}

	protected static RecruitDTO initRecruit() {
		return new RecruitDTO(CandidateFile.getApiFirstname(), 
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
}
