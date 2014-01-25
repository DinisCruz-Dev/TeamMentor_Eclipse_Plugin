package tm.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class CollectionToString_Test 
{
	String   	 target1  = "12345";
	String[] 	 target2  = new String[] { "12345", "abcedf"};
	List<String> list1 	 = Arrays.asList ( "12345", "ghijlm","one more");
	@SuppressWarnings("serial")
	Map<String, String> map1 = new HashMap<String,String>() {{ put("key1","value1"); put("key2","value2");}};
	
	String		target1_Expected = "12345";
	String		target2_Expected = target2[0] + "\n" + target2[1] + "\n";
	String		list1_Expected   = list1.get(0) + "\n" + list1.get(1) + "\n" + list1.get(2) + "\n";
	String		map1_Expected    = map1.keySet().toArray()[0]+ "=" + map1.values().toArray()[0] + "\n" + 
								   map1.keySet().toArray()[1]+ "=" + map1.values().toArray()[1] + "\n";
	
	@Test
	public void CollectionToString_Ctor()
	{
		assertNotNull  (new CollectionToString(target1));
		assertNotNull  (new CollectionToString(target2));
		assertNotNull  (new CollectionToString(list1));
		assertNotNull  (new CollectionToString(map1));
		assertNotNull  (new CollectionToString(null));
		
		assertEquals   (new CollectionToString(target1).target, target1);
		assertEquals   (new CollectionToString(target2).target, target2);
		assertEquals   (new CollectionToString(list1  ).target, list1);
		assertEquals   (new CollectionToString(map1   ).target, map1);
		
		assertFalse    (new CollectionToString(target1).target.equals(target2)); // assertNotEquals(new CollectionToString(target1).target, target2);
		assertFalse    (new CollectionToString(target2).target.equals(target1)); // assertNotEquals(new CollectionToString(target2).target, target1);
		assertFalse    (new CollectionToString(target2).target.equals(target1)); // assertNotEquals(new CollectionToString(target2).target, target1);
	}
	
	@Test
	public void isCollection()
	{
		assertFalse (new CollectionToString(null)   .isCollection());
		assertFalse (new CollectionToString(target1).isCollection());
		assertTrue  (new CollectionToString(target2).isCollection());
		assertTrue  (new CollectionToString(list1)  .isCollection());
		assertTrue  (new CollectionToString(map1)   .isCollection());
	}
	
	@Test
	public void asArray()
	{
		assertNull   (new CollectionToString(null)	 .asArray());
		assertNull   (new CollectionToString(target1).asArray());
		assertNotNull(new CollectionToString(target2).asArray());	
		assertNotNull(new CollectionToString(list1)  .asArray());
		assertNotNull(new CollectionToString(map1)   .asArray());
		
		Object[] target2_asArray = new CollectionToString(target2).asArray();
		Object[] list1_asArray   = new CollectionToString(list1)  .asArray();
		
		assertEquals    (target2_asArray.length ,target2.length);
		assertEquals    (list1_asArray.length   ,list1.size()  );
		assertFalse     (target2_asArray.length == list1_asArray.length);  // assertNotEquals (target2_asArray.length ,list1_asArray.length);
		assertFalse     (target2_asArray[0].equals(target2_asArray[1]));    // assertNotEquals (target2_asArray.length ,list1_asArray.length);
		assertFalse     (list1_asArray[0].equals(list1_asArray[1]));      // assertNotEquals (list1_asArray[0]       ,list1_asArray[1]);
		assertEquals    (target2_asArray[0]     ,list1_asArray[0]);
		assertFalse     (target2_asArray[1].equals(list1_asArray[1]));      // assertNotEquals (target2_asArray[1]     ,list1_asArray[1]);
	}
	
	@Test
	public void asString()
	{
		assertNull   (new CollectionToString(null)   .asString());
		assertNotNull(new CollectionToString(target1).asString());
		assertNotNull(new CollectionToString(target2).asString());
		assertNotNull(new CollectionToString(list1)  .asString());
		
		assertEquals(new CollectionToString(null)   .asString(),null);
		assertEquals(new CollectionToString(target1).asString(),target1_Expected);
		assertEquals(new CollectionToString(target2).asString(),target2_Expected);
		assertEquals(new CollectionToString(list1)  .asString(),list1_Expected);
		assertEquals(new CollectionToString(map1)   .asString(),map1_Expected);		
	}
}
