package apiTests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import config.CandidateFile;
import config.EmployeeFile;
import config.MongoConnection;
import config.PropertiesFile;
import dtos.CandidateAttachmentDTO;
import dtos.CandidateDTO;
import dtos.employee.EmployeeDTO;
import dtos.employee.UserRoleDTO;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.CRUDoperation;
import utils.Utility;

public class BaseApiTest extends Utility {

	private static String token;
	
	private static Cookies cookies;
	private MongoConnection db;
	private MongoCollection<Document> collection;
	protected static CandidateDTO candidate;
	protected static CandidateAttachmentDTO candidateAttachment;
	protected static EmployeeDTO employee;
	protected static UserRoleDTO userRole;

	private static RequestSpecification request;

	private EmployeeDTO initEmployee;
	private SoftAssert sa;
	
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
		Response response = CRUDoperation.getInstance().get();
		cookies = response.getDetailedCookies();
		token = extractTokenFromHtml(response.asPrettyString());
		return response;
	}
	
	protected static Response login() {
		acquireToken();
		String location = "/web/index.php/auth/validate";	
		Response response = CRUDoperation.getInstance().post(location, cookies, token, PropertiesFile.getUsername(), PropertiesFile.getPassword());
		cookies = response.getDetailedCookies();	//new cookie is saved
		return response;
	}

	protected static Response login(String username, String password) {
		acquireToken();
		String location = "/web/index.php/auth/validate";	
		Response response = CRUDoperation.getInstance().post(location, cookies, token, EmployeeFile.getAPIusername(), EmployeeFile.getAPIpassword());
		cookies = response.getDetailedCookies();	//new cookie is saved
		return response;
	}

	protected static Response logout() {
		acquireToken();
		String location = "/web/index.php/auth/logout";	
		Response response = CRUDoperation.getInstance().get(location);
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
		Response response = CRUDoperation.getInstance().put(location, cookies, payload);
		System.out.println("Localisation is executed");
		return response;
	}
	
	/**
	 * Due to frequent changes in vacancy settings on the site by the system, 
	 * this method reliably sets an existing employee as a hiring manager of prefered vacancy (Senior QA Lead).
	 */
	public static Response setHiringManager() {
		login();
		String location = "/web/index.php/api/v2/recruitment/vacancies/6";
		String payload = "{\r\n"
				+ "    \"employeeId\": 69,\r\n"
				+ "    \"name\": \"Senior QA Lead\",\r\n"
				+ "    \"status\": true,\r\n"
				+ "    \"jobTitleId\": 8,\r\n"
				+ "    \"isPublished\": true\r\n"
				+ "}";
		Response response = CRUDoperation.getInstance().put(location, cookies, payload);
		System.out.println("Hiring manager set");
		return response;
	}
	
	public static Response getCandidate(int id) {
		String location = "/web/index.php/api/v2/recruitment/candidates/" + id;
		Response response = CRUDoperation.getInstance().get(location, cookies);
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
		Response response = CRUDoperation.getInstance().post(location, cookies, payload);
//		cand.setId(Integer.parseInt(getDataFromJson(response, "data", "id")));
		candidate = populateDTO(response, CandidateDTO.class);
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
		Response response = CRUDoperation.getInstance().put(location, cookies, payload);
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
		Response response = CRUDoperation.getInstance().post(location, cookies, payload);
		candidateAttachment = populateDTO(response, CandidateAttachmentDTO.class);
		return response;
	}

	private static String checkEmployeeId(String employeeId) {
		String location = "/web/index.php/api/v2/core/validation/unique?value=" + employeeId + "&entityName=Employee&attributeName=employeeId";
		Response response = CRUDoperation.getInstance().get(location, cookies);
		return getDataFromJson(response, "data", "valid");
	}

	public static Response createNewEmployee(String employeeID, EmployeeDTO emp) {
		String location = "/web/index.php/api/v2/pim/employees";
		String isValidId = checkEmployeeId(employeeID);

		if(!isValidId.equals("true")) {
			createNewEmployee(incrementEmployeeId(employeeID), emp);
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
				+ "    \"firstName\": \"" + emp.getFirstName() + "\",\r\n"
				+ "    \"lastName\": \"" + emp.getLastName() + "\",\r\n"
				+ "    \"middleName\": \"" + emp.getMiddleName() + "\"\r\n"
				+ "}";
		Response response = CRUDoperation.getInstance().post(location, cookies, payload);
		EmployeeFile.setAPIempNumber(getDataFromJson(response, "data", "empNumber"));
		employee = populateDTO(response, EmployeeDTO.class);
		return response;
	}

	public static Response activateNewEmployee(Integer empNumber) {
		String location = "/web/index.php/api/v2/admin/users";
		String payload = "{\r\n"
				+ "    \"empNumber\": " + empNumber + ",\r\n"
				+ "    \"password\": \"" + EmployeeFile.getAPIpassword() + "\",\r\n"
				+ "    \"status\": " + EmployeeFile.getAPIstatus() + ",\r\n"
				+ "    \"userRoleId\": " + EmployeeFile.getAPIuserRoleId() + ",\r\n"
				+ "    \"username\": \"" + EmployeeFile.getAPIusername() + "\"\r\n"
				+ "}";
		Response response = CRUDoperation.getInstance().post(location, cookies, payload);
		EmployeeFile.setAPIusername(incrementEmployeeUsername(getDataFromJson(response, "data", "userName")));
		userRole = populateDTO(response, "userRole", UserRoleDTO.class);
		return response;
	}

	public static Response deleteEmployee(int[] ids) {
		String location = "/web/index.php/api/v2/pim/employees";
		String payload = "{\"ids\":[" + formArrayOfIdsForPayload(ids) + "]}";
		Response response = CRUDoperation.getInstance().delete(location, cookies, payload);
		return response;
	}
	
	public static Response deleteCandidate(int[] ids) {
		String location = "/web/index.php/api/v2/recruitment/candidates";
		String payload = "{\"ids\":[" + formArrayOfIdsForPayload(ids) + "]}";
		Response response = CRUDoperation.getInstance().delete(location, cookies, payload);
		return response;
	}
	
	public static Response shortlistNewCandidate(int id) {
		String location = "/web/index.php/api/v2/recruitment/candidates/" + id + "/shortlist";
		Response response = CRUDoperation.getInstance().put(location, cookies);
		return response;
	}
	
	public static Response scheduleInterviewWithNewCandidate(int candidateId, String title, String date, String time, int[] empNumbers) {
		String location = "/web/index.php/api/v2/recruitment/candidates/" + candidateId + "/shedule-interview";
		String payload = "{\r\n"
				+ "    \"interviewName\": \"" + title + "\",\r\n"
				+ "    \"interviewDate\": \"" + date + "\",\r\n"
				+ "    \"interviewTime\": \"" + time + "\",\r\n"
				+ "    \"interviewerEmpNumbers\": [\r\n"
				+ "        " + formArrayOfIdsForPayload(empNumbers) + "\r\n"
				+ "    ],\r\n"
				+ "    \"note\": \"Lorem Ipsum\"\r\n"
				+ "}";
		Response response = CRUDoperation.getInstance().post(location, cookies, payload);
		return response;
	}
	
	public static Response markInterviewAsPassed(int id, String interviewId) {
		String location = "/web/index.php/api/v2/recruitment/candidates/" + id + "/interviews/" + interviewId + "/pass";
		String payload = "{\r\n"
				+ "    \"note\": \"Lorem Ipsom\"\r\n"
				+ "}";
		Response response = CRUDoperation.getInstance().put(location, cookies, payload);
		return response;
	}
	
	public static Response jobOffer(int id) {
		String location = "/web/index.php/api/v2/recruitment/candidates/" + id + "/job/offer";
		String payload = "{\r\n"
				+ "    \"note\": \"Lorem Ipsom\"\r\n"
				+ "}";
		Response response = CRUDoperation.getInstance().put(location, cookies, payload);
		return response;
	}
	
	public static Response hireCandidate(int id) {
		String location = "/web/index.php/api/v2/recruitment/candidates/" + id + "/hire";
		String payload = "{\r\n"
				+ "    \"note\": \"Lorem Ipsom\"\r\n"
				+ "}";
		Response response = CRUDoperation.getInstance().put(location, cookies, payload);
		return response;
	}
	
	public <T> List<T> readData(String collectionType, Class<T> dtoClass) {
		db = new MongoConnection();
		collection = db.getCollection(collectionType);
		
		List<T> dtoList = new ArrayList<>();
		
		ObjectMapper obj = new ObjectMapper();
		try(MongoCursor<Document> cursor = collection.find().iterator()){
			while(cursor.hasNext()) {
				Document doc = cursor.next();
				T dto = obj.convertValue(doc, dtoClass);
				dtoList.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
//		Document data = collection.find(new Document("id",id)).first();
//		candidate = populateDTOFromMongo(data.toJson(), CandidateDTO.class);
		return dtoList;
	}
	
	public <T> void insertData(String collectionType, T object) {
		db = new MongoConnection();
		collection = db.getCollection(collectionType);
		try {
			ObjectMapper obj = new ObjectMapper();
			Map<String, Object> map = obj.convertValue(object, Map.class);
			Document doc = new Document(map);
			collection.insertOne(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public <T> void updateData(String collectionType, T object) {
		db = new MongoConnection();
		collection = db.getCollection(collectionType);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
