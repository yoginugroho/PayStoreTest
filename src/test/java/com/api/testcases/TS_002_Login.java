package com.api.testcases;

import static org.hamcrest.Matchers.*;
import java.io.IOException;
import org.testng.annotations.*;
import com.api.config.*;
import com.api.requesthandler.*;
import com.api.utilities.*;

import io.restassured.response.Response;

public class TS_002_Login extends Config{
	
	private UserHandler userRequestHandler = new UserHandler();
	private LocalHandler localRequestHandler = new LocalHandler();

	@BeforeClass
	public void firstLogMessage() {
		userRequestHandler.logout(localRequestHandler.getIdUser("83833833834"));
		logger.info("");
		logger.info("=====TS_002 Login=====");
	}
	
	@DataProvider(name="login-data")
	public Object[][] loginData() throws IOException {
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Login", 1);
		return data;
	}
	
	@Test(dataProvider="login-data")
	public void loginTest(String testCaseId, String description, String phoneNumber, String password, 
			String expectedStatus, String expectedStatusCode, String expectedMessage ) {
	
		Response response;
		response=userRequestHandler.login(phoneNumber, password);
		logger.info("");
		logger.info("");
		logger.info("Test Case ID :"+testCaseId+" - Description :"+description);
		
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
				body("$",hasKey("idUser")).
				body("message",equalTo(expectedMessage)).
				body("$",hasKey("token")).
				body("status",equalTo(expectedStatusCode));
			logger.info("success!");
		}
		else {
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
