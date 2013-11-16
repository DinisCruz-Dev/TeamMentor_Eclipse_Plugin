package tm.eclipse.ui;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class PluginPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static IPreferenceStore store = Activator.getDefault().getPreferenceStore();
	
	public PluginPreferences() 
	{
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("\nPlease use the fields below to configure the TeamMentor behaviour,server and account\n\n");
	}
		
	public void createFieldEditors() 
	{		
		addField(new StringFieldEditor (PreferenceInitializer.P_TEAMMENTOR_SERVER, "TeamMentor &Server:", getFieldEditorParent()));
		addField(new StringFieldEditor (PreferenceInitializer.P_TEAMMENTOR_SESSION_ID, "TeamMentor &SessionId:", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceInitializer.P_OPEN_ARTICLE_NEW_WINDOW,"&Open TeamMentor article in new window",getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceInitializer.P_TEAMMENTOR_ADVANCED_MODE,"&Show Advanced Mode Features",getFieldEditorParent()));
	}
	
	public void init(IWorkbench workbench) {
	}
	
	public static boolean openArticleInNewWindow()
	{			
		return store.getBoolean(PreferenceInitializer.P_OPEN_ARTICLE_NEW_WINDOW);
	}
	public static boolean showAdvancedMode()
	{			
		return store.getBoolean(PreferenceInitializer.P_TEAMMENTOR_ADVANCED_MODE);
	}
	public static String getServer()
	{
		return store.getString(PreferenceInitializer.P_TEAMMENTOR_SERVER);
	}
	public static void setServer(String value)
	{
		store.setValue(PreferenceInitializer.P_TEAMMENTOR_SERVER,value);
	}
	public static String getSessionId()
	{
		return store.getString(PreferenceInitializer.P_TEAMMENTOR_SESSION_ID);
	}
	public static void setSessionId(String value)
	{
		store.setValue(PreferenceInitializer.P_TEAMMENTOR_SESSION_ID, value);
	}
	public static String getDefaultBrowserId()
	{
		return "TeamMentor Content";		
	}
	public static String getDefaultBrowserTitle()
	{
		return "TeamMentor";		
	}
	
}