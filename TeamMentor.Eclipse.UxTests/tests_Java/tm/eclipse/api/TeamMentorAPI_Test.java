package tm.eclipse.api;

import static org.junit.Assert.*;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotBrowser;
import org.junit.Test;

import tm.eclipse.ui.views.DefaultPart_WebBrowser;
import tm.teammentor.TeamMentorAPI;

public class TeamMentorAPI_Test 
{	
	TeamMentorAPI 		   teamMentorApi;
	EclipseAPI    		   elipseAPI;	
	DefaultPart_WebBrowser defaultWebBrowser;
	Browser				   webBrowser;
	String 				   html;
	 
	public TeamMentorAPI_Test()
	{
		teamMentorApi = new TeamMentorAPI();
		elipseAPI     = TeamMentorAPI.eclipseAPI;
	}
	
	@Test
	public void TeamMentorAPI_Ctor()
	{
		assertNotNull(teamMentorApi);
		assertNotNull(TeamMentorAPI.eclipseAPI);		
	}	
	
	@Test 
	public void show_Html_With_TeamMentor_Banner()
	{		
		final String htmlToShow = "...html to show ..."; 
		

		defaultWebBrowser = TeamMentorAPI.show_Html_With_TeamMentor_Banner(htmlToShow);
		assertNotNull(defaultWebBrowser);    
		assertNotNull(defaultWebBrowser.browser);
		webBrowser = defaultWebBrowser.browser;
		
		SWTBotBrowser swtBotBrowser = new SWTBotBrowser(defaultWebBrowser.browser);
		swtBotBrowser.waitForPageLoaded();
		html = swtBotBrowser.getText();
		assertTrue(html.contains(htmlToShow));		
	}
}

