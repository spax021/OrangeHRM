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

import config.PropertiesFile;
import dtos.RecruitDTO;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseApiTest {

	private static String token;
	private static String username = PropertiesFile.getUsername();
	private static String password = PropertiesFile.getPassword();
	
	private static String base64String = "";
	private static long fileSize = 0;
	
	private static Cookies cookies;
	private static RecruitDTO recruit;

	private static RequestSpecification request;

	public BaseApiTest() {
		this.token = "";
		this.username = PropertiesFile.getUsername();
		this.password = PropertiesFile.getPassword();
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
							.formParam("username", username)
							.formParam("password", password)
							.post("/web/index.php/auth/validate");
		
		cookies = response.getDetailedCookies();	//new cookie is saved
		
		if(response.getStatusCode() != 302) {
			System.out.println("Failed to login");
		}
		return response;
	}
	
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
		
		return response;
	}
	
	public static Response createNewRecruit(RecruitDTO recruit) {
		String payload = "{\r\n"
				+ "    \"firstName\": \"" + recruit.getFirstName() + "\",\r\n"
				+ "    \"middleName\": \"" + recruit.getMiddleName() + "\",\r\n"
				+ "    \"lastName\": \"" + recruit.getLastName() + "\",\r\n"
				+ "    \"vacancyId\": " + recruit.getVacancyId() + ",\r\n"
				+ "    \"email\": \"" + recruit.getEmail() + "\",\r\n"
				+ "    \"contactNumber\": \"" + recruit.getContactNumber() + "\",\r\n"
				+ "    \"keywords\": \"" + recruit.getKeywords() + "\",\r\n"
				+ "    \"dateOfApplication\": \"" + recruit.getDateOfApplication() + "\",\r\n"
				+ "    \"comment\": \"" + recruit.getComment() + "\",\r\n"
				+ "    \"consentToKeepData\": " + recruit.isConsentToKeepData() + "\r\n"
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
		
		recruit.setId(Integer.parseInt(getDataFromRecruitJsonResponse(response, "id")));
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
			System.out.println("Failed to create new candidate");
			System.out.println(response.asPrettyString());
			recruit.setConsentToKeepData(false);
		}
		
		recruit.setConsentToKeepData(true);
		return response;
	}
	
	private static void encodePdfFile() {
        try {
            File pdfFile = new File(".\\docs\\mock-cv.pdf");
            byte[] fileContent = FileUtils.readFileToByteArray(pdfFile);

            base64String = Base64.getEncoder().encodeToString(fileContent);
            fileSize = pdfFile.length();
           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	protected static String getDataFromRecruitJsonResponse(Response response, String value) {
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
	
	private static String extractTokenFromHtml(String response) {
		Document doc = Jsoup.parse(response);
		Element token = doc.selectFirst("auth-login");
		return token.attr("token"); //.replace("\"", "");
	}

	protected static RecruitDTO initRecruit() {
		recruit = new RecruitDTO("Petar", 
								"Pera", 
								"Petrovic", 
								6, 
								"pe.pe@example.com", 
								"+0111222333", 
								"one two three", 
								"2024-05-16", 
								"Lorem Ipsom Dolomet", 
								true);
		return recruit;
	}
}
