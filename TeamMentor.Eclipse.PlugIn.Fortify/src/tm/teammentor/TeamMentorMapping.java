package tm.teammentor;

public class TeamMentorMapping 
{
	public String Fortify_Category;
	public String CWE_IDs;
	public String TM_GUID;
	public String TM_Title;
	public String TM_Technology;
	
	public String getFortify_Category() { return this.Fortify_Category; }
	public String getCWE_IDs() 		 	{ return this.CWE_IDs; 		 	}
	public String getTM_GUID() 		 	{ return this.TM_GUID; 	     	}
	public String getTM_Title() 		{ return this.TM_Title; 	    }
	public String getTM_Technology() 	{ return this.TM_Technology;    }
	
	public TeamMentorMapping(String cvsData)
	{
		String[] splittedData = cvsData.split(",");
		try
		{
			Fortify_Category = 	splittedData[0];
			CWE_IDs 		 = 	splittedData[1];
			TM_GUID 	     = 	splittedData[2];
			TM_Title 	     = 	splittedData[3];
			TM_Technology    = 	splittedData[4];
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public String guid()
	{
		return TM_GUID;
	}
	@Override
	public String toString()
	{
		return String.format("%s %s %s %s %s", TM_Technology, TM_Title, TM_GUID, CWE_IDs, Fortify_Category);
	}
}
