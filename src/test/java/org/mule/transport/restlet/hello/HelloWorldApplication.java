package org.mule.transport.restlet.hello;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.Router;
import org.restlet.data.Request;
import org.restlet.data.Response;

public class HelloWorldApplication extends Application {

    public HelloWorldApplication() {
    }

    @Override
    public Restlet createRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attachDefault(HelloWorldResource.class);

        return router;

    }

    @Override
    public void handle(Request request, Response response) {
        super.handle(request, response);
    }

}
