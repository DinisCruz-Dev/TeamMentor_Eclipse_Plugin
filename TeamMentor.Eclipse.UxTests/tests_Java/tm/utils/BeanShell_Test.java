package tm.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeanShell_Test 
{
	BeanShell beanShell;
	public BeanShell_Test()
	{
		beanShell = new BeanShell();
	}
	@Test	
	public void BeanShell_Ctor()
	{		
		assertNotNull(beanShell);
		assertNotNull(beanShell.interpreter);
		assertNull	 (beanShell.lastError);
		assertNull	 (beanShell.lastReturnValue);
	}
	
	@Test
	public void eval_No_Errors()
	{
		String script = "return 40 + 2;";
		Object result = beanShell.eval(script);
		assertEquals(beanShell.lastScript, script);
		assertEquals(beanShell.lastReturnValue, result);		
		assertNotNull(result);
		assertNull   (beanShell.lastError);
		assertEquals (result, 42);
	}
	@Test
	public void eval_With_Errors()
	{
		String script = "return 40 + a;";
		Object result = beanShell.eval(script);
		assertEquals (beanShell.lastScript, script);
		assertEquals (beanShell.lastReturnValue, result);		
		assertNull   (result);
		assertNotNull(beanShell.lastError);
		assertTrue   (beanShell.lastError.getMessage().contains("illegal use of undefined variable"));
		//eval with errors
	}

}
