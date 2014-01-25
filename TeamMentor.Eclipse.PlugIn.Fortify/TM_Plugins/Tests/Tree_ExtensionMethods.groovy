package tm.eclipse.groovy

import org.eclipse.swt.widgets.Tree;

import tm.eclipse.api.Eclipse_Window;

public class Tree_ExtensionMethods 
{
	public static void setExtensionmethods()
	{
		//Tree
		Tree.metaClass.clear =
			{
				->
						Tree tree = delegate;
						if(tree.getItems().size() > 0)
							tree.removeAll();
						return tree;
			}
			
	    //Tree.metaClass.clear =null;
		
		Tree.metaClass.add_Node =
			{ 
				text ->
						Tree tree = delegate;
						Eclipse_Window.add_Node(tree,text	);
						return 	tree;							
			};
	
		Tree.metaClass.add_Nodes =
			{
				ArrayList texts ->
									Tree tree = delegate;
									//eclipse_Window.add_Node(tree,text	);
									return tree;
			};		
		
		
		//String		
		
		/*String.metaClass.alert = 
			{
				text ->					
							return delegate;			
			};*/
		
			
	}
}
