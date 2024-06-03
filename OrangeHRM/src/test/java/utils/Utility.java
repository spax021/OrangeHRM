package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.CandidateFile;
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
	 * Method is incrementing employee ID by 1 if provided al ready exist
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
}
