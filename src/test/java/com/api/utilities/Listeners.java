package com.api.utilities;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Listeners extends TestListenerAdapter {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	public void onStart(ITestContext testContext)
	{
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/reports/testReport.html");

		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Rest API testing Report");
		htmlReporter.config().setTheme(Theme.DARK);

		extent=new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host name","localhost");
		extent.setSystemInfo("Environtment","QA");
		extent.setSystemInfo("user","muhammad yogie nugroho");

	}
	
	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getName()); // create new entry in th report
		test.log(Status.PASS,"Test Case PASSED is "+result.getName());
		
	}

	public void onTestFailure(ITestResult result) 
	{
		test=extent.createTest(result.getName()); 
		test.log(Status.FAIL,"Test Case FAILED is "+result.getName());
		test.log(Status.FAIL,"Test Case FAILED is "+result.getThrowable());
	}
	
	public void onFinish(ITestContext textContext) 
	{
		extent.flush();
	}
	

}
