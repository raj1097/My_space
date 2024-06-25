package com.comcast.crm.pomclass;


import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepository.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepository.HomePage;
import com.comcast.crm.objectrepository.LoginPage;
import com.comcast.crm.objectrepository.OrganizationInformationPage;
import com.comcast.crm.objectrepository.OrganizationPage;

public class CreateOrgTestWithPhoneNumber
{
	public static void main(String[] args) throws IOException 
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
		String OrgName = eu.getDataFromExcel("Org", 7, 2)+ju.getRandomNum();
		String Phone = eu.getDataFromExcel("Org", 7, 3);
		
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
		
		// Creating Objects for ObjectRepository
		LoginPage lp = new LoginPage(driver);
		HomePage hp = new HomePage(driver);
		OrganizationPage op = new OrganizationPage(driver);
		CreatingNewOrganizationPage cop = new CreatingNewOrganizationPage(driver);
		OrganizationInformationPage oip = new OrganizationInformationPage(driver);
		
		// Script
		lp.getloginOperation(Url,UN, PW);
		
		// Creating new Organization
		hp.getOrganizationsMod().click();
		op.getCreateOrgBt().click();
		
		// Entering data
		cop.CreateOrg(OrgName, Phone);
		
		// verify Phone number
		String PhV = oip.getPhoneTxtIP().getText();
		if(PhV.contains(Phone))
			System.out.println(PhV+" is Verified ======> Pass");
		else
			System.out.println(PhV+" is not Verified ======> Fail");
		
		hp.getSignOutOperation(driver);
		
		driver.quit();	
		
	}

}
