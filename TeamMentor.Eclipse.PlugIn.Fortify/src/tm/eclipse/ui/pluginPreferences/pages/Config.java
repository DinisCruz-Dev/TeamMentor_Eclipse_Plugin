package tm.eclipse.ui.pluginPreferences.pages;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import tm.eclipse.ui.Activator;
import tm.eclipse.ui.pluginPreferences.PreferenceInitializer;

public class Config extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public Config() 
	{
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("\nPlease use the fields below to configure the TeamMentor's plugin behavior");
	}

	@Override
	protected void createFieldEditors() 
	{
		final Composite parent = getFieldEditorParent();
		Group group_Config = new Group(parent, SWT.NONE);		
		group_Config.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addField(new BooleanFieldEditor(PreferenceInitializer.P_OPEN_ARTICLE_NEW_WINDOW	 , "&Open TeamMentor article in new window"	, group_Config));		
		addField(new BooleanFieldEditor(PreferenceInitializer.P_TEAMMENTOR_LOAD_PLUGINS	 , "&Load Plugins on Startup"				, group_Config));		
		addField(new BooleanFieldEditor(PreferenceInitializer.P_TEAMMENTOR_ADVANCED_MODE , "&Show Advanced Mode Features"			, group_Config));

		
	}

	@Override
	public void init(IWorkbench workbench) { }

}
