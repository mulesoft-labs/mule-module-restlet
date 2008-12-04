package org.mule.transport.restlet.client;

import com.noelios.restlet.http.HttpClientHelper;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.module.client.MuleClient;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.restlet.transformer.RequestToMuleMessageTransformer;
import org.restlet.data.Request;
import org.restlet.data.Status;

public class HttpClientCall extends com.noelios.restlet.http.HttpClientCall {

    private static final RequestToMuleMessageTransformer REQUEST_TRANSFORMER = new RequestToMuleMessageTransformer();
    private MuleClient muleClient;
    private MuleMessage muleResponse;
    
    public HttpClientCall(HttpClientHelper helper, String method, 
                          String requestUri, MuleClient muleClient) {
        super(helper, method, requestUri);
        this.muleClient = muleClient;
    }

    /**
     * Sends the request to the client. Commits the request line, headers and
     * optional entity and send them over the network.
     * 
     * @param request
     *            The high-level request.
     * @return The result status.
     */
    public Status sendRequest(Request request) {
        Status result = null;

        try {
            MuleMessage muleRequest = (MuleMessage) REQUEST_TRANSFORMER.transform(request);
            
            muleResponse = muleClient.send(getRequestUri(), muleRequest);
            
            result = new Status(Integer.valueOf((String) muleResponse.getProperty(HttpConnector.HTTP_STATUS_PROPERTY)));
        } catch (Exception e) {
            getHelper().getLogger()
                .log(Level.INFO, "An unexpected error occurred during the sending of the HTTP request.", e);
            result = new Status(Status.CONNECTOR_ERROR_INTERNAL, "Unable to send the HTTP request. "
                                                                 + e.getMessage());
        }

        return result;
    }

    @Override
    public WritableByteChannel getRequestEntityChannel() {
        return null;
    }

    @Override
    public OutputStream getRequestEntityStream() {
        return null;
    }

    @Override
    public OutputStream getRequestHeadStream() {
        return null;
    }

    @Override
    public ReadableByteChannel getResponseEntityChannel(long arg0) {
        return null;
    }

    @Override
    public InputStream getResponseEntityStream(long arg0) {
        try {
            return (InputStream) muleResponse.getPayload(InputStream.class);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }


    
}
