package org.mule.transport.restlet.hello;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;
import org.restlet.security.Guard;


public class HelloWorldApplication extends Application {

    public HelloWorldApplication() {
    }

    @Override
    public Restlet createRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());
        
        Guard guard = new SimpleGuard(getContext(), ChallengeScheme.HTTP_BASIC, "someRealm"); 
        guard.setNext(HelloWorldResource.class);
        
        // Defines only one route
        router.attachDefault(guard);

        return router;

    }

    @Override
    public void handle(Request request, Response response) {
        super.handle(request, response);
    }

}
