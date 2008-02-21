
package org.mule.transport.restlet;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.mule.api.MessagingException;
import org.mule.api.ThreadSafeAccess;
import org.mule.api.config.MuleProperties;
import org.mule.api.transport.MessageTypeNotSupportedException;
import org.mule.transport.AbstractMessageAdapter;
import org.mule.transport.http.HttpConstants;
import org.restlet.data.Parameter;
import org.restlet.data.Request;
import org.restlet.util.Series;

/**
 * <code>RestletRequestMessageAdapter</code> is a Mule message adapter for {@link Request} objects.
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
public class RestletRequestMessageAdapter extends AbstractMessageAdapter
{

    private static final long serialVersionUID = 1L;

    private Request request;

    public RestletRequestMessageAdapter(final Object message) throws MessagingException
    {
        if (message instanceof Request)
        {
            setPayload((Request) message);
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
                            setProperty(key, ((Object[]) value)[0]);
                        }
                        else
                        {
                            setProperty(key, value);
                        }
                    }
                }
                String realKey;

                for (final Parameter parameter : (Series<Parameter>) attributesMap
                    .get(com.noelios.restlet.http.HttpConstants.ATTRIBUTE_HEADERS))
                {
                    final String key = parameter.getName();
                    realKey = key;
                    if (key.startsWith(HttpConstants.X_PROPERTY_PREFIX))
                    {
                        realKey = key.substring(2);
                    }
                    setProperty(realKey, parameter.getValue());
                }
            }
        }
        else
        {
            throw new MessageTypeNotSupportedException(message, getClass());
        }
    }

    protected RestletRequestMessageAdapter(final RestletRequestMessageAdapter template)
    {
        super(template);
        request = template.request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.umo.providers.UMOMessageAdapter#getMessage()
     */
    public Request getPayload()
    {
        return request;
    }

    public boolean isBinary()
    {
        return false;// !request.getClientInfo().getAcceptedMediaTypes().contains(MediaType.TEXT_ALL);
    }

    private void setPayload(final Request message) throws MessagingException
    {
        request = message;
    }

    public Request getRequest()
    {
        return request;
    }

    /**
     * Sets a replyTo address for this message. This is useful in an asynchronous environment where the caller doesn't
     * wait for a response and the response needs to be routed somewhere for further processing. The value of this field
     * can be any valid endpointUri url.
     * 
     * @param replyTo the endpointUri url to reply to
     */
    @Override
    public void setReplyTo(final Object replyTo)
    {
        if (replyTo != null && replyTo.toString().startsWith("http"))
        {
            setProperty(HttpConstants.HEADER_LOCATION, replyTo);
        }
        setProperty(MuleProperties.MULE_CORRELATION_ID_PROPERTY, replyTo);
    }

    /**
     * Sets a replyTo address for this message. This is useful in an asynchronous environment where the caller doesn't
     * wait for a response and the response needs to be routed somewhere for further processing. The value of this field
     * can be any valid endpointUri url.
     * 
     * @return the endpointUri url to reply to or null if one has not been set
     */
    @Override
    public Object getReplyTo()
    {
        String replyto = (String) getProperty(MuleProperties.MULE_REPLY_TO_PROPERTY);
        if (replyto == null)
        {
            replyto = (String) getProperty(HttpConstants.HEADER_LOCATION);
        }
        return replyto;
    }

    @Override
    public ThreadSafeAccess newThreadCopy()
    {
        return new RestletRequestMessageAdapter(this);
    }

}
