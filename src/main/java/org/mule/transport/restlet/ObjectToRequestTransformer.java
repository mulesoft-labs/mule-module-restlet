package org.mule.transport.restlet;

import java.io.InputStream;
import java.util.Iterator;

import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.MessageAdapter;
import org.mule.api.transport.OutputHandler;
import org.mule.providers.http.HttpConnector;
import org.mule.providers.http.HttpConstants;
import org.mule.transformer.AbstractTransformer;
import org.mule.transport.restlet.i18n.RestletMessages;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Request;
import org.restlet.resource.InputRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;

public class ObjectToRequestTransformer extends AbstractTransformer {

    private static final String[] IGNORED_REQUEST_HEADERS = {
    };
    
    @Override
    protected Object doTransform(Object o, String encoding) throws TransformerException {
        MessageAdapter message = (MessageAdapter) o;
        Request r = new Request();
        
        for (Iterator itr = message.getPropertyNames().iterator(); itr.hasNext();) {
            String name = (String)itr.next();
            String value = message.getProperty(name).toString();
            if (HttpConnector.HTTP_METHOD_PROPERTY.equals(name)) {
                r.setMethod(Method.valueOf((String)message.getProperty(value)));
            } else if (isValidHttpHeader(name)) {
                r.getAttributes().put(name, value);
            }
        }
        
        MediaType mediaType = getMediaType(message, encoding);
        Object payload = message.getPayload();
        Representation rep = null; 
        if (payload instanceof InputStream) {
            rep = new InputRepresentation((InputStream) payload, mediaType);
        } else if (payload instanceof String) {
            rep = new StringRepresentation((String) payload, mediaType);
        } else if (payload instanceof byte[]) {
            
        }  else if (payload instanceof OutputHandler) {
            
        } else {
            throw new TransformerException(RestletMessages.unsupportedTransformation(payload.getClass()), this);
        }
        return r;
    }

    protected MediaType getMediaType(MessageAdapter msg, String encoding)
    {
        String mimeType = (String) msg.getProperty(HttpConstants.HEADER_CONTENT_TYPE);
        
        if (mimeType == null)
        {
            mimeType = HttpConstants.DEFAULT_CONTENT_TYPE;
        }
        
        if (encoding != null
                && !"UTF-8".equals(encoding.toUpperCase())
                && mimeType.indexOf("charset") == -1)
        {
            mimeType += "; charset=" + encoding;
        }
        return new MediaType(mimeType);
    }
    
    private boolean isValidHttpHeader(String name) {
        // TODO Auto-generated method stub
        return false;
    }

}
