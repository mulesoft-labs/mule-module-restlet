package org.mule.transport.restlet.order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Resource;


public class OrderResource extends Resource {
    
    private Variant jsonVariant;

    public OrderResource() {
       
        
        jsonVariant = new Variant(MediaType.APPLICATION_JSON);
        getVariants().add(jsonVariant);
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
    }
    

    public Variant getPreferredVariant() {
        return jsonVariant;
    }
    
    public Representation represent(Variant variant) {
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
            return super.represent(variant);
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
    public void acceptRepresentation(Representation entity) {
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

   
}
