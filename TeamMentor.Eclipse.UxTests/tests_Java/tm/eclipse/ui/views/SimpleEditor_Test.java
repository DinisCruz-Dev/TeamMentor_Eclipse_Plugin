package tm.eclipse.ui.views;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;

import org.eclipse.swtbot.swt.finder.results.VoidResult;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IViewPart;
import org.junit.Test;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.groovy.plugins.GroovyExecution;
import tm.eclipse.ui.Startup;
import tm.swtbot.SWTBot_JUnit;
import tm.teammentor.TeamMentorAPI;

public class SimpleEditor_Test extends SWTBot_JUnit
{	
	public SimpleEditor simpleEditorView;
	public EclipseAPI   eclipseAPI;
	
	public SimpleEditor_Test()
	{		
		eclipseAPI = EclipseAPI.current();
		
		IViewPart viewPart = null;
		viewPart =  eclipseAPI.views.open(SimpleEditor.ID); 
		/*try 
		{
			viewPart = eclipseAPI.activePage().showView(SimpleEditor.ID);
		} 
		catch (PartInitException e) 
		{
			e.printStackTrace();
		}*/
		assertNotNull(viewPart);
		assertTrue(viewPart instanceof SimpleEditor);
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
		//assertNull   (simpleEditorView.groovyExecution);				
	}
	@Test
	public void closeSimpleEditor()
	{
		assertNotNull(eclipseAPI.views.reference(SimpleEditor.ID));
		simpleEditorView.close();
		
		assertNull(eclipseAPI.views.reference(SimpleEditor.ID));
			
	}
	
	@Test
	public void compileAndExecuteCode()
	{
		//eclipseAPI.display.syncExec(new Runnable() { public void run() 
		//{
			String testGroovy     = "40+2";
			String expectedResult = "42";
			
			String originalCode = simpleEditorView.get_ScriptToExecute();
			assertNotNull(originalCode);		
			
			// set simple Groovy to test execution		
			simpleEditorView.set_ScriptToExecute(testGroovy);		 
			assertEquals   (testGroovy, simpleEditorView.get_ScriptToExecute());
			Object output  = simpleEditorView.compileAndExecuteCode_Sync();
			assertNotNull  (simpleEditorView.groovyExecution.returnValue);
			assertEquals   (output, simpleEditorView.groovyExecution.returnValue);
			assertEquals   (expectedResult, output.toString());
			
			//check that 42 != 43 :)
			//assertNotEquals(simpleEditorView.compileAndExecuteCode("40+2"), 43);
			assertNotSame(simpleEditorView.compileAndExecuteCode("40+2"), 43);
		//}});
	}
	
	@Test
	public void groovy_Execution_ViaButton()
	{
		syncExec(new VoidResult() { public void run() 
			{
				simpleEditorView.styledText_Result.setText("");
				simpleEditorView.styledText_Code  .setText("30+10+2");
				
				//checks that there is one listener
				Listener[] listeners =  simpleEditorView.execute_Button.getListeners(SWT.Selection);
				assertNotNull(listeners);
				assertEquals(1,listeners.length);
				
				//invokes the button and checks the results
				simpleEditorView.execute_Button.notifyListeners(SWT.Selection, null);
			}});
		
						
		boolean waitResult = simpleEditorView.waitForExecutionComplete();
		assertTrue(waitResult);				
				
			
		assertEquals(simpleEditorView.groovyExecution.returnValue, 42);
		assertEquals(simpleEditorView.groovyExecution.returnValue.toString() , "42");
	}
	@Test
	public void groovy_Execution_Check_Binded_Variables()
	{
		//syncExec(new Runnable() { public void run() 
		//	{
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
				assertEquals   (simpleEditorView.compileAndExecuteCode("!@123$"),null);
				assertEquals   (simpleEditorView.compileAndExecuteCode("return nonExistentVariable"),null);
				simpleEditorView.executeSync = false;
//			}});
		
	}	
	
	@Test
	public void executeScript_UIThread_Sync()
	{
		simpleEditorView.executeSync = true;
		simpleEditorView.groovyExecution = new GroovyExecution();
		simpleEditorView.groovyExecution.scriptToExecute = "return eclipseAPI.activeWorkbenchPage"; 
		simpleEditorView.groovyExecution.executeScript_UIThread_Sync();
		assertEquals(simpleEditorView.groovyExecution.returnValue, simpleEditorView.groovyExecution.eclipseApi.activeWorkbenchPage);
		simpleEditorView.executeSync = false;
		//assertEquals   (simpleEditorView.compileAndExecuteCode("return eclipseAPI.activePage()"), simpleEditorView.groovyExecution.eclipseApi.activePage());
	}
}
