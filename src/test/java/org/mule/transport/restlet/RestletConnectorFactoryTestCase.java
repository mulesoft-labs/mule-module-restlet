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
import org.mule.tck.AbstractMuleTestCase;


public class RestletConnectorFactoryTestCase extends AbstractMuleTestCase
{

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    public void testCreateFromFactory() throws Exception
    {
        ImmutableEndpoint endpoint = muleContext.getRegistry()
                .lookupEndpointFactory().getInboundEndpoint(getEndpointURI());
        assertNotNull(endpoint);
        assertNotNull(endpoint.getConnector());
        assertTrue(endpoint.getConnector() instanceof RestletConnector);
        assertEquals(getEndpointURI(), endpoint.getEndpointURI().getAddress());
    }
    
    public String getEndpointURI() 
    {
        // TODO return a valid endpoint URI string for your transport
        // i.e. tcp://localhost:1234
        throw new UnsupportedOperationException("getEndpointURI");
    }

}
