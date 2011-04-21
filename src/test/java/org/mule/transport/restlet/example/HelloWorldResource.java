package org.mule.transport.restlet.example;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;


import org.restlet.resource.Resource;



public class HelloWorldResource extends Resource {
    
    public HelloWorldResource(Context context, Request request, Response response) {
       
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
    }
    
    @Override
    public Representation represent(Variant variant) {
        return new StringRepresentation("Hello World", MediaType.TEXT_PLAIN);
    }
}
