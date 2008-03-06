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

import java.util.HashMap;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.extras.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;

public class RestletRouterTestCase extends FunctionalTestCase
{

    /*
     * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
     */

    public void testMessages() throws Exception
    {
        MuleClient client = new MuleClient();
        
        Map<String, String> props = new HashMap<String, String>();
        props.put(HttpConnector.HTTP_METHOD_PROPERTY, HttpConstants.METHOD_GET);
        MuleMessage result = client.send("http://localhost:9002/orders/123", "", props);
        assertEquals(200, result.getIntProperty(HttpConnector.HTTP_STATUS_PROPERTY, 0));
        
        result = client.send("http://localhost:9002/bleh/123", "", props);
        assertEquals(404, result.getIntProperty(HttpConnector.HTTP_STATUS_PROPERTY, 0));
    }

    @Override
    protected String getConfigResources() {
        return "restlet-router-conf.xml";
    }

}
