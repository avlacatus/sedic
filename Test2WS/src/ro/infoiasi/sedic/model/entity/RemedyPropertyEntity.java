package ro.infoiasi.sedic.model.entity;

import org.apache.jena.atlas.json.JsonObject;

public class RemedyPropertyEntity {

	private long Id;
	private String Name;

	public RemedyPropertyEntity() {
	}

	public RemedyPropertyEntity(long Id, String Name) {
		super();
		this.Id = Id;
		this.Name = Name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(", frequentUsageId=");
		builder.append(Id);
		builder.append(", frequentUsageName=");
		builder.append(Name);
		builder.append("]");
		return builder.toString();
	}

	public JsonObject toJSONString(String usageMode) {
		JsonObject outputObject = new JsonObject();
		switch (usageMode) {
		case "F":
			outputObject.put("frequentUsage_id", Id);
			outputObject.put("frequentUsage_Name", Name);
			break;
		case "R":
			outputObject.put("reportedUsage_id", Id);
			outputObject.put("reportedUsage_Name", Name);
			break;
		case "A":
			outputObject.put("adjuvantUsage_id", Id);
			outputObject.put("adjuvantUsage_Name", Name);
			break;
		case "T":
			outputObject.put("therapeuticalUsage_id", Id);
			outputObject.put("therapeuticalUsage_Name", Name);
			break;
		}
		return outputObject;
	}

	public long getId() {
		return Id;
	}

	public void setId(long Id) {
		this.Id = Id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

}
