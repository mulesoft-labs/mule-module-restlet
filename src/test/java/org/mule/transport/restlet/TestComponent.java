package org.mule.transport.restlet;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class TestComponent implements Callable {

    public Object onCall(MuleEventContext eventContext) throws Exception {
        return new DefaultMuleMessage(eventContext.getMessageAsString() + " received", 
                                      eventContext.getMessage());
    }

}
