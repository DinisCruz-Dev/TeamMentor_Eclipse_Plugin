package tm.eclipse.groovy.plugins;

import static org.junit.Assert.*;

import org.junit.Test;

public class GroovyPlugins_Test 
{
	GroovyPlugins groovyPlugins;
	
	public GroovyPlugins_Test()
	{
		groovyPlugins = new GroovyPlugins();
	}
	@Test
	public void get_PluginScript_Path()
	{		
		assertNotNull(groovyPlugins.get_PluginScript_Path("TM_Plugins/Fortify/FortifyAPI.groovy"));		
		assertNotNull(groovyPlugins.get_PluginScript_Path("TM_Plugins/Tests/Tree_ExtensionMethods.groovy"));
		assertNotNull(groovyPlugins.get_PluginScript_Path("TM_Plugins/Tests/TestGroovy.groovy"));
		assertNotNull(groovyPlugins.get_PluginScript_Path("TM_Plugins/Tests/MeaningOfLife.groovy"));
		assertNull	 (groovyPlugins.get_PluginScript_Path("TM_Plugins/Fortify/AAAAA"));
		assertNull	 (groovyPlugins.get_PluginScript_Path("TM_Plugins/AAAAA"));
		assertNull	 (groovyPlugins.get_PluginScript_Path("AAAAA"));		
	}
	
	@Test
	public void get_PluginScript()
	{		
		assertNotNull(groovyPlugins.get_PluginScript_Code("TM_Plugins/Fortify/FortifyAPI.groovy"));		
		assertNotNull(groovyPlugins.get_PluginScript_Code("TM_Plugins/Tests/Tree_ExtensionMethods.groovy"));
		assertNotNull(groovyPlugins.get_PluginScript_Code("TM_Plugins/Tests/TestGroovy.groovy"));
		assertNotNull(groovyPlugins.get_PluginScript_Code("TM_Plugins/Tests/MeaningOfLife.groovy"));
		assertNull	 (groovyPlugins.get_PluginScript_Code("TM_Plugins/Fortify/AAAAA"));
		assertNull	 (groovyPlugins.get_PluginScript_Code("TM_Plugins/AAAAA"));
		assertNull	 (groovyPlugins.get_PluginScript_Code("AAAAA"));		
		
		assertEquals("return 40+2", groovyPlugins.get_PluginScript_Code("TM_Plugins/Tests/MeaningOfLife.groovy"));
	}
	
	@Test 
	public void execute_PluginScript()
	{
		Object executionResult = groovyPlugins.execute_PluginScript("TM_Plugins/Tests/MeaningOfLife.groovy");
		assertNotNull(executionResult);
		assertEquals(42, executionResult);
		
		assertNull(groovyPlugins.execute_PluginScript("AAAA"));
	}
	
	
}
