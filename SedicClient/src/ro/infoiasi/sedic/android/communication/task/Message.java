package ro.infoiasi.sedic.android.communication.task;

public class Message {

	public static enum RequestType {
		GET, POST, PUT, DELETE
	}

	public static enum EntityType {
		ROAD, INDICATOR, MAPPING
	}

	public RequestType requestType;
	public EntityType entityType;
	public String extraData;

	public Message(RequestType requestType, EntityType entityType,
			String extraData) {
		super();
		this.requestType = requestType;
		this.entityType = entityType;
		this.extraData = extraData;
	}

	public Message(RequestType requestType, EntityType entityType) {
		super();
		this.requestType = requestType;
		this.entityType = entityType;
	}
	
	

}
