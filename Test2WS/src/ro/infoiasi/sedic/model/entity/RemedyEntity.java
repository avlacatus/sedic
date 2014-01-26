package ro.infoiasi.sedic.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

public class RemedyEntity {
	private String remedyName;
	private long remedyId;
	private String remedyURI;
	private long remedyPlantId;
	private List<RemedyPropertyEntity> adjuvantUsage = new ArrayList<RemedyPropertyEntity>();
	private List<RemedyPropertyEntity> frequentUsage = new ArrayList<RemedyPropertyEntity>();
	private List<RemedyPropertyEntity> reportedUsage = new ArrayList<RemedyPropertyEntity>();
	private List<RemedyPropertyEntity> therapeuticalUsage = new ArrayList<RemedyPropertyEntity>();
	private List<String> partPlantUsage = new ArrayList<String>();
	private MedicalConditionEntity medicalCondition = new MedicalConditionEntity() ;

	public RemedyEntity() {
	}

	public RemedyEntity(String remedyName, long remedyId, String remedyURI,
			List<RemedyPropertyEntity> adjuvantUsage,
			List<RemedyPropertyEntity> frequentUsage,
			List<RemedyPropertyEntity> reportedUsage,
			List<RemedyPropertyEntity> therapeuticalUsage, long remedyPlantId,
			List<String> partPlantUsage, MedicalConditionEntity medicalCondition) {
		super();
		this.remedyName = remedyName;
		this.remedyId = remedyId;
		this.remedyURI = remedyURI;
		this.adjuvantUsage = adjuvantUsage;
		this.frequentUsage = frequentUsage;
		this.reportedUsage = reportedUsage;
		this.therapeuticalUsage = therapeuticalUsage;
		this.remedyPlantId = remedyPlantId;
		this.partPlantUsage = partPlantUsage;
		this.medicalCondition = medicalCondition;
	}

	public long getRemedyPlantId() {
		return remedyPlantId;
	}

	public void setRemedyPlantId(long plantId) {
		this.remedyPlantId = plantId;
	}

	@Override
	public String toString() {
		return "RemedyEntity [remedyName=" + remedyName + ", remedyId="
				+ remedyId + ", remedyURI=" + remedyURI + ", plantId="
				+ remedyPlantId + "]";
	}

	public JsonObject toCompactJSONString() {
		JsonObject outputObject = new JsonObject();
		outputObject.put("remedy_name", remedyName);
		outputObject.put("remedy_id", remedyId);
		outputObject.put("remedy_uri", remedyURI);
		outputObject.put("remedy_plant_id", remedyPlantId);
		return outputObject;
	}

	public JsonObject toJSONString() {
		JsonObject outputObject = new JsonObject();
		outputObject.put("remedy_name", remedyName);
		outputObject.put("remedy_id", remedyId);
		outputObject.put("medical_condition", medicalCondition.toJSONString());
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
		outputObject.put("remedy_plant_id", remedyPlantId);
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

	public List<String> getPartPlantUsage() {
		return partPlantUsage;
	}

	public void setPartPlantUsage(List<String> partPlantUsage) {
		this.partPlantUsage = partPlantUsage;
	}

	public void addPlantPartUsage(String plantPart) {
		this.partPlantUsage.add(plantPart);
	}

	public List<RemedyPropertyEntity> getAdjuvantUsage() {
		return adjuvantUsage;
	}

	public void setAdjuvantUsage(List<RemedyPropertyEntity> adjuvantUsage) {
		this.adjuvantUsage = adjuvantUsage;
	}

	public void addAdjuvantUsage(RemedyPropertyEntity adjuvant) {
		this.adjuvantUsage.add(adjuvant);
	}

	public List<RemedyPropertyEntity> getTherapeuticalUsage() {
		return therapeuticalUsage;
	}

	public void setTherapeuticalUsage(ArrayList<RemedyPropertyEntity> tUsage) {
		this.therapeuticalUsage = tUsage;
	}

	public void addTherapeuticalUsage(RemedyPropertyEntity therapeutical) {
		this.therapeuticalUsage.add(therapeutical);
	}

	public List<RemedyPropertyEntity> getFrequentUsage() {
		return frequentUsage;
	}

	public void setFrequentUsage(List<RemedyPropertyEntity> fUsage) {
		this.frequentUsage = fUsage;
	}

	public void addFrequentUsage(RemedyPropertyEntity frequent) {
		this.frequentUsage.add(frequent);
	}

	public List<RemedyPropertyEntity> getReportedUsage() {
		return reportedUsage;
	}

	public void setReportedUsage(List<RemedyPropertyEntity> reportedUsage) {
		this.reportedUsage = reportedUsage;
	}

	public void addReportedUsage(RemedyPropertyEntity reported) {
		this.reportedUsage.add(reported);
	}

	public MedicalConditionEntity getMedicalCondition() {
		return medicalCondition;
	}
	public void setMedicalCondition(MedicalConditionEntity medicalCondition)
	{
		this.medicalCondition = medicalCondition;
	}
}
