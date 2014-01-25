package tm.eclipse.ui.pluginPreferences;

import org.eclipse.jface.preference.IPreferenceStore;

import tm.eclipse.ui.Activator;
import tm.eclipse.ui.pluginPreferences.pages.MainPreferences;

public class TM_Preferences 
{
	public static IPreferenceStore store = Activator.getDefault().getPreferenceStore();

	public static boolean openArticleInNewWindow()
	{			
		return MainPreferences.store.getBoolean(PreferenceInitializer.P_OPEN_ARTICLE_NEW_WINDOW);
	}

	public static boolean loadPluginsOnStartup()
	{			
		return MainPreferences.store.getBoolean(PreferenceInitializer.P_TEAMMENTOR_LOAD_PLUGINS);
	}

	public static boolean showAdvancedMode()
	{			
		return MainPreferences.store.getBoolean(PreferenceInitializer.P_TEAMMENTOR_ADVANCED_MODE);
	}

	public static String getServer()
	{
		return MainPreferences.store.getString(PreferenceInitializer.P_TEAMMENTOR_SERVER);
	}

	public static String getSessionId()
	{
		return MainPreferences.store.getString(PreferenceInitializer.P_TEAMMENTOR_SESSION_ID);
	}

	public static String getAboutHtml()
	{
		return MainPreferences.store.getString(PreferenceInitializer.P_TEAMMENTOR_ABOUT_HTML);
	}

	public static void setAboutHtml(String value)
	{
		MainPreferences.store.setValue(PreferenceInitializer.P_TEAMMENTOR_ABOUT_HTML,value);
	}

	public static void setServer(String value)
	{
		MainPreferences.store.setValue(PreferenceInitializer.P_TEAMMENTOR_SERVER,value);
	}

	public static String getDefaultBrowserId()
	{
		return "TeamMentor Content";		
	}

	public static String getDefaultBrowserTitle()
	{
		return "TeamMentor";		
	}

	public static void setSessionId(String value)
	{
		MainPreferences.store.setValue(PreferenceInitializer.P_TEAMMENTOR_SESSION_ID, value);		
	}

}
