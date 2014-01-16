package ro.infoiasi.sedic.model.entity;


import org.apache.jena.atlas.json.JsonObject;

public class RemedyEntity {
    private String remedyName;
    private long remedyId;
    private String remedyURI;
    private String adjuvantUsage = "";
    private String frequentUsage = "";
    private String reportedUsage = "";
    private String therapeuticalUsage = "";

    public RemedyEntity() {
    }

    public RemedyEntity(String remedyName, long remedyId, String remedyURI, String adjuvant, String frequent, String reported, String therapeutical) {
        super();
        this.remedyName = remedyName;
        this.remedyId = remedyId;
        this.remedyURI = remedyURI;
        this.adjuvantUsage = adjuvant;
        this.frequentUsage = frequent;
        this.reportedUsage = reported;
        this.therapeuticalUsage = therapeutical;
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
        outputObject.put("adjuvant_usage", adjuvantUsage);
        outputObject.put("therapeutical_usage", therapeuticalUsage);
        outputObject.put("frequent_usage", frequentUsage);
        outputObject.put("reported_usage", reportedUsage);
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
    public String getAdjuvantUsage() {
        return adjuvantUsage;
    }

    public void setAdjuvantUsage(String adjuvantUsage) {
        this.adjuvantUsage += adjuvantUsage + "; ";
    }
    public String getTherapeuticalUsage() {
        return therapeuticalUsage;
    }

    public void setTherapeuticalUsage(String tUsage) {
        this.therapeuticalUsage += tUsage + "; ";
    }
    public String getFrequentUsage() {
        return frequentUsage;
    }

    public void setFrequentUsage(String fUsage) {
        this.frequentUsage += fUsage + "; ";
    }
    public String getReportedUsage() {
        return reportedUsage;
    }

    public void setReportedUsage(String reportedUsage) {
        this.reportedUsage += reportedUsage + "; ";
    }

}
