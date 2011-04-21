package org.mule.transport.restlet.example;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class HelloWorldApplication extends Application {

    @Override
    public Restlet createRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attachDefault(HelloWorldResource.class);

        return router;
    }
}
