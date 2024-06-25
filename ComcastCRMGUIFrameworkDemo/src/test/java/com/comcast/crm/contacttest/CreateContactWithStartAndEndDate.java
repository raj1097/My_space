package com.comcast.crm.contacttest;

import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateContactWithStartAndEndDate
{
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		// Creating objects for utilities 
		FileUtility fu = new FileUtility();
		ExcelUtility eu = new ExcelUtility();
		JavaUtility ju = new JavaUtility();
		WebDriverUtility wu = new WebDriverUtility();
		
		// Get values from properties file based on key
		String Browser = fu.getDataFromPropertiesFile("browser");
		String Url = fu.getDataFromPropertiesFile("url");
		String UN = fu.getDataFromPropertiesFile("username");
		String PW = fu.getDataFromPropertiesFile("password");
		// Random number
		Random r = new Random();
		int nextInt = r.nextInt(1000);
		// Getting data from Excel and Java utility
		String LastName = eu.getDataFromExcel("Contact", 1, 2)+ju.getRandomNum();
		// Date 
		String StartDate = ju.getSystemDateYYYYMMDD(); // Current Date
		String EndDate = ju.getRequiredDate(30);	 // Required End Date
		
		// driver configuration
		WebDriver driver=null;
		if(Browser.equalsIgnoreCase("chrome"))
			driver=new ChromeDriver();
		else if (Browser.equalsIgnoreCase("edge"))
			driver=new EdgeDriver();
		else if(Browser.contentEquals("firefox"))
			driver =new FirefoxDriver();
		else if(Browser.equalsIgnoreCase("IED"))
			driver= new InternetExplorerDriver();
		else
			driver=new ChromeDriver();
		
		// Script
		wu.waitForPageToLoad(driver);
		driver.manage().window().maximize();
		driver.get(Url);
		//Login into CRM 
		driver.findElement(By.name("user_name")).sendKeys(UN);
		driver.findElement(By.name("user_password")).sendKeys(PW);
		driver.findElement(By.id("submitButton")).click();
		
		// Creating new Contact
		driver.findElement(By.linkText("Contacts")).click(); // Contact module
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();  // Create Contact button
		// Entering data
		driver.findElement(By.name("lastname")).sendKeys(LastName) ;	// Last name 
		// Clearing dates 
		driver.findElement(By.name("support_start_date")).clear();
		driver.findElement(By.name("support_end_date")).clear();
		// Pasing Dates 
		driver.findElement(By.name("support_start_date")).sendKeys(StartDate);
		driver.findElement(By.name("support_end_date")).sendKeys(EndDate);
			Thread.sleep(3000);
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])")).click();  // Save button
		
		String StartV = driver.findElement(By.id("dtlview_Support Start Date")).getText();  // Start date from App
		String EndV = driver.findElement(By.id("dtlview_Support End Date")).getText();  // End date from App
		// Validating dates 
		if(StartV.equals(StartDate))
			System.out.println(StartV+" Start date is as Expected ======> Pass");
		else
			System.out.println(StartV+" start date is not as Expected ======> Fail");
		
		if(EndV.equals(EndDate))
			System.out.println(EndV+" End date is as Expected ======> Pass");
		else
			System.out.println(EndV+" End date is not as Expected ======> Fail");
		System.out.println(LastName+" Last Name");
		 
		System.out.println(EndV+ "<===== "+ EndDate);
		//Close browser
		driver.quit();
	}

}
