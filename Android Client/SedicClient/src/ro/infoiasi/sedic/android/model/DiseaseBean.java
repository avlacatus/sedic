package ro.infoiasi.sedic.android.model;

import java.util.List;

public class DiseaseBean {
	private String diseaseName;
	private long diseaseId;
	private String diseaseURI;
	private List<String> diseaseParents;

	public DiseaseBean() {
	}

	public DiseaseBean(String diseaseName, long diseaseId, String diseaseURI, List<String> diseaseParents) {
		super();
		this.diseaseName = diseaseName;
		this.diseaseId = diseaseId;
		this.diseaseURI = diseaseURI;
		this.setDiseaseParents(diseaseParents);
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

	public List<String> getDiseaseParents() {
		return diseaseParents;
	}

	public void setDiseaseParents(List<String> diseaseParents) {
		this.diseaseParents = diseaseParents;
	}

}
