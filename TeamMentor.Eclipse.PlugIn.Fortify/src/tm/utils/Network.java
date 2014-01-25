package tm.utils;

import java.net.HttpURLConnection;
import java.net.URL;

import tm.eclipse.Plugin_Config;

public class Network 
{	
	public static boolean online()
	{
		if (Plugin_Config.FORCE_OFFLINE)
			return false;
		return url_Exists("http://www.google.com");
	}
	public static boolean url_Exists(String URLName)
	{		
		if (Plugin_Config.FORCE_OFFLINE)
			return false;
	    try 
	    {
	    	HttpURLConnection.setFollowRedirects(true);	      
		    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
		    con.setInstanceFollowRedirects(true);
		    con.setRequestMethod("HEAD");
		    int responseCode = con.getResponseCode();
		    return (responseCode == HttpURLConnection.HTTP_OK);
	    }
	    catch (Exception e) 
	    {	       
	       return false;
	    }
	  }
}
