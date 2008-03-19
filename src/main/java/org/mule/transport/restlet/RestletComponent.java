package org.mule.transport.restlet;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.transport.restlet.transformer.ResponseToMuleMessageTransformer;
import org.restlet.Restlet;
import org.restlet.data.Request;
import org.restlet.data.Response;

public class RestletComponent implements Callable {
    private Restlet restlet;
    
    private static final ResponseToMuleMessageTransformer transformer = new ResponseToMuleMessageTransformer();
    
    public Object onCall(MuleEventContext eventContext) throws Exception {
        MuleMessage message = eventContext.getMessage();
        
        Request request = (Request) message.getPayload(Request.class);
        
        return transformer.transform(restlet.handle(request));
    }

    public Restlet getRestlet() {
        return restlet;
    }

    public void setRestlet(Restlet restlet) {
        this.restlet = restlet;
    }

}
