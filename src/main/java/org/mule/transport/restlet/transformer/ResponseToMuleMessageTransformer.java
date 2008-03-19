package org.mule.transport.restlet.transformer;

import java.io.IOException;
import java.io.OutputStream;

import org.mortbay.http.handler.SetResponseHeadersHandler;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transport.OutputHandler;
import org.restlet.data.Response;

public class ResponseToMuleMessageTransformer extends AbstractResponseTransformer {

    public ResponseToMuleMessageTransformer() {
    }

    @Override
    protected Object getPayload(final Response response, String encoding) {
        return new OutputHandler() {
            public void write(MuleEvent event, OutputStream out) throws IOException {
                response.getEntity().write(out);
            }
        };
    }
}
