/*
 * \$Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.restlet;

import org.mule.api.endpoint.EndpointURI;
import org.mule.endpoint.MuleEndpointURI;
import org.mule.tck.AbstractMuleTestCase;

public class RestletHttpEndpointTestCase extends AbstractMuleTestCase
{

    /*
     * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
     */

    public void testValidEndpointURI() throws Exception
    {
        final EndpointURI url = new MuleEndpointURI("restlet:http://localhost:7856");
        url.initialise();
        assertEquals("restlet", url.getSchemeMetaInfo());
        assertEquals("http", url.getScheme());
        assertEquals("localhost", url.getHost());
        assertEquals(7856, url.getPort());
        assertEquals("http://localhost:7856", url.getAddress());
        assertNull(url.getParams().get(EndpointURI.PROPERTY_ENDPOINT_NAME));
        assertEquals(0, url.getParams().size());
    }

}
