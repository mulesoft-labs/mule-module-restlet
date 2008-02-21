
package org.mule.transport.restlet;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.service.Service;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractMessageReceiver;
import org.restlet.data.Status;

public abstract class AbstractRestletMessageReceiver extends AbstractMessageReceiver
{

    public AbstractRestletMessageReceiver(final Connector connector, final Service service,
    final ImmutableEndpoint endpoint) throws CreateException
    {
        super(connector, service, endpoint);
    }

    @Override
    protected MuleMessage handleUnacceptedFilter(final MuleMessage message)
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("Message request '" + message.getProperty(RestletConnector.HTTP_REQUEST_PROPERTY)
                + "' is being rejected since it does not match the filter on this endpoint: " + endpoint);
        }
        message.setProperty(RestletConnector.HTTP_STATUS_PROPERTY, Status.CLIENT_ERROR_NOT_ACCEPTABLE.getCode());
        return message;
    }

    @Override
    protected void doConnect() throws Exception
    {

    }

    @Override
    protected void doDisconnect() throws Exception
    {

    }

    @Override
    protected void doDispose()
    {

    }

    @Override
    protected void doStart() throws MuleException
    {

    }

    @Override
    protected void doStop() throws MuleException
    {

    }

}