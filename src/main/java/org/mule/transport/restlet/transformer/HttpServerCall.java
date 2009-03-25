package org.mule.transport.restlet.transformer;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Iterator;
import java.util.Set;

import org.mule.api.MuleMessage;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.restlet.data.Parameter;
import org.restlet.util.Series;

public class HttpServerCall extends com.noelios.restlet.http.HttpServerCall {
    public HttpServerCall(MuleMessage message, String host, int port) {
        super(host, port);
        Series<Parameter> headers = getRequestHeaders();
        Set httpHeaders = HttpConstants.REQUEST_HEADER_NAMES.keySet();
        for (final Iterator itr = message.getPropertyNames().iterator(); itr.hasNext();)
        {
            final String name = (String) itr.next();
            final String value = message.getProperty(name).toString();
            if (HttpConnector.HTTP_METHOD_PROPERTY.equals(name))
            {
                setMethod(value);
            }
            else if (name.startsWith("X-") || httpHeaders.contains(name))
            {
                headers.add(name, value);
            } 
        }

    }

    @Override
    public ReadableByteChannel getRequestEntityChannel(long arg0) {
        return null;
    }

    @Override
    public InputStream getRequestEntityStream(long arg0) {
        return null;
    }

    @Override
    public ReadableByteChannel getRequestHeadChannel() {
        return null;
    }

    @Override
    public InputStream getRequestHeadStream() {
        return null;
    }

    @Override
    public WritableByteChannel getResponseEntityChannel() {
        return null;
    }

    @Override
    public OutputStream getResponseEntityStream() {
        return null;
    }

}
