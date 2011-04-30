/*
 * $Id$
 * -----------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc. All rights reserved. http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.restlet.transformer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.mule.RequestContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.OutputHandler;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.transformer.types.SimpleDataType;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.mule.transport.http.i18n.HttpMessages;
import org.mule.transport.restlet.RestletHttpConstants;
import org.mule.transport.restlet.i18n.RestletMessages;
import org.restlet.Context;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.engine.http.HttpRequest;
import org.restlet.engine.http.HttpResponse;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
/**
 * <code>ObjectToRequestTransformer</code> TODO document
 * @author <a href="mailto:alberto.aresca@gmail.co">Alberto Aresca</a>
 */
public class ObjectToRequestTransformer extends AbstractMessageTransformer implements DiscoverableTransformer {
	public static final String MULE_MESSAGE = "mule.message";
	private int priorityWeighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING + 1;
	private Context context = new Context();

	public ObjectToRequestTransformer() {
		super();
		registerSourceType(new SimpleDataType<Object>(String.class));
		registerSourceType(new SimpleDataType<Object>(byte[].class));
		registerSourceType(new SimpleDataType<Object>(InputStream.class));
		registerSourceType(new SimpleDataType<Object>(OutputHandler.class));
		setReturnDataType(new SimpleDataType<Object>(Response.class));
	}

	protected MediaType getMediaType(final MuleMessage msg, final String encoding) {
		String mimeType = (String) msg.getProperty(HttpConstants.HEADER_CONTENT_TYPE);

		if (mimeType == null) {
			mimeType = HttpConstants.DEFAULT_CONTENT_TYPE;
		}

		if (encoding != null && !"UTF-8".equals(encoding.toUpperCase()) && mimeType.indexOf("charset") == -1) {
			mimeType += "; charset=" + encoding;
		}
		return new MediaType(mimeType);
	}

	public int getPriorityWeighting() {
		return priorityWeighting;
	}

	public void setPriorityWeighting(final int priorityWeighting) {
		this.priorityWeighting = priorityWeighting;
	}

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String hostHeader = message.getStringProperty(HttpConstants.HEADER_HOST, "localhost:80");
		int idx = hostHeader.indexOf(':');
		String host;
		int port;
		if (idx != -1) {
			host = hostHeader.substring(0, idx);
			port = Integer.valueOf(hostHeader.substring(idx + 1));
		} else {
			host = hostHeader;
			port = 80;
		}

		HttpServerCall call = new HttpServerCall(message, host, port);
		final HttpRequest r = new HttpRequest(context, call);

		r.getAttributes().put(RestletHttpConstants.ATTRIBUTE_HEADERS, call.getRequestHeaders());
		r.getAttributes().put(MULE_MESSAGE, message);

		if (r.getMethod() == null) {
			r.setMethod(Method.POST);
		}

		final Object payload = message.getPayload();

		String path = message.getStringProperty(HttpConnector.HTTP_REQUEST_PROPERTY, null);
		if (path == null) {
			MuleEvent event = RequestContext.getEvent();
			if (event != null) {
				path = event.getEndpoint().getEndpointURI().toString();
			}
		}

		if (path == null) {
			throw new TransformerException(HttpMessages.eventPropertyNotSetCannotProcessRequest(MuleProperties.MULE_ENDPOINT_PROPERTY), this);
		}
		r.setResourceRef(path);

		final MediaType mediaType = getMediaType(message, encoding);

		Representation rep = null;
		if (payload instanceof InputStream) {
			rep = new InputRepresentation((InputStream) payload, mediaType);
		} else if (payload instanceof String) {
			rep = new StringRepresentation((String) payload, mediaType);
		} else if (payload instanceof byte[]) {
			rep = new InputRepresentation(new ByteArrayInputStream((byte[]) payload), mediaType);
		} else if (payload instanceof OutputHandler) {
			throw new TransformerException(RestletMessages.unsupportedTransformation(payload.getClass()), this);
		} else {
			throw new TransformerException(RestletMessages.unsupportedTransformation(payload.getClass()), this);
		}

		r.setEntity(rep);
		
		HttpResponse resp = new HttpResponse(call, r);
		try {
			resp.setEntity(message.getPayloadAsString(), getMediaType(message, outputEncoding));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

}
