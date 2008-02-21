
package org.mule.transport.restlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.mule.DefaultMuleMessage;
import org.mule.RequestContext;
import org.mule.api.MessagingException;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.EndpointException;
import org.mule.api.transport.Connector;
import org.mule.api.transport.MessageReceiver;
import org.mule.api.transport.OutputHandler;
import org.mule.endpoint.DynamicEndpointURIEndpoint;
import org.mule.endpoint.MuleEndpointURI;
import org.mule.transport.http.HttpConnector;
import org.mule.util.NumberUtils;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.InputRepresentation;
import org.restlet.resource.OutputRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

/**
 * The MuleResource is where the work occurs. It behaves very similarly to the MuleReceiverServlet. Since it is
 * instantiated at the time of the Request, we can do safely do all of the processing.
 * 
 * @author <a href="mailto:keithnaas@biglots.com">keithnaas@biglots.com</a>
 */
public class RestletReceiverResource extends Resource
{
    private static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
        .getLog(RestletReceiverResource.class);

    private MuleMessage responseMessage;

    private final MessageReceiver receiver;

    private boolean allowDelete;

    private boolean allowPost;

    private boolean allowPut;

    private boolean allowGet;

    /**
     * The constructor does the actual routing. Within restlet, this would normally be done in the handle* methods, but
     * I can't figure out an easy way to do a handleGet, so we'll just do the easy way for now. If the method is
     * allowed, route it to mule and handle the response. If it isn't allowed, continue.
     * 
     * @param receiver
     * @param context
     * @param request
     * @param response
     * @throws MuleException
     */
    public RestletReceiverResource(final MessageReceiver receiver, final Context context, final Request request,
    final Response response) throws MuleException
    {
        super(context, request, response);

        this.receiver = receiver;

        initAllowedMethods();

        final Method method = getRequest().getMethod();
        if (method == null)
        {
            handleBadRequest();
        }
        else if (isAllowMethod(method))
        {
            initVariants();
            routeMessage();
            handleResponseMessage();
        }
        else
        {
            handleNotAllowedMethod();
        }
    }

    /**
     * Handle a bad client request.
     * 
     * @param response
     */
    private void handleBadRequest()
    {
        getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "No method specified");
    }

    /**
     * Handles an invalid HTTP method.
     */
    private void handleNotAllowedMethod()
    {
        getResponse().setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    }

    /**
     * Handles the response based on the response message from the Mule container. If there is an exception or an issue
     * routing it, then just keep going.
     */
    private void handleResponseMessage()
    {
        Status status = Status.SUCCESS_OK;
        if (getResponseMessage() != null && getResponseMessage().getExceptionPayload() != null)
        {
            if (logger.isWarnEnabled())
            {
                logger.warn("Error processing message", responseMessage.getExceptionPayload().getException());
            }
            status = Status.SERVER_ERROR_INTERNAL;
        }
        else if (getResponseMessage() != null)
        {
            status = Status.valueOf(NumberUtils.toInt(ObjectUtils.defaultIfNull(getResponseMessage().getProperty(
                RestletConnector.HTTP_STATUS_PROPERTY), Status.SUCCESS_OK.getCode())));
        }
        getResponse().setStatus(status);
    }

    /**
     * Indicates if a method is allowed on a this resource. TODO: add other http methods
     * 
     * @param method The method to test.
     * @param target The target resource.
     * @return True if a method is allowed on a target resource.
     */
    private boolean isAllowMethod(final Method method)
    {
        boolean result = false;

        if (method != null)
        {
            if (method.equals(Method.GET) || method.equals(Method.HEAD))
            {
                result = allowGet();
            }
            else if (method.equals(Method.POST))
            {
                result = allowPost();
            }
            else if (method.equals(Method.PUT))
            {
                result = allowPut();
            }
            else if (method.equals(Method.DELETE))
            {
                result = allowDelete();
            }
            else if (method.equals(Method.OPTIONS))
            {
                result = true;
            }
        }

        return result;
    }

    /**
     * Routes the message to the Mule container.
     * 
     * @throws EndpointException
     * @throws MessagingException
     * @throws MuleException
     */
    private void routeMessage() throws EndpointException, MessagingException, MuleException
    {
        getReceiver().getService().getOutboundRouter().setCatchAllStrategy(new NoRouterCatchAllStrategy(getResponse()));

        getReceiver().setEndpoint(
            new DynamicEndpointURIEndpoint(getReceiver().getEndpoint(), new MuleEndpointURI(getRequestUrl(getReceiver()
                .getConnector()))));

        final MuleMessage requestMessage = createRequestMessage();
        setResponseMessage(getReceiver().routeMessage(requestMessage, getReceiver().getEndpoint().isSynchronous()));
    }

    /**
     * Check the receiver for the allowed methods. This is the only way for the endpoint to prevent HTTP methods from
     * being allowed.
     */
    private void initAllowedMethods()
    {
        allowGet = BooleanUtils.toBoolean(ObjectUtils.toString(receiver.getEndpoint().getProperty("allowGet"), "true"));
        allowDelete = BooleanUtils.toBoolean(ObjectUtils.toString(receiver.getEndpoint().getProperty("allowDelete"),
            "false"));
        allowPost = BooleanUtils.toBoolean(ObjectUtils.toString(receiver.getEndpoint().getProperty("allowPost"),
            "false"));
        allowPut = BooleanUtils
            .toBoolean(ObjectUtils.toString(receiver.getEndpoint().getProperty("allowPut"), "false"));
    }

    /**
     * 
     */
    private void initVariants()
    {
        // Here we add the representation variants exposed
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
        getVariants().add(new Variant(MediaType.TEXT_XML));
        getVariants().add(new Variant(MediaType.TEXT_HTML));
        getVariants().add(new Variant(MediaType.APPLICATION_XHTML_XML));
        // getVariants().add(new Variant(MediaType.APPLICATION_JSON));
    }

    @Override
    public void delete()
    {
        if (getResponseMessage() != null)
        {
            getResponse().setStatus(Status.SUCCESS_OK);
        }
        else
        {
            getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
        }
    }

    @Override
    public void post(final Representation entity)
    {
        if (getResponseMessage() != null)
        {
            getResponse().setStatus(Status.SUCCESS_OK);
        }
        else
        {
            getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
        }
    }

    @Override
    public void put(final Representation entity)
    {
        getResponse().setStatus(Status.SUCCESS_CREATED);
    }

    private MuleMessage createRequestMessage() throws MessagingException
    {
        final MuleMessage requestMessage = new DefaultMuleMessage(receiver.getConnector().getMessageAdapter(
            getRequest()));
        requestMessage.setProperty(HttpConnector.HTTP_METHOD_PROPERTY, getRequest().getMethod().getName());
        requestMessage.setProperty(HttpConnector.HTTP_REQUEST_PROPERTY, getRequest().getResourceRef().getIdentifier());
        return requestMessage;
    }

    @Override
    public Representation getRepresentation(final Variant variant)
    {
        Representation retVal = null;
        final MediaType mediaType = variant.getMediaType();

        if (getResponseMessage() != null)
        {
            final Object payload = getResponseMessage().getPayload();
            if (payload instanceof InputStream)
            {
                retVal = new InputRepresentation((InputStream) payload, mediaType);
            }
            else if (payload instanceof String)
            {
                retVal = new StringRepresentation((String) payload, mediaType);
            }
            else if (payload instanceof byte[])
            {
                retVal = new InputRepresentation(new ByteArrayInputStream((byte[]) payload), mediaType);
            }
            else if (payload instanceof OutputHandler)
            {
                retVal = new OutputRepresentation(mediaType)
                {
                    @Override
                    public void write(final OutputStream outputStream) throws IOException
                    {
                        ((OutputHandler) payload).write(RequestContext.getEvent(), outputStream);
                    }
                };
            }
            else
            {
                // ??
            }
        }
        else
        {
            retVal = super.getRepresentation(variant);
        }

        return retVal;
    }

    /**
     * Returns the url for this request. TODO: Its possible there is a method in Restlet to do this too!
     * 
     * @param connector
     * @return
     */
    protected String getRequestUrl(final Connector connector)
    {
        final StringBuilder url = new StringBuilder();
        url.append(connector.getProtocol().toLowerCase());
        url.append(":");
        url.append(getRequest().getHostRef().getHostIdentifier());
        url.append("/");
        url.append(getReceiverName(getRequest()));
        if (getRequest().getHostRef().getQuery() != null)
        {
            url.append("?");
            url.append(getRequest().getHostRef().getQuery());
        }
        if (getRequest().getHostRef().getFragment() != null)
        {
            url.append("#");
            url.append(getRequest().getHostRef().getFragment());
        }
        return url.toString();
    }

    /**
     * Returns the receiver name for this request.
     * 
     * @param request
     * @return
     */
    protected String getReceiverName(final Request request)
    {
        String name = request.getResourceRef().getPath();
        if (name == null)
        {
            name = request.getRootRef().getPath();
        }

        if (name.startsWith("/"))
        {
            name = name.substring(1);
        }
        return name;
    }

    @Override
    public boolean allowDelete()
    {
        return allowDelete;
    }

    @Override
    public boolean allowGet()
    {
        return allowGet;
    }

    @Override
    public boolean allowPost()
    {
        return allowPost;
    }

    @Override
    public boolean allowPut()
    {
        return allowPut;
    }

    public MessageReceiver getReceiver()
    {
        return receiver;
    }

    public MuleMessage getResponseMessage()
    {
        return responseMessage;
    }

    protected void setResponseMessage(final MuleMessage responseMessage)
    {
        this.responseMessage = responseMessage;
    }
}