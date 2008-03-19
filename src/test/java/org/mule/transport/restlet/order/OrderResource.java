package org.mule.transport.restlet.order;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

public class OrderResource extends Resource {
    
    private Variant jsonVariant;

    public OrderResource(Context context, Request request, Response response) {
        super(context, request, response);
        
        jsonVariant = new Variant(MediaType.APPLICATION_JSON);
        getVariants().add(jsonVariant);
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
    }
    
    @Override
    public Variant getPreferredVariant() {
        return jsonVariant;
    }

    @Override
    public Representation getRepresentation(Variant variant) {
        if (variant.getMediaType().equals(jsonVariant.getMediaType())) {
            try {
                JSONObject root = createJSONOrder();
                
                return new JsonRepresentation(root);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else if (variant.getMediaType().equals(MediaType.TEXT_XML)) {
            return new StringRepresentation("");
        } else {
            return super.getRepresentation(variant);
        }
    }

    private JSONObject createJSONOrder() throws JSONException {
        JSONObject root = new JSONObject();
        root.put("orderId", "1");
        root.put("customer", "Dan Diephouse");
        
        JSONArray lineItems = new JSONArray();
        lineItems.put("AIBO");
        
        root.put("lineItems", lineItems);
        return root;
    }

    @Override
    public void post(Representation entity) {
        try {
            if (entity.getMediaType().equals(MediaType.APPLICATION_JSON)) {
                JsonRepresentation jsonRep = (JsonRepresentation) entity;
                
                JSONObject obj = jsonRep.toJsonObject();
                
                System.out.println("Got " + obj);
            } else {
                
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean allowDelete() {
        return super.allowDelete();
    }

    @Override
    public boolean allowPost() {
        return true;
    }

    @Override
    public boolean allowPut() {
        return super.allowPut();
    }
}
