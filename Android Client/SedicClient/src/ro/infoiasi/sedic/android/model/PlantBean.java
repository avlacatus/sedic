package ro.infoiasi.sedic.android.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PlantBean {
	private String plantName;
	private long plantId;
	private String plantURI;

	public PlantBean() {
	}

	public PlantBean(String plantName, long plantId, String plantURI) {
		super();
		this.plantName = plantName;
		this.plantId = plantId;
		this.plantURI = plantURI;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlantEntity [plantName=");
		builder.append(plantName);
		builder.append(", plantId=");
		builder.append(plantId);
		builder.append(", plantResource=");
		builder.append(plantURI);
		builder.append("]");
		return builder.toString();
	}

	public JSONObject toJSONString() throws JSONException {
		JSONObject outputObject = new JSONObject();
		outputObject.put("plant_name", plantName);
		outputObject.put("plant_id", plantId);
		outputObject.put("plant_uri", plantURI);
		return outputObject;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public long getPlantId() {
		return plantId;
	}

	public void setPlantId(long plantId) {
		this.plantId = plantId;
	}

	public String getPlantURI() {
		return plantURI;
	}

	public void setPlantURI(String plantURI) {
		this.plantURI = plantURI;
	}

}
