
package org.mule.transport.restlet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.MessageAdapter;
import org.mule.api.transport.OutputHandler;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.http.i18n.HttpMessages;
import org.mule.transport.restlet.i18n.RestletMessages;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Request;
import org.restlet.resource.InputRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;

public class ObjectToRequestTransformer extends AbstractMessageAwareTransformer implements DiscoverableTransformer
{

    private static final String[] IGNORED_REQUEST_HEADERS = {};

    private int priorityWeighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING + 1;

    public ObjectToRequestTransformer()
    {
        super();
        registerSourceType(String.class);
        registerSourceType(byte[].class);
        registerSourceType(InputStream.class);
        registerSourceType(OutputHandler.class);
        setReturnClass(Request.class);
    }

    @Override
    public Object transform(final MuleMessage message, final String encoding) throws TransformerException
    {
        final Request r = new Request();

        for (final Iterator itr = message.getPropertyNames().iterator(); itr.hasNext();)
        {
            final String name = (String) itr.next();
            final String value = message.getProperty(name).toString();
            if (HttpConnector.HTTP_METHOD_PROPERTY.equals(name))
            {
                r.setMethod(Method.valueOf((String) message.getProperty(value)));
            }
            else if (isValidHttpHeader(name))
            {
                r.getAttributes().put(name, value);
            }
        }

        if (r.getMethod() == null)
        {
            r.setMethod(Method.POST);
        }

        final Object payload = message.getPayload();

        final String endpoint = message.getStringProperty(MuleProperties.MULE_ENDPOINT_PROPERTY, null);
        if (endpoint == null)
        {
            throw new TransformerException(HttpMessages
                .eventPropertyNotSetCannotProcessRequest(MuleProperties.MULE_ENDPOINT_PROPERTY), this);
        }
        r.setResourceRef(endpoint);

        final MediaType mediaType = getMediaType(message, encoding);

        Representation rep = null;
        if (payload instanceof InputStream)
        {
            rep = new InputRepresentation((InputStream) payload, mediaType);
        }
        else if (payload instanceof String)
        {
            rep = new StringRepresentation((String) payload, mediaType);
        }
        else if (payload instanceof byte[])
        {
            rep = new InputRepresentation(new ByteArrayInputStream((byte[]) payload), mediaType);
        }
        else if (payload instanceof OutputHandler)
        {

        }
        else
        {
            throw new TransformerException(RestletMessages.unsupportedTransformation(payload.getClass()), this);
        }

        r.setEntity(rep);

        return r;
    }

    protected MediaType getMediaType(final MessageAdapter msg, final String encoding)
    {
        String mimeType = (String) msg.getProperty(HttpConstants.HEADER_CONTENT_TYPE);

        if (mimeType == null)
        {
            mimeType = HttpConstants.DEFAULT_CONTENT_TYPE;
        }

        if (encoding != null && !"UTF-8".equals(encoding.toUpperCase()) && mimeType.indexOf("charset") == -1)
        {
            mimeType += "; charset=" + encoding;
        }
        return new MediaType(mimeType);
    }

    private boolean isValidHttpHeader(final String name)
    {
        // TODO Auto-generated method stub
        return false;
    }

    public int getPriorityWeighting()
    {
        return priorityWeighting;
    }

    public void setPriorityWeighting(final int priorityWeighting)
    {
        this.priorityWeighting = priorityWeighting;
    }

}
