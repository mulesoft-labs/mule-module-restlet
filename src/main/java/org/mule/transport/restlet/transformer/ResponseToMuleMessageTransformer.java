package org.mule.transport.restlet.transformer;

import java.io.IOException;
import java.io.OutputStream;



import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.OutputHandler;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.types.SimpleDataType;
import org.restlet.data.Response;
import org.restlet.resource.Representation;

public class ResponseToMuleMessageTransformer extends AbstractMessageTransformer
    implements DiscoverableTransformer {


    private int priorityWeighting;
    public ResponseToMuleMessageTransformer() {
    	setReturnDataType(new SimpleDataType<Object>(OutputHandler.class));
        registerSourceType(new SimpleDataType<Object>(Response.class));
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


	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		Response response = (Response) message.getPayload();
        
        return getPayload(response, encoding);
	}
}
