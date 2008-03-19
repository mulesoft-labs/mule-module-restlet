package org.mule.transport.restlet;

import com.noelios.restlet.Engine;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.restlet.client.MuleClientHelper;
import org.restlet.Client;
import org.restlet.data.Protocol;
import org.restlet.data.Response;
import org.restlet.resource.StringRepresentation;
import org.restlet.util.Helper;

public class RestletClientTestCase extends FunctionalTestCase {

    public void testEcho() throws Exception {
        Engine engine = new Engine(false);
        engine.getRegisteredClients().add(new MuleClientHelper());
        Engine.setInstance(engine);
        
        Client client = new Client(Protocol.HTTP);
        
        Response response = client.post("http://localhost:63080/echo", new StringRepresentation("hello"));
        
        assertEquals(200, response.getStatus().getCode());
        
        assertTrue(response.isEntityAvailable());
        assertEquals("hello", response.getEntity().getText());
    }

    @Override
    protected String getConfigResources() {
        return "dispatcher-config.xml";
    }

}
