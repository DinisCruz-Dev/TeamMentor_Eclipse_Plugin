package tm.eclipse.ui.pluginPreferences.pages;

import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Display;

import tm.eclipse.api.EclipseAPI;
import tm.eclipse.groovy.plugins.GroovyExecution;
import tm.eclipse.helpers.Images;
import tm.eclipse.swt.ViewPart;
import tm.eclipse.swt.controls.*;
import tm.eclipse.swt.controls.extra.Form;
import tm.eclipse.swt.jface.TreeViewer;
import tm.eclipse.ui.Startup;
public class JUnitTestExecutionUI 
{
	EclipseAPI eclipse;
	Display    display;
	
	public JUnitTestExecutionUI()
	{
		this(EclipseAPI.current());
	}
	public JUnitTestExecutionUI(EclipseAPI eclipse)
	{
		this.eclipse = eclipse;
		this.display = eclipse.display;
		buildGui();
	}
	public JUnitTestExecutionUI buildGui()
	{
		//return images.view()
		final String typeName = "tm.eclipse.ui.pluginPreferences.pages.MainPreferences_Live_Test";
		final Class<?> targetType = tm.eclipse.ui.pluginPreferences.pages.MainPreferences_Live_Test.class;

		//return targetType.getDeclaredMethods()

	
		//ViewPart view = eclipse.views.create("Executing TeamMentor Preferences JUnitTests").clear(); 

		Shell view = Form.popupWindow().shell
								.title("Executing TeamMentor Preferences JUnitTests")
								.size(350,500);
		view.set.layout.grid(1);
		ToolBar toolBar = view.add.toolBar();
		ToolItem execute_Button =  toolBar.add_Button("execute", "IMG_OBJS_CUNIT");
		ToolItem refresh_Button =  toolBar.add_Button("refresh", "IMG_ELCL_SYNCED");
		SashForm sashForm = view.add.sashForm().vertical();
		
		sashForm.set.layout.grid_Fill();

		//def loadData_Button = view.add.button("load data");
		//def execute_Button = view.add.button("execute");

		final TreeViewer treeViewer = sashForm.add.group().text("Tests").add.treeViewer();
		final Text result            = sashForm.add.group().text("Execution Result").add.textArea("");

		sashForm.weights(3,1);
		
		//view.add.label("result:");



		//def result =  Text.add.TextArea_MultiLine(view.composite,"").fill();; //  view.add.text().fill();

		Runnable loadData = new Runnable()  { public void run()
			{
			   treeViewer.getTree().removeAll();
			   for(Method method : targetType.getDeclaredMethods())
				   treeViewer.add_Node(method.getName().replace("_", " "), method.getName()).image(Images.get("IMG_MISC_PROTECTED"));
			}};

		eclipse.console.log("Building GUI");

		//org.eclipse.swt.widgets.Tree tree  = treeViewer.getTree();
		
		final Runnable execute_Thread= new Runnable()  { public void run()
			{		
				eclipse.console.log("In execute");
				TreeItem selectedNode = treeViewer.selected(); //.getTree().getSelection().first();

				eclipse.console.log("selected node: " + selectedNode.toString());

				String methodName = (String)selectedNode.data();
				
				String text = "executing method : " + methodName + "\n\n";
				eclipse.console.log(text);
				result.text(text);
 
				GroovyExecution groovyExecution = new GroovyExecution();			
				String script = String.format("return new %s().%s();",typeName,methodName);
			
				result.set.color.gray();
				try
			    {
		 			groovyExecution.executeScript(script);
		 			if (groovyExecution.executionException == null)
		 			{
		 				text += "Unit Test OK";
		 				result.set.color.green();
		 				result.text(text);
		 				selectedNode.image(Images.get("IMG_MISC_PUBLIC"));
		 				return;
		 			}
		 			text += "Unit Test FAILED (on Groovy execution): " + groovyExecution.executionException.getMessage() + "\n\n";// + groovyExecution.executionException.getStackTrace().toString() ;
				}
				catch (AssertionError e) 
				{
					eclipse.console.log("exception: " + e.toString());
					text += "Unit Test FAILED: " + e.getMessage() + "\n\n" + e.getClass().toString() ;
					
				}
				result.text(text);
				selectedNode.image(Images.get("IMG_MISC_PRIVATE"));
				result.set.color.red();
				

			}};
		
		Runnable execute = new Runnable()  { public void run() 
			{
				new Thread(execute_Thread).start();
			}};
		treeViewer.onDoubleClick(execute);//.click();
		execute_Button.onClick(execute);
		refresh_Button.onClick(loadData);
		view.refresh();


		loadData.run();

		//tree.setSelection(treeViewer.nodes()[0]);
		//execute_Button.click();

		return this;
	}
}
