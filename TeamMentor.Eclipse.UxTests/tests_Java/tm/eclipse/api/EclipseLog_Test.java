package tm.eclipse.api;

import static org.junit.Assert.*;
import static tm.eclipse.helpers.log.*;

import org.eclipse.core.internal.runtime.*;
import org.eclipse.core.runtime.*;
import org.eclipse.ui.statushandlers.*;
import org.junit.*;


public class EclipseLog_Test 
{
	public LogListener logListener;
	public StatusManager statusManager;
	
	public class LogListener implements ILogListener 
	{		
		public IStatus lastLogEntry;	
		public void logging(IStatus status, String plugin) 
		{
			lastLogEntry = status;
		}
	}
	
	@SuppressWarnings("restriction")
	@Before
	public void beforeClass()
	{
		logListener = new LogListener();
		statusManager = StatusManager.getManager();
		org.eclipse.core.runtime.Platform.addLogListener(logListener);
		
		assertNotNull(logListener);
		assertNotNull(statusManager);
		assertNull   (logListener.lastLogEntry);
		assertTrue   (RuntimeLog.contains(logListener));
	}
	
	@SuppressWarnings("restriction")
	@After
	public void afterClass()
	{
		org.eclipse.core.runtime.Platform.removeLogListener(logListener);
		assertFalse(RuntimeLog.contains(logListener));
	}	
	
	public void check_LastLogEntry(int expectedSeverity, String expectedMessage)
	{
		IStatus logEntry = logListener.lastLogEntry;
		assertEquals(expectedSeverity, logEntry.getSeverity());
		assertEquals(expectedMessage, logEntry.getMessage());
	}
	
	@Test
	public void test_log_Methods()
	{
		cancel ("cancel Message");	   
		check_LastLogEntry(Status.CANCEL , "cancel Message" );
		
		error  ("error Message");
		check_LastLogEntry(Status.ERROR  , "error Message"  );
				
		info   ("info Message");
		check_LastLogEntry(Status.INFO   , "info Message"   );
		
		ok     ("ok Message");
		check_LastLogEntry(Status.OK     , "ok Message"     );
		
		warning("warning Message");
		check_LastLogEntry(Status.WARNING, "warning Message");
	}
	
}
