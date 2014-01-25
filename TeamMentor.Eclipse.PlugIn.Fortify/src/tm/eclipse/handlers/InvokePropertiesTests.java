package tm.eclipse.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.groovy.plugins.GroovyPlugins;
import tm.eclipse.helpers.EclipseUI;
import tm.eclipse.ui.Startup;
import tm.eclipse.ui.pluginPreferences.TM_Preferences;
import tm.eclipse.ui.pluginPreferences.pages.JUnitTestExecutionUI;

public class InvokePropertiesTests implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{				
		/*GroovyPlugins groovyPlugins = new GroovyPlugins();
		String file = "TM_Plugins/Views/ExecutePreferencesJUnitTests.groovy";

		groovyPlugins.execute_PluginScript(file);*/
		return new JUnitTestExecutionUI(EclipseAPI.current());		
	}

	@Override public boolean isEnabled()  { return TM_Preferences.showAdvancedMode(); }
	@Override public boolean isHandled()  { return TM_Preferences.showAdvancedMode(); }	
	@Override public void 	 dispose() 											       { }
	@Override public void 	 addHandlerListener(IHandlerListener handlerListener)      { }
	@Override public void 	 removeHandlerListener(IHandlerListener handlerListener)   { }

}
