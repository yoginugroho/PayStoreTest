package com.api.testcases;

import static org.hamcrest.Matchers.*;
import java.io.IOException;
import org.testng.annotations.*;
import com.api.config.*;
import com.api.requesthandler.*;
import com.api.utilities.*;
import io.restassured.response.Response;

public class TS_003_SendOTP extends Config{
	private UserHandler userRequestHandler = new UserHandler();

	@BeforeClass
	public void firstLogMessage() {
		logger.info("");
		logger.info("=====TS_003 Send OTP=====");
	}

	@DataProvider(name="sendOTP-data")
	public Object[][] sendOTPData() throws IOException {
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Send OTP", 1);
		return data;
	}
	
	
	@Test(dataProvider="sendOTP-data")
	public void sendOTPTest(String testCaseId, String description, String phoneNumber, String email, 
			String expectedStatus, String expectedStatusCode, String expectedMessage ) {
	
		Response response;
		response=userRequestHandler.requestOTP(phoneNumber, email);
		logger.info("");
		logger.info("");
		logger.info("Test Case ID :"+testCaseId+" - Description :"+description);
		
		int statusCode = response.getStatusCode();
		String contentType = response.getContentType();
		String responseBody = response.getBody().asString();
		logger.info("Status code => " + statusCode);
		logger.info("Content-type => " + contentType);
		logger.info("Response body => " + responseBody);
		
		if (expectedStatusCode.equals("201")) {
			logger.info("Assert status code...");
			response.then().statusCode(Integer.parseInt(expectedStatusCode));
			logger.info("Success!");
			logger.info("Assert Content-Type...");
			response.then().contentType("application/json");
			logger.info("Success!");
			logger.info("Assert response body...");
			response.then().
				body("phoneNumber",equalTo(phoneNumber)).
				body("email",equalTo(email)).
				body("$",hasKey("otp"));
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
