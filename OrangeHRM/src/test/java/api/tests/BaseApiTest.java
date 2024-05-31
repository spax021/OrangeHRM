package api.tests;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.CandidateFile;
import config.PropertiesFile;
import dtos.CandidateAttachmentDTO;
import dtos.CandidateDTO;
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
		
		candidate = populateDTO(response, CandidateDTO.class);
		return response;
	}
		
	public static Response createNewCandidate(CandidateDTO cand) {
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
		
		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.post("/web/index.php/api/v2/recruitment/candidates");
		
		if(response.getStatusCode() != 200) {
			System.out.println("Failed to create new candidate");
			System.out.println(response.asPrettyString());
		}
		
		cand.setId(Integer.parseInt(getDataFromJson(response, "data", "id")));
		CandidateFile.setApiId(getDataFromJson(response, "data", "id"));
		return response;
	}
	
	public static Response updateCandidate(CandidateDTO cand) {
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
		
		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.put("/web/index.php/api/v2/recruitment/candidates/" + cand.getId());
		
		if(response.getStatusCode() != 200) {
			System.out.println("Failed to update recruit");
			System.out.println(response.asPrettyString());
		}
		
		return response;
	}
	
	protected static Response addAttachmentToNewCandidate(int id) {
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

		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.post("/web/index.php/api/v2/recruitment/candidate/attachments");

		if(response.getStatusCode() != 200) {
			System.out.println("Failed to attach resume");
			System.out.println(response.asPrettyString());
		}
		candidateAttachment = populateDTO(response, CandidateAttachmentDTO.class);
		return response;
	}

	 public static <T> T populateDTO(Response response, Class<T> dtoClass) {
	        ObjectMapper obj = new ObjectMapper();
	        T dtoObject = null;
	        try {
	            JsonNode data = obj.readTree(response.asPrettyString());
	            JsonNode dataNode = data.get("data");
	            dtoObject = obj.treeToValue(dataNode, dtoClass);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dtoObject;
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
	
	
	///////////////////////////////////Employee
	/**
	 * Can be used for extracting data from response
	 * Json structure example:
	 * {
	 * 	"data": {
        	"id":
        	"firstName":
        	"middleName":
        	"lastName":
        	"email":
    		}
    	}
    	In which case DATA is representing parent level
    	In case of an error, insted of DATA we will have ERROR as parent
	 * @param respons
	 * response payload
	 * @param parent
	 * @return
	 * Extracted values from parent:data
	 */
	protected static String getDataFromJson(Response respons, String parent) {
		ObjectMapper obj = new ObjectMapper();
		String extractedValue = null;
		try {
			JsonNode data = obj.readTree(respons.asPrettyString());
			JsonNode parentNode = data.get(parent);
			extractedValue = parentNode.toString().replaceAll("^\"|\"$", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return extractedValue;
	}
	
	protected static String getDataFromJson(Response response, String parent, String childOne) {
		String parentJson = getDataFromJson(response, parent);
		ObjectMapper obj = new ObjectMapper();
		String extractedValue = null;
		try {
			JsonNode parentNode = obj.readTree(parentJson);
			JsonNode childNode = parentNode.get(childOne);
			extractedValue = childNode.toString().replaceAll("^\"|\"$", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(extractedValue);
		return extractedValue;
	}
	
	protected static String getDataFromJson(Response response, String parent, String childOne, String childTwo) {
		String childOneJson = getDataFromJson(response, parent, childOne);
		ObjectMapper obj = new ObjectMapper();
		String extractedValue = null;
		try {
			JsonNode childOneNode = obj.readTree(childOneJson);
			JsonNode childTwoNode = childOneNode.get(childTwo);
			extractedValue = childTwoNode.toString().replaceAll("^\"|\"$", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return extractedValue;
	}

	private static Response respo;
	
	private static Response checkId() {
		respo = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.get("/web/index.php/api/v2/core/validation/unique?value=0548&entityName=Employee&attributeName=employeeId");
		
		return respo;
	}
	
	private static Response checkRest() {
		respo = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.get("/web/index.php/api/v2/core/validation/unique?value=Jovanica&entityName=User&attributeName=userName&matchByField=deleted&matchByValue=false");
		
		return respo;
	}
	
	private static Response checkPassword() {
		
		String payload = "{\r\n"
				+ "    \"password\": \"Jovan123\"\r\n"
				+ "}";
		
		respo = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.post("/web/index.php/api/v2/auth/public/validation/password");
		return respo;
	}
	
	public static Response createNewEmployee() {
		HashMap<String, Object> fileInfo = encodeFile("image");
		String payload = "{\r\n"
				+ "    \"empPicture\": {\r\n"
				+ "        \"base64\": \"" + fileInfo.get("base64String") + "\",\r\n"
				+ "        \"name\": \"" + fileInfo.get("fileName") + "\",\r\n"
				+ "        \"size\": " + fileInfo.get("fileSize") + ",\r\n"
				+ "        \"type\": \"" + fileInfo.get("fileType") + "\"\r\n"
				+ "    },\r\n"
				+ "    \"employeeId\": \"0561\",\r\n"
				+ "    \"firstName\": \"Jovan\",\r\n"
				+ "    \"lastName\": \"Jovanovic\",\r\n"
				+ "    \"middleName\": \"Jova\"\r\n"
				+ "}";

		Response response = given()
				.header("Content-Type", "application/json")
				.cookies(cookies)
				.body(payload)
				.post("/web/index.php/api/v2/pim/employees");

		if(response.getStatusCode() != 200) {
			System.out.println("Failed to create new employee");
			System.out.println(response.asPrettyString());
		}
		return response;
	}
	
	/**
	 * @param
	 * encodeImageFile("image")
	 * encodeImageFile("pdf")
	 */
	private static HashMap<String, Object> encodeFile(String type) {
		HashMap<String, Object> fileInfo = new HashMap<>();
        try {
        	
        	if (type.equals("image")) {
                
        		File imageFile = new File(CandidateFile.getApiAvatar());
                Path imagePath = imageFile.toPath();
                byte[] imageBytes = Files.readAllBytes(imagePath);
               
                fileInfo.put("base64String", Base64.getEncoder().encodeToString(imageBytes));
                fileInfo.put("fileName", imagePath.getFileName().toString());
                fileInfo.put("fileSize", imageFile.length());
                fileInfo.put("fileType", Files.probeContentType(imagePath));
                
                return fileInfo;
			} else if(type.equals("pdf")) {
	        	
				File pdfFile = new File(CandidateFile.getApiResume());
	        	Path pdfPath = pdfFile.toPath();
	        	byte[] pdfBytes = Files.readAllBytes(pdfPath);

	        	fileInfo.put("base64String", Base64.getEncoder().encodeToString(pdfBytes));
	        	fileInfo.put("fileName", pdfPath.getFileName().toString());
	        	fileInfo.put("fileSize", pdfFile.length());
	        	fileInfo.put("fileType", Files.probeContentType(pdfPath));
	            
	            return fileInfo;
			} else {
				System.out.println("Unknown attachemnt type: " + type);
			}

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	public static Response activateNewEmployee() {
		String payload = "{\r\n"
				+ "    \"empNumber\": 171,\r\n"
				+ "    \"password\": \"Jovan123\",\r\n"
				+ "    \"status\": true,\r\n"
				+ "    \"userRoleId\": 2,\r\n"
				+ "    \"username\": \"Jovaaan\"\r\n"
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
		
		return respo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
