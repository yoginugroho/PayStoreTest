package com.api.requesthandler;

import static io.restassured.RestAssured.*;
import org.json.simple.JSONObject;
import com.api.config.Config;
import io.restassured.response.Response;


@SuppressWarnings("unchecked")
public class UserHandler extends Config{
	
	public UserHandler() {
		super();
	}

	public Response createAccount(String name, String email, String phoneNumber, String password) {
		String endPoint = "/user";
		Response response;
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("name",name);
		requestBody.put("phoneNumber", phoneNumber);
		requestBody.put("email", email);
		requestBody.put("password",password);
		
		
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
	
	public Response login(String phoneNumber, String password) {
		Response response;
		String endPoint = "/user/login";
		String url=baseUrl + endPoint;	
		JSONObject requestBody = new JSONObject();
		requestBody.put("phoneNumber", phoneNumber);	
		requestBody.put("password", password);
			
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
	
	public Response requestOTP(String phoneNumber, String email) {
		Response response;
		String endPoint = "/user/otp";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("phoneNumber", phoneNumber);
		requestBody.put("email", email);
		
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
	
	public Response getUserProfile(String idUser) {
		Response response;
		String endPoint = "/user/profile";
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
	
	public Response logout(String idUser) {
		Response response;
		String endPoint = "/user/logout";
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
	
	public Response update(int idUser, String name, String email, String password, String phoneNumber, float balance ) {
		Response response;
		String endPoint = "/user";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("idUser",idUser);
		requestBody.put("name",name);
		requestBody.put("email",email);
		requestBody.put("password",password);
		requestBody.put("phoneNumber",phoneNumber);
		requestBody.put("balance",balance);
		response =
			given().
				header("Content-Type","application/json").
				header("Accept","application/json").
				body(requestBody.toString()).
			when().
				put(url).
				then().extract().response();
		return response;
	}
	
	public Response delete(int idUser) {
		Response response;
		String endPoint = "/user";
		String url=baseUrl + endPoint;
		JSONObject requestBody = new JSONObject();
		requestBody.put("idUser",idUser);
		
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
