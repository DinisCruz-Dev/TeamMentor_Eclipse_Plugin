package teammentor.eclipse.uxtests;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {
	public static final String ID = "TeamMentor.Eclipse.UxTests.view";

	private Browser browser;

	public void createPartControl(Composite parent) 
	{
		browser = new Browser(parent,SWT.H_SCROLL | SWT.V_SCROLL ); 
		browser.setUrl("http://www.google.com");
	}
	public void setFocus() {
		browser.setFocus();
	}
}