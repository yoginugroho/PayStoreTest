package com.api.config;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.api.models.Bill;
import com.api.models.User;


public class Config {

	public static Logger logger;
	public static String baseUrl;
	public static String xlsTestFile;
	public static String xlsReportFile;
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Bill> bills = new ArrayList<Bill>();
	@BeforeClass
	public static void setUp() {
		baseUrl = "https://infinite-waters-35921.herokuapp.com/api/paystore";
		//baseUrl="https://infinite-waters-35921.herokuapp.com/api/paystore";
		xlsTestFile=System.getProperty("user.dir")+"/test-data/testData.xls";
		logger = Logger.getLogger("Config");
		PropertyConfigurator.configure("Log4j.properties");
		logger.setLevel(Level.INFO);

	}

}