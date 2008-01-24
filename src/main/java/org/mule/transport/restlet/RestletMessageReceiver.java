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

import org.mule.api.component.Component;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractPollingMessageReceiver;
import org.mule.transport.ConnectException;

/**
 * <code>RestletMessageReceiver</code> TODO document
 */
public class RestletMessageReceiver extends  AbstractPollingMessageReceiver {

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    public RestletMessageReceiver(Connector connector, Component component,
                              ImmutableEndpoint endpoint)
            throws CreateException
    {
        super(connector, component, endpoint);
    }

    public void doConnect() throws ConnectException
    {
    }

    public void doDisconnect() throws ConnectException
    {
        /* IMPLEMENTATION NOTE: Disconnects and tidies up any rources allocted
           using the doConnect() method. This method should return the
           MessageReceiver into a disconnected state so that it can be
           connected again using the doConnect() method. */

        // TODO release any resources here
    }

    public void doStart()
    {
        // Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE: Should perform any actions necessary to enable
           the reciever to start reciving events. This is different to the
           doConnect() method which actually makes a connection to the
           transport, but leaves the MessageReceiver in a stopped state. For
           polling-based MessageReceivers the start() method simply starts the
           polling thread, for the Axis Message receiver the start method on
           the SOAPService is called. What action is performed here depends on
           the transport being used. Most of the time a custom provider
           doesn't need to override this method. */
    }

    public void doStop()
    {
        // Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE: Should perform any actions necessary to stop
           the reciever from receiving events. */
    }

    public void doDispose()
    {
        // Optional; does not need to be implemented. Delete if not required

        /* IMPLEMENTATION NOTE: Is called when the Conector is being dispoed
           and should clean up any resources. The doStop() and doDisconnect()
           methods will be called implicitly when this method is called. */
    }

    public void poll() throws Exception
    {
        /* IMPLEMENTATION NOTE: Once you have read the object it can be passed
           into Mule by first wrapping the object with the Message adapter for
           this transport and calling routeMessage i.e.

            MessageAdapter adapter = connector.getMessageAdapter(object);
            routeMessage(new MuleMessage(adapter), endpoint.isSynchronous());
        */

        // TODO request a message from the underlying technology e.g. Read a file
    }

}

