package com.comcast.crm.objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage 
{
	WebDriver driver;
	public ProductPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//img[@src='themes/softed/images/btnL3Add.gif']")
	private WebElement createProductbtn;
	
	public WebElement getProductbtn()
	{
		return createProductbtn;
	}
	
	// Submit button
	@FindBy(name = "submit")
	private WebElement submitBt;
	
	
	public WebElement getSubmitBt() {
		return submitBt;
	}
	
}
