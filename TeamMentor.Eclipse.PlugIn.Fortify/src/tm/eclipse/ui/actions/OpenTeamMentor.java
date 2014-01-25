package tm.eclipse.ui.actions;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.api.Panels;
import tm.eclipse.ui.Startup;

public class OpenTeamMentor implements IWorkbenchWindowActionDelegate 
{
	public IWorkbenchWindow window;	
	
	public OpenTeamMentor() 
	{
	}


	public void run(IAction action) 
	{
						
		IWorkbench workbench = EclipseAPI.current().workbench;
		final Panels panels = new Panels(workbench);	
		
		panels.open_Url_in_WebBrowser("teammentor.net","http://teammentor.net");											
				
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}