/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.restlet;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.restlet.RestletConnector;

/**
 * TODO
 */
public class RestletNamespaceHandlerTestCase extends FunctionalTestCase
{
    protected String getConfigResources()
    {
        //TODO You'll need to edit this file to configure the properties specific to your transport
        return "restlet-namespace-config.xml";
    }

    public void testRestletConfig() throws Exception
    {
        RestletConnector c = (RestletConnector)muleContext.getRegistry().lookupConnector("restletConnector");
        assertNotNull(c);
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());

        //TODO Assert specific properties are configured correctly


    }
}
