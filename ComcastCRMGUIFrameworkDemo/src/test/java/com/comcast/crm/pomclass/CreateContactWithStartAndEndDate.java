package com.comcast.crm.pomclass;



import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepository.ContactInformationPage;
import com.comcast.crm.objectrepository.ContactsPage;
import com.comcast.crm.objectrepository.CreatingNewContactPage;
import com.comcast.crm.objectrepository.HomePage;
import com.comcast.crm.objectrepository.LoginPage;

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
		
		//Creating Objects for Object repository
		LoginPage lp = new LoginPage(driver);
		HomePage hp = new HomePage(driver);
		ContactsPage cp = new ContactsPage(driver);
		CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
		ContactInformationPage cip = new ContactInformationPage(driver);
		
		// Login
		lp.getloginOperation(Url,UN, PW);
		
		// Creating new Contact
		hp.getContactsMod().click();
		cp.getCreateNewConBt().click();
		
		// Entering data
		ccp.getLastNameTxtF().sendKeys(LastName);
		// Clearing dates 
		ccp.getSupStDate().clear();
		ccp.getSupEndDate().clear();
		// Pasing Dates 
		ccp.getSupStDate().sendKeys(StartDate);
		ccp.getSupEndDate().sendKeys(EndDate);
		ccp.getSaveBt().click();
		
		String StartV = cip.getSupStDtIP().getText();
		String EndV = cip.getSupEndDtIP().getText();
		
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
