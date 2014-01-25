package tm.eclipse.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import tm.eclipse.helpers.EclipseUI;

public class OpenPropertiesPage implements IHandler 
{
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{				
		return EclipseUI.open_PreferencesDialog("tm.eclipse.ui.pluginPreferences");		
	}

	@Override public boolean isEnabled()  { return true; }
	@Override public boolean isHandled()  { return true; }	
	@Override public void 	 dispose() 											     { }
	@Override public void 	 addHandlerListener(IHandlerListener handlerListener)    { }
	@Override public void 	 removeHandlerListener(IHandlerListener handlerListener) { }

}
