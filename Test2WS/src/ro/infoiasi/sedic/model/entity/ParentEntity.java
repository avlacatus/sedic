package ro.infoiasi.sedic.model.entity;

import org.apache.jena.atlas.json.JsonObject;

public class ParentEntity {
	 private long parentId;
	    private String parentURI;

	    public ParentEntity() {
	    }

	    public ParentEntity( long plantId, String plantURI) {
	        super();
	        this.parentId = plantId;
	        this.parentURI = plantURI;
	    }

	    @Override
	    public String toString() {
	        StringBuilder builder = new StringBuilder();
	        builder.append(", parentId=");
	        builder.append(parentId);
	        builder.append(", parentURI=");
	        builder.append(parentURI);
	        builder.append("]");
	        return builder.toString();
	    }
	    
	    public JsonObject toJSONString() {
	        JsonObject outputObject = new JsonObject();
	        outputObject.put("parent_id", parentId);
	        outputObject.put("parent_uri", parentURI);
	        return outputObject;
	    }

	  
	    public long getParentId() {
	        return parentId;
	    }

	    public void setParentId(long parentId) {
	        this.parentId = parentId;
	    }

	    public String getParentURI() {
	        return parentURI;
	    }

	    public void setParentURI(String parentURI) {
	        this.parentURI = parentURI;
	    }


}
