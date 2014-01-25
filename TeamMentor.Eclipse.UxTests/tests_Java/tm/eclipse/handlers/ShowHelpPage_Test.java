package tm.eclipse.handlers;

import org.eclipse.swt.browser.Browser;
import tm.swtbot.SWTBot_JUnit;
import tm.swtbot.models.SWTBot_View;

public class ShowHelpPage_Test extends SWTBot_JUnit
{
	SWTBot_View aboutView;
	Browser     webBrowser;
	String      html;
	
	/* Rewrite these without depending on bot.menu (those are already checked by the 
	 * TeamMentor_Menu_Test test
	 *
	 *
	@Test
	public void openView()
	{
		assertTrue(true);
		aboutView = swtBot_View_Fast("TeamMentor");
		if (aboutView == null)
		{
			bot.menu("TeamMentor").menu("About TeamMentor Eclipse Plugin").click();
			aboutView = swtBot_View_Fast("TeamMentor");
		}
		assertNotNull(aboutView);
		webBrowser = ((DefaultPart_WebBrowser)aboutView.viewPart).browser;
		assertNotNull(webBrowser);
		display.syncExec(new Runnable() { @Override public void run() 
		{
			html = webBrowser.getText();			
		}});
		//fail("this tests a fail");
	}
	@Test 
	public void checkHtmlContent()
	{
		openView();
		assertNotNull(aboutView);
		assertNotNull(webBrowser);
		assertNotNull(html);
		assertContains("TeamMentor", html);	
		assertContains("<html>", html);
		assertContains("</html>", html);
		
		assertFalse    ("HeaderImage.jpg ref should be local",html.contains("https://vulnerabilities.teammentor.net/Images/HeaderImage.jpg"));
	}*/
}
