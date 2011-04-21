package org.mule.transport.restlet;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.DynamicPortTestCase;
import org.restlet.Response;

public class DispatcherTestCase extends DynamicPortTestCase {

    public void testClient() throws Exception {
        String address = "restlet:http://localhost:63080/echo";

        MuleClient client = new MuleClient(muleContext);
        MuleMessage muleResponse = client.send(address, "Hello", null);

        assertNotNull(muleResponse);
        logger.debug(muleResponse.getPayloadAsString());
        Object payload = muleResponse.getPayload(Response.class);

        Response response = (Response)payload;
        
        assertEquals(200, response.getStatus().getCode());
        assertTrue(response.isEntityAvailable());
    }

    @Override
    protected String getConfigResources() {
        return "dispatcher-config.xml";
    }

	@Override
	protected int getNumPortsToFind() {
		return 1;
	}

}
