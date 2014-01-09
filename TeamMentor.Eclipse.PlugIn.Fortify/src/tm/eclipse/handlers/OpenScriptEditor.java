package tm.eclipse.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import tm.eclipse.ui.pluginPreferences.TM_Preferences;
import tm.eclipse.ui.Startup;
import tm.eclipse.ui.views.SimpleEditor;

public class OpenScriptEditor implements IHandler 
{
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{				
		return Startup.eclipseApi.views.open(SimpleEditor.ID);					
	}

	@Override public boolean isEnabled()  { return TM_Preferences.showAdvancedMode(); }
	@Override public boolean isHandled()  { return TM_Preferences.showAdvancedMode(); }	
	@Override public void 	 dispose() 											       { }
	@Override public void 	 addHandlerListener(IHandlerListener handlerListener)      { }
	@Override public void 	 removeHandlerListener(IHandlerListener handlerListener)   { }

}
