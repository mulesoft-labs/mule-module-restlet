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
import org.mule.api.transport.MessageRequesterFactory;
import org.mule.transport.AbstractConnectorTestCase;
import org.restlet.data.Method;
import org.restlet.data.Request;

public class RestletConnectorTestCase extends AbstractConnectorTestCase
{

    /*
     * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
     */

    @Override
    public Connector createConnector() throws Exception
    {
        final RestletConnector c = new RestletConnector(muleContext);
        c.setName("RestletConnector");
        return c;
    }

    @Override
    public String getTestEndpointURI()
    {
        return "restlet:http://localhost:60127";
    }

    @Override
    public Object getValidMessage() throws Exception
    {
        return new Request(Method.GET, "/endpoint");
    }
}
