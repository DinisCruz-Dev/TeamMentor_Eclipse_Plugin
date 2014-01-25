package tm.eclipse.ui;

import static org.junit.Assert.*;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Test;
import tm.eclipse.ui.pluginPreferences.*;

public class PreferenceInitializer_Test
{
	@Test
	public void initializeDefaultPreferences_Test() 
	{	
		PreferenceInitializer preferenceInitializer = new PreferenceInitializer();				
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		assertNotNull(preferenceInitializer);
		assertNotNull(store);
		
		preferenceInitializer.initializeDefaultPreferences();

		assertEquals(store.getDefaultString (PreferenceInitializer.P_TEAMMENTOR_SERVER),"https://vulnerabilities.teammentor.net");
		assertEquals(store.getDefaultString (PreferenceInitializer.P_TEAMMENTOR_SESSION_ID),"00000000-0000-0000-0000-000000000000");
		assertEquals(store.getDefaultBoolean(PreferenceInitializer.P_OPEN_ARTICLE_NEW_WINDOW),false);
		assertEquals(store.getDefaultBoolean(PreferenceInitializer.P_TEAMMENTOR_ADVANCED_MODE),false);
		
		/*verify(store).setDefault(PreferenceInitializer.P_TEAMMENTOR_SERVER, "https://vulnerabilities.teammentor.net");
		verify(store).setDefault(PreferenceInitializer.P_TEAMMENTOR_SESSION_ID, "00000000-0000-0000-0000-000000000000");
		verify(store).setDefault(PreferenceInitializer.P_OPEN_ARTICLE_NEW_WINDOW, false);
		verify(store).setDefault(PreferenceInitializer.P_TEAMMENTOR_ADVANCED_MODE, false);
		*/
	}
}
