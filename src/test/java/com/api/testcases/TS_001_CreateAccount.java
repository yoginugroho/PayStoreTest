package com.api.testcases;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.api.config.*;
import com.api.requesthandler.*;
import com.api.utilities.*;
import com.api.models.*;

import io.restassured.response.Response;

public class TS_001_CreateAccount extends Config {
	private UserHandler userRequestHandler = new UserHandler();
	
	@BeforeClass
	public void firstLogMessage() {
		logger.info("");
		logger.info("=====TS_001 Create Account=====");
	}
	
	@DataProvider(name="createAccount-data")
	public Object[][] createAccountData() throws IOException {
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Create Account", 1);
		return data;
	}
	
	@Test(dataProvider="createAccount-data")
	public void createAccountTest(String testCaseId, String description, 
			String name, String email, String phoneNumber, String password, String expectedStatus, 
			String expectedStatusCode, String expectedMessage ) {
	
		Response response;
		response=userRequestHandler.createAccount(name, email, phoneNumber, password);
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
			logger.info("Assert response body...");
			response.then().
				body("data",hasKey("idUser")).
				body("data.name",equalTo(name)).
				body("data.email",equalTo(email)).
				body("data.password",equalTo(password)).
				body("data.phoneNumber",equalTo(phoneNumber)).
				body("status",equalTo(expectedStatusCode)).
				content(containsString(expectedMessage));
			logger.info("success!");
			int idUser = response.then().extract().path("data.idUser");
			float balance = response.then().extract().path("data.balance");
			users.add(new User(idUser,name, phoneNumber, email, password,balance));
		}
		else {
			if(statusCode==201) {
				int idUser=response.then().extract().path("data.idUser");
				userRequestHandler.delete(idUser);
			}
			
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
