package tm.eclipse.handlers;

import static tm.utils.Network.*;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IWorkbench;

import tm.eclipse.api.Panels;
import tm.eclipse.api.TeamMentorAPI;
import tm.eclipse.ui.Startup;
import tm.eclipse.ui.pluginPreferences.TM_Preferences;
import tm.utils.Consts_Eclipse;

public class OpenTeamMentorWebsite implements IHandler 
{
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{
		TeamMentorAPI.setBrowserCookieToTMSession();
		String server = TM_Preferences.getServer(); //"https://teammentor.net"
		IWorkbench workbench = Startup.eclipseApi.workbench;
		final Panels panels = new Panels(workbench);	
		if (online())
			panels.open_Url_in_WebBrowser("TeamMentor Website",server);
		else
			//panels.open_Html_in_WebBrowser(Eclipse_Consts.DEFAULT_TM_NET_OFFLINE_MESSAGE);
			TeamMentorAPI.show_Html_With_TeamMentor_Banner(Consts_Eclipse.DEFAULT_TM_NET_OFFLINE_MESSAGE);		
		return null;
	}

	@Override public boolean isEnabled()  { return true; }
	@Override public boolean isHandled()  { return true; }	
	@Override public void 	 dispose() 											     { }
	@Override public void 	 addHandlerListener(IHandlerListener handlerListener)    { }
	@Override public void 	 removeHandlerListener(IHandlerListener handlerListener) { }
}
