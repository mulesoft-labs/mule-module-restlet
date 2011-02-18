package org.mule.transport.restlet;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.component.DefaultJavaComponent;
import org.mule.object.SingletonObjectFactory;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.restlet.Restlet;
import org.restlet.data.Parameter;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.util.Series;

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
            MuleMessage msg = new DefaultMuleMessage(response, req, eventContext.getMuleContext());
            msg.setProperty(HttpConnector.HTTP_STATUS_PROPERTY, response.getStatus().getCode());

            final Map<String, Object> attributesMap = response.getAttributes();
            if (attributesMap != null && attributesMap.size() > 0)
            {
                for (final Map.Entry<String, Object> entry : attributesMap.entrySet())
                {
                    final String key = entry.getKey();
                    final Object value = entry.getValue();
                    if (value != null && !ArrayUtils.contains(RestletConnector.RESTLET_IGNORE_KEYS, key))
                    {
                        if (value.getClass().isArray() && ((Object[]) value).length == 1)
                        {
                            msg.setProperty(key, ((Object[]) value)[0]);
                        }
                        else
                        {
                            msg.setProperty(key, value);
                        }
                    }
                }
                String realKey;
                Series<Parameter> params = (Series<Parameter>) attributesMap.get(com.noelios.restlet.http.HttpConstants.ATTRIBUTE_HEADERS);
                if (params != null) 
                {
                    for (final Parameter parameter : params)
                    {
                        final String key = parameter.getName();
                        realKey = key;
                        if (key.startsWith(HttpConstants.X_PROPERTY_PREFIX))
                        {
                            realKey = key.substring(2);
                        }
                        String value = parameter.getValue();
                        if (value != null)
                        {
                            msg.setProperty(realKey, value);
                        }
                    }
                }
            }
            
            msg.setProperty(HttpConstants.HEADER_CONTENT_TYPE, response.getEntity().getMediaType().toString());
            
            return msg;
        }
    }

}
