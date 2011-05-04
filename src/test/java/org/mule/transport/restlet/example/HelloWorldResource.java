package org.mule.transport.restlet.example;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;



public class HelloWorldResource extends ServerResource {
    
    public HelloWorldResource(Context context, Request request, Response response) {
       
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
    }
    
    @Get
    public Representation represent(Variant variant) {
        return new StringRepresentation("Hello World", MediaType.TEXT_PLAIN);
    }
}
