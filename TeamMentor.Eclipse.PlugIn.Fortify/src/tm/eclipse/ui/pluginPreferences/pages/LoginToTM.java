package tm.eclipse.ui.pluginPreferences.pages;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import tm.eclipse.ui.Activator;
import tm.eclipse.ui.pluginPreferences.PreferenceInitializer;
import tm.eclipse.ui.pluginPreferences.TM_Preferences;
import tm.teammentor.TeamMentorAPI;
import tm.utils.Consts_TM;

public class LoginToTM extends PreferencePage implements IWorkbenchPreferencePage 
{
	public StringFieldEditor fieldEditor;
	
	public LoginToTM() 
	{
		IPreferenceStore store =
				Activator.getDefault().getPreferenceStore();
			setPreferenceStore(store);
	}

	public LoginToTM(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public LoginToTM(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Control createContents(Composite parent) 
	{
		Label label1=new Label(parent, SWT.NULL);
		  label1.setText("User Name: ");
		  
		  final Text username = new Text(parent, SWT.SINGLE | SWT.BORDER);
		  username.setText("");
		  
		  Label label2=new Label(parent, SWT.NULL);
		  label2.setText("Password: ");
		  
		  final Text password = new Text(parent, SWT.SINGLE | SWT.BORDER);
		  System.out.println(password.getEchoChar());
		  password.setEchoChar('*');

		  Button button=new Button(parent,SWT.PUSH);
		  button.setText("Submit");
		  
		  final Label result =new Label(parent, SWT.WRAP);
		  result.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,false,false,1,1)); // needed or the setText below will not show

		  
		  fieldEditor = new StringFieldEditor (PreferenceInitializer.P_TEAMMENTOR_SESSION_ID, "TeamMentor &Session ID:",parent);
		  //fieldEditor.setPreferencePage(this);
		  fieldEditor.setPreferenceStore(getPreferenceStore());
		  fieldEditor.load();
		  fieldEditor.getTextControl(parent).setVisible(false);
		  fieldEditor.getLabelControl(parent).setVisible(false);
		  
		  button.addListener(SWT.Selection, new Listener() 
		  	{	
			  //Shell shell = Startup.eclipseApi.shell;
			  public void handleEvent(Event event) 
			  {				  
				  result.setText("Logging in...");
				  String sessionId = TeamMentorAPI.loginIntoTeamMentor(username.getText(), password.getText());
				  if (sessionId == null || sessionId.equals("") || sessionId.equals(Consts_TM.EMPTY_GUID))
					 result.setText(Consts_TM.MSG_LOGIN_FAILED); 
				  else
				  {
					  //log.info("Before: "+ MainPreferences.getSessionId());
					  TM_Preferences.setSessionId(sessionId);
					  //log.info("After: "+ MainPreferences.getSessionId());
					  result.setText(Consts_TM.MSG_LOGIN_OK); 
					  //shell.layout();
					  //getShell().layout();
					  fieldEditor.load();
					  performApply();
					  performOk();					  
				  }
				  result.redraw();
				
				  }
				  });
		  username.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  password.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		  		  
		return null;
	}
	@Override
	public void createControl(Composite parent)
	{
		super.createControl(parent);
		Button applyButton = getApplyButton();
		applyButton.setVisible(false);
		getDefaultsButton().setVisible(false);
	}
	public boolean performOk()
	{
		fieldEditor.store();
		return super.performOk();
	}
	/*protected void performDefaults() 
	{
		
	}*/

}
