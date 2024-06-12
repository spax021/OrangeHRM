package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.CandidateFile;
import config.EmployeeFile;
import dtos.CandidateDTO;
import dtos.VacancyDTO;
import dtos.employee.EmployeeDTO;
import io.restassured.response.Response;

public class Utility {

	/**
	 * @param
	 * encodeImageFile("image")
	 * encodeImageFile("pdf")
	 */
	protected static HashMap<String, Object> encodeFile(String type) {
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
	
	/**
	 * Method is incrementing number part of the username by 1 for future use
	 * @param username
	 */
	protected static String incrementEmployeeUsername(String username) {
		int i = username.length() - 1;
        while (i >= 0 && Character.isDigit(username.charAt(i))) {
            i--;
        }
        String textPart = username.substring(0, i + 1);
        String numberPart = username.substring(i + 1);
        int number = Integer.parseInt(numberPart);
        number++;
        return textPart + number;
	}
	
	/**
	 * Method is incrementing employee ID by 1 if provided already exist
	 * @param employee id
	 */
	protected static String incrementEmployeeId(String id) {
		int number = Integer.parseInt(id);
        int incrementedNumber = number + 1;
        return String.format("%04d", incrementedNumber);
	}
	
	/**
	 * In case of an error, insted of DATA we will have ERROR as first level node
	 * @param response
	 * @param first node
	 * @return
	 * Extracted values from first node level
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
	/**
	 * In case of an error, insted of DATA we will have ERROR as first level node
	 * @param response
	 * @param first node
	 * @param second node
	 * @return
	 * Extracted values from second node level
	 */	
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
		return extractedValue;
	}
	
	/**
	 * In case of an error, insted of DATA we will have ERROR as first level node
	 * @param response
	 * @param first node
	 * @param second node
	 * @param third node
	 * @return
	 * Extracted values from third node level
	 */	
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
	
	/**
	 * In case of an error, insted of DATA we will have ERROR as first level node
	 * @param response
	 * @param first node
	 * @param second node
	 * @param third node
	 * @param fourth node
	 * @return
	 * Extracted values from fourth node level
	 */	
	protected static String getDataFromJson(Response response, String parent, String childOne, String childTwo, String childThree) {
		String childTwoJson = getDataFromJson(response, parent, childOne, childTwo);
		ObjectMapper obj = new ObjectMapper();
		String extractedValue = null;
		try {
			JsonNode childTwoNode = obj.readTree(childTwoJson);
			JsonNode childThreeNode = childTwoNode.get(childThree);
			extractedValue = childThreeNode.toString().replaceAll("^\"|\"$", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return extractedValue;
	}

	/**
	 * Method is forming an array of IDs in format known to requestbody
	 * @params employeNumber
	 */
	protected static String formArrayOfIdsForPayload(int[] ids) {
		ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        String payload = null;
        try {
            payload = mapper.writeValueAsString(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return payload.substring(8, payload.length() - 2);
	}
	
	protected static <T> T populateDTO(Response response, Class<T> dtoClass) {
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

	protected static <T> T populateDTO(Response response, String child, Class<T> dtoClass) {
	        ObjectMapper obj = new ObjectMapper();
	        T dtoObject = null;
	        try {
	            JsonNode data = obj.readTree(response.asPrettyString());
	            JsonNode dataNode = data.get("data");
	            JsonNode childNode = dataNode.get(child);
	            dtoObject = obj.treeToValue(childNode, dtoClass);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dtoObject;
	}
	
	protected static String extractTokenFromHtml(String response) {
		Document doc = Jsoup.parse(response);
		Element token = doc.selectFirst("auth-login");
		return token.attr("token"); //.replace("\"", "");
	}
	
	protected List<String> getinterviewIdsFromJson(Response response, String parent, String child, String value) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> empNumbers = new ArrayList<>();
		try {
			JsonNode rootNode = objectMapper.readTree(response.asPrettyString());
	        JsonNode interviewersNode = rootNode.path(parent).path(child);
	        if(interviewersNode.isArray()) {
	        	for (JsonNode interviewerNode : interviewersNode) {
	        		String empNumber = interviewerNode.path(value).asText();
	        		empNumbers.add(empNumber);
	        	}
	        }
	    } catch (Exception e) {
			e.printStackTrace();
		}
		return empNumbers;
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
	protected static EmployeeDTO initEmployee() {
		return new EmployeeDTO(
				Integer.parseInt(EmployeeFile.getAPIempNumber()),
				EmployeeFile.getAPIfirstname(),
				EmployeeFile.getAPImiddlename(),
				EmployeeFile.getAPIlastnam());
	}

}
