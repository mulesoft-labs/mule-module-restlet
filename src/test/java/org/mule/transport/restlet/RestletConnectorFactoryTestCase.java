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

import junit.framework.Assert;

import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transport.Connector;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.transport.AbstractConnectorTestCase;
import org.mule.transport.http.HttpConnector;

public class RestletConnectorFactoryTestCase extends AbstractConnectorTestCase
{
    /*
     * For general guidelines on writing transports see http://mule.mulesource.org/display/MULE/Writing+Transports
     */

    public void testCreateFromFactory() throws Exception{
        
    	
    	final ImmutableEndpoint endpoint = muleContext.getRegistry().lookupEndpointFactory().getInboundEndpoint(
    			getTestEndpointURI());
        Assert.assertNotNull(endpoint);
        Assert.assertNotNull(endpoint.getConnector());
        Assert.assertEquals("restlet", endpoint.getEndpointURI().getSchemeMetaInfo());
        Assert.assertEquals(getTestEndpointURI(), endpoint.getEndpointURI().getSchemeMetaInfo() + ":"
            + endpoint.getEndpointURI().getAddress());
        Assert.assertTrue("Endpoint is of class "+endpoint.getConnector().getClass().getName()+" instead",endpoint.getConnector() instanceof RestletConnector); 
        //FIXME somehow the restlet connector seems not to be registered
    }

    
	@Override
	public Connector createConnector() throws Exception {
		RestletConnector c = new RestletConnector(muleContext);
        c.setName("RestletConnector");
        return c;
	}

	@Override
	public Object getValidMessage() throws Exception {
		return "Hello".getBytes();
	}

	@Override
	public String getTestEndpointURI() {
		 return "restlet:http://localhost:5678/endpoint";
	}
    
}
