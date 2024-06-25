package com.comcast.crm.contacttest;


import java.util.Random;
import java.util.Set;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateContactWithOrg 
{
	public static void main(String[] args) throws Throwable 
	{
	
	// Creating objects for utilities 
	FileUtility fu = new FileUtility();
	ExcelUtility eu = new ExcelUtility();
	JavaUtility ju = new JavaUtility();
	WebDriverUtility wu = new WebDriverUtility();
	
	// Get values from properties file based on key
	String Browser =fu.getDataFromPropertiesFile("browser");
	String Url =fu.getDataFromPropertiesFile("url");
	String UN =fu.getDataFromPropertiesFile("username");
	String PW =fu.getDataFromPropertiesFile("password");
	
	// Random number
	Random r = new Random();
	int nextInt = r.nextInt(1000);
	// Getting data from Excel
	
	String OrgName = eu.getDataFromExcel("Contact", 7, 2)+ju.getRandomNum();
	String LastName = eu.getDataFromExcel("Contact", 7, 3)+ju.getRandomNum();
	
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
	
	// Creating new Organization
	driver.findElement(By.linkText("Organizations")).click(); // Organization module
	driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();  // Create Organization button
	// Entering data
	driver.findElement(By.name("accountname")).sendKeys(OrgName) ;	// Organization name 
	driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])")).click();  // Save button
	
	String Titleinfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();  // Title of created Org
	// Validating Header msg
	if(Titleinfo.contains(OrgName))
		System.out.println(OrgName+" Header is verified ======> Pass");
	else
		System.out.println(OrgName+" Header is not as expected ======> Fail");
	
	// Verify orgName
	String ActualOrg = driver.findElement(By.id("dtlview_Organization Name")).getText();
	if(ActualOrg.equals(OrgName))
		System.out.println(ActualOrg+" Name is Verified ======> Pass");
	else
		System.out.println(ActualOrg+" Name is not as expected  ======> Fail");
	
	
	// Creating new Contact
	driver.findElement(By.linkText("Contacts")).click(); // Contact module
	driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();  // Create Contact button
	// Entering data
	driver.findElement(By.name("lastname")).sendKeys(LastName) ;	// Last name
	driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click(); // Lookup button
	
	//switching to child window
	wu.switchToTabBasedOnURl(driver, "module=Accounts&action");
	// Child window operations
	driver.findElement(By.name("search_text")).sendKeys(OrgName); // Search bar in lookup window
	driver.findElement(By.name("search")).click();  //search button
	driver.findElement(By.xpath("//a[text()='"+OrgName+"']")).click();
	
	//switching back to parent window
	wu.switchToTabBasedOnTitle(driver, "module=Contacts&action");
	driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])")).click();  // Save button
	
	String Titleinfo1 = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();  // Title of created Contact
	String OrgV = driver.findElement(By.id("mouseArea_Organization Name")).getText();

	// Validating Header msg
	if(Titleinfo1.contains(LastName))
		System.out.println(LastName+" contact created  ======> Pass");
	else
		System.out.println(LastName+" contact is not created ======> Fail");
	// Validating Orgname
	if(OrgV.trim().equals(OrgName))
		System.out.println(OrgName+" is verified =======> Pass");
	else
		System.out.println(OrgName+" is not as expected =======> Fail");
	
	driver.quit();
	}
	

}
