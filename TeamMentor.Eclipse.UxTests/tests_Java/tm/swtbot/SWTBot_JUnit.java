package tm.swtbot; //aa so that it runs first

import static org.junit.Assert.*;
import tm.swtbot.models.SWTBot_View;
import static tm.swtbot.helpers.SWTBot_Views.*;

public class SWTBot_JUnit extends SWTBot_Helpers
{

	public SWTBot_JUnit()
	{
		Close_Welcome_View();
	}
		
	//@Test
	public void SWTWorkbenchBot_OK() 
	{
		assertNotNull(bot);
	}

	public SWTBot_JUnit Close_Welcome_View()
	{
		SWTBot_View introView = swtBot_View_Fast(SWTBot_Consts.VIEW_ID_IntroView);
		if (introView != null)
			introView.swtBotView.close();
		return this;		
	}
}
