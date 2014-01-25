package tm.eclipse.ui;

import static org.eclipse.swtbot.swt.finder.SWTBotAssert.assertContains;
import static tm.utils.Network.*;
import static org.junit.Assert.*;
import static tm.swtbot.helpers.SWTBot_Views.*;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotBrowser;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.junit.Test;

import tm.eclipse.Plugin_Config;
import tm.eclipse.ui.pluginPreferences.TM_Preferences;
import tm.eclipse.ui.views.DefaultPart_WebBrowser;
import tm.swtbot.SWTBot_JUnit;
import tm.swtbot.models.SWTBot_View;
import tm.utils.Consts_Eclipse;

public class TeamMentor_Menu_Test extends SWTBot_JUnit
{
	@Test	
	public void check_Expected_TeamMentor_Menu_Items()
	{
		
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		SWTBotMenu teamMentorMenu = bot.menu("TeamMentor");				
		assertNotNull(teamMentorMenu.menu("About TeamMentor Eclipse Plugin"));
		assertNotNull(teamMentorMenu.menu("Open TeamMentor Website"));
		assertNotNull(teamMentorMenu.menu("Open Properties Page"));
		//assertNotNull(teamMentorMenu.menu("Open Properties Page"));		
	}
	
	@Test	
	public void about_TeamMentor_Plugin()
	{			

		SWTBot_View aboutView = swtBot_View_Fast("TeamMentor");
		if (aboutView != null)
			aboutView.close();
		bot.menu("TeamMentor").menu("About TeamMentor Eclipse Plugin").click();
		
		aboutView = swtBot_View("TeamMentor");
		assertNotNull(aboutView);
		assertNotNull(aboutView.viewPart);
		assertTrue(aboutView.viewPart instanceof DefaultPart_WebBrowser);
		final Browser webBrowser =  ((DefaultPart_WebBrowser)aboutView.viewPart).browser;			
		assertNotNull(webBrowser);
		
		SWTBotBrowser swtBotBrowser = new SWTBotBrowser(webBrowser);
		swtBotBrowser.waitForPageLoaded();
		String html = swtBotBrowser.getText();
		assertContains("TeamMentor", html);		
		aboutView.close();
		aboutView = swtBot_View_Fast("TeamMentor");
		assertNull(aboutView);
	}
	
	@Test
	public void open_TeamMentor_Website()
	{
		if (online())
		{
			String expectedServer = TM_Preferences.getServer();
			bot.menu("TeamMentor").menu("Open TeamMentor Website").click();			
			SWTBot_View teamMentorNet = swtBot_View_Fast(expectedServer);//"https://teammentor.net");
			assertNotNull(teamMentorNet);
			teamMentorNet.close();
			teamMentorNet = swtBot_View_Fast(expectedServer);//"https://teammentor.net");
			assertNull(teamMentorNet);			
		}

		Plugin_Config.FORCE_OFFLINE = true;
		bot.menu("TeamMentor").menu("Open TeamMentor Website").click();
		
		SWTBot_View teamMentorNet = swtBot_View_Fast("TeamMentor");
		assertNotNull(teamMentorNet);		
		final Browser webBrowser =  ((DefaultPart_WebBrowser)teamMentorNet.viewPart).browser;
		final SWTBotBrowser swtBotBrowser = new SWTBotBrowser(webBrowser);		
		swtBotBrowser.waitForPageLoaded();			
		String html = swtBotBrowser.getText();
		assertContains(Consts_Eclipse.DEFAULT_TM_NET_OFFLINE_MESSAGE, html);		
		Plugin_Config.FORCE_OFFLINE = false;
		teamMentorNet.close();
		teamMentorNet = swtBot_View_Fast("TeamMentor");
		assertNull(teamMentorNet);
	}
}
