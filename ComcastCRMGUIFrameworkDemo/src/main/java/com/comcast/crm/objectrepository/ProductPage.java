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
	
	@FindBy(name = "search_text")
	private WebElement searchTextEle;
	
	public WebElement getSearchText()
	{
		return searchTextEle;
	}
	// Submit button
	@FindBy(name = "submit")
	private WebElement submitBt;
	
	
	public WebElement getSubmitBt() 
	{
		return submitBt;
	}
	
	@FindBy(name = "delete")
	private WebElement deleteBtn;
	
	
	public WebElement getDeleteBtn() 
	{
		return deleteBtn;
	}
	
//<<<<<<< HEAD
	@FindBy(name = "delete1")
	private WebElement deleteBtn1;
	
	
	public WebElement getDeleteBtn1() 
	{
		return deleteBtn1;
	}
	@FindBy(name = "lookup")
	private WebElement lookupBtn;
	
	
	public WebElement getLookUpBtn() 
	{
		return lookupBtn;
// branch 'master' of https://github.com/raj1097/My_space
	}
	@FindBy(name = "lookup1")
	private WebElement lookupBtn1;
	
	
	public WebElement getLookUpBtn1() 
	{
		return lookupBtn;
	}
	
}
