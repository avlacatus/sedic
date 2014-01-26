package ro.infoiasi.sedic.model.entity;

import java.util.ArrayList;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

public class DiseaseEntity {
	private String diseaseName;
	private long diseaseId;
	private String diseaseURI;
	private ArrayList<ChildEntity> children;

	public DiseaseEntity() {
	}

	public DiseaseEntity(String diseaseName, long diseaseId, String diseaseURI,
			ArrayList<ChildEntity> children) {
		super();
		this.diseaseName = diseaseName;
		this.diseaseId = diseaseId;
		this.diseaseURI = diseaseURI;
		this.children = children;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("diseaseEntity [diseaseName=");
		builder.append(diseaseName);
		builder.append(", diseaseId=");
		builder.append(diseaseId);
		builder.append(", diseaseResource=");
		builder.append(diseaseURI);
		builder.append("]");
		return builder.toString();
	}

	public JsonObject toJSONString() {
		JsonObject outputObject = new JsonObject();
		outputObject.put("disease_name", diseaseName);
		outputObject.put("disease_id", diseaseId);
		outputObject.put("disease_uri", diseaseURI);
		JsonArray childArray = new JsonArray();
		for (ChildEntity p : children) {
			childArray.add(p.toJSONString());
		}
		outputObject.put("disease_children", childArray);
		return outputObject;
	}
	public JsonObject toCompactJSONString() {
		JsonObject outputObject = new JsonObject();
		outputObject.put("disease_name", diseaseName);
		outputObject.put("disease_id", diseaseId);
		outputObject.put("disease_uri", diseaseURI);
		return outputObject;
	}
	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public long getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(long diseaseId) {
		this.diseaseId = diseaseId;
	}

	public String getDiseaseURI() {
		return diseaseURI;
	}

	public void setDiseaseURI(String diseaseURI) {
		this.diseaseURI = diseaseURI;
	}

	public ArrayList<ChildEntity> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ChildEntity> children) {
		this.children = children;
	}
}
