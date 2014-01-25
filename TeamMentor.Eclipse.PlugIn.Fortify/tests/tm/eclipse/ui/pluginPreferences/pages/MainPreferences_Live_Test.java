package tm.eclipse.ui.pluginPreferences.pages;

import static org.junit.Assert.*;
import groovy.inspect.swingui.ObjectBrowser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.junit.Test;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.helpers.colors;
import tm.eclipse.swt.Mouse;
import tm.eclipse.swt.controls.Shell;
import tm.eclipse.swt.controls.extra.Form;
import tm.eclipse.ui.Startup;


public class MainPreferences_Live_Test 
{
	public MainPreferences propertyPage;
	public EclipseAPI 	   eclipse ;	
	public Shell   		   shell;
	public Mouse 		   mouse;
	
	public MainPreferences_Live_Test()
	{
		eclipse = EclipseAPI.current();
		mouse = eclipse.mouse;
		mouse.delay(0);
		setup_CreateShell();				
	}
	
	@Test
	public void setup_CreateShell()
	{		
		String title =  "Test main TM Properties Window";
		shell = (Shell)eclipse.platform.shell(title);
		
		if (shell == null)
		{
			shell = Form.popupWindow().shell.title(title)
									  .size(500, 500);
									  //.location(200, 200);
		
			eclipse.syncExec( new Runnable() {  public void run() 
			{
				MainPreferences propertyPage = new MainPreferences();
			
				shell.setBackground(colors.get(SWT.COLOR_WIDGET_BACKGROUND));
				propertyPage.setControl(shell);
				propertyPage.createContents(shell);
				shell.setData(propertyPage);
			}});
			shell.refresh();
		}
		
		propertyPage = (MainPreferences)shell.data();		
		assertNotNull(shell);
		//assertFalse  (shell.isDisposed());
		//assertNotNull(propertyPage);
		shell.focus();
	}
	
	@Test
	public void setup_TestMoveMouse()
	{
		mouse.glide(propertyPage.server_Text)
			 .click(propertyPage.username_Text)
			 .click(propertyPage.password_Text)
			 .click(propertyPage.ssoToken_Text)
			 .click(propertyPage.authenticate_Button)
			 .glide(propertyPage.loginToken_Text);
		
	}
		
	@Test
	public void setup_ShouldFail()
	{				
		assertTrue("This should fail since 1 != 2", 1==2);		
	}
	
	// UI Tests
	
	@Test
	public void Only_Server_Field_should_have_Data()
	{		
		assertFalse("server_Text"  , propertyPage.server_Text().equals(""));
		assertTrue ("username_Text", propertyPage.username_Text.text().equals(""));
		assertTrue ("password_Text", propertyPage.password_Text.text().equals(""));
		assertTrue ("ssoToken_Text", propertyPage.ssoToken_Text.text().equals(""));
	}
	
	@Test
	public void Default_Should_Only_Set_Server_Field()
	{
		shell.close();
		setup_CreateShell();
		Only_Server_Field_should_have_Data();
	}
	
	@Test
	public void Text_in_Username_Should_NOT_Disable_Others()
	{
		propertyPage.username_Text.text("");
		mouse.glide(propertyPage.username_Text)
			 .click()
			 .keyPress("this is is a long username");
		assertTrue ("password_Text", propertyPage.password_Text.enabled());
		assertTrue ("ssoToken_Text", propertyPage.ssoToken_Text.enabled());
	}
	@Test
	public void Text_In_Password_Disables_SSO_Token()
	{
		//reset values
		propertyPage.username_Text.text("");
		propertyPage.password_Text.text("");
		propertyPage.ssoToken_Text.text("");
		
		//check that both password and SSO token are enabled
		assertTrue ("password_Text", propertyPage.password_Text.enabled());
		assertTrue ("ssoToken_Text", propertyPage.ssoToken_Text.enabled());
		assertFalse("authenticate_Button", propertyPage.authenticate_Button.enabled());
		
		//move mouse and enter data
		mouse.glide(propertyPage.username_Text).click()
		 	 	.keyPress("The sso should be disabled after the password field has data")
		 	 .glide(propertyPage.password_Text).click()
		 	 	.keyPress("some pwd")
			 .glide(propertyPage.authenticate_Button);
		
		//check that SSO token is disabled (and password is still enabled)
		assertTrue ("password_Text", propertyPage.password_Text.enabled());
		assertFalse("ssoToken_Text", propertyPage.ssoToken_Text.enabled());
		assertTrue ("authenticate_Button", propertyPage.authenticate_Button.enabled());
	}
	
	@Test
	public void Text_In_SSO_Token_Disables_Password()
	{
		//reset values
		propertyPage.username_Text.text("");
		propertyPage.password_Text.text("");
		propertyPage.ssoToken_Text.text("");
		
		//check that both password and SSO token are enabled
		assertTrue ("password_Text"		 , propertyPage.password_Text	  .enabled());
		assertTrue ("ssoToken_Text"	     , propertyPage.ssoToken_Text	  .enabled());
		assertFalse("authenticate_Button", propertyPage.authenticate_Button.enabled());
		
		//move mouse and enter data
		mouse.glide(propertyPage.username_Text).click()
		 	 	.keyPress("The sso should be disabled after the password field has data")
		 	 .glide(propertyPage.ssoToken_Text).click()
		 	 	.keyPress("some pwd")
		 	 .glide(propertyPage.authenticate_Button);
		
		//check that SSO token is disabled (and password is still enabled)
		assertFalse ("password_Text", propertyPage.password_Text.enabled());
		assertTrue ("ssoToken_Text", propertyPage.ssoToken_Text.enabled());
		assertTrue ("authenticate_Button", propertyPage.authenticate_Button.enabled());
	}
	
	@Test
	public void Fail_to_Login()
	{
		propertyPage.username_Text.text("");
		propertyPage.password_Text.text("");
		propertyPage.ssoToken_Text.text("");
		//move mouse and enter data
		mouse.glide(propertyPage.username_Text).click()
		 	 	.keyPress("anUser")
		 	 .glide(propertyPage.password_Text).click()
		 	 	.keyPress("some pwd")
		 	 .glide(propertyPage.authenticate_Button).click();
		//add assert for status message
		fail("need to check status message");
	}
	
	@Test
	public void Login_with_Test_User()
	{
		propertyPage.username_Text.text("");
		propertyPage.password_Text.text("");
		propertyPage.ssoToken_Text.text("");
		//move mouse and enter data
		mouse.glide(propertyPage.username_Text).click()
		 	 	.keyPress("test")
		 	 .glide(propertyPage.password_Text).click()
		 	 	.keyPress("!!12345")
		 	 .glide(propertyPage.authenticate_Button).click();
		//add assert for status message
		//fail("need to check status message");
	}
}
