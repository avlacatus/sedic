package ro.infoiasi.sedic.model.entity;


import org.apache.jena.atlas.json.JsonObject;

public class RemedyEntity {
    private String remedyName;
    private long remedyId;
    private String remedyURI;

    public RemedyEntity() {
    }

    public RemedyEntity(String remedyName, long remedyId, String remedyURI) {
        super();
        this.remedyName = remedyName;
        this.remedyId = remedyId;
        this.remedyURI = remedyURI;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("remedyEntity [remedyName=");
        builder.append(remedyName);
        builder.append(", remedyId=");
        builder.append(remedyId);
        builder.append(", remedyResource=");
        builder.append(remedyURI);
        builder.append("]");
        return builder.toString();
    }
    
    public JsonObject toJSONString() {
        JsonObject outputObject = new JsonObject();
        outputObject.put("remedy_name", remedyName);
        outputObject.put("remedy_id", remedyId);
        outputObject.put("remedy_uri", remedyURI);
        return outputObject;
    }

    public String getRemedyName() {
        return remedyName;
    }

    public void setRemedyName(String remedyName) {
        this.remedyName = remedyName;
    }

    public long getRemedyId() {
        return remedyId;
    }

    public void setRemedyId(long remedyId) {
        this.remedyId = remedyId;
    }

    public String getRemedyURI() {
        return remedyURI;
    }

    public void setRemedyURI(String remedyURI) {
        this.remedyURI = remedyURI;
    }

}
