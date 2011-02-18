package org.mule.transport.restlet.client;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.module.client.MuleClient;
import org.restlet.Client;
import org.restlet.data.Protocol;
import org.restlet.data.Request;

import com.noelios.restlet.Engine;
import com.noelios.restlet.http.HttpClientHelper;

public class MuleClientHelper extends HttpClientHelper {
    private MuleClient muleClient;

    public MuleClientHelper(MuleContext muleContext) throws MuleException {
        this(null, new MuleClient(muleContext));
    }
    
    public MuleClientHelper(MuleClient muleClient) {
        this(null, muleClient);
    }
    
    public MuleClientHelper(Client client) throws MuleException {
        this(client, ((MuleClientHelper) Engine.getInstance().getRegisteredClients().get(0)).muleClient);
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
