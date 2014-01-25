package tm.swtbot;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class SWTBot_Helpers
{	
	public SWTWorkbenchBot	bot;
	public IWorkbench workbench;	
	public Display display;
	//public SWTBot_Files     files;
	//public SWTBot_Views     views;
	
	public SWTBot_Helpers()
	{
		bot       = new SWTWorkbenchBot();
		workbench = PlatformUI.getWorkbench();
		display   = workbench.getDisplay();
		//files = new SWTBot_Files(bot);
		//views = new SWTBot_Views(bot);		
	}
	   
	
	/*public SWTBot_Helpers addDummyTaskToWorkspace()
	{
	
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		assertNotNull(workspace);
		
		try 
		{
			workspace.run(new IWorkspaceRunnable() 
				{
					public void run(IProgressMonitor monitor) throws CoreException 
					{
						//String test = "123";
						// nothing to do!
					}
				}, new NullProgressMonitor());
		} catch (CoreException e) 
		{			// 
			e.printStackTrace();
		}
		return this;
	}*/
	

	
	

}
