package ro.infoiasi.sedic.model.entity;

import org.apache.jena.atlas.json.JsonObject;

public class MedicalFactorEntity {
	private String medicalFactorURI;
	private String medicalFactorName;
	private long medicalFactorId;

	public MedicalFactorEntity() {
	}

	public MedicalFactorEntity(long Id, String URI) {
		super();
		this.medicalFactorId = Id;
		this.medicalFactorURI = URI;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(", Id=");
		builder.append(medicalFactorId);
		builder.append(", URI=");
		builder.append(medicalFactorURI);
		builder.append("]");
		return builder.toString();
	}

	public JsonObject toJSONString() {
		JsonObject outputObject = new JsonObject();
		outputObject.put("medical_factor_name", medicalFactorName);
		outputObject.put("medical_factor_id", medicalFactorId);
		outputObject.put("medical_factor_uri", medicalFactorURI);
		return outputObject;
	}

	public long getMedicalFactorId() {
		return medicalFactorId;
	}

	public void setMedicalFactorId(long Id) {
		this.medicalFactorId = Id;
	}

	public String getMedicalFactorURI() {
		return medicalFactorURI;
	}

	public void setMedicalFactorURI(String URI) {
		this.medicalFactorURI = URI;
	}
	public String getMedicalFactorName() {
		return medicalFactorName;
	}

	public void setMedicalFactorName(String name) {
		this.medicalFactorName = name;
	}
}
