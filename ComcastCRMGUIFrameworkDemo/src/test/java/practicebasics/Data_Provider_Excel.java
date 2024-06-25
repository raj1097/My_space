package practicebasics;

import java.io.IOException;


import org.apache.poi.EncryptedDocumentException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;



public class Data_Provider_Excel
{
	
	@Test(dataProvider = "getData")
	public void readData(String Item, String Product) throws InterruptedException
	{
		System.out.println(Item+"\t"+Product);
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
