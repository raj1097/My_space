package com.comcast.crm.pomclass;


import java.io.IOException;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepository.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepository.HomePage;
import com.comcast.crm.objectrepository.LoginPage;
import com.comcast.crm.objectrepository.OrganizationInformationPage;
import com.comcast.crm.objectrepository.OrganizationPage;

public class CreateOrgTestWithName
{
	public static void main(String[] args) throws IOException, InterruptedException 
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
		
		// Getting data from Excel
		String OrgName = eu.getDataFromExcel("Org", 10, 2)+ju.getRandomNum();
		
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
		
		// Navigating to Org page and Creating new Org
		hp.getOrganizationsMod().click();  // OrgMod
		op.getCreateOrgBt().click();  // Creating new Org Button
		
		// Entering data and Saving
		cop.CreateOrg(OrgName);
		
		// Validating Header msg
		String Titleinfo = oip.getOrgHeaderIP().getText();
		
		if(Titleinfo.contains(OrgName))
			System.out.println(OrgName+" is created ======> Pass");
		else
			System.out.println(OrgName+" is not created ======> Fail");
		
		// Verify orgName
		String ActualOrg = oip.getOrgNameIP().getText();
		
		if(ActualOrg.equals(OrgName))
			System.out.println(ActualOrg+" is created ======> Pass");
		else
			System.out.println(ActualOrg+" is not created ======> Fail");
		
		// Navigate to Org page
		Thread.sleep(2000);
		hp.getOrganizationsMod().click();
		
		// Change in DD to Org Name
		Select sel = new Select(op.getInDD());
		sel.selectByVisibleText(eu.getDataFromExcel("Org", 10, 3));
		
		// Search Org name
		
		op.getSearchBar().sendKeys(OrgName);		
		
		// click on Search now bt
		
		op.getSearchBt().click();
		Thread.sleep(2000);
		String OrgDel ="//a[text()='"+OrgName+"' and @title='Organizations']/ancestor::tr[@class='lvtColData']/descendant::a[.='del']";
		Thread.sleep(2000);
		driver.findElement(By.xpath(OrgDel)).click();
		
		wu.switchTOAlertAndAccept(driver);
	
		hp.getSignOutOperation(driver);
		
		driver.quit();
	}

}
