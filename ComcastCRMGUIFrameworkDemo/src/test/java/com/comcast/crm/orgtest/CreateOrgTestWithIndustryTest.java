package com.comcast.crm.orgtest;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateOrgTestWithIndustryTest
{
	public static void main(String[] args) throws IOException 
	{

		// Creating objects for utilities
		FileUtility fu = new FileUtility();
		ExcelUtility eu = new ExcelUtility();
		JavaUtility ju =  new JavaUtility();
		WebDriverUtility wu = new WebDriverUtility();
		
		// Get values from properties file based on key
		String Browser = fu.getDataFromPropertiesFile("browser");
		String Url =fu.getDataFromPropertiesFile("url");
		String UN =fu.getDataFromPropertiesFile("username");
		String PW =fu.getDataFromPropertiesFile("password");
		
		
		// Getting data from Excel
		String OrgName = eu.getDataFromExcel("Org", 4, 2)+ju.getRandomNum();
		String Industry = eu.getDataFromExcel("Org", 4, 3);
		String Type = eu.getDataFromExcel("Org", 4, 4);
		

		
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
		wu.maximizeWindow(driver);
		driver.get(Url);
		//Login into CRM 
		driver.findElement(By.name("user_name")).sendKeys(UN);
		driver.findElement(By.name("user_password")).sendKeys(PW);
		driver.findElement(By.id("submitButton")).click();
		
		// Creating new Organization
		driver.findElement(By.linkText("Organizations")).click(); // Organization module
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();  // Create Organization button
		// Entering data 
		driver.findElement(By.name("accountname")).sendKeys(OrgName) ;	// Organization name 
		WebElement IndDD = driver.findElement(By.name("industry"));
		Select Inds = new Select(IndDD);
		Inds.selectByVisibleText(Industry);
		
		WebElement TypeDD = driver.findElement(By.name("accounttype"));
		Select Tys = new Select(TypeDD);
		Tys.selectByVisibleText(Type);
		
		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])")).click();  // Save button
		
		String IndustryV = driver.findElement(By.id("dtlview_Industry")).getText();
		String TyV = driver.findElement(By.id("dtlview_Type")).getText();
		
		// Validate Industy and Type
		if(IndustryV.equals(Industry))
			System.out.println(IndustryV+" is created ======> Pass");
		else
			System.out.println(IndustryV+" is not created ======> Fail");
		
		// Type
		if(TyV.equals(Type))
			System.out.println(TyV+" is created ======> Pass");
		else
			System.out.println(TyV+" is not created ======> Fail");
			
		driver.quit();
		
		
	}

}
