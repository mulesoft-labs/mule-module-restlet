package org.mule.transport.restlet.hello;

import org.restlet.Context;
import org.restlet.Guard;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Request;

public class SimpleGuard extends Guard {

    public SimpleGuard(Context context, ChallengeScheme scheme, String realm) throws IllegalArgumentException {
        super(context, scheme, realm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int authenticate(Request request) {
        ChallengeResponse cr = request.getChallengeResponse();

//        if (cr == null) {
//            return Guard.AUTHENTICATION_INVALID;
//        }

        return Guard.AUTHENTICATION_VALID;
    }

}
