package org.mule.transport.restlet;

import java.util.HashMap;
import java.util.Map;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.http.HttpConnector;

public class FilterTest extends FunctionalTestCase {
    public void testFilter() throws Exception {
        MuleClient client = new MuleClient();
        
        MuleMessage result = client.send("http://localhost:63080/bar/foo", new DefaultMuleMessage("test"));
        assertEquals("test received", result.getPayloadAsString());

        Map<String,Object> props = new HashMap<String, Object>();
        props.put(HttpConnector.HTTP_METHOD_PROPERTY, "GET");
        result = client.send("http://localhost:63080/echo/body", new DefaultMuleMessage(""), props);
        assertEquals("body received", result.getPayloadAsString());
        
        result = client.send("http://localhost:63080/baz", new DefaultMuleMessage("test"));
        assertEquals("test received", result.getPayloadAsString());

        result = client.send("http://localhost:63080/quo", new DefaultMuleMessage("test"));
        assertEquals("test", result.getPayloadAsString());
    }

    @Override
    protected String getConfigResources() {
        return "filter-conf.xml";
    }
    
}
