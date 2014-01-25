package tm.tests.helpers;

import static org.junit.Assert.*;
import static tm.utils.ExtraAsserts.*;

import org.junit.Test;

public class ExtraAsserts_Test 
{		
	@Test()
	public void assertIsClass_Test()
	{					
		assertIsClass("a string", String .class);
		assertIsClass((int)123  , Integer.class);
		assertIsClass((long)123 , Long.class);		
		//I'm sure there is a better way to check the cases when assertIsClass throws an exception
		try
		{
			assertIsClass((long)123 , String .class);
		}
		catch(AssertionError ex)
		{
			return;
		}
		fail("we should be here");
	}
	
	/*because the assertNotEquals does not exist on the JUnit that is on Indigo
	 *
	@Test()
	public void assertIsNotClass_Test()
	{
		assertIsNotClass("a string", Integer .class);
		assertIsNotClass((int)123  , String.class);
		assertIsNotClass((long)123 , String.class);		
		try
		{
			assertIsNotClass((long)123 , Long .class);
		}
		catch(AssertionError ex)
		{
			return;
		}
		fail();
	}*/
}
