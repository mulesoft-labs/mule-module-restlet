package org.mule.transport.restlet.transformer;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.MessageAdapter;
import org.mule.transformer.AbstractDiscoverableTransformer;
import org.mule.transport.DefaultMessageAdapter;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.restlet.RestletConnector;
import org.restlet.data.Parameter;
import org.restlet.data.Response;
import org.restlet.util.Series;

public abstract class AbstractResponseTransformer extends AbstractDiscoverableTransformer {

    public AbstractResponseTransformer() {
        super();
        
        setReturnClass(MuleMessage.class);
        registerSourceType(Response.class);
    }

    @SuppressWarnings("unchecked")
    protected Object doTransform(Object src, String encoding) throws TransformerException {
        Response response = (Response) src;
        
        DefaultMuleMessage msg = new DefaultMuleMessage(getPayload(response, encoding));
        
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
        return msg;
    }

    protected abstract Object getPayload(Response response, String encoding);

}
