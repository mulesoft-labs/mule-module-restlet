package org.mule.transport.restlet;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.restlet.client.MuleClientHelper;
import org.restlet.Client;
import org.restlet.data.Protocol;
import org.restlet.data.Response;
import org.restlet.resource.StringRepresentation;

import com.noelios.restlet.Engine;

public class RestletClientTestCase extends FunctionalTestCase {

    public void testEcho() throws Exception {
        Engine engine = new Engine(false);
        engine.getRegisteredClients().add(new MuleClientHelper(muleContext));
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
