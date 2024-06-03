package config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class EmployeeFile {

    private static String fileLocation = ".\\src\\test\\resources\\employee.properties";
    private static LinkedHashMap<String, String> properties = new LinkedHashMap<>();

    private EmployeeFile() {
    }

    private static class EmployeeFileHolder {
        private static final LinkedHashMap<String, String> INSTANCE = loadProperties();
    }

     private static LinkedHashMap<String, String> loadProperties() {
        LinkedHashMap<String, String> props = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] keyValue = line.split("=", 2);
                if (keyValue.length == 2) {
                    props.put(keyValue[0].trim(), keyValue[1].trim());
                } else {
                    props.put(keyValue[0].trim(), "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static LinkedHashMap<String, String> getInstance() {
        return EmployeeFileHolder.INSTANCE;
    }

    public static void writePropertiesFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileLocation))) {
            for (Map.Entry<String, String> entry : getInstance().entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAPIemployeeId() {
        return getInstance().get("employeeAPIemployeeId");
    }

    public static String getAPIempNumber() {
        return getInstance().get("employeeAPIempNumber");
    }

    public static String getAPIfirstname() {
        return getInstance().get("employeeAPIfirstname");
    }

    public static String getAPImiddlename() {
        return getInstance().get("employeeAPImiddlename");
    }

    public static String getAPIlastnam() {
        return getInstance().get("employeeAPIlastname");
    }

    public static String getAPIotherId() {
        return getInstance().get("employeeAPIotherId");
    }

    public static String getAPIdrivingLicenceNo() {
        return getInstance().get("employeeAPIdrivingLicenceNo");
    }

    public static String getAPIdrivingLicenceExpiredDate() {
        return getInstance().get("employeeAPIdrivingLicenceExpiredDate");
    }

    public static String getAPIgender() {
        return getInstance().get("employeeAPIgender");
    }

    public static String getAPImaritalStatus() {
        return getInstance().get("employeeAPImaritalStatus");
    }

    public static String getAPIbirthday() {
        return getInstance().get("employeeAPIbirthday");
    }

    public static String getAPIterminationId() {
        return getInstance().get("employeeAPIterminationId");
    }

    public static void setAPIempNumber(String empNumber) {
        getInstance().put("employeeAPIempNumber", empNumber);
        writePropertiesFile();
    }

    public static void setAPIusername(String username) {
        getInstance().put("employeeAPIusername", username);
        writePropertiesFile();
    }

    public static String getAPInationalityId() {
        return getInstance().get("employeeAPInationalityId");
    }

    public static String getAPInationalityName() {
        return getInstance().get("employeeAPInationalityName");
    }

    public static String getAPIuserRoleId() {
        return getInstance().get("employeeAPIuserRoleId");
    }

    public static String getAPIusername() {
        return getInstance().get("employeeAPIusername");
    }

    public static String getAPIpassword() {
        return getInstance().get("employeeAPIpassword");
    }

    public static String getAPIstatus() {
        return getInstance().get("employeeAPIstatus");
    }

    public static String getAPIemail() {
        return getInstance().get("employeeAPIemail");
    }

    public static String getAPIcontactNumber() {
        return getInstance().get("employeeAPIcontactNumber");
    }

    public static String getAPIresume() {
        return getInstance().get("employeeAPIresume");
    }

    public static String getAPIavatar() {
        return getInstance().get("employeeAPIavatar");
    }
}
