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

import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.service.Service;
import org.mule.api.transport.Connector;

/**
 * <code>ServletRestletMessageReceiver</code> is a receiver that is invoked from a Restlet Servlet when an event is
 * received. There is a one-to-one mapping between a ServletRestletMessageReceiver and a Restlet servlet in the serving
 * webapp.
 */
public class ServletRestletMessageReceiver extends AbstractRestletMessageReceiver
{

    /*
     * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
     */

    public ServletRestletMessageReceiver(final Connector connector, final Service service,
    final ImmutableEndpoint endpoint) throws CreateException
    {
        super(connector, service, endpoint);
    }
}
