package tm.eclipse.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.groovy.plugins.Plugin_Fortify;
import tm.eclipse.ui.pluginPreferences.TM_Preferences;
import tm.teammentor.TeamMentorAPI;

public class Startup implements IStartup {

	//public static EclipseAPI   eclipseApi;
	public static boolean      showDebugViews = true;
	public static List<Object> loadedPlugins = new ArrayList<Object>();
	//public static FortifyAPI fortifyApi;

	/*static 
	{
		eclipseApi = EclipseAPI.current();
	}*/
	public Startup()
	{		
		assert(EclipseAPI.current() != null);    // ensure it is loaded
	}
	
	@Override
	public void earlyStartup() 
	{			
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() { @Override public void run() 
			{
				startDefaultTeamMentorPlugins();
			}});
	}
	
	
	public void startDefaultTeamMentorPlugins()
	{
		if (TM_Preferences.loadPluginsOnStartup())
		{
			Object fortifyApi = new Plugin_Fortify().startup();
			if (fortifyApi ==null)
				EclipseAPI.current().log("ERROR: Plugin_Fortify().startup() returned null");
			else
				loadedPlugins.add(fortifyApi);
			
		}
	}
}
