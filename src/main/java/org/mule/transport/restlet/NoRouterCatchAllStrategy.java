
package org.mule.transport.restlet;

import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.routing.RouterCatchAllStrategy;
import org.mule.api.routing.RoutingException;
import org.restlet.data.Response;
import org.restlet.data.Status;

/**
 * If a request doesn't have an outbound router associated with it, send back a 404.
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
final class NoRouterCatchAllStrategy implements RouterCatchAllStrategy
{
    private final Response response;

    NoRouterCatchAllStrategy(final Response response)
    {
        this.response = response;
    }

    public MuleMessage catchMessage(final MuleMessage message, final MuleSession session, final boolean synchronous)
        throws RoutingException
    {
        response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        return message;
    }

    public ImmutableEndpoint getEndpoint()
    {
        return null;
    }

    public void setEndpoint(final ImmutableEndpoint endpoint)
    {

    }
}