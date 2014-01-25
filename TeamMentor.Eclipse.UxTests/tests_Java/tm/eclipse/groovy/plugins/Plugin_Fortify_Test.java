package tm.eclipse.groovy.plugins;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Plugin_Fortify_Test 
{

	Plugin_Fortify fortify;
	
	public Plugin_Fortify_Test()
	{
		fortify = new Plugin_Fortify();
	}
	
	@Test
	public void code()
	{
		assertNotNull(fortify.code());
	}

}
