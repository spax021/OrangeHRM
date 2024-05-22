package config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile {

    private static String fileLocation = ".\\src\\test\\resources\\application.properties";
    private static Properties prop;

    private PropertiesFile() {
    }

    private static class PropertiesFileHolder {
        private static final Properties INSTANCE = loadProperties();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(fileLocation)) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static Properties getInstance() {
        return PropertiesFileHolder.INSTANCE;
    }

    public static String getLoginUrl() {
        return getInstance().getProperty("loginUrl");
    }

    public static String getBaseUrl() {
        return getInstance().getProperty("baseUrl");
    }

    public static String getUsername() {
        return getInstance().getProperty("username");
    }

    public static String getPassword() {
        return getInstance().getProperty("password");
    }

    public static String getInvalidUsername() {
        return getInstance().getProperty("invalidUsername");
    }

    public static String getInvalidPassword() {
        return getInstance().getProperty("invalidPassword");
    }

    public static String getLoginPageUrl() {
        return getInstance().getProperty("loginPageUrl");
    }

    public static String getRecruitmentPageUrl() {
        return getInstance().getProperty("recruitmentPageUrl");
    }

    public static String getRecruitmentAddNewCandidatePageUrl() {
        return getInstance().getProperty("recruitmentAddNewCandidatePageUrl");
    }
}
