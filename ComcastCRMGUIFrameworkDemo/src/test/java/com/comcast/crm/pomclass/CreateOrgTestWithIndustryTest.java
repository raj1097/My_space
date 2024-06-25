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
import com.comcast.crm.objectrepository.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepository.HomePage;
import com.comcast.crm.objectrepository.LoginPage;
import com.comcast.crm.objectrepository.OrganizationInformationPage;
import com.comcast.crm.objectrepository.OrganizationPage;

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
		
		// Creating Objects for ObjectRepository
		LoginPage lp = new LoginPage(driver);
		HomePage hp = new HomePage(driver);
		OrganizationPage op = new OrganizationPage(driver);
		CreatingNewOrganizationPage cop = new CreatingNewOrganizationPage(driver);
		OrganizationInformationPage oip = new OrganizationInformationPage(driver);
		
		//Login
		lp.getloginOperation(Url,UN, PW);
		
		hp.getOrganizationsMod().click();
		op.getCreateOrgBt().click();
		
		// Entering data 
		cop.CreateOrg(OrgName, Industry, Type);
		
		// Validate Industy and Type
		String IndustryV = oip.getIndustryDDIP().getText();
		if(IndustryV.equals(Industry))
			System.out.println(IndustryV+" is created ======> Pass");
		else
			System.out.println(IndustryV+" is not created ======> Fail");
		// Type Validation
		String TyV = oip.getTypeDDIP().getText();
		if(TyV.equals(Type))
			System.out.println(TyV+" is created ======> Pass");
		else
			System.out.println(TyV+" is not created ======> Fail");
		
		hp.getSignOutOperation(driver);
		
		driver.quit();
		
		
	}

}
