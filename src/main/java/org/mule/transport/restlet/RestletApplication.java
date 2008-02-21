
package org.mule.transport.restlet;

import org.apache.commons.lang.ObjectUtils;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transport.MessageReceiver;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Router;

/**
 * The RestletApplication behaves very similarly to how a Servlet would. It registers all necessary Mule endpoints with
 * the Restlet container.
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
public abstract class RestletApplication extends Application
{
    private static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
        .getLog(RestletApplication.class);

    public RestletApplication(final Context context)
    {
        super(context);
    }

    /**
     * Attaches a receiver to a Restlet Router using a standard uri pattern.
     * 
     * @param retVal
     * @param receiver
     */
    protected void attach(final Router retVal, final MessageReceiver receiver)
    {
        final String uriPattern = getUriPattern(receiver);
        if (logger.isInfoEnabled())
        {
            logger.info(String.format("Attached %s at '%s':", receiver, uriPattern));
        }
        retVal.attach(uriPattern, new RestletReceiverFinder(receiver, getContext()));
    }

    /**
     * Returns the restlet address to use for attaching TODO: there has to be a utility for making the address absolute.
     * <p/> Because the URIBuilder blows up when creating an address in restlet form (no curly braces!), I am using a
     * property on the inbound endpoint for now.
     * 
     * @param messageReceiver
     * @return
     */
    protected String getUriPattern(final MessageReceiver messageReceiver)
    {
        final ImmutableEndpoint endpoint = messageReceiver.getEndpoint();
        String retVal = ObjectUtils.toString(endpoint.getProperty("uriPattern"), null);
        if (retVal == null)
        {
            retVal = endpoint.getEndpointURI().getAddress();
        }
        if (!retVal.startsWith("/"))
        {
            retVal = "/" + retVal;
        }
        return retVal;
    }
}
