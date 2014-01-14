package ro.infoiasi.sedic.model.entity;


import org.apache.jena.atlas.json.JsonObject;

public class PlantEntity {
    private String plantName;
    private long plantId;
    private String plantURI;

    public PlantEntity() {
    }

    public PlantEntity(String plantName, long plantId, String plantURI) {
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
    
    public JsonObject toJSONString() {
        JsonObject outputObject = new JsonObject();
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
