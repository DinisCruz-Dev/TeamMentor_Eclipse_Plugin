def typeName = "tm.eclipse.ui.pluginPreferences.pages.MainPreferences_Live_Test"
def targetType = tm.eclipse.ui.pluginPreferences.pages.MainPreferences_Live_Test.class

//return targetType.getDeclaredMethods()

import tm.eclipse.swt.controls.*


def eclipse = eclipse

def view = eclipse.views.create("Executing TeamMentor Preferences JUnitTests").clear();
view.layout_Grid();
//def loadData_Button = view.add_Button("load data");
def execute_Button = view.add_Button("execute");

def treeViewer = view.add.treeViewer().gridData_Fill();
view.add.label("result:");


def result =  Text.add_Text_MultiLine(view.composite,"").fill();; //  view.add.text().fill();

def loadData = new Runnable()  { public void run()
	{
	   treeViewer.getTree().removeAll();
	   for(def method in targetType.getDeclaredMethods())
		   treeViewer.add_Node(method.getName())
	}};



def tree  = treeViewer.getTree();

def execute = new Runnable()  { public void run()
	{

		def selectedNode = treeViewer.getTree().getSelection().first();
		def methodName = selectedNode.text;

		def text = "executing method : " + methodName + "\n\n";

		result.text(text);

		GroovyExecution groovyExecution = new GroovyExecution();			
		def script = String.format("return new %s().%s();",typeName,methodName);
	
		groovyExecution.executeScript(script);

		text += "Unit Test OK";
		result.text(text);

	}}

treeViewer.onDoubleClick(execute);//.click();
execute_Button.onClick(execute)
//loadData_Button.onClick(loadData);
view.refresh();


loadData.run();

//tree.setSelection(treeViewer.nodes()[0]);
//execute_Button.click();

return "done";



