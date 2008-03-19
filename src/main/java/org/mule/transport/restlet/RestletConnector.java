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

import com.noelios.restlet.http.HttpConstants;
import org.mule.api.MuleException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.transport.AbstractConnector;
import org.restlet.Application;

/**
 * <code>RestletConnector</code> TODO document
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
public class RestletConnector extends AbstractConnector
{
    public static final String RESTLET = "restlet";

    public static final String HTTP_PREFIX = "http.";

    /**
     * MuleEvent property to pass back the status for the response
     */
    public static final String HTTP_METHOD_PROPERTY = HTTP_PREFIX + "method";

    public static final String HTTP_REQUEST_PROPERTY = HTTP_PREFIX + "request";

    public static final String HTTP_STATUS_PROPERTY = HTTP_PREFIX + "status";

    public static final String[] RESTLET_IGNORE_KEYS = {Application.KEY, HttpConstants.ATTRIBUTE_HEADERS,
        HttpConstants.ATTRIBUTE_VERSION};

    public RestletConnector()
    {
        super();
    }
    
    public boolean supportsProtocol(String protocol)
    {
        // we can listen on any protocol. provided that the necessary 
        // http headers are there.
        return true;
    }

    @Override
    public void doInitialise() throws InitialisationException
    {
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
