package com.api.requesthandler;

import com.api.config.Config;
import com.api.models.Bill;
import com.api.models.User;

public class LocalHandler extends Config{
	UserHandler userRequestHandler = new UserHandler();
	public String getIdUser(String phoneNumber) {
		for(User user : users) {
			if(user.getPhoneNumber().equals(phoneNumber)) {
				return String.valueOf(user.getIdUser());
			}
		}
		return null;
	}
	
	public String getIdBill(String telephoneNumber) {
		for(Bill bill : bills) {
			if(bill.getTelephoneNumber().equals(telephoneNumber)) {
				return String.valueOf(bill.getIdBill());
			}
		}
		return null;
	}
	
	public void updateBalance(String phoneNumber, float balance) {
		int idUser=0;
		String name=null;
		String email=null;
		String password=null;
		for(User user : users) {
			if(user.getPhoneNumber().equals(phoneNumber)) {
				idUser=user.getIdUser();
				name=user.getName();
				email=user.getEmail();
				password=user.getPassword();
			}
		} 
		userRequestHandler.update(idUser, name, email, password, phoneNumber, balance);
	}
}
