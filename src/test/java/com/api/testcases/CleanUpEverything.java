package com.api.testcases;

import java.util.List;
import org.testng.annotations.*;
import com.api.models.*;
import com.api.config.Config;
import com.api.requesthandler.*;
import io.restassured.response.Response;


public class CleanUpEverything extends Config{
	private UserHandler userRequestHandler = new UserHandler();
	private BillHandler billRequestHandler = new BillHandler();
	private PaymentHandler paymentRequestHandler = new PaymentHandler();
	private LocalHandler localRequestHandler = new LocalHandler();
	
	@Test
	public void cleanUp() {
		Response response;
		response = paymentRequestHandler.getAllPayment();
		String idUser = localRequestHandler.getIdUser("83833833834");
		List<Integer> idPayments = response.jsonPath().getList("data.findAll{it.idUser=="+idUser+"}.idPayment");
		logger.info("clean payment data...");
		for(Integer idPayment : idPayments) {
			paymentRequestHandler.deletePayment(idPayment);
		}
		logger.info("success!");
		logger.info("clean bill data...");
		for	(Bill bill:bills) {
			billRequestHandler.deleteBill(bill.getIdBill());
		}
		logger.info("success!");
		logger.info("clean user data...");
		userRequestHandler.delete(Integer.parseInt(idUser));
		logger.info("success!");
	}
}
