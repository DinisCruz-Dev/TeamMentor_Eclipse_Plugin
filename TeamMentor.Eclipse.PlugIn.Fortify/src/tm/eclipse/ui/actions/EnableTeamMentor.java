package tm.eclipse.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import tm.teammentor.TeamMentorAPI;

public class EnableTeamMentor implements IWorkbenchWindowActionDelegate 
{
	public IWorkbenchWindow window;	
	
	public EnableTeamMentor() 
	{
	}


	public void run(IAction action) 
	{
		String message = 	"<h4>TeamMentor Eclipse Plugin</h1>Welcome to Teammentor Eclipse Plugin for Fortify. You should now see TeamMentor Articles when you click on a Fortify Finding";
		TeamMentorAPI.show_Html_With_TeamMentor_Banner(message);
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() 
	{ 
	}

	public void init(IWorkbenchWindow window) 
	{
		this.window = window;
	}
}