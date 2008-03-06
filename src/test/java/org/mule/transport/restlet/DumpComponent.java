package org.mule.transport.restlet;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class DumpComponent implements Callable {

    public Object onCall(MuleEventContext eventContext) throws Exception {
        System.out.println("Got message!");
        System.out.println(eventContext.getMessage());
        return null;
    }

}
