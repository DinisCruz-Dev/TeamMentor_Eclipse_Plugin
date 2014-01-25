package tm.eclipse.api;

import static org.junit.Assert.*;

import java.util.List;

import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.junit.Test;

import tm.eclipse.Plugin_Config;

public class Console_Test 
{
	//public EclipseAPI eclipseApi;
	public Console console;	
	 
	String consoleName = "Message Console for JUnit Test";  // there shouldn't be one with this name
	
	public Console_Test()
	{
	//	eclipseApi = Startup.eclipseApi;
		
		console = new Console();
//		assertNotNull(eclipseApi);

		
//		console.close(consoleName);  // while development of this test
	}
	@Test
	public void Console_Ctor()
	{
		assertNotNull(console);
		assertNotNull(console.consolePlugin);
		assertNotNull(console.messageConsole);
		assertNotNull(console.consoleName);				
		assertNotNull(console.consoleManager);  
/*		syncExec(new VoidResult() { @Override public void run() 		
		{ 							
			assertNotNull(console.consoleManager);  // will fail if accessed outside the UI thread							
		}});*/		
		
		assertEquals(Plugin_Config.CONSOLE_NAME,console.consoleName);
		assertEquals(Plugin_Config.CONSOLE_NAME,console.messageConsole.getName());
	}
	@Test
	public void contents()
	{		
		assertNotNull(console.contents());
	}
	@Test
	public void get()
	{
		assertNull   (console.get("AAAAAAAA"));
		assertNotNull(console.get(Plugin_Config.CONSOLE_NAME));
	}
	@Test 
	public void existing()
	{			
		List<IConsole> consoles = console.existing();
		assertNotNull("consoles was null"  , consoles);
		assertTrue   ("Console size was 0", consoles.size() > 0);		
	}
	@Test 
	public void open()
	{		
		String testConsole = "This console should not be there at first";
		
		// check that the console doesn't exist
		assertFalse  (console.exists(testConsole));
		assertNull   (console.get   (testConsole)); 	// get should not create the console
		assertFalse  (console.exists(testConsole));
		
		// call open which will create it
		assertNotNull(console.open  (testConsole));
		assertNotNull(console.get   (testConsole)); 	
		assertTrue   (console.exists(testConsole));

		// close it 
		console.close(testConsole);
		assertFalse  (console.exists(testConsole));
		assertNull   (console.get   (testConsole)); 	
		assertFalse  (console.exists(testConsole));
	}
	
	@Test 
	public void new_MessageConsole()
	{		
		assertFalse(console.exists(consoleName));
		MessageConsole newConsole = console.new_MessageConsole(consoleName);
		assertNotNull(newConsole);		
		console.close(consoleName);		
		assertFalse(console.exists(consoleName));
	}
	
	@Test
	public void write()
	{
		assertFalse(console.exists(consoleName));
		console.new_MessageConsole(consoleName);
		assertTrue (console.exists(consoleName));
		console.write(consoleName, "This is a message");		
		console.close(consoleName);
		
	}
	/*		syncExec(new VoidResult() { public void run() 		
	{
	}});
	*/
}
