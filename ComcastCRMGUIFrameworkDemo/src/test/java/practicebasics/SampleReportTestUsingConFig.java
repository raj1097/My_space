package practicebasics;

import java.io.IOException;

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

public class SampleReportTestUsingConFig 
{
	ExtentReports report;
	@BeforeSuite
	public void configBS()
	{
		//Spark report config
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvanceReport/report.html");
		spark.config().setDocumentTitle("CRM test suite result");
		spark.config().setReportName("CRM report");
		spark.config().setTheme(Theme.DARK);


		//add environment information
		report = new ExtentReports();   
		report.attachReporter(spark);
		report.setSystemInfo("OS", "windows-11");
		report.setSystemInfo("BROWSER", "chrome-200");

	}

	@AfterSuite
	public void configAS()
	{
		report.flush();
	}

	@Test
	public void createContactTest()
	{
		ExtentTest test = report.createTest("createContactTest");
		test.log(Status.INFO,"Login to app");
		test.log(Status.INFO,"navigate to contact page");
		test.log(Status.INFO,"create contact");
		if("HDFC".equals("HDFC"))
			test.log(Status.PASS,"contact is created");
		else

			test.log(Status.FAIL,"contact is not created");
	}


	@Test
	public void createOrgTest()
	{
		ExtentTest test = report.createTest("createOrgTest");
		test.log(Status.INFO,"Login to app");
		test.log(Status.INFO,"navigate to contact page");
		test.log(Status.INFO,"create contact");
		if("HDFC".equals("HDFC"))
			test.log(Status.PASS,"contact is created");
		else
			test.log(Status.FAIL,"contact is not created");
	}


	@Test
	public void createContactwithPhoneNum() throws IOException
	{
		//how to attach take screenshot to extent report
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888");

		TakesScreenshot ts = (TakesScreenshot)driver;
		String filepath= ts.getScreenshotAs(OutputType.BASE64);


		ExtentTest test = report.createTest("createContactwithPhoneNum");
		test.log(Status.INFO,"Login to app");
		test.log(Status.INFO,"navigate to contact page");
		test.log(Status.INFO,"create contact");
		if("HDFC".equals("HFDC"))
		{
			test.log(Status.PASS,"contact is created");
		}
		else
		{
			//test.log(Status.FAIL,"contact is not created");
			test.addScreenCaptureFromBase64String(filepath, "Errorfile");
		}
		
		driver.quit();
	}
}
