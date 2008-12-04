/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.restlet.config;

import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.generic.OrphanDefinitionParser;
import org.mule.config.spring.parsers.specific.ComponentDefinitionParser;
import org.mule.transport.restlet.RestletComponent;
import org.mule.transport.restlet.RestletConnector;
import org.mule.transport.restlet.UriTemplateFilter;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Registers a Bean Definition Parser for handling <code><restlet:connector></code> elements.
 *
 */
public class RestletNamespaceHandler extends NamespaceHandlerSupport
{
    public void init()
    {
        registerBeanDefinitionParser("uri-template-filter", new ChildDefinitionParser("filter", UriTemplateFilter.class));
        registerBeanDefinitionParser("connector", new OrphanDefinitionParser(RestletConnector.class, true));
        registerBeanDefinitionParser("component", new ComponentDefinitionParser(RestletComponent.class));
    }
}