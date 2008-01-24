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

import org.mule.api.transport.Connector;
import org.mule.tck.providers.AbstractConnectorTestCase;

public class RestletConnectorTestCase extends AbstractConnectorTestCase
{

    /* For general guidelines on writing transports see
       http://mule.mulesource.org/display/MULE/Writing+Transports */

    public Connector createConnector() throws Exception
    {
        /* IMPLEMENTATION NOTE: Create and initialise an instance of your
           connector here. Do not actually call the connect method. */

        RestletConnector c = new RestletConnector();
        
        // TODO Set any additional properties on the connector here
        return c;
    }

    public String getTestEndpointURI()
    {
        // TODO Return a valid endpoint for you transport here
        throw new UnsupportedOperationException("getTestEndpointURI");
    }

    public Object getValidMessage() throws Exception
    {
        // TODO Return an valid message for your transport
        throw new UnsupportedOperationException("getValidMessage");
    }


    public void testProperties() throws Exception
    {
        // TODO test setting and retrieving any custom properties on the
        // Connector as necessary
    }

}
