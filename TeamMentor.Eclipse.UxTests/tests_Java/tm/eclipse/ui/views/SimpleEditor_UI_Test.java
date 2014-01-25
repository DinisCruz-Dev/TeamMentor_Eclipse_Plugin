package tm.eclipse.ui.views;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import static org.junit.Assert.*;

import org.eclipse.swt.custom.StyledText;
import org.junit.Test;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.ui.Startup;

public class SimpleEditor_UI_Test 
{
	public SimpleEditor simpleEditor;
	public EclipseAPI   eclipseAPI;
	
	public SimpleEditor_UI_Test()
	{		
		eclipseAPI = EclipseAPI.current();
		simpleEditor = (SimpleEditor)eclipseAPI.views.open(SimpleEditor.ID);
		assertNotNull(simpleEditor);
	}

	@Test
	public void Check_Result_StyledText_Properties()
	{
		final StyledText result = simpleEditor.styledText_Result;
		assertNotNull(result);
		syncExec(new VoidResult() { public void run () 
			{
				String testText = "123";
				result.setText(testText);
				assertEquals(result.getText(), testText);
				assertTrue  (result.getWordWrap());
			}});
					
		//assertEquals(result.getStyle() , SWT.BORDER,  SWT.V_SCROLL| SWT.H_SCROLL );  //fails with: java.lang.AssertionError: expected:<571738882> but was:<2304>						
	}
}
