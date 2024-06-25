package com.comcast.crm.annotations.copy;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;

import org.testng.annotations.Test;

import com.comcast.crm.baseclassimp.BaseClass;

import com.comcast.crm.objectrepository.ContactInformationPage;
import com.comcast.crm.objectrepository.ContactsPage;
import com.comcast.crm.objectrepository.CreatingNewContactPage;
import com.comcast.crm.objectrepository.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepository.HomePage;
import com.comcast.crm.objectrepository.LoginPage;
import com.comcast.crm.objectrepository.OrgChildWinInContactMod;
import com.comcast.crm.objectrepository.OrganizationInformationPage;
import com.comcast.crm.objectrepository.OrganizationPage;

public class CreateContactTest extends BaseClass {
	@Test
	public void CreateContactWithMandatoryTest() throws EncryptedDocumentException, IOException {
		// Creating objects for POM
		HomePage hp = new HomePage(driver);
		ContactsPage cp = new ContactsPage(driver);
		CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
		ContactInformationPage cip = new ContactInformationPage(driver);

		// Getting data from Excel
		String LastName = eu.getDataFromExcel("Contact", 1, 2) + ju.getRandomNum();

		// Creating new Contact
		hp.getContactsMod().click();
		cp.getCreateNewConBt().click();

		// Entering data
		ccp.getLastNameTxtF().sendKeys(LastName);
		ccp.getSaveBt().click();

		// Validating Header msg
		String Titleinfo = cip.getConHeaderIP().getText();
		if (Titleinfo.contains(LastName))
			System.out.println(LastName + " contact created  ======> Pass");
		else
			System.out.println(LastName + " contact is not created ======> Fail");
	}

	@Test
	public void createContactWithOrg() throws EncryptedDocumentException, IOException {
		// Getting data from Excel

		String OrgName = eu.getDataFromExcel("Contact", 7, 2) + ju.getRandomNum();
		String LastName = eu.getDataFromExcel("Contact", 7, 3) + ju.getRandomNum();

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

		// Creating new Organization

		hp.getOrganizationsMod().click();
		op.getCreateOrgBt().click();
		cop.CreateOrg(OrgName);

		String Titleinfo = oip.getOrgHeaderIP().getText();
		// Validating Header msg
		if (Titleinfo.contains(OrgName))
			System.out.println(OrgName + " Header is verified ======> Pass");
		else
			System.out.println(OrgName + " Header is not as expected ======> Fail");

		// Verify orgName
		String ActualOrg = driver.findElement(By.id("dtlview_Organization Name")).getText();
		if (ActualOrg.equals(OrgName))
			System.out.println(ActualOrg + " Name is Verified ======> Pass");
		else
			System.out.println(ActualOrg + " Name is not as expected  ======> Fail");

		// Creating new Contact
		hp.getContactsMod().click();
		cp.getCreateNewConBt().click();

		// Entering data
		ccp.getLastNameTxtF().sendKeys(LastName);
		ccp.getSelectOrgBt().click();

		// switching to child window
		wu.switchToTabBasedOnURl(driver, "module=Accounts&action");
		// Child window operations

		ocp.getSearchBar().sendKeys(OrgName);

		ocp.getSearchBt().click();
		driver.findElement(By.xpath("//a[text()='" + OrgName + "']")).click();

		// switching back to parent window
		wu.switchToTabBasedOnTitle(driver, "module=Contacts&action");

		ccp.getSaveBt().click();
		String Titleinfo1 = cip.getConHeaderIP().getText();
		String OrgV = cip.getOrgNameCIP().getText();

		// Validating Header msg
		if (Titleinfo1.contains(LastName))
			System.out.println(LastName + " contact created  ======> Pass");
		else
			System.out.println(LastName + " contact is not created ======> Fail");
		// Validating Orgname
		if (OrgV.trim().equals(OrgName))
			System.out.println(OrgName + " is verified =======> Pass");
		else
			System.out.println(OrgName + " is not as expected =======> Fail");
	}

	@Test
	public void createContactWithStartAndEndDate() throws EncryptedDocumentException, IOException {

		// Getting data from Excel and Java utility
		String LastName = eu.getDataFromExcel("Contact", 1, 2) + ju.getRandomNum();
		// Date
		String StartDate = ju.getSystemDateYYYYMMDD(); // Current Date
		String EndDate = ju.getRequiredDate(30); // Required End Date

		// Creating Objects for Object repository
		LoginPage lp = new LoginPage(driver);
		HomePage hp = new HomePage(driver);
		ContactsPage cp = new ContactsPage(driver);
		CreatingNewContactPage ccp = new CreatingNewContactPage(driver);
		ContactInformationPage cip = new ContactInformationPage(driver);

		// Creating new Contact
		hp.getContactsMod().click();
		cp.getCreateNewConBt().click();

		// Entering data
		ccp.getLastNameTxtF().sendKeys(LastName);
		// Clearing dates
		ccp.getSupStDate().clear();
		ccp.getSupEndDate().clear();
		// Parsing Dates
		ccp.getSupStDate().sendKeys(StartDate);
		ccp.getSupEndDate().sendKeys(EndDate);
		ccp.getSaveBt().click();

		String StartV = cip.getSupStDtIP().getText();
		String EndV = cip.getSupEndDtIP().getText();

		// Validating dates
		if (StartV.equals(StartDate))
			System.out.println(StartV + " Start date is as Expected ======> Pass");
		else
			System.out.println(StartV + " start date is not as Expected ======> Fail");

		if (EndV.equals(EndDate))
			System.out.println(EndV + " End date is as Expected ======> Pass");
		else
			System.out.println(EndV + " End date is not as Expected ======> Fail");
		System.out.println(LastName + " Last Name");

		System.out.println(EndV + "<===== " + EndDate);
	}

}
