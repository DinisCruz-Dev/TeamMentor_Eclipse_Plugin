package tm.eclipse.ui.pluginPreferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import tm.eclipse.groovy.plugins.GroovyPlugins;
import tm.eclipse.ui.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer 
{
	
	public static final String P_OPEN_ARTICLE_NEW_WINDOW 	= "P_OPEN_ARTICLE_NEW_WINDOW";
	public static final String P_TEAMMENTOR_SERVER 		 	= "P_TEAMMENTOR_SERVER";
	public static final String P_TEAMMENTOR_SESSION_ID   	= "P_TEAMMENTOR_SESSION_ID";
	public static final String P_TEAMMENTOR_ADVANCED_MODE   = "P_TEAMMENTOR_ADVANCED_MODE";	
	public static final String P_TEAMMENTOR_LOAD_PLUGINS    = "P_TEAMMENTOR_LOAD_PLUGINS";
	public static final String P_TEAMMENTOR_ABOUT_HTML      = "P_TEAMMENTOR_ABOUT_HTML";
	
	public void initializeDefaultPreferences() 
	{		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
				
		store.setDefault(P_OPEN_ARTICLE_NEW_WINDOW	, false);
		store.setDefault(P_TEAMMENTOR_SERVER		, "https://vulnerabilities.teammentor.net");
		store.setDefault(P_TEAMMENTOR_SESSION_ID	, "00000000-0000-0000-0000-000000000000");		
		store.setDefault(P_TEAMMENTOR_LOAD_PLUGINS 	, true);
		store.setDefault(P_TEAMMENTOR_ADVANCED_MODE	, false);
		store.setDefault(P_TEAMMENTOR_ABOUT_HTML	, new GroovyPlugins().get_PluginScript_Code("/TM_Plugins/AboutText.html"));
		//store.setDefault(P_TEAMMENTOR_ABOUT_HTML	, new PluginResources().get_Resource_Saved_on_TempFolder("/TM_Plugins/About_Text"));
	}

}
