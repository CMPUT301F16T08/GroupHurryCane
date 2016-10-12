package hurrycaneblurryname.ryde;

import android.location.Location;

import junit.framework.TestCase;

/**
 * <h1>Request Test</h1>
 * Tests Request Class
 * <p>
 *     <b>Use Cases</b>
 *     <ul>
 *         <li>UC-1</li>
 *     </ul>
 * </p>
 * @author Blaz Pocrnja
 * @version 1.0
 * @since 10/12/2016
 */
public class RequestTest extends TestCase{

    public void testSetLocations(){
        Location from = new Location("from");
        Location to = new Location("to");
        Request request = new Request();
        request.setLocations(from,to);

        assertTrue("From Location Not Set!", request.getFrom() == from);
        assertTrue("To Location Not Set!", request.getTo() == to);
    }
}