package com.api.models;

public class Bill {
	private int idBill;
	private String telephoneOwner;
	private String telephoneNumber;
	private String month;
	private double amount;
	private String status;
	

	public Bill(int idBill, String telephoneOwner, String telephoneNumber, String month, double amount, String status) {
		super();
		this.idBill = idBill;
		this.telephoneOwner = telephoneOwner;
		this.telephoneNumber = telephoneNumber;
		this.month = month;
		this.amount = amount;
		this.status = status;
	}
	public int getIdBill() {
		return idBill;
	}
	public void setIdBill(int idBill) {
		this.idBill = idBill;
	}
	public String getTelephoneOwner() {
		return telephoneOwner;
	}
	public void setTelephoneOwner(String telephoneOwner) {
		this.telephoneOwner = telephoneOwner;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
