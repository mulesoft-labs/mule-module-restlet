package org.mule.transport.restlet.client;

import com.noelios.restlet.http.HttpClientHelper;
import org.mule.api.MuleException;
import org.mule.module.client.MuleClient;
import org.restlet.Client;
import org.restlet.data.Protocol;
import org.restlet.data.Request;

public class MuleClientHelper extends HttpClientHelper {
    private MuleClient muleClient;

    public MuleClientHelper() throws MuleException {
        this(null, new MuleClient());
    }
    

    public MuleClientHelper(MuleClient muleClient) {
        this(null, muleClient);
    }
    
    public MuleClientHelper(Client client) throws MuleException {
        this(client, new MuleClient());
    }
    
    public MuleClientHelper(Client client, MuleClient muleClient) {
        super(client);
        getProtocols().add(Protocol.HTTP);
        getProtocols().add(Protocol.HTTPS);
        
        this.muleClient = muleClient;
    }

    @Override
    public com.noelios.restlet.http.HttpClientCall create(Request request) {
        return new HttpClientCall(this, 
                                  request.getMethod().toString(), 
                                  request.getResourceRef().toString(), 
                                  muleClient);
    }

}
