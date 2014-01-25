package tm.eclipse.ui;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;

import tm.utils.Action_T1;

public class PluginResources_Test 
{
	public PluginResources pluginResources;
		
	public PluginResources_Test()
	{
		pluginResources = new PluginResources();
		assertNotNull(pluginResources);
	}
	
	@Test
	public void PluginResources_Ctor()
	{
		assertNotNull(pluginResources.plugin);
	}
	
	@Test 
	public void get_Resource_Stream()
	{
		assertNull    (pluginResources.get_Resource_Stream("AAAAA.xyz"));
		assertNull    (pluginResources.get_Resource_Stream("123456.xyz"));
		assertNull    (pluginResources.get_Resource_Stream("/icons"));
		assertNotNull (pluginResources.get_Resource_Stream("plugin.xml"));		
		assertNotNull (pluginResources.get_Resource_Stream("/images/icons/TM.ico"));
		assertNotNull (pluginResources.get_Resource_Stream("/images/jpgs/HeaderImage.jpg"));
	}
	@Test 
	public void get_Path_To_Resource_in_TempFolder()
	{		
		final String tempFolder = pluginResources.get_Plugin_TempPath().toString();
		
		Action_T1<String> checkMapping= new Action_T1<String>()
		{			
			public void run(String resourceName)
			{
				String expected = tempFolder + (resourceName.startsWith("/") ? resourceName : "/" + resourceName);
				String actual   = pluginResources.get_Path_To_Resource_in_TempFolder(resourceName).toString();	
				assertEquals(expected, actual);
			}
		};
		checkMapping.run("AAAAA.xyz");
		checkMapping.run("/AAAAA.xyz");
		checkMapping.run("123456.xyz");
		checkMapping.run("/123456.xyz");
		checkMapping.run("plugin.xml");
		checkMapping.run("/icons");
		checkMapping.run("/icons/TM.ico");
	}
	@Test
	public void has_Resource()
	{
		assertFalse(pluginResources.has_Resource("AAAAA.xyz"));
		assertFalse(pluginResources.has_Resource("123456.xyz"));
		assertTrue (pluginResources.has_Resource("plugin.xml"));
		assertTrue (pluginResources.has_Resource("/images"));
		assertTrue (pluginResources.has_Resource("/images/icons"));
		assertTrue (pluginResources.has_Resource("/images/icons/TM.ico"));
		assertTrue (pluginResources.has_Resource("/images/jpgs"));
		assertTrue (pluginResources.has_Resource("/images/jpgs/HeaderImage.jpg"));		
	}
	
	@Test 
	public void save_Resource_on_TempFolder()
	{		
		//non existing resources should return null
		assertNull(pluginResources.get_Resource_Saved_on_TempFolder("AAAAA.xyz"));
		assertNull(pluginResources.get_Resource_Saved_on_TempFolder("123/AAAAA.xyz"));
		
		//for two existing resources:
		String resource1 = "plugin.xml";
		String resource2 = "/images/jpgs/HeaderImage.jpg";
		
		//get its expected file path
		File file_Resource1 = pluginResources.get_Path_To_Resource_in_TempFolder(resource1).toFile();
		File file_Resource2 = pluginResources.get_Path_To_Resource_in_TempFolder(resource2).toFile();
		
		//make sure they don't exist
		if(file_Resource1.exists())
		{
			assertTrue(file_Resource1.delete());
			assertFalse(file_Resource1.exists());
		}
		if(file_Resource2.exists())
		{
			assertTrue(file_Resource2.delete());
			assertFalse(file_Resource2.exists());
		}
	
		//create them and check they exist
		assertNotNull(pluginResources.get_Resource_Saved_on_TempFolder(resource1));
		assertNotNull(pluginResources.get_Resource_Saved_on_TempFolder(resource2));
		assertTrue(file_Resource1.exists());
		assertTrue(file_Resource2.exists());		
		
		//test deletion				
		assertTrue(file_Resource1.delete());	
		assertTrue(file_Resource2.delete());
		assertFalse(file_Resource1.exists());
		assertFalse(file_Resource2.exists());
	}
}
