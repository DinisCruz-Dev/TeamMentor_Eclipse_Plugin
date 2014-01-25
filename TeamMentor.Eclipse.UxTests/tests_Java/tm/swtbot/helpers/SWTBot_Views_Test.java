package tm.swtbot.helpers;

import static org.junit.Assert.*;
import static tm.swtbot.helpers.SWTBot_Views.*;
import java.util.Random;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.Test;
import tm.swtbot.SWTBot_Consts;
import tm.swtbot.SWTBot_Helpers;
import tm.swtbot.models.SWTBot_View;

public class SWTBot_Views_Test 
{
	SWTBot_Helpers helper;	
	
	public SWTBot_Views_Test()
	{
		helper = new SWTBot_Helpers();		
	}
	@Test 
	public void SWTBot_Views_Test_Ctor()
	{	
		assertNotNull(helper);
		assertNotNull(helper.bot);		
	}
	
	@Test 
	public void getset_timeout()
	{		
		long originalTimeout = SWTBotPreferences.TIMEOUT;
		long newTimeout 	 = Math.abs(new Random().nextLong());
		
		assertEquals	 (originalTimeout, swtBot_Timeout());		 
		assertFalse      (newTimeout    == swtBot_Timeout()); //assertNotEquals  (newTimeout     , swtBot_Timeout());
		
		swtBot_Timeout(newTimeout);
		assertFalse	  (originalTimeout  == swtBot_Timeout()); //assertNotEquals	 (originalTimeout, swtBot_Timeout());
		assertEquals  (newTimeout        , swtBot_Timeout());
		
		swtBot_Timeout(originalTimeout);
		assertFalse   (newTimeout    == swtBot_Timeout()); //assertNotEquals  (newTimeout     , swtBot_Timeout());
		assertEquals  (originalTimeout, swtBot_Timeout());
		
	}
		
	@Test
	public void get_view()
	{	
		//this one shouldn't exist
		SWTBot_View view_ShouldNotExist = swtBot_View_Fast("Welcome__ABC");
		assertNull(view_ShouldNotExist);
				
		//if this one exists, get it and close it
		SWTBot_View view_ShouldExist    = swtBot_View_Fast("Welcome");
		if (view_ShouldExist!=null)
		{
			assertNotNull(view_ShouldExist);
			view_ShouldExist.close();
		}				
		
		//getting the views 
		view_ShouldExist    = swtBot_View_Fast("Welcome");
		view_ShouldNotExist = swtBot_View_Fast("Welcome__ABC");		
		
		assertNull(view_ShouldExist);
		assertNull(view_ShouldNotExist);			
	}
	
	@Test
	public void get_views()
	{				
		for(SWTBot_View view : swtBot_Views())			
		{
			assertNotNull(view);			
			assertNotNull(view.id);
			assertNotNull(view.name);
			assertNotNull(view.swtBotView);
			assertNotNull(view.title);
			assertNotNull(view.viewPart);
			assertNotNull(view.viewReference);
			assertNotNull(view.viewSite);
			assertNotNull(view.widget);
			assertNotNull(view.workbenchPart);
		}
	}
	
	@Test
	public void open_View()
	{		
		String intro_View_Id = SWTBot_Consts.VIEW_ID_IntroView;
		
		//if it is already open, get the reference and close it
		SWTBot_View  intro_View = swtBot_View_Fast(intro_View_Id);
		if (intro_View!=null)
		{
			assertNotNull(intro_View);
			intro_View.close();
			intro_View = swtBot_View_Fast(intro_View_Id);
		}
		assertNull(intro_View);
				
		//helper.addDummyTaskToWorkspace();
		
		//now open it 
		SWTBot_View 	  intro_View_after_Open = swtBot_View_Open(intro_View_Id);		
		assertNotNull(intro_View_after_Open );
		SWTBot_View 	  intro_View_after_Get  = swtBot_View_Fast(intro_View_Id);
		assertNotNull(intro_View_after_Get  );
		
		//and close it again
		intro_View_after_Open.close();
		SWTBot_View 	intro_View_after_Close  = swtBot_View_Fast(intro_View_Id);
		assertNull( intro_View_after_Close);
	}

}

