package practicebasics;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Asserts_prac 
{
	@Test
	public void isAssertTest()
	{
		SoftAssert sfas = new SoftAssert();
		String Expected="Raj";
		sfas.assertEquals("raj", Expected);
		System.out.println(Expected);
		
		String s1="Akshay";
		sfas.assertEquals("akshay", s1);
		System.out.println(s1);
		
		sfas.assertAll();
	}

}
