package config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class CandidateFile {

    private static String fileLocation = ".\\src\\test\\resources\\candidate.properties";
    private static LinkedHashMap<String, String> properties = new LinkedHashMap<>();

    private CandidateFile() {
    }

    private static class CandidateFileHolder {
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
        return CandidateFileHolder.INSTANCE;
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

    public static String getFirstname() {
        return getInstance().get("firstname");
    }

    public static String getMiddlename() {
        return getInstance().get("middlename");
    }

    public static String getLastname() {
        return getInstance().get("lastname");
    }

    public static String getVacancy() {
        return getInstance().get("vacancy");
    }

    public static String getEmail() {
        return getInstance().get("email");
    }

    public static String getContactNumber() {
        return getInstance().get("contactNumber");
    }

    public static String getResume() {
        return getInstance().get("resume");
    }

    public static String getKeywords() {
        return getInstance().get("keyWords");
    }

    public static String getDate() {
        return getInstance().get("date");
    }

    public static String getNotes() {
        return getInstance().get("notes");
    }

    public static String getConsent() {
        return getInstance().get("consent");
    }

    // API Values
    public static String getApiid() {
        return getInstance().get("APIid");
    }

    public static void setApiId(String apiId) {
        getInstance().put("APIid", apiId);
        writePropertiesFile();
    }

    public static String getApiFirstname() {
        return getInstance().get("APIfirstname");
    }

    public static String getApiMiddlename() {
        return getInstance().get("APImiddlename");
    }

    public static String getApiLastname() {
        return getInstance().get("APIlastname");
    }

    public static String getApiVacancyId() {
        return getInstance().get("APIvacancyId");
    }

    public static String getApiEmail() {
        return getInstance().get("APIemail");
    }

    public static String getApiContactNumber() {
        return getInstance().get("APIcontactNumber");
    }

    public static String getApiResume() {
        return getInstance().get("APIresume");
    }

    public static String getApiKeywords() {
        return getInstance().get("APIkeyWords");
    }

    public static String getApiDateOfApplication() {
        return getInstance().get("APIdateOfApplication");
    }

    public static String getApiNotes() {
        return getInstance().get("APInotes");
    }

    public static String getApiConsent() {
        return getInstance().get("APIconsent");
    }
}
