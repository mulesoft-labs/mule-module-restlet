
package org.mule.transport.restlet;

import java.util.Map;

import org.mule.RegistryContext;
import org.mule.api.transport.MessageReceiver;
import org.mule.transport.restlet.i18n.RestletMessages;
import org.mule.transport.service.TransportFactory;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.Router;
import org.restlet.data.Parameter;

/**
 * The RestletApplication behaves very similarly to how a Servlet would. It registers all necessary Mule endpoints with
 * the Restlet container.
 * 
 * @author knaas
 */
public class ServletRestletApplication extends RestletApplication
{
    private static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
        .getLog(RestletApplication.class);

    private static final String RESTLET_CONNECTOR_NAME_PROPERTY = "org.mule.restlet.connector.name";

    private RestletConnector connector;

    public ServletRestletApplication(final Context context)
    {
        super(context);

        final Parameter connectorName = context.getParameters().getFirst(RESTLET_CONNECTOR_NAME_PROPERTY);

        if (connectorName == null || connectorName.getValue() == null)
        {
            connector = (RestletConnector) TransportFactory.getConnectorByProtocol(RestletConnector.RESTLET);
            if (connector == null)
            {
                throw new IllegalStateException(RestletMessages.noConnectorForProtocolRestlet().toString());
            }
        }
        else
        {
            connector = (RestletConnector) RegistryContext.getRegistry().lookupConnector(connectorName.getValue());
            if (connector == null)
            {
                throw new IllegalStateException(RestletMessages.noRestletConnectorFound(connectorName.getValue())
                    .toString());
            }
        }
    }

    @Override
    public Restlet createRoot()
    {
        final Router retVal = new Router(getContext());
        for (final Map.Entry<String, MessageReceiver> entry : getReceivers().entrySet())
        {
            attach(retVal, entry.getValue());
        }

        return retVal;
    }

    @SuppressWarnings("unchecked")
    protected Map<String, MessageReceiver> getReceivers()
    {
        return connector != null ? connector.getReceivers() : null;
    }
}
