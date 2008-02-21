
package org.mule.transport.restlet;

import org.mule.api.transport.MessageReceiver;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.Router;

public class ReceiverRestletApplication extends RestletApplication
{
    private final MessageReceiver receiver;

    public ReceiverRestletApplication(final Context context, final MessageReceiver receiver)
    {
        super(context);
        this.receiver = receiver;
    }

    @Override
    public Restlet createRoot()
    {
        final Router retVal = new Router(getContext());
        attach(retVal, this.receiver);

        return retVal;
    }
}
