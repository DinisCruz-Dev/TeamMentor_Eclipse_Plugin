package tm.eclipse.helpers;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.asyncExec;
import static org.junit.Assert.*;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.junit.Test;

public class Images_Test 
{
	public String  expected_FirstName = "IMG_DEC_FIELD_ERROR";
	public String  expected_LastName  = "IMG_CORRECTION_MOVE";
	public int     expected_Size      = 173;
	
	@Test
	public void Images_Ctor()
	{ 
		assertNotNull(Images.javaPluginImages_Names);
		assertNotNull(Images.sharedImages_Names);
	}
	
	@Test
	public void names()
	{ 		
		List<String> names = Images.names();
		assertNotNull(names);
		assertTrue   (names.size() > 0);
		assertEquals (names.size()               , expected_Size);
		assertEquals (names.get(0)               , expected_FirstName);
		assertEquals (names.get(expected_Size-1) , expected_LastName);
		
	}
	
	@Test
	public void get()
	{
		Image image_Via_Index = Images.get(0);
		Image image_Via_Name = Images.get(expected_FirstName);
		assertNotNull(image_Via_Index);
		assertNotNull(image_Via_Name);
		assertEquals (image_Via_Name.getImageData().height, image_Via_Index.getImageData().height);
		assertEquals (image_Via_Name.getImageData().width , image_Via_Index.getImageData().width);
		assertEquals (image_Via_Name, image_Via_Index);
		UIThreadRunnable.syncExec(new VoidResult() { public void run() 
			{
				assertEquals (Images.get(expected_Size-1), Images.get(expected_LastName));
			}});
	}
}