package org.mule.transport.restlet.transformer;

import java.io.IOException;
import java.util.Map;

import org.restlet.data.Parameter;
import org.restlet.data.Request;
import org.restlet.util.Series;
import org.apache.commons.lang.ArrayUtils;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractDiscoverableTransformer;
import org.mule.transformer.AbstractTransformer;
import org.mule.transport.DefaultMessageAdapter;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.restlet.RestletConnector;

public class RequestToMuleMessageTransformer extends AbstractDiscoverableTransformer {

    public RequestToMuleMessageTransformer() {
        super();
        registerSourceType(Request.class);
        setReturnClass(MuleMessage.class);
    }

    @SuppressWarnings("unchecked")
    protected Object doTransform(Object src, String encoding) throws TransformerException {
        Request request = (Request) src;
        
        try {
            DefaultMuleMessage msg = new DefaultMuleMessage(request.getEntity().getStream());
            
            final Map<String, Object> attributesMap = request.getAttributes();
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
                        msg.setProperty(realKey, parameter.getValue());
                    }
                }
            }
            return msg;
        } catch (IOException e) {
            throw new TransformerException(this, e);
        }
    }

}
