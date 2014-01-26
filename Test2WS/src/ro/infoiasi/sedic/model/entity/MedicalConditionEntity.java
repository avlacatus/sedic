package ro.infoiasi.sedic.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

public class MedicalConditionEntity {
	   private String name;
	    private long id;
	    private String uri; 
	    private List<MedicalFactorEntity> medicalFactors = new ArrayList<MedicalFactorEntity>();
	    private List<DiseaseEntity> diseases = new ArrayList<DiseaseEntity>();
	    private int minimumAge;

	    public MedicalConditionEntity() {
	    }

	    public MedicalConditionEntity(String Name, long Id, String URI , ArrayList<MedicalFactorEntity>medicalFactors, ArrayList<DiseaseEntity>diseases, int age) {
	        super();
	        this.name = Name;
	        this.id = Id;
	        this.uri = URI;
	        this.diseases = diseases;
	        this.medicalFactors = medicalFactors;
	        this.minimumAge = age;
	    }

	    @Override
	    public String toString() {
	        StringBuilder builder = new StringBuilder();
	        builder.append("MedicalConditionEntity [Name=");
	        builder.append(name);
	        builder.append(", Id=");
	        builder.append(id);
	        builder.append(", Resource=");
	        builder.append(uri);
	        builder.append("]");
	        return builder.toString();
	    }
	    
	    public JsonObject toJSONString() {
	        JsonObject outputObject = new JsonObject();
	        outputObject.put("medical_condition_name", name);
	        outputObject.put("medical_condition_id", id);
	        outputObject.put("medical_condition_uri", uri);
	        outputObject.put("medical_condition_mininum_age", minimumAge);
	        JsonArray factorsArray = new JsonArray();
			for (MedicalFactorEntity f : medicalFactors) {
				factorsArray.add(f.toJSONString());
			}
			 JsonArray diseasesArray = new JsonArray();
				for (DiseaseEntity d : diseases) {
					diseasesArray.add(d.toCompactJSONString());
				}
				outputObject.put("medical_factors", factorsArray);
				outputObject.put("diseases", diseasesArray);
	        return outputObject;
	    }

	    public String getMedicalConditionName() {
	        return name;
	    }

	    public void setMedicalConditionName(String name) {
	        this.name = name;
	    }

	    public long getMedicalConditionId() {
	        return id;
	    }

	    public void setMedicalConditionId(long Id) {
	        this.id = Id;
	    }
	    public int getMedicalConditionMinAge() {
	        return minimumAge;
	    }

	    public void setMedicalConditionMinAge(int age) {
	        this.minimumAge = age;
	    }
	    public String getMedicalConditionURI() {
	        return uri;
	    }

	    public void setMedicalconditionURI(String URI) {
	        this.uri = URI;
	    }
	    public List<MedicalFactorEntity> getMedicalFactors() {
	        return medicalFactors;
	    }

	    public void setMedicalFactors(List<MedicalFactorEntity> medicalFactors) {
	        this.medicalFactors = medicalFactors;
	    }
	    public List<DiseaseEntity> getDiseases() {
	        return diseases;
	    }

	    public void setDiseases(List<DiseaseEntity> diseases) {
	        this.diseases = diseases;
	    }
}
