package com.api.testcases;

import static org.hamcrest.Matchers.*;
import java.io.IOException;
import org.testng.annotations.*;
import com.api.config.*;
import com.api.models.*;
import com.api.requesthandler.*;
import com.api.utilities.XLSutils;
import io.restassured.response.Response;

public class TS_006_InsertPayment extends Config{
	
	private BillHandler billRequestHandler = new BillHandler();
	private PaymentHandler paymentRequestHandler = new PaymentHandler();
	private LocalHandler localRequestHandler = new LocalHandler();
	
	@BeforeClass
	public void firstLogMessage() {
		logger.info("");
		logger.info("=====TS_006 Insert Payment=====");
	}

	@DataProvider(name="getInsertPayment-data")
	public Object[][] insertPaymentData() throws IOException {
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Insert Payment", 1);
		return data;
	}
	
	@DataProvider(name="createBill-data")
	public Object[][] createbillData() throws IOException{
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Insert Payment Precondition"
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
	
	
	@Test(dataProvider="getInsertPayment-data",dependsOnMethods= {"createBillData"})
	public void insertPaymentTest(String testCaseId, String description, String idUser, 
			String idBill, String idPaymentMethod, String balance, String expectedStatusCode, String expectedMessage) {
		logger.info("");
		logger.info("");
		logger.info("Test Case ID :"+testCaseId+" - Description :"+description);
		
		Response response;
		if(idUser.contains("Depend")) {
			String[] splitString =  idUser.split("#");
			String userTelephoneNumber=splitString[1];
			idUser = localRequestHandler.getIdUser(userTelephoneNumber);
			localRequestHandler.updateBalance(userTelephoneNumber,Float.parseFloat(balance));
		}
		if(idBill.contains("Depend")){
			String[] splitString =  idBill.split("#");
			String billTelephoneNumber=splitString[1];
			idBill=localRequestHandler.getIdBill(billTelephoneNumber);
		}
		response=paymentRequestHandler.insertPayment(idUser, idBill, idPaymentMethod);
		
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
				body("status",equalTo(expectedStatusCode)).
				content(containsString(expectedMessage));
			logger.info("success!");
		}
		if (expectedStatusCode.equals("400")) {
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
