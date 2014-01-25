package tm.eclipse.groovy.plugins;

public class Plugin_Fortify 
{
	GroovyPlugins groovyPlugins;
	public Plugin_Fortify()
	{
		groovyPlugins = new GroovyPlugins();
	}
	public String code()
	{
		return groovyPlugins.get_PluginScript_Code("TM_Plugins/Fortify/FortifyAPI.groovy");
	}
	public Object startup() 
	{
		String fortifyScript = "TM_Plugins/Fortify/FortifyAPI.groovy";		
		return groovyPlugins.execute_PluginScript(fortifyScript);		
	}
}
