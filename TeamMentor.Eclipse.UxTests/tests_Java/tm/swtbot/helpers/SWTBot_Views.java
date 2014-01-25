package tm.swtbot.helpers;

import java.util.ArrayList;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.finders.WorkbenchContentsFinder;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import tm.swtbot.SWTBot_Consts;
import tm.swtbot.models.SWTBot_View;

public class SWTBot_Views 
{
	public static boolean 		    LOG_EXCEPTION_VIEW_NOT_FOUND;	
	public static SWTWorkbenchBot	bot;
	
	static  
	{
		bot = new SWTWorkbenchBot();
		LOG_EXCEPTION_VIEW_NOT_FOUND = false;
	}
		
	public static ArrayList<SWTBot_View> swtBot_Views()
	{
		ArrayList<SWTBot_View> views = new ArrayList<SWTBot_View>();
		for(SWTBotView swtBotView : bot.views())
		{
			views.add(new SWTBot_View(swtBotView));
		}
		return views;
	}	
	public static SWTBot_View 			 swtBot_View_Fast(String viewNameOrId)
	{
		long originalTimeout = swtBot_Timeout();		// save current value
		swtBot_Timeout(SWTBot_Consts.TIMEOUT_FAST);		// set to TIMEOUT_FAST
		SWTBot_View view = swtBot_View(viewNameOrId);	// get view
		swtBot_Timeout(originalTimeout);					// reset original value
		return view;
	}
	public static SWTBot_View 			 swtBot_View(String viewNameOrId)
	{
		SWTBotView view = null;
		
		try
		{
			view =  bot.viewByTitle(viewNameOrId);
		}
		catch(Exception ex)
		{	
			if (LOG_EXCEPTION_VIEW_NOT_FOUND)
				ex.printStackTrace();
		}
		if(view == null)
		{
			try
			{
				view =  bot.viewById(viewNameOrId);
			}
			catch(Exception ex)
			{	
				if (LOG_EXCEPTION_VIEW_NOT_FOUND)
					ex.printStackTrace();
			}
		}
		if (view!= null)
			return new SWTBot_View(view);
		return null;
	}
	public static long 				     swtBot_Timeout()
	{
		return SWTBotPreferences.TIMEOUT;
	}
	public static long 		  			 swtBot_Timeout(long value)
	{
		SWTBotPreferences.TIMEOUT = value;
		return value;
	}
	public static SWTBot_View			 swtBot_View_Open(String primaryId)
	{
		return swtBot_View_Open(primaryId, null);
	}
	public static SWTBot_View			 swtBot_View_Open(final String primaryId, String secundaryId)
	{
		SWTBot_View swtBotView = swtBot_View_Fast(primaryId);
		if (swtBotView != null)
			return swtBotView;
		
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				try {
									
						IWorkbenchWindow workbenchWindow = new WorkbenchContentsFinder().activeWorkbenchWindow();
						workbenchWindow.getActivePage().showView(primaryId);
					}
					catch (PartInitException e) 
					{
						e.printStackTrace();
					}
				}});
		return swtBot_View(primaryId);
	}
	
	public static String test123()
	{
		return "hello asd";
	}
}

