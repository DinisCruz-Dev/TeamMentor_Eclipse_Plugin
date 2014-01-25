package tm.eclipse.pluginPreferences;

import static org.junit.Assert.*;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Ignore;
import org.junit.Test;

import tm.eclipse.Plugin_Config;
import tm.eclipse.ui.pluginPreferences.TM_Preferences;
import tm.utils.Consts_TM;

public class LoginToTM_Test extends Preferences_JUnit
{
	SWTBotButton submitButton;
	SWTBotButton okButton;
	SWTBotLabel  messageLabel;
	SWTBotText   usernameText;
	SWTBotText   passwordText;
	//SWTBotText   sessionIdText;	   // not visible by default
	
	public LoginToTM_Test()
	{
		viewId = "tm.eclipse.ui.PluginPreferences.LoginToTM";
		Plugin_Config.UNIT_TEST_MODE = true;
		assertNull(shell);
		
		
		openPropertiesPage();
		assertNotNull(shell);
		captureControlObjects();
		
		assertNotNull(okButton);
		assertNotNull(submitButton);
		assertNotNull(messageLabel);
		assertNotNull(usernameText);
		assertNotNull(passwordText);
		//assertNotNull(sessionIdText);
	}
	
	@Test 
	@Ignore
	public void open_Preferences_Page()
	{			
		//check textboxes
		assertNotNull(bot.text(0));
		assertNotNull(bot.text(1));
		assertNotNull(bot.text(2));
		
		//check labels
		assertNotNull(bot.label(0));
		assertNotNull(bot.label(1));
		assertNotNull(bot.label(1));		
		assertEquals (bot.label(2).getText(),"User Name: ");
		assertEquals (bot.label(3).getText(),"Password: ");
		
		//check buttons
		assertNotNull(bot.button(0));
		assertEquals (bot.button(0).getText(),"Submit");
		closePropertiesPage();
	}
	
	@Test 	
	@Ignore
	public void click_Login_Button()
	{				
		assertEquals("Submit", submitButton.getText());
		assertEquals(""      , messageLabel.getText());
		assertEquals(""      , usernameText.getText());
		assertEquals(""      , passwordText.getText());
		
		//test with no values
		loginUser("","", Consts_TM.MSG_LOGIN_FAILED);		
		
		//test with good values
		loginUser(Consts_TM.QA_LOGIN_USERNAME,Consts_TM.QA_LOGIN_PASSWORD, Consts_TM.MSG_LOGIN_OK); 		
		
		//test with bad values
		loginUser(Consts_TM.QA_LOGIN_USERNAME,"" 						 , Consts_TM.MSG_LOGIN_FAILED); // empty password		
		loginUser(""                         ,Consts_TM.QA_LOGIN_PASSWORD , Consts_TM.MSG_LOGIN_FAILED); // empty username		
		loginUser(Consts_TM.QA_LOGIN_USERNAME,Consts_TM.QA_LOGIN_USERNAME , Consts_TM.MSG_LOGIN_FAILED); // repeated username
		loginUser(Consts_TM.QA_LOGIN_PASSWORD,Consts_TM.QA_LOGIN_PASSWORD , Consts_TM.MSG_LOGIN_FAILED); // repeated password
		
		closePropertiesPage();
	}	
	
	@Test	
	@Ignore
	public void check_Properties_Values_After_Login()
	{		
		TM_Preferences.setSessionId("12345_ABCDEF");		
		assertFalse(Consts_TM.QA_LOGIN_SESSION.equals(TM_Preferences.getSessionId()));	// was assertNotEquals 

		loginUser(Consts_TM.QA_LOGIN_USERNAME,Consts_TM.QA_LOGIN_PASSWORD, Consts_TM.MSG_LOGIN_OK);
		assertEquals(Consts_TM.QA_LOGIN_SESSION, TM_Preferences.getSessionId());
		//assertEquals(Consts_TM.QA_LOGIN_SESSION,sessionIdText.getText());
		
		okButton.click();	
		assertEquals(Consts_TM.QA_LOGIN_SESSION, TM_Preferences.getSessionId());	
	}
	
/*	@Test		
	public void check_Race_Condition_on_Login_SessionID_Save()
	{
		//log.info("sessionIdText.getText(): " + sessionIdText.getText());
		String value1 = "12345_ABCDEF";
		String value2 = "ABCDEF_12345";
		String value3 = "ABCDEF_BBBBBB";	
		//String currentValue = MainPreferences.getSessionId();					
		MainPreferences.setSessionId(value1);
		assertEquals(value1, MainPreferences.getSessionId());
		okButton.click();
		assertEquals(value1, MainPreferences.getSessionId());		
		
		openPropertiesPage();	
		captureControlObjects();
		//assertEquals(sessionIdText.getText(), value1);
		//assertEquals(sessionIdText.getText(), MainPreferences.getSessionId());
		//log.info("sessionIdText.getText(): " + sessionIdText.getText());
		//log.info("MainPreferences.getSessionId(): " + MainPreferences.getSessionId());
		MainPreferences.setSessionId(value2);
		assertEquals(value2, MainPreferences.getSessionId());
		okButton.click();
		assertEquals(value2, MainPreferences.getSessionId());			
		
		openPropertiesPage();	
		captureControlObjects();
		//assertEquals(sessionIdText.getText(), value2);
		//assertEquals(sessionIdText.getText(), MainPreferences.getSessionId());
		log.info(value3);
		//sessionIdText.setText(value3);		
		//assertEquals(sessionIdText.getText()	   , value3);
		assertNotEquals(MainPreferences.getSessionId(), value3);
		log.info(MainPreferences.getSessionId());
		//bot.button("Apply").click();
		okButton.click();
		log.info(MainPreferences.getSessionId());
		assertEquals(value3,MainPreferences.getSessionId());
	}*/
	
	//Helper methods
	public String loginUser(String username, String password, String expectedResult)
	{
		usernameText.setText(username);
		passwordText.setText(password);
		submitButton.click();
		String result = messageLabel.getText();
		assertEquals(expectedResult, result);
		return result;
	}
	public void captureControlObjects()
	{
		okButton = bot.button("OK");
		submitButton  = bot.button("Submit");
		messageLabel  = bot.label(4);
		usernameText  = bot.text(1);
		passwordText  = bot.text(2);
		//sessionIdText = bot.text(3);
	}
}
