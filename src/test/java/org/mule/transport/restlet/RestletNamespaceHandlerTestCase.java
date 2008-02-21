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

/**
 * TODO
 */
public class RestletNamespaceHandlerTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "restlet-namespace-config.xml";
    }

    public void testRestletConfig() throws Exception
    {
        final RestletConnector c = (RestletConnector) muleContext.getRegistry().lookupConnector("restletConnector");
        assertNotNull(c);
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());

        // TODO Assert specific properties are configured correctly

    }
}
