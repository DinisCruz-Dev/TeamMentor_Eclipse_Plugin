package tm.eclipse.ui.views;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.api.TeamMentorAPI;
import tm.eclipse.groovy.plugins.GroovyExecution;
import tm.eclipse.ui.Startup;

public class SimpleEditor_Test 
{	
	public SimpleEditor simpleEditorView;
	public EclipseAPI   eclipseAPI;
	
	public SimpleEditor_Test()
	{		
		eclipseAPI = Startup.eclipseApi;
		
		IViewPart viewPart = null;
		try 
		{
			viewPart = eclipseAPI.activePage().showView(SimpleEditor.ID);
		} 
		catch (PartInitException e) 
		{
			e.printStackTrace();
		}
		assertNotNull(viewPart);
		simpleEditorView = (SimpleEditor) viewPart;
		assertNotNull(simpleEditorView);				
	}
	
	@Test
	public void SimpleEditor_Ctor()
	{		
		assertNotNull(simpleEditorView.sashForm);
		assertNotNull(simpleEditorView.styledText_Code);
		assertNotNull(simpleEditorView.styledText_Result);
		assertNotNull(simpleEditorView.composite);
		assertNull(simpleEditorView.groovyExecution);				
	}
	
	
	@Test
	public void compileAndExecuteCode()
	{
		String testGroovy     = "40+2";
		String expectedResult = "42";
		
		String originalCode = simpleEditorView.styledText_Code.getText();
		assertNotNull(originalCode);		
		
		// set simple Groovy to test execution		
		simpleEditorView.styledText_Code.setText(testGroovy);		
		assertEquals   (testGroovy, simpleEditorView.styledText_Code.getText());
		Object output  = simpleEditorView.compileAndExecuteCode_Sync();
		assertNotNull  (simpleEditorView.groovyExecution.returnValue);
		assertEquals   (output, simpleEditorView.groovyExecution.returnValue);
		assertEquals   (expectedResult, output.toString());
		
		//check that 42 != 43 :)
		//assertNotEquals(simpleEditorView.compileAndExecuteCode("40+2"), 43);
		assertNotSame(simpleEditorView.compileAndExecuteCode("40+2"), 43);
	}
	
	@Test
	public void groovy_Execution_ViaButton()
	{
		simpleEditorView.styledText_Result.setText("");
		simpleEditorView.styledText_Code  .setText("30+10+2");
		
		//checks that there is one listener
		Listener[] listeners =  simpleEditorView.execute_Button.getListeners(SWT.Selection);
		assertNotNull(listeners);
		assertEquals(1,listeners.length);
		
		//invokes the button and checks the results
		simpleEditorView.execute_Button.notifyListeners(SWT.Selection, null);
		
		boolean waitResult = simpleEditorView.waitForExecutionComplete();
		assertTrue(waitResult);
		assertEquals(simpleEditorView.groovyExecution.returnValue, 42);
		assertEquals(simpleEditorView.groovyExecution.returnValue.toString() , "42");
		
		
		//TODO: there is a race condition on the test below caused by the
		/*    Startup.eclipseApi.display.asyncExec
		 * in 
		 *    showExecutionResult    
		 */
		/*
		eclipseAPI.display.syncExec(new Runnable() { @Override public void run() 
			{
				assertEquals(simpleEditorView.styledText_Result.getText(), "42");		
				assertEquals(simpleEditorView.groovyExecution.returnValue.toString() , "42");
			}});
		assertEquals(simpleEditorView.styledText_Result.getText(), "42");
		*/				
	}
	@Test
	public void groovy_Execution_Check_Binded_Variables()
	{
		simpleEditorView.executeSync = true;
		assertEquals   (simpleEditorView.compileAndExecuteCode("40+2"        	  ), 42);
		assertEquals   (simpleEditorView.compileAndExecuteCode("40+2-10+12-2"	  ), 42);
		assertEquals   (simpleEditorView.compileAndExecuteCode("(40+2).toString()"),"42");
		assertEquals   (simpleEditorView.compileAndExecuteCode("return eclipseApi"), null);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return null"      ), null);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return binding"   	 	), simpleEditorView.groovyExecution.binding	  		);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return configuration"	), simpleEditorView.groovyExecution.configuration	);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return composite"	 	), simpleEditorView.composite	  	);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return importCustomizer"), simpleEditorView.groovyExecution.importCustomizer);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return groovyShell"		), simpleEditorView.groovyExecution.groovyShell	  	);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return view"	  	 	), simpleEditorView			  		);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return eclipseAPI"	 	), simpleEditorView.groovyExecution.eclipseApi	  	);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return teammentorAPI"	), TeamMentorAPI.class	  			);
		
		assertEquals   (simpleEditorView.compileAndExecuteCode("return eclipseAPI.workbench   "), simpleEditorView.groovyExecution.eclipseApi.workbench);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return eclipseAPI.workspace   "), simpleEditorView.groovyExecution.eclipseApi.workspace);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return eclipseAPI.display	  "), simpleEditorView.groovyExecution.eclipseApi.display);			
		
		assertEquals   (simpleEditorView.compileAndExecuteCode(null),null);
		assertEquals   (simpleEditorView.compileAndExecuteCode("!@�$"),null);
		assertEquals   (simpleEditorView.compileAndExecuteCode("return nonExistentVariable"),null);
	}	
	
	@Test
	public void executeScript_UIThread_Sync()
	{
		simpleEditorView.executeSync = true;
		simpleEditorView.groovyExecution = new GroovyExecution();
		simpleEditorView.groovyExecution.scriptToExecute = "return eclipseAPI.activePage()"; 
		simpleEditorView.groovyExecution.executeScript_UIThread_Sync();
		assertEquals(simpleEditorView.groovyExecution.returnValue, simpleEditorView.groovyExecution.eclipseApi.activePage());
		//assertEquals   (simpleEditorView.compileAndExecuteCode("return eclipseAPI.activePage()"), simpleEditorView.groovyExecution.eclipseApi.activePage());
	}
}
