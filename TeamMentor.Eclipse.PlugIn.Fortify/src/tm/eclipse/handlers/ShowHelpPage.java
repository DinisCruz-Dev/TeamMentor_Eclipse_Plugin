package tm.eclipse.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import tm.eclipse.ui.pluginPreferences.TM_Preferences;
import tm.teammentor.TeamMentorAPI;

public class ShowHelpPage implements IHandler 
{
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{		
		String message = TM_Preferences.getAboutHtml();// 	"<h4>TeamMentor Eclipse Plugin</h1>Welcome to Teammentor Eclipse Plugin for Fortify. You should now see TeamMentor Articles when you click on a Fortify Finding";
		TeamMentorAPI.show_Html_With_TeamMentor_Banner(message);
		return null;
	}

	@Override public boolean isEnabled()  { return true; }
	@Override public boolean isHandled()  { return true; }	
	@Override public void 	 dispose() 											     { }
	@Override public void 	 addHandlerListener(IHandlerListener handlerListener)    { }
	@Override public void 	 removeHandlerListener(IHandlerListener handlerListener) { }

}
