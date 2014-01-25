import tm.eclipse.swt.controls.*

GroovyExecution groovyExecution = new GroovyExecution();
URL binFolder_TMCode   = GroovyExecution.class.getProtectionDomain().getCodeSource().getLocation();
URL binFolder_TMJUnit;
binFolder_TMJUnit = new URL(binFolder_TMCode, "../../TeamMentor.Eclipse.UxTests/bin/");
groovyExecution.addRefToGroovyShell(binFolder_TMJUnit.getPath());

def compilationUnit = eclipse.editors.active_JavaCompilationUnit();

//Config:UIThread__False

import org.eclipse.jdt.ui.JavaUI
def eclipse = eclipse

def view = eclipse.views.create("Execute JUnitTests").clear();
view.layout_Grid();
def loadData_Button = view.add_Button("load data");
def execute_Button = view.add_Button("execute");

def treeViewer = view.add.treeViewer().gridData_Fill();
view.add.label("result:");


def result =  Text.add_Text_MultiLine(view.composite,"").fill();; //  view.add.text().fill();

def loadData = new Runnable()  { public void run()
	{
		def javaCompilationUnit = eclipse.editors.active_JavaCompilationUnit();
		def methodNames = javaCompilationUnit.method_Names();
		treeViewer.getTree().removeAll();

		for(def methodName in methodNames)
		   treeViewer.add_Node(methodName);
	}};



def tree  = treeViewer.getTree();

def execute = new Runnable()  { public void run()
	{
		def javaCompilationUnit = eclipse.editors.active_JavaCompilationUnit();
		def selectedNode = treeViewer.getTree().getSelection().first();
		def methodName = selectedNode.text;
		def method = javaCompilationUnit.method(methodName);

		def text = "executing method : " + method + "\n\n";

		result.text(text);

		def typeName = method.declaringType.fullyQualifiedName;
//		text += GroovyExecution.dev_Execute_TM_JUnit(typeName)

		def script = String.format("return new %s().%s();",typeName,methodName);
		script += "\n\n//Config:UIThread_False";
		groovyExecution.executeScript(script);
		text += "Unit Test OK";
		result.text(text);

	}}

treeViewer.onDoubleClick(execute);//.click();
execute_Button.onClick(execute)
loadData_Button.onClick(loadData);
view.refresh();


loadData.run();
tree.setSelection(treeViewer.nodes()[2]);
execute_Button.click();

return "done";

