package tm.eclipse.ui.pluginPreferences.pages;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
//import org.eclipse.swt.widgets.*;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import tm.eclipse.api.Console;
import tm.eclipse.api.EclipseAPI;
import tm.eclipse.helpers.colors;
import tm.eclipse.swt.controls.Button;
import tm.eclipse.swt.controls.Label;
import tm.eclipse.swt.controls.Text;
import tm.eclipse.ui.Activator;
import tm.eclipse.ui.Startup;
import tm.eclipse.ui.pluginPreferences.PreferenceInitializer;
import tm.eclipse.ui.pluginPreferences.TM_Preferences;
import tm.teammentor.TeamMentorAPI;
import tm.utils.Consts_TM;

public class MainPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static IPreferenceStore 	    store = Activator.getDefault().getPreferenceStore();
	public org.eclipse.swt.widgets.Text server_Text;
	public org.eclipse.swt.widgets.Text loginToken_Text;
	public Display 			 display;
	public StringFieldEditor server_FieldEditor;	
	public StringFieldEditor loginToken;	
	public Text 			 username_Text;
	public Text 			 password_Text;
	public Text 			 ssoToken_Text;
	public Label			 password_Label;
	public Label			 ssoToken_Label;
	public Label			 or_Label;
	public Label			 result;
	public Button			 authenticate_Button;
	public Composite 	     parent;
	public AuthFieldsStatus  setAuthFieldsStatus;
	public AuthButtonStatus  setAuthButtonStatus;	
	public ParseSSOTokenUrl  parseSSOTokenUrl;	
	
	public class loginIntoTM_UsingPasswordOrSSo implements Listener
	{
		public void handleEvent(Event event) 
		{				
			MainPreferences.this.parent.layout(true);
			
			final String  username 			 = username_Text.getText().trim();
			final String  password			 = password_Text.getText().trim();
			final String  ssoToken			 = ssoToken_Text.getText().trim();
			final String  server  			 = server_FieldEditor.getStringValue().trim();
			final boolean loginUsingPassword = ssoToken.equals("");
			
			result.setText("Logging in using " + ((loginUsingPassword) ? "Password" : "SSO Token "));
			
			new Thread(new Runnable() { public void run() 
				{
					final String sessionId = (loginUsingPassword) 
													? TeamMentorAPI.loginIntoTeamMentor(server, username, password)
													: TeamMentorAPI.loginIntoTeamMentor_SSOToken(server,username, ssoToken);
										
					UIThreadRunnable.syncExec(new VoidResult() { public void run() 
						{
							if (sessionId == null || sessionId.equals("") || sessionId.equals(Consts_TM.EMPTY_GUID))
							{
								logMessage("Login failed");									
								result.setText(Consts_TM.MSG_LOGIN_FAILED);
							}
							else
							{
								logMessage("Login Ok");
								result.setText(Consts_TM.MSG_LOGIN_OK);
							}
							TM_Preferences.setServer(server);
							TM_Preferences.setSessionId(sessionId);
							updateApplyButton();
							loginToken.load();		
							
							TeamMentorAPI.setBrowserCookieToTMSession();
							
						}});							
				}}).start();
		}
	}

	public class AuthFieldsStatus implements ModifyListener
	{		
		@Override
		public void modifyText(ModifyEvent e) 
		{
			//Set enable state
			ssoToken_Text.enabled(password_Text.isEmpty());
			password_Text.enabled(ssoToken_Text.isEmpty());
			
			//Set colors
			ssoToken_Label.setForeground(colors.control());
			password_Label.setForeground(colors.control());
			or_Label      .setForeground(colors.control());
			
			if (ssoToken_Text.disabled())
			{
				ssoToken_Label.setForeground(colors.gray());
				or_Label.setForeground(colors.gray());
			}
			if (password_Text.disabled())
			{		
				password_Label.setForeground(colors.gray());
				or_Label.setForeground(colors.gray());
			}	
		}
	}
	public class AuthButtonStatus implements ModifyListener 
	{
		@Override
		public void modifyText(ModifyEvent e) 
		{					
			if(server_FieldEditor.getStringValue().equals("") || username_Text.getText().equals("") || 
			   password_Text.getText().equals("") && ssoToken_Text.getText().equals("")) 
			{
				authenticate_Button.disable();
			}
			else
			{
				authenticate_Button.enable();
			}
		}
	}
	
	public class ParseSSOTokenUrl implements ModifyListener 
	{
		@Override
		public void modifyText(ModifyEvent e) 
		{					
			String ssoToken = ssoToken_Text.getText();

			URL url;
			try 
			{
				url = new URL(ssoToken);
				
				 Map<String, String> query = splitQuery(url);
				 if (query.containsKey("username") && query.containsKey("requestToken"))
				 {
					 String host 		 = String.format("%s://%s", url.getProtocol(), url.getHost());
					 String username     = query.get("username");
					 String requestToken = query.get("requestToken");
					 
					 server_FieldEditor.setStringValue(host);
					 username_Text.setText(username);
					 ssoToken_Text.setText(requestToken);
				 }
			} 
			catch (Exception ex) 
			{
				//not an url
			}
			
		}
	}
	
	public MainPreferences() 
	{
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setAuthFieldsStatus = new AuthFieldsStatus();
		setAuthButtonStatus = new AuthButtonStatus();
		parseSSOTokenUrl    = new ParseSSOTokenUrl();
	}
	public void addGroup_TeamMentorServer()
	{	
		String title = "To access password protected instances of TeamMentor you need to have a valid Session ID.\n\n" + 
				             "Please use the fields below to set the required options:";
	
		Group group = addGroup(title);
		
		server_FieldEditor = new StringFieldEditor (PreferenceInitializer.P_TEAMMENTOR_SERVER		 , "TeamMentor &Server:", group);
		server_Text        = server_FieldEditor.getTextControl(group);
		addField(server_FieldEditor);
		
		server_FieldEditor.getTextControl(group).addModifyListener(setAuthButtonStatus);
		
	}
	public void addGroup_LoginAndGetToken()
	{
		String groupTitle = "Please provide provide a valid username and either a password or SSO token";
		Group group  = addGroup(groupTitle);	
			
		//Username
		new Label(group,SWT.None).setText("User name:");
		
		username_Text = new Text(group,SWT.BORDER);
		username_Text.setText("");
		username_Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		//Password
		password_Label = new Label(group,SWT.None);
		password_Label.setText("Password");
		
		password_Text = new Text(group,SWT.BORDER | SWT.PASSWORD);
		password_Text.setText("");
		password_Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
								
		or_Label = new Label(group,SWT.None).text("or");;
		new Label(group,SWT.None);
		
		//SSO Token
		ssoToken_Label = new Label(group,SWT.None).text("SSO Token");
		ssoToken_Text = new Text(group,SWT.BORDER);
		ssoToken_Text.setText("");
		ssoToken_Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				
		
		new Label(group,SWT.None);
		tm.eclipse.swt.controls.Composite composite = new tm.eclipse.swt.controls.Composite(group);
		composite.set.layout.grid(2);
		authenticate_Button = composite.add.button("Authenticate");
		result = composite.add.label("",SWT.None);
		composite.layout(true);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		
		//Events
		password_Text.addModifyListener(setAuthFieldsStatus);
		ssoToken_Text.addModifyListener(setAuthFieldsStatus);
		
		username_Text.addModifyListener(setAuthButtonStatus);
		password_Text.addModifyListener(setAuthButtonStatus);
		ssoToken_Text.addModifyListener(setAuthButtonStatus);
		
		ssoToken_Text.addModifyListener(parseSSOTokenUrl); 
		
		authenticate_Button.addListener(SWT.Selection, new loginIntoTM_UsingPasswordOrSSo());
		
		new AuthButtonStatus().modifyText(null);
		
	
	}
		
	public void addGroupCurrentSessionId()
	{
		String title = "For reference, here is your current Session ID:";
		Group group  = addGroup(title);	
		
		loginToken 		= new StringFieldEditor (PreferenceInitializer.P_TEAMMENTOR_SESSION_ID	 , "Session ID:"		, group);
		loginToken_Text = loginToken.getTextControl(group);
		loginToken.setEnabled(false, group);
		addField(loginToken);
	}
	
	public Group addGroup(String title)
	{		
		new Label(parent,SWT.None).setText(title);
		Group group = new Group(parent, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));		
		group.setLayout(new GridLayout(2, false));	
		return group;
	}
	public void createFieldEditors() 
	{	
		parent = getFieldEditorParent();
		
		addGroup_TeamMentorServer();
		addGroup_LoginAndGetToken();
		addGroupCurrentSessionId();
		
	}

	public void logMessage(String message)
	{ 
		Console console = EclipseAPI.current().console;	
		console.write(message);
	}
	
	public void init(IWorkbench workbench) { }

	protected void setControl(Control newControl)
	{
		super.setControl(newControl);
	}
	protected Control createContents(Composite parent)
	{		
		this.display = parent.getDisplay();
		return super.createContents(parent);
	}
	
	public String server_Text()
	{
		return UIThreadRunnable.syncExec(display,new Result<String>() { public String run() 
			{
				return server_Text.getText();
			}});
	}
	
	//URL util (move to another class)
	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
}