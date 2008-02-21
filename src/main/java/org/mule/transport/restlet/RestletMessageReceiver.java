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

import java.net.URI;

import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.lifecycle.StartException;
import org.mule.api.lifecycle.StopException;
import org.mule.api.service.Service;
import org.mule.api.transport.Connector;
import org.mule.api.transport.MessageReceiver;
import org.mule.config.i18n.CoreMessages;
import org.mule.transport.ConnectException;
import org.restlet.Component;
import org.restlet.data.Protocol;

/**
 * <code>RestletMessageReceiver</code> TODO document
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
public class RestletMessageReceiver extends AbstractRestletMessageReceiver
{

    protected Component component;

    /*
     * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
     */

    public RestletMessageReceiver(final Connector connector, final Service service, final ImmutableEndpoint endpoint)
    throws CreateException
    {
        super(connector, service, endpoint);
    }

    @Override
    public void doConnect() throws ConnectException
    {
        disposing.set(false);

        if (shouldConnect())
        {
            final URI uri = endpoint.getEndpointURI().getUri();
            int port = uri.getPort();
            if (port == -1)
            {
                port = Protocol.HTTP.getDefaultPort();
            }
            component = new org.restlet.Component();
            component.getServers().add(Protocol.HTTP, port);
            component.getDefaultHost().attach("", new ReceiverRestletApplication(component.getContext(), this));
        }
    }

    protected boolean shouldConnect()
    {
        final StringBuilder requestUri = new StringBuilder(80);
        requestUri.append(endpoint.getProtocol()).append("://");
        requestUri.append(endpoint.getEndpointURI().getHost());
        requestUri.append(':').append(endpoint.getEndpointURI().getPort());
        requestUri.append('*');

        final MessageReceiver[] receivers = connector.getReceivers(requestUri.toString());
        for (final MessageReceiver element : receivers)
        {
            if (element.isConnected())
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public void doDisconnect() throws ConnectException
    {
        // this will cause the server thread to quit
        disposing.set(true);

        /*
         * IMPLEMENTATION NOTE: Disconnects and tidies up any rources allocted using the doConnect() method. This method
         * should return the MessageReceiver into a disconnected state so that it can be connected again using the
         * doConnect() method.
         */

        // TODO release any resources here
    }

    @Override
    public void doStart() throws StartException
    {
        if (component != null && !component.isStarted())
        {
            try
            {
                component.start();
            }
            catch (final Exception e)
            {
                throw new StartException(CoreMessages.failedToStart(this.getReceiverKey()), e, this);
            }
        }
    }

    @Override
    public void doStop() throws StopException
    {
        if (component != null && !component.isStopped())
        {
            try
            {
                component.stop();
            }
            catch (final Exception e)
            {
                throw new StopException(CoreMessages.failedToStop(this.getReceiverKey()), e, this);
            }
        }
    }

    @Override
    public void doDispose()
    {
        component = null;
    }

}
