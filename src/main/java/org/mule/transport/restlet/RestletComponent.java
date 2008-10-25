package org.mule.transport.restlet;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.component.DefaultJavaComponent;
import org.mule.object.SingletonObjectFactory;
import org.mule.transport.http.HttpConnector;
import org.restlet.Restlet;
import org.restlet.data.Request;
import org.restlet.data.Response;

public class RestletComponent extends DefaultJavaComponent {
    private Restlet restlet;
    
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
            MuleMessage req = eventContext.getMessage();
            
            Request request = (Request) req.getPayload(Request.class);
            
            Response response = restlet.getRestlet().handle(request);
            MuleMessage responseMsg = new DefaultMuleMessage(response, req);
            responseMsg.setProperty(HttpConnector.HTTP_STATUS_PROPERTY, response.getStatus().getCode());
            return responseMsg;
        }
    }

}
