package practicebasics;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;



public class Data_Provider_Amazon_Test 
{
	
	@Test(dataProvider  = "getData")
	public void searchAndCapturePriceTest(String Data, String Prod) throws InterruptedException
	{
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://www.amazon.in/");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Data,Keys.ENTER);
		String xpath="//span[contains(text(),'"+Prod+"')]/ancestor::div[contains(@class,'top-small')]/descendant::span[@class='a-price-whole']";
		String Price = driver.findElement(By.xpath(xpath)).getText();
		System.out.println(Price);
		Thread.sleep(1000);
		driver.quit();
	}

	@DataProvider
	public Object[][] getData() throws EncryptedDocumentException, IOException
	{
		
		ExcelUtility eu = new ExcelUtility();
		int lastRowNum = eu.getLastRowNum("Products");
		Object[][] objarr = new Object[lastRowNum][2];
		for(int i=0;i<lastRowNum;i++)
		{
			objarr[i][0]= eu.getDataFromExcel("Products", i+1, 0);
			objarr[i][1]= eu.getDataFromExcel("Products", i+1, 1);
		}
		return objarr;
	}

}
