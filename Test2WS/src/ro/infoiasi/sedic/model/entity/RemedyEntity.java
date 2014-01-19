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
	private long plantId;

	public RemedyEntity() {
	}

	public RemedyEntity(String remedyName, long remedyId, String remedyURI, String adjuvantUsage, String frequentUsage,
			String reportedUsage, String therapeuticalUsage, long plantId) {
		super();
		this.remedyName = remedyName;
		this.remedyId = remedyId;
		this.remedyURI = remedyURI;
		this.adjuvantUsage = adjuvantUsage;
		this.frequentUsage = frequentUsage;
		this.reportedUsage = reportedUsage;
		this.therapeuticalUsage = therapeuticalUsage;
		this.plantId = plantId;
	}

	public long getPlantId() {
		return plantId;
	}

	public void setPlantId(long plantId) {
		this.plantId = plantId;
	}

	@Override
	public String toString() {
		return "RemedyEntity [remedyName=" + remedyName + ", remedyId=" + remedyId + ", remedyURI=" + remedyURI
				+ ", plantId=" + plantId + "]";
	}

	public JsonObject toCompactJSONString() {
		JsonObject outputObject = new JsonObject();
		outputObject.put("remedy_name", remedyName);
		outputObject.put("remedy_id", remedyId);
		outputObject.put("remedy_uri", remedyURI);
		outputObject.put("remedy_plant_id", plantId);
		return outputObject;
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
		outputObject.put("remedy_plant_id", plantId);
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
