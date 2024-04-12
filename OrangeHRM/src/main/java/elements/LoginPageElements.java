package elements;

import org.openqa.selenium.By;

public class LoginPageElements {

	private By username = By.cssSelector("input[name='username']");
	private By password = By.cssSelector("input[name='password']");
	private By login = By.cssSelector("[type='submit']");
	private By errorMessage = By.cssSelector(".oxd-alert-content--error > p");
//	private By forgotPassword = By.linkText("Forgot Your Password?");
	
	public By getUsername() {
		return username;
	}
	public By getPassword() {
		return password;
	}
	public By getLogin() {
		return login;
	}
	public By getErrorMessage() {
		return errorMessage;
	}
//	public By getForgotPassword() {
//		return forgotPassword;
//	}
	
}

