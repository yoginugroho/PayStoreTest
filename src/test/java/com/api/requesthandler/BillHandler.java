package com.api.requesthandler;

import static io.restassured.RestAssured.*;
import org.json.simple.JSONObject;
import com.api.config.Config;
import io.restassured.response.Response;

@SuppressWarnings("unchecked")
public class BillHandler extends Config {
	
	public BillHandler() {
		super();
	}

	public Response createBill(String telephoneOwner, String telephoneNumber, 
		String month, double amount, String status) {
		Response response;
		String endPoint = "/bill";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("telephoneOwner",telephoneOwner);
		requestBody.put("telephoneNumber",telephoneNumber);
		requestBody.put("month", month);
		requestBody.put("amount",amount);
		requestBody.put("status",status);
		
		
		response =
			given().
				header("Content-Type","application/json").
				header("Accept","application/json").
				body(requestBody.toString()).
			when().
				post(url).
			then().extract().response();
		return response;
	}
	
	public Response updateBill(int idBill, String telephoneOwner, String telephoneNumber, 
			String month, double amount, String status) {
		Response response;
		String endPoint = "/bill";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("idBill",idBill);
		requestBody.put("telephoneOwner",telephoneOwner);
		requestBody.put("telephoneNumber",telephoneNumber);
		requestBody.put("month", month);
		requestBody.put("amount",amount);
		requestBody.put("status",status);
		
		response =
			given().
				header("Content-Type","application/json").
				header("Accept","application/json").
				body(requestBody.toString()).
			when().
				put	(url).
			then().extract().response();
		return response;
	}
	
	public Response deleteBill(int idBill) {
		Response response;
		String endPoint = "/bill";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("idBill",idBill);
		
		response =
			given().
				header("Content-Type","application/json").
				header("Accept","application/json").
				body(requestBody.toString()).
			when().
				delete(url).
			then().extract().response();
		return response;
	}
	
	public Response getUnpaidBill(String telephoneNumber) {
		Response response;
		String endPoint = "/bill/telephone/unpaid";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("telephoneNumber",telephoneNumber);
		System.out.println(requestBody.toString());
		response =
			given().
				header("Content-Type","application/json").
				header("Accept","application/json").
				body(requestBody.toString()).
			when().
				post(url).
			then().extract().response();
		return response;
	}
}
