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

import com.mockobjects.dynamic.Mock;
import org.mule.api.component.Component;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transport.MessageReceiver;
import org.mule.tck.providers.AbstractMessageReceiverTestCase;


public class RestletMessageReceiverTestCase extends AbstractMessageReceiverTestCase
{

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    public MessageReceiver getMessageReceiver() throws Exception
    {
        Mock mockComponent = new Mock(Component.class);
        mockComponent.expectAndReturn("getResponseTransformer", null);
        return new RestletMessageReceiver(endpoint.getConnector(), 
                                          (Component) mockComponent.proxy(), 
                                          endpoint);
    }

    public ImmutableEndpoint getEndpoint() throws Exception
    {
        // TODO return a valid UMOendpoint i.e.
        // return new MuleEndpoint("tcp://localhost:1234", true)
        throw new UnsupportedOperationException("getEndpoint");
    }

}
