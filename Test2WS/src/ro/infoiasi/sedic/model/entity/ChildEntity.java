package ro.infoiasi.sedic.model.entity;

import org.apache.jena.atlas.json.JsonObject;

public class ChildEntity {
	private long childId;
	private String childURI;

	public ChildEntity() {
	}

	public ChildEntity(long plantId, String plantURI) {
		super();
		this.childId = plantId;
		this.childURI = plantURI;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(", parentId=");
		builder.append(childId);
		builder.append(", parentURI=");
		builder.append(childURI);
		builder.append("]");
		return builder.toString();
	}

	public JsonObject toJSONString() {
		JsonObject outputObject = new JsonObject();
		outputObject.put("child_id", childId);
		outputObject.put("child_uri", childURI);
		return outputObject;
	}

	public long getParentId() {
		return childId;
	}

	public void setParentId(long parentId) {
		this.childId = parentId;
	}

	public String getParentURI() {
		return childURI;
	}

	public void setParentURI(String parentURI) {
		this.childURI = parentURI;
	}

}
