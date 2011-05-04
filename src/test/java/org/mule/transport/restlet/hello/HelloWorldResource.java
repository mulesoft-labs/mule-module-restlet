package org.mule.transport.restlet.hello;


import org.mule.transport.restlet.RestletHttpConstants;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;


public class HelloWorldResource extends ServerResource {
    public static final String X_CUSTOM_HEADER = "X-Custom-Header";
    public static final String X_STATUS_HEADER = "X-Status-Header";
    
    private String name;
    private String headerValue;
    private int status = 200;
    
    public HelloWorldResource() {
        super();
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
    }
    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        Series<Parameter> params = (Series<Parameter>) getRequest().getAttributes().get(RestletHttpConstants.ATTRIBUTE_HEADERS);
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
         
        name = getRequest().getResourceRef().getQueryAsForm().getFirstValue("name");
    }

    @Override
    public Response getResponse() {
        Response response = super.getResponse();
        
        Series<Parameter> series = new Form();
        series.set(X_CUSTOM_HEADER, headerValue, false);
        response.getAttributes().put(RestletHttpConstants.ATTRIBUTE_HEADERS, series);
        response.setStatus(new Status(status));
        
        return response;
    }



    /**
     * Returns a full representation for a given variant.
     */
    @Get
    public Representation get(Variant variant) {
        if (name == null) {
            return new StringRepresentation("hello, world", MediaType.TEXT_PLAIN);
        } else if ("Mr. XML".equals(name)) {
            return new StringRepresentation("<hello>" + name + "</hello>", 
                                            MediaType.APPLICATION_XML);
        } else {
            return new StringRepresentation("hello " + name, MediaType.TEXT_PLAIN);
        }
    }

 

}
