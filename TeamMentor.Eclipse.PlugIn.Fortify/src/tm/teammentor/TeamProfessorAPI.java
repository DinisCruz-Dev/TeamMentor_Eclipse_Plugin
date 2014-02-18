package tm.teammentor;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.swt.controls.Browser;
import tm.eclipse.ui.pluginPreferences.TM_Preferences;

public class TeamProfessorAPI 
{
	public Browser setTeamProfessorBrowserLinkHook(Browser browser)
	{		
		if (browser.locationListener != null)	// don;t set the hook if there is already one there
			return browser; 				  
		
		
		browser.onLocationChange(new LocationListener() {

			@Override
			public void changing(LocationEvent event) 
			{				
				String location = event.location;  
				EclipseAPI.current().log("[Browser] changing to: " + location);
				String teamProfessorLinkId = TM_Preferences.getTeamProfessorUrlId();
				if (teamProfessorLinkId != null && teamProfessorLinkId.equals("") == false && 
					location.contains(teamProfessorLinkId)) 
				{					
					EclipseAPI.current().log("[Browser] found match for TeamProfessorUrlId: " + TM_Preferences.getTeamProfessorUrlId());
					if (openInNewOperatingSystemBrowserWindow(location))
						event.doit = false;  		// cancel TM load 					
				}				
			}

			@Override
			public void changed(LocationEvent event) 
			{			
			}
			
		});
		return browser;
	}
	public boolean openInNewOperatingSystemBrowserWindow(String location)
	{
		try 
		{			
			return openInNewOperatingSystemBrowserWindow(new URI(location));
		} 
		catch (URISyntaxException e) 
		{
			EclipseAPI.current().log("[Browser] Failed to to create URI from location: " + location);
			return false;
		}
	}
	public boolean openInNewOperatingSystemBrowserWindow(URI location)
	{				
	    try 
	    {
	    	EclipseAPI.current().log("[Browser] Opening in default OS browser window the location: " + location);
			java.awt.Desktop.getDesktop().browse(location);
			return true;
		} 
	    catch (IOException e) 
		{
			EclipseAPI.current().log("[Browser] IOException in getDesktop().browse call for location: " + location);
			e.printStackTrace();
		}
	     
		/*String os = EclipseAPI.current().platform.OS();
		EclipseAPI.current().log("[Browser] For OS " + os + " opening in new window: " + location);
		//
		if(currentArch.equals(Platform.OS_WIN32))
		{
			new java.lang.ProcessBuilder("cmd", location.toString()).start();
		}
		if(currentArch.equals(Platform.OS_MACOSX))
		{
			new java.lang.ProcessBuilder("open", location.toString()).start();
			return true;
		}
		if(currentArch.equals(Platform.OS_LINUX))
		{
			new java.lang.ProcessBuilder("xdg-open", location.toString()).start();
			return true;
		}*/
		return false;
	}
}
