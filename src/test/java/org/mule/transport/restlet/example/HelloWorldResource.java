package org.mule.transport.restlet.example;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

public class HelloWorldResource extends Resource {
    
    public HelloWorldResource(Context context, Request request, Response response) {
        super(context, request, response);
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
    }
    
    @Override
    public Representation getRepresentation(Variant variant) {
        return new StringRepresentation("Hello World", MediaType.TEXT_PLAIN);
    }
}
