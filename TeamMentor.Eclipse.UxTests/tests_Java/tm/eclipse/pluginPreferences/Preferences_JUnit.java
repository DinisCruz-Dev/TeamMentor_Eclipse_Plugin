package tm.eclipse.pluginPreferences;

import static org.junit.Assert.*;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;

import tm.eclipse.helpers.EclipseUI;
import tm.eclipse.ui.Activator;

public class Preferences_JUnit 
{
	public SWTWorkbenchBot  bot;
	public IPreferenceStore store;
	public SWTBotShell 		shell;
	public String 			viewId;
	
	public Preferences_JUnit()
	{
		 bot = new SWTWorkbenchBot();
		 store = Activator.getDefault().getPreferenceStore();
	}
	public Preferences_JUnit closePropertiesPage()
	{
		shell.close();
		return this;
	}
	public Preferences_JUnit openPropertiesPage( String _viewId)
	{
		viewId = _viewId;
		return openPropertiesPage();
	}
	public Preferences_JUnit openPropertiesPage()	
	{
		new Thread() { public void run() 
		{			
			EclipseUI.open_PreferencesDialog(viewId);
		}}.start();
				
		shell = bot.shell("Preferences");
		
		assertNotNull("openPropertiesPage shell is null", shell);
		return this;
	}
	
	public Preferences_JUnit restoreDefaults()
	{
		bot.button("Restore Defaults").click();
		bot.button("Apply").click();
		return this;
	}
	public void is_TextBox_equal_DefaultPreference(Integer index, String stringId, boolean equals)
	{
		SWTBotText text = bot.text(index);				
		String value_default = store.getDefaultString (stringId);
		String value_Live = text.getText();		
		if(equals)
			assertEquals   (value_default, value_Live);
		else
			assertFalse(value_default.equals(value_Live));  // assertNotEquals(value_default, value_Live);	
	}
	public void is_Checkbox_equal_DefaultPreference(Integer index, String stringId, boolean equals)
	{
		SWTBotCheckBox checkBox = bot.checkBox(index);				
		Boolean value_default = store.getDefaultBoolean(stringId);
		
		Boolean value_Live = checkBox.isChecked();
		if(equals)
			assertEquals   (stringId, value_default, value_Live);
		else
			assertFalse(stringId,value_default.equals(value_Live));  //			assertNotEquals(stringId, value_default, value_Live);		
	}
}
