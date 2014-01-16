package ro.infoiasi.sedic.model.entity;


import java.util.ArrayList;

import org.apache.jena.atlas.json.JsonObject;

public class DrugEntity {
    private String drugName;
    private long drugId;
    private String drugURI;
    private ArrayList<String>parents;

    public DrugEntity() {
   
    }

    public DrugEntity(String drugName, long drugId, String drugURI, ArrayList<String>parents) {
        super();
        this.drugName = drugName;
        this.drugId = drugId;
        this.drugURI = drugURI;
        this.parents = parents;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("drugEntity [drugName=");
        builder.append(drugName);
        builder.append(", drugId=");
        builder.append(drugId);
        builder.append(", drugResource=");
        builder.append(drugURI);
        builder.append("]");
        return builder.toString();
    }
    
    public JsonObject toJSONString() {
        JsonObject outputObject = new JsonObject();
        outputObject.put("drug_name", drugName);
        outputObject.put("drug_id", drugId);
        outputObject.put("drug_uri", drugURI);
        outputObject.put("drug_parents", "parents");
        return outputObject;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public long getDrugId() {
        return drugId;
    }

    public void setDrugId(long drugId) {
        this.drugId = drugId;
    }

    public String getDrugURI() {
        return drugURI;
    }

    public void setDrugURI(String drugURI) {
        this.drugURI = drugURI;
    }
    public ArrayList<String> getParents()
    {
    	return parents;
    }
    
public void setParents(ArrayList<String>parents)
{
	this.parents = parents;
}
}
