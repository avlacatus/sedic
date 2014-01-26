package ro.infoiasi.sedic.model.entity;

import org.apache.jena.atlas.json.JsonObject;

public class RemedyPropertyEntity {

	private long id;
	private String name;

	public RemedyPropertyEntity() {
	}

	public RemedyPropertyEntity(long Id, String Name) {
		super();
		this.id = Id;
		this.name = Name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(", id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	public JsonObject toJSONString() {
		JsonObject outputObject = new JsonObject();
		outputObject.put("remedy_usage_id", id);
		outputObject.put("remedy_usage_name", name);
		return outputObject;
	}

	public long getId() {
		return id;
	}

	public void setId(long Id) {
		this.id = Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

}
