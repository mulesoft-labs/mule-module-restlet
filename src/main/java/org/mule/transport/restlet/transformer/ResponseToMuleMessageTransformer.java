package org.mule.transport.restlet.transformer;

import java.io.IOException;
import java.io.OutputStream;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.OutputHandler;
import org.mule.transformer.AbstractMessageAwareTransformer;
import org.restlet.data.Response;
import org.restlet.resource.Representation;

public class ResponseToMuleMessageTransformer extends AbstractMessageAwareTransformer
    implements DiscoverableTransformer {


    private int priorityWeighting;
    public ResponseToMuleMessageTransformer() {
        setReturnClass(OutputHandler.class);
        registerSourceType(Response.class);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Object transform(MuleMessage msg, String encoding) throws TransformerException {
        Response response = (Response) msg.getPayload();
        
        return getPayload(response, encoding);
    }

    protected Object getPayload(final Response response, String encoding) {
        return new OutputHandler() {
            public void write(MuleEvent event, OutputStream out) throws IOException {
                Representation entity = response.getEntity();
                if (entity != null) {
                    entity.write(out);
                }
            }
        };
    }

    public int getPriorityWeighting() 
    {
        return priorityWeighting;
    }

    public void setPriorityWeighting(int priorityWeighting) 
    {
        this.priorityWeighting = priorityWeighting;
    }
}
