package tm.eclipse.swt.controls;

import static org.junit.Assert.*;

import org.eclipse.swt.SWTException;
import org.junit.Test;

import tm.eclipse.swt.controls.Browser;
import tm.eclipse.swt.controls.extra.Form;

public class Form_Ex_Test 
{
	@Test(expected = SWTException.class)
	public void form_Ctor()
	{
		Form form = new Form().layout_Fill();				
		assertNotNull(form);
		assertNotNull(form.shell);
		assertNotNull(form.display);
		assertFalse(form.display.isDisposed());
		assertFalse(form.shell.isDisposed());
		assertFalse(form.shell.isVisible());
		
		form.show();
		assertTrue(form.shell.isVisible());
		assertTrue(form.shell.isEnabled());
		assertFalse(form.display.isDisposed());
		assertFalse(form.shell.isDisposed());
		form.close();

		assertTrue(form.shell.isDisposed());
		assertFalse(form.display.isDisposed());
		assertFalse(form.shell.isVisible());    // should throw SWTException: Widget is disposed
	}
	
	@Test
	public void wait_MiliSeconds()
	{		
		Form form = new Form().layout_Fill();	
		Browser browser = Browser.add_Browser(form.shell);
		String htmlText = "this is some test <h1>Html Content</h1>";
		form.show();
		browser.open("about:blank");
		browser.setText(htmlText);
		
		form.wait_MiliSeconds(500);
		if (browser.getText().equals(htmlText) == false)	// wait a bit more if needed
			form.wait_MiliSeconds(500);	
		assertEquals(browser.getText(),htmlText);		
		form.close();
	}
	
	@Test 
	public void add_Browser()
	{
		Form form = Form.popupWindow();
		Browser browser = form.add_Browser();		
		assertNotNull(browser);
		assertTrue(form.controls().size() > 0);
		assertEquals(browser, form.controls().get(0));				
		form.close();
	}
	
}
