package practicebasics;

import org.testng.Reporter;
import org.testng.annotations.Test;

public class Reporter_log 
{
	@Test
	public void testingReport()
	{
		Reporter.log("Sample-1", true);
	}

}
