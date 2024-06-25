package com.comcast.crm.pomclass;



import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import com.comcast.crm.objectrepository.ContactInformationPage;
import com.comcast.crm.objectrepository.ContactsPage;
import com.comcast.crm.objectrepository.CreatingNewContactPage;
import com.comcast.crm.objectrepository.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepository.HomePage;
import com.comcast.crm.objectrepository.LoginPage;
import com.comcast.crm.objectrepository.OrgChildWinInContactMod;
import com.comcast.crm.objectrepository.OrganizationInformationPage;
import com.comcast.crm.objectrepository.OrganizationPage;

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
	
	// Creating Objects for Object repository
	LoginPage lp = new LoginPage(driver);
	HomePage hp = new HomePage(driver);
	OrganizationPage op = new OrganizationPage(driver);
	CreatingNewOrganizationPage cop = new CreatingNewOrganizationPage(driver);
	OrganizationInformationPage oip = new OrganizationInformationPage(driver);
	ContactsPage cp = new ContactsPage(driver);
	CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
	ContactInformationPage cip = new ContactInformationPage(driver);
	OrgChildWinInContactMod ocp = new OrgChildWinInContactMod(driver);
	
	// Script
	lp.getloginOperation(Url,UN, PW);
	
	// Creating new Organization
	
	hp.getOrganizationsMod().click();
	op.getCreateOrgBt().click();
	cop.CreateOrg(OrgName);
	
	String Titleinfo = oip.getOrgHeaderIP().getText();
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
	hp.getContactsMod().click();
	cp.getCreateNewConBt().click();

	// Entering data
	ccp.getLastNameTxtF().sendKeys(LastName);
	ccp.getSelectOrgBt().click();
		
	//switching to child window
	wu.switchToTabBasedOnURl(driver, "module=Accounts&action");
	// Child window operations
	
	
	ocp.getSearchBar().sendKeys(OrgName);
	
	ocp.getSearchBt().click();
	driver.findElement(By.xpath("//a[text()='"+OrgName+"']")).click();
	
	//switching back to parent window
	wu.switchToTabBasedOnTitle(driver, "module=Contacts&action");
	
	ccp.getSaveBt().click();
	String Titleinfo1 = cip.getConHeaderIP().getText();
	String OrgV = cip.getOrgNameCIP().getText();

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
	
	hp.getSignOutOperation(driver);
	
	driver.quit();
	}
	

}
