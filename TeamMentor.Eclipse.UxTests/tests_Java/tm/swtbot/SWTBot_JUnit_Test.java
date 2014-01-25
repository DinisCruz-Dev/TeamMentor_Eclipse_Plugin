package tm.swtbot;

import static org.junit.Assert.*;
import static tm.swtbot.helpers.SWTBot_Views.*;
import org.junit.Test;

import tm.swtbot.models.SWTBot_View;

public class SWTBot_JUnit_Test 
{
	SWTBot_JUnit swtbotJunit = new SWTBot_JUnit();
	
	@Test
	public void SWTBot_JUnit_Ctor()
	{
		assertNotNull(swtbotJunit);
		SWTBot_View welcome = swtBot_View_Fast(SWTBot_Consts.VIEW_ID_IntroView);
		assertNull(welcome);
	}
}
