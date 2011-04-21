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

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.transport.http.HttpConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.noelios.restlet.http.HttpConstants;

/**
 * <code>RestletConnector</code> TODO document
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
public class RestletConnector extends HttpConnector {
	public static final String RESTLET = "restlet";

	public static final String HTTP_PREFIX = "http";

	/**
	 * MuleEvent property to pass back the status for the response
	 */
	public static final String HTTP_METHOD_PROPERTY = HTTP_PREFIX + "method";

	public static final String HTTP_REQUEST_PROPERTY = HTTP_PREFIX + "request";

	public static final String HTTP_STATUS_PROPERTY = HTTP_PREFIX + "status";

	public static final String[] RESTLET_IGNORE_KEYS = {
			HttpConstants.ATTRIBUTE_HEADERS, HttpConstants.ATTRIBUTE_VERSION };
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	public RestletConnector(MuleContext muleContext) {
		super(muleContext);
	}

	public boolean supportsProtocol(String protocol) {
		// we can listen on any protocol. provided that the necessary
		// http headers are there.
		return protocol.startsWith("restlet");
	}
	
	@Override
	protected void doInitialise() throws InitialisationException {
		// TODO Auto-generated method stub
		super.doInitialise();
	}

   
    @Override
    public void doConnect() throws Exception
    {
    }

    @Override
    public void doDisconnect() throws Exception
    {
    }

    @Override
    public void doStart() throws MuleException
    {
    }

    @Override
    public void doStop() throws MuleException
    {
    }

    @Override
    public void doDispose()
    {
    }

    public String getProtocol()
    {
        return RESTLET;
    }

}
