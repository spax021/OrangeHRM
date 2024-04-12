package config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile {

	private static String fileLocation = ".\\src\\test\\resources\\application.properties";
	private static Properties prop;

	public static void readPropertiesFile() {
		prop = new Properties();
		try {
			InputStream input = new FileInputStream(fileLocation);
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getLoginUrl() {
		readPropertiesFile();
		return prop.getProperty("loginUrl");
	}
	
	public static String getBaseUrl() {
		readPropertiesFile();
		return prop.getProperty("baseUrl");
	}
	
	public static String getUsername() {
		readPropertiesFile();
		return prop.getProperty("username");
	}
	
	public static String getPassword() {
		readPropertiesFile();
		return prop.getProperty("password");
	}
	
	public static String getInvalidUsername() {
		readPropertiesFile();
		return prop.getProperty("invalidUsername");
	}
	
	public static String getInvalidPassword() {
		readPropertiesFile();
		return prop.getProperty("invalidPassword");
	}

	public static String getLoginPageUrl() {
		readPropertiesFile();
		return prop.getProperty("loginPageUrl");
	}

	public static String getRecruitmentPageUrl() {
		readPropertiesFile();
		return prop.getProperty("recruitmentPageUrl");
	}

	public static String getRecruitmentAddNewCandidatePageUrl() {
		readPropertiesFile();
		return prop.getProperty("recruitmentAddNewCandidatePageUrl");
	}
	
	public static String getFirstname() {
		readPropertiesFile();
		return prop.getProperty("firstname");
	}
	
	public static String getMiddlename() {
		readPropertiesFile();
		return prop.getProperty("middlename");
	}

	public static String getLastname() {
		readPropertiesFile();
		return prop.getProperty("lastname");
	}

	public static String getVacancy() {
		readPropertiesFile();
		return prop.getProperty("vacancy");
	}

	public static String getEmail() {
		readPropertiesFile();
		return prop.getProperty("email");
	}

	public static String getContactNumber() {
		readPropertiesFile();
		return prop.getProperty("contactNumber");
	}

	public static String getResume() {
		readPropertiesFile();
		return prop.getProperty("resume");
	}

	public static String getKeywords() {
		readPropertiesFile();
		return prop.getProperty("keyWords");
	}

	public static String getDate() {
		readPropertiesFile();
		return prop.getProperty("date");
	}

	public static String getNotes() {
		readPropertiesFile();
		return prop.getProperty("notes");
	}

	public static String getConsent() {
		readPropertiesFile();
		return prop.getProperty("consent");
	}

	public static String getCandidateStatus() {
		readPropertiesFile();
		return prop.getProperty("candidateStatus");
	}

}
