package practicebasics;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtendReportTest 
{
	
	ExtentReports report;
	ExtentSparkReporter spark;
	@BeforeSuite
	public void configBS()
	{
		spark = new ExtentSparkReporter("./AdvanceReport/report.html");
		spark.config().setDocumentTitle("Practice Extent Report");
		spark.config().setReportName("Practice Report");
		spark.config().setTheme(Theme.STANDARD);
		
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows 10");
	}
	
	@AfterSuite
	public void configAS()
	{
		report.flush();
	}
	
	
	@Test
	public void reportTest()
	{
		ExtentTest test = report.createTest("reportTest");
		
		test.log(Status.INFO, "Test start");
		if("raj".equals("Raj"))
			test.log(Status.PASS, "Success");
		else
			test.log(Status.FAIL, "Fail");
	}
	
	@Test
	public void reportTest1()
	{
		ExtentTest test = report.createTest("reportTest1");
		
		test.log(Status.INFO, "Test start");
		if("raj".equalsIgnoreCase("Raj"))
			test.log(Status.PASS, "Success");
		else
			test.log(Status.FAIL, "Fail");
	}
	
	@Test
	public void reportTest2()
	{
		ExtentTest test = report.createTest("reportTest2");
		
		test.log(Status.INFO, "Test start");
		if("raj".equalsIgnoreCase("Raj"))
			test.log(Status.PASS, "Success");
		else
			test.log(Status.FAIL, "Fail");
	}
	
	@Test
	public void reportTest3()
	{
		WebDriver driver = new ChromeDriver();
		driver.get("https://chatgpt.com/");
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		String Filepath = ts.getScreenshotAs(OutputType.BASE64);
		
		
		ExtentTest test = report.createTest("reportTest3");
		
		test.log(Status.INFO, "Test start");
		if("raj".equals("Raj"))
			test.log(Status.PASS, "Success");
		else
		{
			test.addScreenCaptureFromBase64String(Filepath,"ErrorFile");
			test.log(Status.FAIL, "Fail");
		}
	}
	@Test
	public void reportTest4s()
	{
		ExtentTest test = report.createTest("reportTest4");
		
		test.log(Status.INFO, "Test start");
		if("raj".equals("Raj"))
			test.log(Status.PASS, "Success");
		else
			test.log(Status.FAIL, "Fail");
	}
}
