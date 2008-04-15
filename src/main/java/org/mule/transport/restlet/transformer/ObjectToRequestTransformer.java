
package org.mule.transport.restlet.transformer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

import org.mule.RequestContext;
import org.mule.api.MuleEvent;
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
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.data.Request;
import org.restlet.resource.InputRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;
import org.restlet.util.Series;

public class ObjectToRequestTransformer extends AbstractMessageAwareTransformer implements DiscoverableTransformer
{
    public static final String MULE_MESSAGE = "mule.message";
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

        Series<Parameter> headers = null;
        Set httpHeaders = HttpConstants.REQUEST_HEADER_NAMES.keySet();
        for (final Iterator itr = message.getPropertyNames().iterator(); itr.hasNext();)
        {
            final String name = (String) itr.next();
            final String value = message.getProperty(name).toString();
            if (HttpConnector.HTTP_METHOD_PROPERTY.equals(name))
            {
                r.setMethod(Method.valueOf(value));
            }
            else if (name.startsWith("X-") || httpHeaders.contains(name))
            {
                if (headers == null) 
                {
                    headers = new Form();
                }
                headers.add(name, value);
            } 
        }
        
        if (headers != null) 
        {
            r.getAttributes().put(com.noelios.restlet.http.HttpConstants.ATTRIBUTE_HEADERS, headers);
        }
        
        r.getAttributes().put(MULE_MESSAGE, message);

        if (r.getMethod() == null)
        {
            r.setMethod(Method.POST);
        }

        final Object payload = message.getPayload();

        String path = message.getStringProperty(HttpConnector.HTTP_REQUEST_PROPERTY, null);
        if (path == null)
        {
            MuleEvent event = RequestContext.getEvent();
            if (event != null) 
            {
                path = event.getEndpoint().getEndpointURI().toString();
            }
        }
        
        if (path == null)
        {
            throw new TransformerException(HttpMessages
                .eventPropertyNotSetCannotProcessRequest(MuleProperties.MULE_ENDPOINT_PROPERTY), this);
        }
        r.setResourceRef(path);

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
            throw new TransformerException(RestletMessages.unsupportedTransformation(payload.getClass()), this);
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
