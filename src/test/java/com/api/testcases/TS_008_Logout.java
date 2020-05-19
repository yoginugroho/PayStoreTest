package com.api.testcases;

import static org.hamcrest.Matchers.*;
import java.io.IOException;
import org.testng.annotations.*;
import com.api.config.*;
import com.api.requesthandler.*;
import com.api.utilities.*;
import io.restassured.response.Response;

public class TS_008_Logout extends Config{
	private UserHandler userRequestHandler = new UserHandler();
	private LocalHandler localRequestHandler = new LocalHandler();
	
	
	@BeforeClass
	public void firstLogMessage() {
		logger.info("");
		logger.info("=====TS_008 Logout=====");
	}

	@DataProvider(name="logout-data")
	public Object[][] logoutData() throws IOException {
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Logout", 1);
		return data;
	}
	
	
	@Test(dataProvider="logout-data")
	public void logoutTest(String testCaseId, String description, String idUser, 
			String expectedStatus, String expectedStatusCode, String expectedMessage ) {
	
		logger.info("");
		logger.info("");
		logger.info("Test Case ID :"+testCaseId+" - Description :"+description);
		
		if(idUser.contains("Depend")) {
			String[] splitString =  idUser.split("#");
			String userTelephoneNumber=splitString[1];
			idUser = localRequestHandler.getIdUser(userTelephoneNumber);
		}
		
		Response response;
		response=userRequestHandler.logout(idUser);
		
		int statusCode = response.getStatusCode();
		String contentType = response.getContentType();
		String responseBody = response.getBody().asString();
		logger.info("Status code => " + statusCode);
		logger.info("Content-type => " + contentType);
		logger.info("Response body => " + responseBody);
		
		if (expectedStatusCode.equals("200")) {
			logger.info("Assert status code...");
			response.then().statusCode(Integer.parseInt(expectedStatusCode));
			logger.info("Success!");
			logger.info("Assert Content-Type...");
			response.then().contentType("application/json");
			logger.info("Success!");
			logger.info("Assert response body...");
			response.then().
				body("data.idUser",equalTo(Integer.parseInt(idUser))).
				body("data",hasKey("name")).
				body("data",hasKey("email")).
				body("data",hasKey("password")).
				body("data",hasKey("phoneNumber")).
				body("data",hasKey("balance")).
				body("data",hasKey("token")).
				body("message",equalTo(expectedMessage)).
				body("status",equalTo(expectedStatusCode));
			logger.info("success!");
		}
		else if(expectedStatusCode.equals("400")){
			logger.info("Assert status code...");
			response.then().statusCode(Integer.parseInt(expectedStatusCode));
			logger.info("Success!");
			logger.info("Assert Content-Type...");
			response.then().contentType("application/json");
			logger.info("Success!");
			logger.info("Assert response body...");
			response.then().
				body("status",equalTo(Integer.parseInt(expectedStatusCode))).
				content(containsString(expectedMessage));
			logger.info("Success!");
		}
		else{
			logger.info("Assert status code...");
			response.then().statusCode(Integer.parseInt(expectedStatusCode));
			logger.info("Success!");
			logger.info("Assert Content-Type...");
			response.then().contentType("application/json");
			logger.info("Success!");
			logger.info("Assert response body...");
			response.then().
				body("status",equalTo(expectedStatusCode)).
				content(containsString(expectedMessage));
			logger.info("Success!");
		}
	}
}
