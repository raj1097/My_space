package com.comcast.crm.annotations.copy;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.Test;

import com.comcast.crm.baseclassimp.BaseClass;
import com.comcast.crm.objectrepository.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepository.HomePage;
import com.comcast.crm.objectrepository.LoginPage;
import com.comcast.crm.objectrepository.OrganizationInformationPage;
import com.comcast.crm.objectrepository.OrganizationPage;

public class CreateOrgTest extends BaseClass
{
	@Test(groups = "smokeTest")
	public void CreateOrgWithNameTest() throws EncryptedDocumentException, IOException
	{
		
		// Getting data from Excel
		String OrgName = eu.getDataFromExcel("Org", 10, 2)+ju.getRandomNum();
		
		// Creating Objects for ObjectRepository
		HomePage hp = new HomePage(driver);
		OrganizationPage op = new OrganizationPage(driver);
		CreatingNewOrganizationPage cop = new CreatingNewOrganizationPage(driver);
		OrganizationInformationPage oip = new OrganizationInformationPage(driver);

		
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
	}
	
	@Test (groups = "regressionTest")
	public void createOrgWithIndustryType() throws EncryptedDocumentException, IOException
	{	
		// Getting data from Excel
		String OrgName = eu.getDataFromExcel("Org", 4, 2)+ju.getRandomNum();
		String Industry = eu.getDataFromExcel("Org", 4, 3);
		String Type = eu.getDataFromExcel("Org", 4, 4);
		
		// Creating Objects for ObjectRepository
		LoginPage lp = new LoginPage(driver);
		HomePage hp = new HomePage(driver);
		OrganizationPage op = new OrganizationPage(driver);
		CreatingNewOrganizationPage cop = new CreatingNewOrganizationPage(driver);
		OrganizationInformationPage oip = new OrganizationInformationPage(driver);
		
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
	}
	
	@Test (groups = "regressionTest")
	public void createWithPhoneNum() throws EncryptedDocumentException, IOException
	{
		// Getting data from Excel
		String OrgName = eu.getDataFromExcel("Org", 7, 2)+ju.getRandomNum();
		String Phone = eu.getDataFromExcel("Org", 7, 3);
		
		// Creating Objects for ObjectRepository
		LoginPage lp = new LoginPage(driver);
		HomePage hp = new HomePage(driver);
		OrganizationPage op = new OrganizationPage(driver);
		CreatingNewOrganizationPage cop = new CreatingNewOrganizationPage(driver);
		OrganizationInformationPage oip = new OrganizationInformationPage(driver);
	
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
	}

}
