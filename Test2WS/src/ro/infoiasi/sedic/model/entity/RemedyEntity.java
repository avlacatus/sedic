package ro.infoiasi.sedic.model.entity;

import java.util.ArrayList;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

public class RemedyEntity {
	private String remedyName;
	private long remedyId;
	private String remedyURI;
	private ArrayList<RemedyPropertyEntity> adjuvantUsage = new ArrayList<RemedyPropertyEntity>();
	private ArrayList<RemedyPropertyEntity> frequentUsage = new ArrayList<RemedyPropertyEntity>();
	private ArrayList<RemedyPropertyEntity> reportedUsage = new ArrayList<RemedyPropertyEntity>();
	private ArrayList<RemedyPropertyEntity> therapeuticalUsage = new ArrayList<RemedyPropertyEntity>();
	private ArrayList<String> partPlantUsage = new ArrayList<String>();
	private long plantId;

	public RemedyEntity() {
	}

	public RemedyEntity(String remedyName, long remedyId, String remedyURI,
			ArrayList<RemedyPropertyEntity> adjuvantUsage, ArrayList<RemedyPropertyEntity> frequentUsage,
			ArrayList<RemedyPropertyEntity> reportedUsage,
			ArrayList<RemedyPropertyEntity> therapeuticalUsage, long plantId, ArrayList<String> partPlantUsage) {
		super();
		this.remedyName = remedyName;
		this.remedyId = remedyId;
		this.remedyURI = remedyURI;
		this.adjuvantUsage = adjuvantUsage;
		this.frequentUsage = frequentUsage;
		this.reportedUsage = reportedUsage;
		this.therapeuticalUsage = therapeuticalUsage;
		this.plantId = plantId;
		this.partPlantUsage = partPlantUsage;
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
		JsonArray partPlantArray = new JsonArray();
		for (String s : partPlantUsage) {
			partPlantArray.add(s);
		}
		JsonArray adjuvantArray = new JsonArray();
		for (RemedyPropertyEntity s : adjuvantUsage) {
			adjuvantArray.add(s.toJSONString());
		}
		JsonArray therapeuticalArray = new JsonArray();
		for (RemedyPropertyEntity s : therapeuticalUsage) {
			therapeuticalArray.add(s.toJSONString());
		}
		JsonArray frequentArray = new JsonArray();
		for (RemedyPropertyEntity s : frequentUsage) {
			frequentArray.add(s.toJSONString());
		}
		JsonArray reportedArray = new JsonArray();
		for (RemedyPropertyEntity s : reportedUsage) {
			reportedArray.add(s.toJSONString());
		}
		outputObject.put("part_plant_usage", partPlantArray);
		outputObject.put("adjuvant_usages", adjuvantArray);
		outputObject.put("therapeutical_usages", therapeuticalArray);
		outputObject.put("frequent_usages", frequentArray);
		outputObject.put("reported_usages", reportedArray);
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

	public ArrayList<String> getPartPlantUsage() {
		return partPlantUsage;
	}

	public void setPartPlantUsage(ArrayList<String> partPlantUsage) {
		this.partPlantUsage = partPlantUsage;
	}
	public void addPlantPartUsage(String plantPart) {
		this.partPlantUsage.add(plantPart);
	}
	public ArrayList<RemedyPropertyEntity> getAdjuvantUsage() {
		return adjuvantUsage;
	}

	public void setAdjuvantUsage(ArrayList<RemedyPropertyEntity> adjuvantUsage) {
		this.adjuvantUsage = adjuvantUsage;
	}

	public void addAdjuvantUsage(RemedyPropertyEntity adjuvant) {
		this.adjuvantUsage.add(adjuvant);
	}

	public ArrayList<RemedyPropertyEntity> getTherapeuticalUsage() {
		return therapeuticalUsage;
	}

	public void setTherapeuticalUsage(ArrayList<RemedyPropertyEntity> tUsage) {
		this.therapeuticalUsage = tUsage;
	}

	public void addTherapeuticalUsage(RemedyPropertyEntity therapeutical) {
		this.therapeuticalUsage.add(therapeutical);
	}

	public ArrayList<RemedyPropertyEntity> getFrequentUsage() {
		return frequentUsage;
	}

	public void setFrequentUsage(ArrayList<RemedyPropertyEntity> fUsage) {
		this.frequentUsage = fUsage;
	}

	public void addFrequentUsage(RemedyPropertyEntity frequent) {
		this.frequentUsage.add(frequent);
	}

	public ArrayList<RemedyPropertyEntity> getReportedUsage() {
		return reportedUsage;
	}

	public void setReportedUsage(ArrayList<RemedyPropertyEntity> reportedUsage) {
		this.reportedUsage = reportedUsage;
	}

	public void addReportedUsage(RemedyPropertyEntity reported) {
		this.reportedUsage.add(reported);
	}

}
