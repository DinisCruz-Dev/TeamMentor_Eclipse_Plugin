package tm.eclipse.ui.pluginPreferences.pages;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import tm.eclipse.ui.Activator;
import tm.eclipse.ui.pluginPreferences.TM_Preferences;

public class EditAboutPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage 
{
	public StyledText aboutText;
	public EditAboutPage()
	{
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("\nThis is the Html shown in the About Page. You can use this to provide customization messages\n\n");
	}
	@Override
	public void init(IWorkbench workbench) {}
	
	@Override
	protected Control createContents(Composite parent) 
	{
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = convertVerticalDLUsToPixels(80 + 50);
		gridData.widthHint  = 400;
		aboutText = new StyledText(parent,  SWT.BORDER  | SWT.H_SCROLL | SWT.V_SCROLL);		
		aboutText.setLayoutData(gridData);
		
		aboutText.setWordWrap(true);
		aboutText.setText(TM_Preferences.getAboutHtml());
		return parent;
	}
	@Override
	protected void createFieldEditors() 
	{
		//StringFieldEditor fieldEditor = new StringFieldEditor (PreferenceInitializer.P_TEAMMENTOR_SERVER, "&Html:", getFieldEditorParent());
		
		//addField(fieldEditor);			
	}
	
	protected void performDefaults() 
	{	
		//.load();
		super.performDefaults();
		aboutText.setText(TM_Preferences.getAboutHtml());
	}
	
	/*
	 * The user has pressed Ok or Apply. Store/apply 
	 * this page's values appropriately.
	 */	
	public boolean performOk() 
	{
		//.store();
		TM_Preferences.setAboutHtml(aboutText.getText());
		return super.performOk();
	}

}
