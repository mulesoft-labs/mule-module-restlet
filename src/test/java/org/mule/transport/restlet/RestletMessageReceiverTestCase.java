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
import org.mule.api.service.Service;
import org.mule.api.transport.MessageReceiver;
import org.mule.tck.providers.AbstractMessageReceiverTestCase;

import com.mockobjects.dynamic.Mock;

public class RestletMessageReceiverTestCase extends AbstractMessageReceiverTestCase
{

    /*
     * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
     */

    @Override
    public MessageReceiver getMessageReceiver() throws Exception
    {
        final Mock mockComponent = new Mock(Service.class);
        mockComponent.expectAndReturn("getResponseRouter", null);
        return new RestletMessageReceiver(endpoint.getConnector(), (Service) mockComponent.proxy(), endpoint);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ImmutableEndpoint getEndpoint() throws Exception
    {
        endpoint = muleContext.getRegistry().lookupEndpointFactory()
            .getInboundEndpoint("restlet:http://localhost:6789");
        endpoint.getProperties().put("uriPattern", "/endpoint/{endpointId}");
        return endpoint;
    }

}
