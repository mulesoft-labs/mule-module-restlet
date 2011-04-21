package org.mule.transport.restlet;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.restlet.client.MuleClientHelper;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;

import org.restlet.representation.StringRepresentation;



public class RestletClientTestCase extends FunctionalTestCase {

    public void testEcho() throws Exception {
        Engine engine = new Engine(false);
        engine.getRegisteredClients().add(new MuleClientHelper(muleContext));
        Engine.setInstance(engine);
        
        Client client = new Client(Protocol.HTTP);
        Request request = new Request(Method.POST, "http://localhost:63080/echo");
        request.setEntity(new StringRepresentation("hello"));
        org.restlet.Response response = client.handle(request);
        
        assertEquals(200, response.getStatus().getCode());
        
        assertTrue(response.isEntityAvailable());
        assertEquals("hello", response.getEntity().getText());
    }

    @Override
    protected String getConfigResources() {
        return "dispatcher-config.xml";
    }

}
