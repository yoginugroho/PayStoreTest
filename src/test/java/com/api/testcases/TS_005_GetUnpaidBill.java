package com.api.testcases;

import static org.hamcrest.Matchers.*;
import java.io.IOException;
import org.testng.annotations.*;
import com.api.config.*;
import com.api.models.*;
import com.api.requesthandler.*;
import com.api.utilities.*;

import io.restassured.response.Response;

public class TS_005_GetUnpaidBill extends Config{
	
	private BillHandler billRequestHandler = new BillHandler();
	
	@BeforeClass
	public void firstLogMessage() {
		logger.info("");
		logger.info("=====TS_005 Get Unpaid Bill=====");
	}

	@DataProvider(name="getUnpaidBill-data")
	public Object[][] getUnpaidBillData() throws IOException {
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Get Unpaid Bill", 1);
		return data;
	}
	
	@DataProvider(name="createBill-data")
	public Object[][] createbillData() throws IOException{
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Get Unpaid Bill Precondition"
				+ "", 1);
		return data;
	}
	
	@Test(dataProvider="createBill-data")
	public void createBillData(String telephoneOwner, String telephoneNumber, String month,
			String amount, String status) {
		Response response;
		response = billRequestHandler.createBill(telephoneOwner, telephoneNumber, month, Double.parseDouble(amount), status);
		int idBill=response.then().extract().path("data.idBill");
		bills.add(new Bill(idBill,telephoneOwner,telephoneNumber,month,Double.parseDouble(amount),status));
	}
	
	@Test(dataProvider="getUnpaidBill-data", dependsOnMethods={"createBillData"})
	public void getUnpaidBillTest(String testCaseId, String description, String telephoneNumber, 
			String expectedStatus, String expectedStatusCode, String expectedMessage ) {
	
		Response response;
		response=billRequestHandler.getUnpaidBill(telephoneNumber);
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
				body("data",hasKey("idBill")).
				body("data",hasKey("telephoneOwner")).
				body("data",hasKey("telephoneNumber")).
				body("data",hasKey("month")).
				body("data",hasKey("amount")).
				body("data.status",equalTo("unpaid")).
				body("status",equalTo(expectedStatusCode)).
				content(containsString(expectedMessage));
			logger.info("success!");
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
