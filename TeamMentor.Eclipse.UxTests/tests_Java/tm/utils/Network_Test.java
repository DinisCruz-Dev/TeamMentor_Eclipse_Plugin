package tm.utils;

import static org.junit.Assert.*;
import static tm.utils.Network.*;
import org.junit.Test;
import tm.eclipse.Plugin_Config;

public class Network_Test 
{		
	@Test 
	public void test_url_Exists()
	{				
		if(online())
		{						
			assertTrue (url_Exists("http://www.google.com"));
			assertTrue (url_Exists("https://www.teammentor.net"));
			assertFalse(url_Exists("http://www.googleAABBCCC.com"));
			assertFalse(url_Exists("http://www.teammentorAABBCC.net"));
			assertFalse(url_Exists("www.googleAABBCC.com"));
			assertFalse(url_Exists("http://"));
		}		
	}
	
	@Test 
	public void test_online()
	{
		assertEquals(url_Exists("http://www.google.com"), online());
		//check Force offline capabilties if we are online
		if(url_Exists("http://www.google.com"))
		{
			assertTrue(online());
			Plugin_Config.FORCE_OFFLINE = true;
			assertFalse(online());
			Plugin_Config.FORCE_OFFLINE = false; 
			assertTrue("We should be back online now" , online());
		} 
	}
}
