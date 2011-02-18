package org.mule.transport.restlet;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.restlet.data.Response;

public class DispatcherTestCase extends FunctionalTestCase {

    public void testClient() throws Exception {
        String address = "restlet:http://localhost:63080/echo";

        MuleClient client = new MuleClient(muleContext);
        MuleMessage muleResponse = client.send(address, "Hello", null);

        assertNotNull(muleResponse);

        Object payload = muleResponse.getPayload();
        assertTrue(payload instanceof Response);

        Response response = (Response)payload;
        
        assertEquals(200, response.getStatus().getCode());
        assertTrue(response.isEntityAvailable());
    }

    @Override
    protected String getConfigResources() {
        return "dispatcher-config.xml";
    }

}
