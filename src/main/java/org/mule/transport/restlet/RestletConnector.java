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

import org.mule.api.MuleException;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.service.Service;
import org.mule.api.transport.MessageReceiver;
import org.mule.transport.AbstractConnector;
import org.mule.transport.restlet.servlet.ServletRestletMessageReceiver;
import org.restlet.Application;

import com.noelios.restlet.http.HttpConstants;

/**
 * <code>RestletConnector</code> TODO document
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
public class RestletConnector extends AbstractConnector
{
    public static final String RESTLET = "restlet";

    public static final String HTTP_PREFIX = "http.";

    /**
     * MuleEvent property to pass back the status for the response
     */
    public static final String HTTP_METHOD_PROPERTY = HTTP_PREFIX + "method";

    public static final String HTTP_REQUEST_PROPERTY = HTTP_PREFIX + "request";

    public static final String HTTP_STATUS_PROPERTY = HTTP_PREFIX + "status";

    public static final String[] RESTLET_IGNORE_KEYS = {Application.KEY, HttpConstants.ATTRIBUTE_HEADERS,
        HttpConstants.ATTRIBUTE_VERSION};

    /*
     * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
     */

    /*
     * IMPLEMENTATION NOTE: All configuaration for the transport should be set on the Connector object, this is the
     * object that gets configured in MuleXml
     */

    public RestletConnector()
    {
        super();
        registerSupportedProtocol("http");
        registerSupportedProtocol("https");
        registerSupportedProtocol("servlet");
    }

    @Override
    public void doInitialise() throws InitialisationException
    {
    // Optional; does not need to be implemented. Delete if not required

    /*
     * IMPLEMENTATION NOTE: Is called once all bean properties have been set on the connector and can be used to
     * validate and initialise the connectors state.
     */
    }

    @Override
    public void doConnect() throws Exception
    {
    // Optional; does not need to be implemented. Delete if not required

    /*
     * IMPLEMENTATION NOTE: Makes a connection to the underlying resource. When connections are managed at the
     * receiver/dispatcher level, this method may do nothing
     */
    }

    @Override
    public void doDisconnect() throws Exception
    {
    // Optional; does not need to be implemented. Delete if not required

    /*
     * IMPLEMENTATION NOTE: Disconnects any connections made in the connect method If the connect method did not do
     * anything then this method shouldn't do anything either.
     */
    }

    @Override
    public void doStart() throws MuleException
    {
    // Optional; does not need to be implemented. Delete if not required

    /*
     * IMPLEMENTATION NOTE: If there is a single server instance or connection associated with the connector i.e.
     * AxisServer or a Jms Connection or Jdbc Connection, this method should put the resource in a started state here.
     */
    }

    @Override
    public void doStop() throws MuleException
    {
    // Optional; does not need to be implemented. Delete if not required

    /*
     * IMPLEMENTATION NOTE: Should put any associated resources into a stopped state. Mule will automatically call the
     * stop() method.
     */
    }

    @Override
    public void doDispose()
    {
    // Optional; does not need to be implemented. Delete if not required

    /*
     * IMPLEMENTATION NOTE: Should clean up any open resources associated with the connector.
     */
    }

    public String getProtocol()
    {
        return RESTLET;
    }

    /**
     * Create a Message receiver for this connector
     * 
     * @param component the component that will receive events from this receiver, the listener
     * @param endpoint the endpoint that defies this inbound communication
     * @return an instance of the message receiver defined in this connectors'
     *         {@link org.mule.transport.service.TransportServiceDescriptor} initialised using the component and
     *         endpoint.
     * @throws Exception if there is a problem creating the receiver. This exception really depends on the underlying
     *         transport, thus any exception could be thrown
     */
    @Override
    protected MessageReceiver createReceiver(final Service service, final ImmutableEndpoint endpoint) throws Exception
    {
        if ("servlet".equals(endpoint.getEndpointURI().getScheme()))
        {
            return new ServletRestletMessageReceiver(this, service, endpoint);
        }
        else
        {
            return new RestletMessageReceiver(this, service, endpoint);
        }
    }
}
