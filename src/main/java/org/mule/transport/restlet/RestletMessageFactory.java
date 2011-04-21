/**
 * 
 */
package org.mule.transport.restlet;

import org.mule.api.MuleContext;
import org.mule.transport.http.HttpMuleMessageFactory;

/**
 * @author alberto
 *
 */
public class RestletMessageFactory extends HttpMuleMessageFactory {

	public RestletMessageFactory(MuleContext context) {
		super(context);
	}


}
