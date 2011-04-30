/*
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
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
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.util.Series;

/**
 * <code>RestletComponent</code> TODO document.
 * @author <a href="mailto:alberto.aresca@gmail.co">Alberto Aresca</a>
 */
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
            Response response = new Response(request);
            restlet.getRestlet().handle(request, response);
            MuleMessage msg = new DefaultMuleMessage(response, req, eventContext.getMuleContext());
            msg.setInvocationProperty(HttpConnector.HTTP_STATUS_PROPERTY, response.getStatus().getCode());

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
                            msg.setInvocationProperty(key, ((Object[]) value)[0]);
                        }
                        else
                        {
                            msg.setInvocationProperty(key, value);
                        }
                    }
                }
                String realKey;
                Series<Parameter> params = (Series<Parameter>) attributesMap.get(RestletHttpConstants.ATTRIBUTE_HEADERS);
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
                            msg.setInvocationProperty(realKey, value);
                        }
                    }
                }
            }
            
            msg.setInvocationProperty(HttpConstants.HEADER_CONTENT_TYPE, response.getEntity().getMediaType().toString());
            
            return msg;
        }
    }

}
