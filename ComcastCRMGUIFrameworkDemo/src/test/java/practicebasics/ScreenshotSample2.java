package practicebasics;

import org.junit.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.comcast.crm.baseclassimp.BaseClass;


@Listeners(com.comcast.crm.listnerutility.ListImpClass.class)
public class ScreenshotSample2 extends BaseClass
{
	@Test
	public void screenshotTest()
	{
		String Expected ="Raj";
		boolean test = Expected.equals("raj");
		Assert.assertTrue(test);
	}
}
