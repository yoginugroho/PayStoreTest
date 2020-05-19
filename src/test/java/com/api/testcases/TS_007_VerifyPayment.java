package com.api.testcases;

import static org.hamcrest.Matchers.*;

import java.io.IOException;
import org.testng.annotations.*;
import com.api.config.*;
import com.api.models.*;
import com.api.requesthandler.*;
import com.api.utilities.*;
import io.restassured.response.Response;


public class TS_007_VerifyPayment extends Config {
	private BillHandler billRequestHandler = new BillHandler();
	private PaymentHandler paymentRequestHandler = new PaymentHandler();
	private LocalHandler localRequestHandler = new LocalHandler();
	
	@BeforeClass
	public void firstLogMessage() {
		logger.info("");
		logger.info("=====TS_007 Verify Payment=====");
	}
	
	@DataProvider(name="verifyPayment-data")
	public Object[][] verifyPaymentData() throws IOException {
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Verify Payment", 1);
		System.out.println("masuk");
		return data;
	}
	
	@DataProvider(name="createBill-data")
	public Object[][] createbillData() throws IOException{
		XLSutils xlsHandler= new XLSutils();
		Object[][] data = xlsHandler.getAllCellData(xlsTestFile, "Verify Payment Precondition"
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
	
	@Test(dependsOnMethods= {"createBillData"})
	public void insertSomePayment() {
		Response response;
		localRequestHandler.updateBalance("83833833834",1000000);
		response=paymentRequestHandler.insertPayment(localRequestHandler.getIdUser("83833833834"), localRequestHandler.getIdBill("02411111111"), "1");
		response=paymentRequestHandler.insertPayment(localRequestHandler.getIdUser("83833833834"), localRequestHandler.getIdBill("02422222222"), "2");
		response=paymentRequestHandler.insertPayment(localRequestHandler.getIdUser("83833833834"), localRequestHandler.getIdBill("02433333333"), "2");
	}
	
	@Test(dataProvider="verifyPayment-data", dependsOnMethods= {"insertSomePayment"})
	public void verifyPaymentTest(String testCaseId, String description, String idUser, 
			String idBill, String idPaymentMethod, String expectedStatusCode, String expectedMessage) {
		logger.info("");
		logger.info("");
		logger.info("Test Case ID :"+testCaseId+" - Description :"+description);
		Response response;
		if(idUser.contains("Depend")) {
			String[] splitString =  idUser.split("#");
			String userTelephoneNumber=splitString[1];
			idUser = localRequestHandler.getIdUser(userTelephoneNumber);
		}
		if(idBill.contains("Depend")){;
			String[] splitString =  idBill.split("#");
			String billTelephoneNumber=splitString[1];
			idBill=localRequestHandler.getIdBill(billTelephoneNumber);
			logger.info("idBill="+idBill);
		}
		response=paymentRequestHandler.verifyPayment(idUser, idBill, idPaymentMethod);
		
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
				body("status",equalTo(expectedStatusCode)).
				content(containsString(expectedMessage));
			logger.info("success!");
		}
		else if (expectedStatusCode.equals("400")) {
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
