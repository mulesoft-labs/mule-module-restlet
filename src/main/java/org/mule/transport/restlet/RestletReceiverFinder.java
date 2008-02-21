
package org.mule.transport.restlet;

import org.mule.api.MuleException;
import org.mule.api.transport.MessageReceiver;
import org.restlet.Context;
import org.restlet.Finder;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Resource;

/**
 * Because a Restlet Resource is instantiated at the time of the actual request time, we need a way to tie the original
 * UMOMessageReceiver to the restlet request. The MuleFinder does this since it does the actual instantation of the
 * MuleResource.
 * 
 * @author knaas
 */
public class RestletReceiverFinder extends Finder
{
    private static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
        .getLog(RestletReceiverFinder.class);

    private final MessageReceiver receiver;

    /**
     * Constructor.
     * 
     * @param context The context.
     */
    public RestletReceiverFinder(final MessageReceiver receiver, final Context context)
    {
        super(context);
        this.receiver = receiver;
    }

    @Override
    public Resource createResource(final Request request, final Response response)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug(String.format("Handling request %s", request.getResourceRef()));
        }
        Resource retVal = null;
        try
        {
            retVal = new RestletReceiverResource(receiver, getContext(), request, response);
        }
        catch (final MuleException e)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("Unable to handle request", e);
            }
            response.setStatus(Status.SERVER_ERROR_INTERNAL);
        }
        return retVal;
    }
}