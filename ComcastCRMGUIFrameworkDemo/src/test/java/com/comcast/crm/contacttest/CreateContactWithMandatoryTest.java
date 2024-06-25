package com.comcast.crm.contacttest;


import java.io.IOException;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

public class CreateContactWithMandatoryTest
{
	public static void main(String[] args) throws IOException
	{
		// Creating objects for utilities 
		FileUtility fu = new FileUtility();
		ExcelUtility eu = new ExcelUtility();
		JavaUtility ju = new JavaUtility();
		WebDriverUtility wu = new WebDriverUtility();
		
		// Get values from properties file based on key
		String Browser = fu.getDataFromPropertiesFile("browser");
		String Url = fu.getDataFromPropertiesFile("url");
		String UN  = fu.getDataFromPropertiesFile("username");
		String PW  = fu.getDataFromPropertiesFile("password");
		
		// Random number
		Random r = new Random();
		int nextInt = r.nextInt(1000);
		
		// Getting data from Excel
		String LastName = eu.getDataFromExcel("Contact", 1, 2)+ju.getRandomNum();
		
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
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])")).click();  // Save button
		
		String Titleinfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();  // Title of created Contact
		// Validating Header msg
		if(Titleinfo.contains(LastName))
			System.out.println(LastName+" contact created  ======> Pass");
		else
			System.out.println(LastName+" contact is not created ======> Fail");
		//Close browser
		driver.quit();
	}

}
