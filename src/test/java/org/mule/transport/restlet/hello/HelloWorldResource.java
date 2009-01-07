package org.mule.transport.restlet.hello;

import com.noelios.restlet.http.HttpConstants;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;
import org.restlet.util.Series;

public class HelloWorldResource extends Resource {
    public static final String X_CUSTOM_HEADER = "X-Custom-Header";
    public static final String X_STATUS_HEADER = "X-Status-Header";
    
    private String name;
    private String headerValue;
    private int status = 200;
    
    @SuppressWarnings("unchecked")
    public HelloWorldResource(Context context, Request request, Response response) {
        super(context, request, response);
        
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
        
        Series<Parameter> params = (Series<Parameter>) request.getAttributes().get(HttpConstants.ATTRIBUTE_HEADERS);
        if (params != null)
        {
            Parameter first = params.getFirst(X_CUSTOM_HEADER);
            if (first != null)
            {
                headerValue = first.getValue();
            }
            
            first = params.getFirst(X_STATUS_HEADER);
            if (first != null)
            {
                status = Integer.valueOf(first.getValue());
            }
        }
         
        name = request.getResourceRef().getQueryAsForm().getFirstValue("name");
    }


    @Override
    public Response getResponse() {
        Response response = super.getResponse();
        
        Series<Parameter> series = new Form();
        series.set(X_CUSTOM_HEADER, headerValue, false);
        response.getAttributes().put(HttpConstants.ATTRIBUTE_HEADERS, series);
        response.setStatus(new Status(status));
        
        return response;
    }

    /**
     * Returns a full representation for a given variant.
     */
    @Override
    public Representation getRepresentation(Variant variant) {
        if (name == null) {
            return new StringRepresentation("hello, world", MediaType.TEXT_PLAIN);
        } else if ("Mr. XML".equals(name)) {
            return new StringRepresentation("<hello>" + name + "</hello>", 
                                            MediaType.APPLICATION_XML);
        } else {
            return new StringRepresentation("hello " + name, MediaType.TEXT_PLAIN);
        }
    }

    @Override
    public boolean allowPost() {
        return true;
    }

}
