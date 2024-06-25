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
		
		//Creating Objects for Object repository
		LoginPage lp = new LoginPage(driver);
		HomePage hp = new HomePage(driver);
		ContactsPage cp = new ContactsPage(driver);
		CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
		ContactInformationPage cip = new ContactInformationPage(driver);
		
		// Script
		
		//Login into CRM 
		lp.getloginOperation(Url,UN, PW);
		
		// Creating new Contact
		hp.getContactsMod().click();
		cp.getCreateNewConBt().click();
		
		// Entering data
		ccp.getLastNameTxtF().sendKeys(LastName);
		ccp.getSaveBt().click();

		// Validating Header msg
		String Titleinfo = cip.getConHeaderIP().getText();
		if(Titleinfo.contains(LastName))
			System.out.println(LastName+" contact created  ======> Pass");
		else
			System.out.println(LastName+" contact is not created ======> Fail");
		//Close browser
		driver.quit();
	}

}
