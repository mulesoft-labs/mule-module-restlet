/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.restlet;


import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transformer.TransformerException;
import org.mule.transport.AbstractMessageDispatcher;
import org.restlet.Client;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;

/**
 * <code>RestletMessageDispatcher</code> TODO document
 */
public class RestletMessageDispatcher extends AbstractMessageDispatcher
{
    private Client client;

    public RestletMessageDispatcher(ImmutableEndpoint endpoint)
    {
        super(endpoint);
        
        RestletConnector cnn = (RestletConnector)endpoint.getConnector();
        Protocol protocol = Protocol.HTTP;
       
        client = new Client(protocol);
    }

    public void doConnect() throws Exception
    {
        client.start();
    }

    public void doDisconnect() throws Exception
    {
        client.stop();
    }

    public void doDispatch(MuleEvent event) throws Exception
    {
        client.handle(getRequest(event));
    }
    
    public MuleMessage doSend(MuleEvent event) throws Exception
    {
        Response response = client.handle(getRequest(event));
        
        // TODO: redirects?
        
        return createResponseMessage(response); 
    }

    public MuleMessage doReceive(long timeout) throws Exception
    {
        Response response = client.get(endpoint.getEndpointURI().getAddress());
        
        // TODO: redirects?
        
        return createResponseMessage(response); 
    }

    protected MuleMessage createResponseMessage(Response response) {
        return new DefaultMuleMessage(response);
    }

    protected Request getRequest(MuleEvent event) throws TransformerException {
        return (Request)event.transformMessage(Request.class);
    }

    public void doDispose()
    {
    }

}

