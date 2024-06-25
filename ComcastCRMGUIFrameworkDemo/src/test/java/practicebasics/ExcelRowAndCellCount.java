package practicebasics;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;

public class ExcelRowAndCellCount 
{
	@Test
	public void getData() throws EncryptedDocumentException, IOException
	{
		ExcelUtility eu = new ExcelUtility();
		int lastRowNum = eu.getLastRowNum("Products");
		System.out.println(lastRowNum);
		int lastCellNum = eu.getLastCellNum("Products", 2);
		System.out.println(lastCellNum);
		
	}

}
