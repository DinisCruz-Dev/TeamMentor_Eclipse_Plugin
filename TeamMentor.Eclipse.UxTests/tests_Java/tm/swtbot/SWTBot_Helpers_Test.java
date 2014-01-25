package tm.swtbot;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class SWTBot_Helpers_Test 
{
	SWTBot_Helpers helper;	
	
	public SWTBot_Helpers_Test()
	{
		helper = new SWTBot_Helpers();		
	}
	@Test 
	public void SWTBot_Helpers_Test_Ctor()
	{	
		assertNotNull(helper);		
	}
}
