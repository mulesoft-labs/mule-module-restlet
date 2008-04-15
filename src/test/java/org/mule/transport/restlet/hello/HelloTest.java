package org.mule.transport.restlet.hello;

import java.util.HashMap;
import java.util.Map;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.http.HttpConnector;

public class HelloTest extends FunctionalTestCase {
    public  static final String X_CUSTOM_HEADER = "X-Custom-Header";
    
    public void testApplication() throws Exception {
        MuleClient client = new MuleClient();
        
        Map<String,Object> props = new HashMap<String, Object>();
        props.put(HttpConnector.HTTP_METHOD_PROPERTY, "GET");
        props.put(X_CUSTOM_HEADER, "foo");
        
        MuleMessage result = client.send("http://localhost:9002/bar/foo", "test", props);
        assertEquals("hello, world", result.getPayloadAsString());
        assertEquals("foo", result.getStringProperty(X_CUSTOM_HEADER, ""));
        
        result = client.send("http://localhost:9002/bar/foo?name=Dan", "test", props);
        assertEquals("hello Dan", result.getPayloadAsString());
    }

    @Override
    protected String getConfigResources() {
        return "hello-conf.xml";
    }
    
}
