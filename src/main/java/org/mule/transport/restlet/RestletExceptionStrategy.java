/*
 * $Id: QuietExceptionStrategy.java 10489 2008-01-23 17:53:38Z dfeist $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.restlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.DefaultExceptionStrategy;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;

/**
 * Restrict exceptions to debug log messages
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
public class RestletExceptionStrategy extends DefaultExceptionStrategy
{

    protected transient Log logger = LogFactory.getLog(getClass());

    @Override
    public void handleRoutingException(final MuleMessage message, final ImmutableEndpoint endpoint, final Throwable e)
    {
        message.setPayload(null);
        logger.debug("Ignoring", e);
    }
}
