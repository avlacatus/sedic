package ro.infoiasi.sedic.android.model;

import org.json.JSONException;
import org.json.JSONObject;


public class DiseaseBean {
	private String diseaseName;
	private long diseaseId;
	private String diseaseURI;

	public DiseaseBean() {
	}

	public DiseaseBean(String diseaseName, long diseaseId, String diseaseURI) {
		super();
		this.diseaseName = diseaseName;
		this.diseaseId = diseaseId;
		this.diseaseURI = diseaseURI;
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

	public JSONObject toJSONString() throws JSONException {
		JSONObject outputObject = new JSONObject();
		outputObject.put("disease_name", diseaseName);
		outputObject.put("disease_id", diseaseId);
		// outputObject.put("disease_uri", diseaseURI);
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

}
