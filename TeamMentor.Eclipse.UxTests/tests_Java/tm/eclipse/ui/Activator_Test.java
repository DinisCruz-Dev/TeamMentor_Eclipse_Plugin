package tm.eclipse.ui;

import static org.junit.Assert.*;

import org.junit.Test;

public class Activator_Test {

	@Test
	public void Activator_Ctor() 
	{		
		assertNotNull(Activator.plugin);
        assertNotNull(Activator.getDefault());                        
        assertEquals (Activator.plugin,Activator.getDefault());
	}
	
	@Test
	public void check_that_Embeded_Images_are_There()
	{
		assertNull   (Activator.getImageDescriptor("aaa.img"));;
        assertNotNull(Activator.getImageDescriptor("images/icons/TM.ico"));
        assertNotNull(Activator.getImageDescriptor("images/jpgs/HeaderImage.jpg"));
        
	}
}                

