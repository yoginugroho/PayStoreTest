package com.api.requesthandler;

import static io.restassured.RestAssured.*;
import org.json.simple.JSONObject;
import com.api.config.Config;
import io.restassured.response.Response;

@SuppressWarnings("unchecked")
public class PaymentHandler extends Config {
	public PaymentHandler() {
		super();
	}
	public Response insertPayment(String idUser, String idBill, String idPaymentMethod) {
		Response response;
		String endPoint = "/payment";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("idUser",idUser);
		requestBody.put("idBill",idBill);
		requestBody.put("idPaymentMethod", idPaymentMethod);
		
		
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
	
	public Response verifyPayment(String idUser, String idBill, String idPaymentMethod) {
		Response response;
		String endPoint = "/payment/verify";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("idUser",idUser);
		requestBody.put("idBill",idBill);
		requestBody.put("idPaymentMethod", idPaymentMethod);
		
		
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
	
	public Response getAllPayment() {
		Response response;
		String endPoint = "/payment/all";
		String url=baseUrl + endPoint;
		
		response =
			given().
				header("Content-Type","application/json").
				header("Accept","application/json").
			when().
				get(url).
			then().extract().response();
		return response;
	}
	
	public Response getPaymentHistory(String idUser){
		Response response;
		String endPoint = "/payment/history";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("idUser",idUser);
	
	
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
	
	public Response deletePayment(int idPayment) {
		Response response;
		String endPoint = "/payment";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("idPayment",idPayment);
	
	
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
}
