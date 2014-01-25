package tm.teammentor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.api.Utils;
import tm.eclipse.swt.controls.extra.ObjectViewer;
import tm.eclipse.ui.PluginResources;
import tm.eclipse.ui.Startup;
import tm.eclipse.ui.views.Eclipse_Panel;
import tm.lang.Reflection;
import tm.lang.Reflection.Comparator_FieldToStringValue;

public class TeamMentorMappings 
{
	List<TeamMentorMapping> mappings;
	public String mappingsFile;
	
	public TeamMentorMappings()
	{
		mappingsFile = "/TM_Plugins/Fortify/TM_Mappings_Fortify_CWE.csv";
		loadMappings();
	}
	
	public String rawData_Path()
	{
		return new PluginResources().get_Resource_Saved_on_TempFolder(mappingsFile);
		//return new PluginResources().get_Path_To_Resource_in_TempFolder(mappingsFile);		
	}
	
	public String rawData()
	{
		return new Utils().file_Contents(rawData_Path());	
	}	
	
	public List<String> rawData_Lines()
	{
		String   	 csvData  = rawData();
		String[] 	 rawLines = csvData.split("\\r?\\n");		
		List<String> lines 	  = new ArrayList<String>(Arrays.asList(rawLines));
		lines.remove(0);
		return lines;
	}
	public TeamMentorMapping get(int index)
	{		
		List<String> lines = rawData_Lines(); 
		return new TeamMentorMapping(lines.get(index));
	}
	
	public List<TeamMentorMapping> all()
	{
		return mappings;
	}
	public List<TeamMentorMapping> list()
	{
		return mappings;
	}
	public List<TeamMentorMapping> loadFromDisk()
	{
		List<TeamMentorMapping> mappings = new ArrayList<TeamMentorMapping>();
		for(String line : rawData_Lines())
			mappings.add(new TeamMentorMapping(line));
		return mappings;
		
	}
	public TeamMentorMappings loadMappings()
	{
		mappings = loadFromDisk();
		return this;
	}
	public TeamMentorMapping resolve_by_Fortify(String technology, String fortifyCategory)
	{
		for(TeamMentorMapping mapping: mappings)
			if(mapping.TM_Technology.equals(technology) && mapping.Fortify_Category.trim().equals(fortifyCategory))
				return mapping;
		return null;
	}
	public TeamMentorMapping resolve_by_TM(String technology, String tmTitle)
	{
		for(TeamMentorMapping mapping: mappings)
			if(mapping.TM_Technology.equals(technology) && mapping.TM_Title.equals(tmTitle))
				return mapping;
		return null;
	}
	
	public List<TeamMentorMapping> indexed_by_TM_Title()
	{
		return indexed_by_TM_Title("Java");
	}
	public List<TeamMentorMapping> indexed_by_TM_Title(String technology)
	{
		Map<String,TeamMentorMapping> mappedData = new HashMap<String,TeamMentorMapping>();
		for(TeamMentorMapping mapping : mappings)
			if (mapping.TM_Title.equals("")== false && mapping.TM_Technology.equals(technology))
			mappedData.put(mapping.TM_Title, mapping);
		List<TeamMentorMapping> values = new ArrayList<TeamMentorMapping>(mappedData.values());
		
		Collections.sort(values,new Reflection.Comparator_FieldToStringValue<TeamMentorMapping>("TM_Title"));
		return values;
	}
	
	public static Eclipse_Panel show()
	{
		Eclipse_Panel mappingsView = EclipseAPI.current().views.create("TM Fortify Mappings");

		ObjectViewer objectViewer = mappingsView.clear().add.objectViewer();

		TeamMentorMappings mappings = new TeamMentorMappings();

		objectViewer.show(mappings.all()).column_Clear(1);

		mappingsView.refresh();
		return mappingsView;
	}
}
