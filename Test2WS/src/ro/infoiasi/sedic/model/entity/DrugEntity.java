package ro.infoiasi.sedic.model.entity;

import java.util.ArrayList;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

public class DrugEntity {
	private String drugName;
	private long drugId;
	private String drugURI;
	private ArrayList<ChildEntity> parents;

	public DrugEntity() {

	}

	public DrugEntity(String drugName, long drugId, String drugURI,
			ArrayList<ChildEntity> parents) {
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
		JsonArray parentsArray = new JsonArray();
		for (ChildEntity p : parents) {
			parentsArray.add(p.toJSONString());
		}
		outputObject.put("drug_children", parentsArray);
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

	public ArrayList<ChildEntity> getParents() {
		return parents;
	}

	public void setParents(ArrayList<ChildEntity> parents) {
		this.parents = parents;
	}
}
