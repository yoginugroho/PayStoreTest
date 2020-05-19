package com.api.models;

public class Payment {
	private String idPayment;
    private int idUser;
    private String name;
    private String phoneNumber;
    private double balance;
    private int idBill;
    private String telephoneNumber;
    private String status;
    private double amount;
    private int idPaymentMethod;
    private String method;
    private String timestamp;
    
	public Payment(int idUser, int idBill, int idPaymentMethod) {
		super();
		this.idUser = idUser;
		this.idBill = idBill;
		this.idPaymentMethod = idPaymentMethod;
	}
	
	public String getIdPayment() {
		return idPayment;
	}
	public void setIdPayment(String idPayment) {
		this.idPayment = idPayment;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getIdBill() {
		return idBill;
	}
	public void setIdBill(int idBill) {
		this.idBill = idBill;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getIdPaymentMethod() {
		return idPaymentMethod;
	}
	public void setIdPaymentMethod(int idPaymentMethod) {
		this.idPaymentMethod = idPaymentMethod;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
