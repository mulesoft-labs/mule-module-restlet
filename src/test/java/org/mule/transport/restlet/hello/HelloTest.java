package org.mule.transport.restlet.hello;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;

import com.noelios.restlet.util.Base64;

public class HelloTest extends FunctionalTestCase {
    
    public void testApplication() throws Exception {
        MuleClient client = new MuleClient(muleContext);
        
        MuleMessage message = createMessage();
        
        MuleMessage result = client.send("http://localhost:63081/bar/foo", message);
        assertEquals("hello, world", result.getPayloadAsString());
        assertEquals("foo", result.getStringProperty(HelloWorldResource.X_CUSTOM_HEADER, ""));
        
        message = createMessage();
        
        result = client.send("http://localhost:63081/bar/foo?name=Dan", message);
        assertEquals("hello Dan", result.getPayloadAsString());

        message = createMessage();

        result = client.send("http://localhost:63081/bar/foo?name=Mr. XML", message);
        assertEquals("<hello>Mr. XML</hello>", result.getPayloadAsString());
        assertEquals("application/xml", result.getProperty("Content-Type", PropertyScope.INBOUND));
        
        // try custom status codes
        message = createMessage();
        message.setProperty(HelloWorldResource.X_STATUS_HEADER, "201", PropertyScope.OUTBOUND);
        
        result = client.send("http://localhost:63081/bar/foo", message);
        assertEquals("hello, world", result.getPayloadAsString());
        assertEquals("201", result.getProperty(HttpConnector.HTTP_STATUS_PROPERTY, PropertyScope.INBOUND));
        

        message = createMessage();
        message.setProperty(HttpConnector.HTTP_METHOD_PROPERTY, "HEAD", PropertyScope.OUTBOUND);
        message.setProperty(HelloWorldResource.X_STATUS_HEADER, "204", PropertyScope.OUTBOUND);
        result = client.send("http://localhost:63081/bar/foo", message);
        assertEquals("204", result.getProperty(HttpConnector.HTTP_STATUS_PROPERTY, PropertyScope.INBOUND));
        assertEquals("foo", result.getStringProperty(HelloWorldResource.X_CUSTOM_HEADER, ""));
    }

    @Override
    protected String getConfigResources() {
        return "hello-conf.xml";
    }
    
    private DefaultMuleMessage createMessage() {
        DefaultMuleMessage message = new DefaultMuleMessage("test", muleContext);
        message.setProperty(HelloWorldResource.X_CUSTOM_HEADER, "foo", PropertyScope.OUTBOUND);
        message.setProperty(HttpConnector.HTTP_METHOD_PROPERTY, "GET", PropertyScope.OUTBOUND);
        message.setProperty(HttpConstants.HEADER_AUTHORIZATION, "Basic " + Base64.encode("admin:admin".getBytes(), true), PropertyScope.OUTBOUND);
        return message;
    }
}
