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
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
public class RestletMessageDispatcher extends AbstractMessageDispatcher
{
    private final Client client;

    public RestletMessageDispatcher(final ImmutableEndpoint endpoint)
    {
        super(endpoint);

        final RestletConnector cnn = (RestletConnector) endpoint.getConnector();
        final Protocol protocol = Protocol.HTTP;

        client = new Client(protocol);
    }

    @Override
    public void doConnect() throws Exception
    {
        client.start();
    }

    @Override
    public void doDisconnect() throws Exception
    {
        client.stop();
    }

    @Override
    public void doDispatch(final MuleEvent event) throws Exception
    {
        client.handle(getRequest(event));
    }

    @Override
    public MuleMessage doSend(final MuleEvent event) throws Exception
    {
        final Response response = client.handle(getRequest(event));

        // TODO: redirects?

        return createResponseMessage(response);
    }

    public MuleMessage doReceive(final long timeout) throws Exception
    {
        final Response response = client.get(endpoint.getEndpointURI().getAddress());

        // TODO: redirects?

        return createResponseMessage(response);
    }

    protected MuleMessage createResponseMessage(final Response response)
    {
        return new DefaultMuleMessage(response);
    }

    protected Request getRequest(final MuleEvent event) throws TransformerException
    {
        return (Request) event.transformMessage(Request.class);
    }

    @Override
    public void doDispose()
    {}

}
