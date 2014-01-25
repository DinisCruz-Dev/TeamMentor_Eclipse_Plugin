package tm.eclipse.groovy.plugins;

import static org.junit.Assert.*;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.net.URL;
import java.util.ArrayList;
import org.junit.Test;

public class GroovyExecution_Test 
{
	public GroovyExecution groovyExecution;
	
	public GroovyExecution_Test()
	{
		groovyExecution = new GroovyExecution();
	}
	
	@Test
	public void setGroovyShell() 
	{
		setBindingVariablesValues();
		setCompilerConfiguration();
		
		//first when there are no extraGroovyJars
		assertEquals(0,groovyExecution.eclipseApi.extraGroovyJars.size());
		GroovyShell first_GroovyShell = groovyExecution.setGroovyShell();
		assertNotNull(groovyExecution.groovyShell);	
		URL[] urls = groovyExecution.get_GroovyShell_ClassLoader_Urls();
		assertEquals(0,urls.length);
		
		//add and extraGroovyJars and check that it is there
		String testJar = "/abc/123.jar";
		groovyExecution.eclipseApi.extraGroovyJars.add(testJar);	
		GroovyShell second_GroovyShell = groovyExecution.setGroovyShell();		
		urls = groovyExecution.get_GroovyShell_ClassLoader_Urls();
		assertEquals(1,urls.length);
		assertNotSame(first_GroovyShell, second_GroovyShell);
		
		/*simpleEditorView.eclipseApi.extraGroovyJars.add("bb$%aa");		// this is not throwing the exception
		  simpleEditorView.create_and_Load_Jars_into_GroovyShell();
		*/
		
		//reset extraGroovyJars		
		groovyExecution.eclipseApi.extraGroovyJars = new ArrayList<String>();
	}
	@Test
	public void executeScript						 () 
	{		
		Object result = groovyExecution.executeScript("40 + 2");
		assertNotNull(result);
		assertEquals (result		   ,  42);
		assertEquals (result.toString(), "42");
		groovyExecution.scriptToExecute = "40 +3";
		assertEquals(43 , groovyExecution.executeScript());
	}
	@Test
	public void executeScript_Sync					 () 
	{
		groovyExecution.scriptToExecute = "40 +4";
		assertEquals(44 , groovyExecution.executeScript_Sync());
	}
	@Test
	public void executeScript_ASync					 () throws InterruptedException 
	{
		groovyExecution.scriptToExecute = "40 +5";
		Thread thread = groovyExecution.executeScript_ASync();
		assertNotNull(thread);
		assertNull   (groovyExecution.returnValue);		
		thread	.join();		
		assertNotNull(groovyExecution.returnValue);
		assertEquals (45, groovyExecution.returnValue);
	}
	public void executeScript_UIThread_Sync					 () 
	{
		groovyExecution.scriptToExecute = "40 +6";
		assertEquals(46 , groovyExecution.executeScript_UIThread_Sync());
	}
	public void executeScript_UIThread_ASync()	
	{
		groovyExecution.scriptToExecute = "40 +7";
		groovyExecution.executeScript_UIThread_ASync();
		assertNull(groovyExecution.returnValue);
		//note: see commented code at the end of this file for an explanation of why the check below will hang the Unit tests
		//assertEquals (47, groovyExecution.returnValue);
	}
	@Test
	public void get_GroovyShell_ClassLoader_Urls() 
	{
		groovyExecution.groovyShell= null; 
		assertNull(groovyExecution.get_GroovyShell_ClassLoader_Urls());
		setGroovyShell();
		assertNotNull(groovyExecution.get_GroovyShell_ClassLoader_Urls());
	}
	public void GroovyExecution_Ctor				 () 
	{	
		assertNotNull(groovyExecution.eclipseApi);
		assertNotNull(groovyExecution.binding);
		assertNotNull(groovyExecution.configuration);
		assertNotNull(groovyExecution.importCustomizer);		
		assertNotNull(groovyExecution.groovyShell);
		assertNull   (groovyExecution.returnValue);
	}
	@Test 
	public void setBindingVariablesValues			 () 
	{
		Binding binding = groovyExecution.setBindingVariablesValues();
		
		assertNotNull(binding);	
		assertNotNull(groovyExecution.binding);
		
		//these throws: java.lang.LinkageError: loader constraint violation: loader (instance of org/eclipse/osgi/internal/baseadaptor/DefaultClassLoader) previously initiated loading for a different type with name "groovy/lang/Binding"		
		//Map variables = binding.getVariables();  		
		/*assertNotNull(variables);
		  assertEquals(10,variables.size());		
		  assertNotNull(binding.getVariable("openArticle"));
		  assertNotNull(binding.getVariable("loginIntoTM"));		
		  assertNull(binding.getVariable("ABCDEFG"));
		*/  	
	}
	@Test 
	public void setCompilerConfiguration			 () 
	{		
		groovyExecution.setCompilerConfiguration();
		assertNotNull(groovyExecution.configuration);
		assertNotNull(groovyExecution.importCustomizer);
	}

	/* this test is being stuck in the Unit test, I think it is because the Unit test is 
	 * already running on the UI Thread
	@Test
	public void executeScript_UIThread_ASync					 () throws InterruptedException  
	{
		final CountDownLatch latch = new CountDownLatch(1);
		groovyExecution.onExecutionComplete = new Runnable() { @Override public void run() 
			{
				latch.countDown();
			}};		
		groovyExecution.scriptToExecute = "40 + 7";
		groovyExecution.executeScript_UIThread_ASync();
		latch.await();		
		/ *
		final Object lock = new Object();
		Thread thread = new Thread(new Runnable() { @Override public void run()
		{
			groovyExecution.scriptToExecute = "40 + 7";
			synchronized(lock)
			{
				groovyExecution.onExecutionComplete = new Runnable() { @Override public void run() 
				{
					lock.notify();
				}};		
				groovyExecution.executeScript_UIThread_ASync();
				assertNull   (groovyExecution.returnValue);
				try 
				{
					lock.wait();
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}	
			}	
		}});
		thread.start();		
		thread.join();
		* /
		//assertNotNull(groovyExecution.returnValue);
		assertEquals (47, groovyExecution.returnValue);
	}*/
}
