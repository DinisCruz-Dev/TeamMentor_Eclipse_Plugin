package tm.eclipse.api;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;
import static org.junit.Assert.*;

import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.junit.Test;

import tm.eclipse.ui.Startup;
import tm.eclipse.ui.views.Eclipse_Panel;

public class Panels_Tests 
{
	public EclipseAPI eclipseApi;
	public Panels panels;	
	
	public Panels_Tests()
	{
		eclipseApi = EclipseAPI.current();
		panels = new Panels(eclipseApi.workbench);
		assertNotNull(eclipseApi);
		assertNotNull(panels);
		assertNotNull(panels.activePage);		
		assertNotNull(panels.workbenchWindow);
		assertNotNull(panels.workbench);		
	}
	
	@Test 
	public void open_Panel()
	{
		String panelId      = "New Test Panel";
		String defaultTitle = "Eclipse Panel";
		final String newTitle     = "A new Title";
		final Eclipse_Panel panel = panels.open_Panel(panelId);
		assertNotNull(panel);
		assertNotNull(panel.composite);
		//assertNotEquals (!defaultTitle, panel.getTitle());
		assertNotSame(defaultTitle, panel.getTitle());
		syncExec(new VoidResult() { public void run() 
			{
				panel.title(newTitle);
				assertEquals (newTitle, panel.getTitle());
			}});
				
	}
}
