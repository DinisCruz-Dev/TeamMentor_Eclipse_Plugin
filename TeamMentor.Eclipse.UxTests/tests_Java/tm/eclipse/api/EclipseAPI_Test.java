package tm.eclipse.api;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import tm.eclipse.api.EclipseAPI;

@RunWith(JUnit4.class)
public class EclipseAPI_Test 
{
        public EclipseAPI eclipseAPI = new EclipseAPI();
        
        @Test
        public void ctor()
        {                
                assertNotNull(eclipseAPI);
                assertNotNull(eclipseAPI.menus);
                assertNotNull(eclipseAPI.menus.workbench);
                assertNotNull(eclipseAPI.panelFactory);                
                assertNotNull(eclipseAPI.panelFactory.workbench);
                assertNotNull(eclipseAPI.views);                
        }
        
        @Test
        public void captureEclipseObjects() 
        {                
                assertNotNull(eclipseAPI);
                assertNotNull(eclipseAPI.workbench);
                assertNotNull(eclipseAPI.display);
                assertNotNull(eclipseAPI.activeWorkbenchWindow);                
                assertNotNull(eclipseAPI.shell);
                assertNotNull(eclipseAPI.workspace);
        }

}