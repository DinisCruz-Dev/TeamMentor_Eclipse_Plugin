package tm.eclipse.ui.actions;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.dialogs.PreferencesUtil;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.ui.Startup;

public class OpenPropertiesPage implements IWorkbenchWindowActionDelegate 
{
	public IWorkbenchWindow window;	
	
	public OpenPropertiesPage() 
	{
	}


	public void run(IAction action) 
	{					
		IWorkbench workbench = EclipseAPI.current().workbench;
		PreferenceDialog preferenceDialog = PreferencesUtil.createPreferenceDialogOn(workbench.getActiveWorkbenchWindow().getShell(), "teammentor.eclipse.plugin.fortify.preferences.PluginPreferences", null, null);
		preferenceDialog.open();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}