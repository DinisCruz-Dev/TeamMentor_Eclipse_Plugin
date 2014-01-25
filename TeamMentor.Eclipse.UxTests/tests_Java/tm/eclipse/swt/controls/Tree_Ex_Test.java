package tm.eclipse.swt.controls;

import static org.junit.Assert.*;

import java.util.List;

import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Ignore;
import org.junit.Test;

import tm.eclipse.swt.controls.Browser;
import tm.eclipse.swt.controls.Label;
import tm.eclipse.swt.controls.Shell;
import tm.eclipse.swt.controls.Text;
import tm.eclipse.swt.controls.Tree;
import tm.eclipse.swt.controls.extra.Form;

public class Tree_Ex_Test
{
	@Test
	public void tree_Ctor()
	{
		Form form = Form.popupWindow();
				
		Tree tree = Tree.add_Tree(form.shell);
		
		assertTrue(form.controls().size() == 1);
		assertEquals(form.controls().get(0), tree);
		form.close();
		assertTrue(form.shell.isDisposed());
		assertTrue(tree.isDisposed());
		
		//tree.add_Node("Text node");

		//form.close_InMiliSeconds(1000)
		//	.waitForClose();
	}
	
	@Test
	public void add_Node()
	{
		Form form = Form.popupWindow();
				
		Tree tree = form.add_Tree();
		assertEquals(0, tree.get_Nodes().size());
		
		tree.add_Node("1st Node");
		tree.add_Node("2nd Node");
		
		List<TreeItem> nodes = tree.get_Nodes();
		assertEquals(2, nodes.size());
		form.close();
	}
	
	@Test
	@Ignore
	public void swt_browser_example()
	{
		/*Display display = new Display ();
		Shell shell = new Shell(display);
		shell.open ();
		Browser browser = new Browser(shell,SWT.BORDER);
		assertNotNull(browser);
		browser.setUrl("http://www.google.com");
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();*/
		
		
		Display display = new Display();
		final Shell shell = new Shell(display);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		shell.setLayout(gridLayout);
		ToolBar toolbar = new ToolBar(shell, SWT.NONE);
		ToolItem itemBack = new ToolItem(toolbar, SWT.PUSH);
		itemBack.setText("Back");
		ToolItem itemForward = new ToolItem(toolbar, SWT.PUSH);
		itemForward.setText("Forward");
		ToolItem itemStop = new ToolItem(toolbar, SWT.PUSH);
		itemStop.setText("Stop");
		ToolItem itemRefresh = new ToolItem(toolbar, SWT.PUSH);
		itemRefresh.setText("Refresh");
		ToolItem itemGo = new ToolItem(toolbar, SWT.PUSH);
		itemGo.setText("Go");
		
		GridData data = new GridData();
		data.horizontalSpan = 3;
		toolbar.setLayoutData(data);

		Label labelAddress = new Label(shell, SWT.NONE);
		labelAddress.setText("Address");
		
		final Text location = new Text(shell, SWT.BORDER);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		location.setLayoutData(data);

		final Browser browser;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		browser.setLayoutData(data);

		final Label status = new Label(shell, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		status.setLayoutData(data);

		final ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		progressBar.setLayoutData(data);

		/* event handling */
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				ToolItem item = (ToolItem)event.widget;
				String string = item.getText();
				if (string.equals("Back")) browser.back(); 
				else if (string.equals("Forward")) browser.forward();
				else if (string.equals("Stop")) browser.stop();
				else if (string.equals("Refresh")) browser.refresh();
				else if (string.equals("Go")) browser.setUrl(location.getText());
		   }
		};
		ProgressListener progressListener = new ProgressListener() {
			@Override
			public void changed(ProgressEvent event) {
					if (event.total == 0) return;                            
					int ratio = event.current * 100 / event.total;
					progressBar.setSelection(ratio);
			}
			@Override
			public void completed(ProgressEvent event) {
				progressBar.setSelection(0);
			}
		};
		browser.addProgressListener(progressListener);
		browser.addStatusTextListener(new StatusTextListener() {
			@Override
			public void changed(StatusTextEvent event) {
				status.setText(event.text);	
			}
		});
		browser.addLocationListener(new LocationListener() {
			@Override
			public void changed(LocationEvent event) {
				if (event.top) location.setText(event.location);
			}
			@Override
			public void changing(LocationEvent event) {
			}
		});
		itemBack.addListener(SWT.Selection, listener);
		itemForward.addListener(SWT.Selection, listener);
		itemStop.addListener(SWT.Selection, listener);
		itemRefresh.addListener(SWT.Selection, listener);
		itemGo.addListener(SWT.Selection, listener);
		location.addListener(SWT.DefaultSelection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				browser.setUrl(location.getText());
			}
		});
		
		shell.open();
		//browser.setUrl("http://eclipse.org");
		browser.setText("http://www.google.com");
		//while (!shell.isDisposed()) {
		//while (!progressListener.is()) {
		//	if (!display.readAndDispatch())
	//			display.sleep();
	//	}
		display.dispose();
		
	}
}
