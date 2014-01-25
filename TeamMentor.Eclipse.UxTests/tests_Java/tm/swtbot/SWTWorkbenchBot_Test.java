package tm.swtbot;

import static org.junit.Assert.*;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.junit.Test;

public class SWTWorkbenchBot_Test 
{	
	@Test
	public void checkSWTWorkbenchBot()
	{
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		assertNotNull(bot);
		SWTBotMenu menu = bot.menu("TeamMentor");		
		assertNotNull(menu);		
	}
	
}
