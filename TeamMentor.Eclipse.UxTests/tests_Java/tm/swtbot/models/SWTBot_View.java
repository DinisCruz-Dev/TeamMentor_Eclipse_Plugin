package tm.swtbot.models;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;

public class SWTBot_View 
{
	public String		    id;
	public String 		    name;
	public SWTBotView		swtBotView;
	public String 		    title;	
	public IViewPart	  	viewPart;
	public IViewReference 	viewReference;
	public IViewSite	  	viewSite;	
	public Widget         	widget;
	public IWorkbenchPart	workbenchPart;
	
	
	public SWTBot_View(SWTBotView _swtBotView)
	{
		swtBotView    = _swtBotView;
		viewReference = swtBotView.getReference();
		widget    	  = swtBotView.getWidget();
		viewPart  	  = viewReference.getView(false);
		viewSite  	  = viewPart.getViewSite();
		workbenchPart = viewReference.getPart(false);
		title         = viewPart.getTitle();
		name          = viewReference.getPartName();
		id 		      = viewSite.getId();		
	}
	
	public SWTBot_View close()
	{
		if (swtBotView != null)
			swtBotView.close();
		return this;
	}
	
	public SWTBot_View focus()
	{
		if (swtBotView != null)
			swtBotView.setFocus();
		return this;
	}	
}

