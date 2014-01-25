package tm.teammentor.poc;

import org.eclipse.jface.action.*;
import tm.teammentor.*;
import tm.eclipse.api.EclipseAPI;


public class TeamMentorMenu 
{
	public static MenuManager tmMenu;	
	public static EclipseAPI eclipseAPI;				
	
	public static void createTeamMentorMenu()
	{
		eclipseAPI = new EclipseAPI();
		TeamMentorAPI.eclipseAPI = eclipseAPI;

		tmMenu = eclipseAPI.menus.add_Menu ("TeamMentor API (v1.0)");
		
		addDefaultMenuItems();			
	}
	

	public static void addDefaultMenuItems()
	{
		eclipseAPI.menus.add_MenuItem_OpenWebPage(tmMenu,"Open Google"		 ,"http://google.net");
		eclipseAPI.menus.add_MenuItem_OpenWebPage(tmMenu,"Open TeamMentor.Net","http://teammentor.net");
		eclipseAPI.menus.add_MenuItem_Article    (tmMenu,"XSS article"        ,"Cross-site Scripting");
		eclipseAPI.menus.add_MenuItem_LoginToTM  (tmMenu);
		eclipseAPI.menus.add_MenuItem_ShowMessage(tmMenu,"Say Hi"			 ,"TeamMentor Message","Hi");		
	} 	
}
