
package org.mule.transport.restlet.servlet;

import javax.servlet.ServletException;

import org.restlet.service.LogService;

import com.noelios.restlet.ext.servlet.ServerServlet;

/**
 * This is necessary in order to control behavior of the Restlet Component.
 * 
 * @author knaas
 */
public class RestletServlet extends ServerServlet
{
    private static final long serialVersionUID = 7533336179946446676L;

    /**
     * Disable the Restlet LogService.
     */
    @Override
    public void init() throws ServletException
    {
        super.init();
        this.getComponent().setLogService(new LogService(false));
    }

}
