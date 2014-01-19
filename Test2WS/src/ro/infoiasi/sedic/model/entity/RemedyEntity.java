package ro.infoiasi.sedic.model.entity;

import java.util.ArrayList;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

public class RemedyEntity {
	private String remedyName;
	private long remedyId;
	private String remedyURI;
	private ArrayList<String> adjuvantUsage = new ArrayList<String>();
	private ArrayList<String> frequentUsage = new ArrayList<String>();
	private ArrayList<String> reportedUsage = new ArrayList<String>();
	private ArrayList<String> therapeuticalUsage = new ArrayList<String>();
	private long plantId;

	public RemedyEntity() {
	}

	public RemedyEntity(String remedyName, long remedyId, String remedyURI,
			ArrayList<String> adjuvantUsage, ArrayList<String> frequentUsage,
			ArrayList<String> reportedUsage,
			ArrayList<String> therapeuticalUsage, long plantId) {
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
		return "RemedyEntity [remedyName=" + remedyName + ", remedyId="
				+ remedyId + ", remedyURI=" + remedyURI + ", plantId="
				+ plantId + "]";
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
		JsonArray adjuvantArray = new JsonArray();
		for (String s : adjuvantUsage) {
			adjuvantArray.add(s);
		}
		JsonArray therapeuticalArray = new JsonArray();
		for (String s : therapeuticalUsage) {
			therapeuticalArray.add(s);
		}
		JsonArray frequentArray = new JsonArray();
		for (String s : frequentUsage) {
			frequentArray.add(s);
		}
		JsonArray reportedArray = new JsonArray();
		for (String s : reportedUsage) {
			reportedArray.add(s);
		}
		outputObject.put("adjuvant_usage", adjuvantArray);
		outputObject.put("therapeutical_usage", therapeuticalArray);
		outputObject.put("frequent_usage", frequentArray);
		outputObject.put("reported_usage", reportedArray);
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

	public ArrayList<String> getAdjuvantUsage() {
		return adjuvantUsage;
	}

	public void setAdjuvantUsage(ArrayList<String> adjuvantUsage) {
		this.adjuvantUsage = adjuvantUsage;
	}

	public void addAdjuvantUsage(String adjuvant) {
		this.adjuvantUsage.add(adjuvant);
	}

	public ArrayList<String> getTherapeuticalUsage() {
		return therapeuticalUsage;
	}

	public void setTherapeuticalUsage(ArrayList<String> tUsage) {
		this.therapeuticalUsage = tUsage;
	}

	public void addTherapeuticalUsage(String therapeutical) {
		this.therapeuticalUsage.add(therapeutical);
	}

	public ArrayList<String> getFrequentUsage() {
		return frequentUsage;
	}

	public void setFrequentUsage(ArrayList<String> fUsage) {
		this.frequentUsage = fUsage;
	}

	public void addFrequentUsage(String frequent) {
		this.frequentUsage.add(frequent);
	}

	public ArrayList<String> getReportedUsage() {
		return reportedUsage;
	}

	public void setReportedUsage(ArrayList<String> reportedUsage) {
		this.reportedUsage = reportedUsage;
	}

	public void addReportedUsage(String reported) {
		this.reportedUsage.add(reported);
	}

}
