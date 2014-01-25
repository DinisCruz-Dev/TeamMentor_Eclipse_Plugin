package tm.eclipse.swt.controls;

import static org.junit.Assert.*;

import org.junit.Test;

import tm.eclipse.swt.controls.Button;
import tm.eclipse.swt.controls.extra.Form;

public class Button_Ex_Test
{
	@Test
	public void text_Ctor()
	{
		Form form = Form.popupWindow();
				
		Button button = Button.add_Button(form.shell);
		
		assertTrue(form.controls().size() == 1);
		assertEquals(form.controls().get(0), button);
		assertEquals(form.controls().get(0).getClass(), Button.class);
		form.close();
		assertTrue(form.shell.isDisposed());
		assertTrue(button.isDisposed());
	}
	
	@Test
	public void text_Get_Set()
	{
		Form form = Form.popupWindow();
				
		Button button = form.add_Button();
	
		assertEquals("", button.getText());
		
		String sampleText1 = "123456";		
		
		button.setText(sampleText1);
		assertEquals (sampleText1, button.getText());
		assertTrue (sampleText1.equals(button.getText()));
		
		String sampleText2 = "abcdef";		
		Button button2 = form.add_Button(sampleText2);
		
		assertEquals (sampleText2, button2.getText());
		assertTrue   (sampleText2.equals(button2.getText()));
		
		form.close();
	}
}
