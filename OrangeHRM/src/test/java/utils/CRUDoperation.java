package utils;

import static io.restassured.RestAssured.given;

import io.restassured.http.Cookies;
import io.restassured.response.Response;

public class CRUDoperation {
	
	 private static CRUDoperation instance;

	    private CRUDoperation() {}

	    public static CRUDoperation getInstance() {
	        if (instance == null) {
	            synchronized (CRUDoperation.class) {
	                if (instance == null) {
	                    instance = new CRUDoperation();
	                }
	            }
	        }
	        return instance;
	    }

	    public Response get() {
	    	return given().get();
	    }
	    
	    public Response get(String location) {
	        return given()
	            .header("Content-Type", "application/json")
	            .get(location);
	    }

	    public Response get(String location, Cookies cookies) {
	        return given()
	            .header("Content-Type", "application/json")
	            .cookies(cookies)
	            .get(location);
	    }

	    public Response put(String location, Cookies cookies, String payload) {
	        return given()
	            .header("Content-Type", "application/json")
	            .cookies(cookies)
	            .body(payload)
	            .put(location);
	    }

	    public Response put(String location, Cookies cookies) {
	        return given()
	            .header("Content-Type", "application/json")
	            .cookies(cookies)
	            .put(location);
	    }

	    public Response post(String location, Cookies cookies, String payload) {
	        return given()
	            .header("Content-Type", "application/json")
	            .cookies(cookies)
	            .body(payload)
	            .post(location);
	    }
	    
	    public Response post(String location, Cookies cookies, String token, String username, String password) {
	        return given()
				.cookies(cookies)
				.formParam("_token", token)
				.formParam("username", username)
				.formParam("password", password)
				.post(location);
	    }

	    public Response delete(String location, Cookies cookies, String payload) {
	        return given()
	            .header("Content-Type", "application/json")
	            .cookies(cookies)
	            .body(payload)
	            .delete(location);
	    }
}
