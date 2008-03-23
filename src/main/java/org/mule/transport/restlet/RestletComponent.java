package org.mule.transport.restlet;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.component.DefaultJavaComponent;
import org.mule.object.SingletonObjectFactory;
import org.mule.transport.restlet.transformer.ResponseToMuleMessageTransformer;
import org.restlet.Restlet;
import org.restlet.data.Request;

public class RestletComponent extends DefaultJavaComponent {
    private Restlet restlet;
    
    private static final ResponseToMuleMessageTransformer transformer = new ResponseToMuleMessageTransformer();
    
    public RestletComponent() {
        super();
        
        setObjectFactory(new SingletonObjectFactory(new RestletCallable(this)));
    }

    public Restlet getRestlet() {
        return restlet;
    }

    public void setRestlet(Restlet restlet) {
        this.restlet = restlet;
    }

    public static class RestletCallable implements Callable {
        private RestletComponent restlet;
        
        public RestletCallable(RestletComponent restlet) {
            super();
            this.restlet = restlet;
        }

        public Object onCall(MuleEventContext eventContext) throws Exception {
            MuleMessage message = eventContext.getMessage();
            
            Request request = (Request) message.getPayload(Request.class);
            
            return transformer.transform(restlet.getRestlet().handle(request));
        }
    }

}
