package org.mule.transport.restlet;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;
import org.mule.transport.http.HttpConnector;
import org.restlet.util.Template;

public class UriTemplateFilter implements Filter {
    private static final Logger logger = Logger.getLogger(UriTemplateFilter.class.getName());
    
    private Set<String> verbs;
    private Template template;
    
    public UriTemplateFilter() {
        super();
    }

    public void setPattern(String pattern) {
        template = new Template(logger, pattern);
    }
    
    public void setVerbs(String verbString) {
        if (verbString.equals("*")) return;
        
        String[] split = verbString.split(" ");
        verbs = new HashSet<String>();
        for (String s : split) {
            verbs.add(s.toUpperCase());
        }
    }
    
    public void setVerbs(Set<String> verbs) {
        this.verbs = verbs;
    }

    public boolean accept(MuleMessage message) {
        String method = message.getStringProperty(HttpConnector.HTTP_METHOD_PROPERTY, "");
        if (verbs != null && !verbs.contains(method)) {
            return false;
        }
        
        String path = message.getStringProperty(HttpConnector.HTTP_REQUEST_PROPERTY, "");
        
        return template.match(path) > 0;
    }

}
