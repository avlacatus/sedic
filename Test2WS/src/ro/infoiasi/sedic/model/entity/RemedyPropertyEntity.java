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

	public JsonObject toJSONString() {
		JsonObject outputObject = new JsonObject();
		outputObject.put("remedy_usage_id", Id);
		outputObject.put("remedy_usage_name", Name);
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
