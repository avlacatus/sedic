package ro.infoiasi.sedic.model.entity;

import java.util.ArrayList;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

public class DiseaseEntity {
	private String diseaseName;
	private long diseaseId;
	private String diseaseURI;
	private ArrayList<ParentEntity> parents;

	public DiseaseEntity() {
	}

	public DiseaseEntity(String diseaseName, long diseaseId, String diseaseURI,
			ArrayList<ParentEntity> parents) {
		super();
		this.diseaseName = diseaseName;
		this.diseaseId = diseaseId;
		this.diseaseURI = diseaseURI;
		this.parents = parents;
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
		JsonArray parentsArray = new JsonArray();
		for (ParentEntity p : parents) {
			parentsArray.add(p.toJSONString());
		}
		outputObject.put("disease_children", parentsArray);
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

	public ArrayList<ParentEntity> getParents() {
		return parents;
	}

	public void setParents(ArrayList<ParentEntity> parents) {
		this.parents = parents;
	}
}
