package tm.eclipse.pluginPreferences;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import tm.eclipse.ui.pluginPreferences.*;

public class MainPreferences_Test extends Preferences_JUnit
{

	@Test
	@Ignore
	public void checkPreferencesPage_DefaultValues()
	{						
		openPropertiesPage("tm.eclipse.ui.pluginPreferences")		
			.restoreDefaults();
		
		is_TextBox_equal_DefaultPreference(1, PreferenceInitializer.P_TEAMMENTOR_SERVER    , true);
		is_TextBox_equal_DefaultPreference(0, PreferenceInitializer.P_TEAMMENTOR_SERVER    , false);
		is_TextBox_equal_DefaultPreference(2, PreferenceInitializer.P_TEAMMENTOR_SESSION_ID, true);
		is_TextBox_equal_DefaultPreference(0, PreferenceInitializer.P_TEAMMENTOR_SESSION_ID, false);
		
		is_Checkbox_equal_DefaultPreference(0, PreferenceInitializer.P_OPEN_ARTICLE_NEW_WINDOW , true);		
		is_Checkbox_equal_DefaultPreference(1, PreferenceInitializer.P_TEAMMENTOR_LOAD_PLUGINS , true);
		is_Checkbox_equal_DefaultPreference(2, PreferenceInitializer.P_TEAMMENTOR_ADVANCED_MODE, true);
		
		closePropertiesPage();
	}
	
	@Test
	@Ignore
	public void checkPreferencesPage_ValueChanges()
	{		
		//Open and set defaults
		openPropertiesPage("tm.eclipse.ui.pluginPreferences")
			.restoreDefaults();
		assertFalse("1st chedckbox (default)", bot.checkBox(0).isChecked());
		assertTrue ("2nd chedckbox (default)", bot.checkBox(1).isChecked());
		assertFalse("3rd chedckbox (default)", bot.checkBox(2).isChecked());
		
		// close and open (without setting defaults)
		closePropertiesPage();
		
		openPropertiesPage("tm.eclipse.ui.PluginPreferences");		
		assertFalse("1st chedckbox", bot.checkBox(0).isChecked());
		assertTrue ("2nd chedckbox", bot.checkBox(1).isChecked());
		assertFalse("3rd chedckbox", bot.checkBox(2).isChecked());

		// uncheck all, apply, close and open (without setting defaults)
		bot.text(1).setText("12345");
		bot.text(2).setText("abcef");
		bot.checkBox(0).click();
		bot.checkBox(2).click();
		bot.button("Apply").click();
		closePropertiesPage();
		
		openPropertiesPage("tm.eclipse.ui.PluginPreferences");		
		assertEquals("1st text     (after click 1)", bot.text(1).getText(), "12345");
		assertEquals("1st text     (after click 1)", bot.text(2).getText(), "abcef");
		assertTrue  ("1st checkbox (after click 1)", bot.checkBox(0).isChecked());
		assertTrue  ("2nd checkbox (after click 1)", bot.checkBox(1).isChecked());
		assertTrue  ("3rd checkbox (after click 1)", bot.checkBox(2).isChecked());
		
		
		// check all, apply, close and open (without setting defaults)
		bot.checkBox(0).click();
		bot.checkBox(1).click();
		bot.checkBox(2).click();
		bot.button("Apply").click();
		shell.close();
		
		openPropertiesPage("tm.eclipse.ui.PluginPreferences");		
		assertFalse ("1st checkbox (after click 2)", bot.checkBox(0).isChecked());
		assertFalse ("2nd checkbox (after click 2", bot.checkBox(1).isChecked());
		assertFalse ("3rd checkbox (after click 3)", bot.checkBox(2).isChecked());
		
		//restore defaults:
		openPropertiesPage("tm.eclipse.ui.PluginPreferences")
			.restoreDefaults()
			.closePropertiesPage();
	}	
}
