package api.tests;

import static io.restassured.RestAssured.given;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import config.PropertiesFile;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseApiTest {

	private static String token;
	private static String username = PropertiesFile.getUsername();
	private static String password = PropertiesFile.getPassword();
	private static Cookies cookies;

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
	
	private static Response login() {
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

	private static String extractTokenFromHtml(String response) {
		Document doc = Jsoup.parse(response);
		Element token = doc.selectFirst("auth-login");
		return token.attr("token"); //.replace("\"", "");
	}

}
