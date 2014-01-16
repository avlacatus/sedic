package ro.infoiasi.sedic.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/disease")
public class Resource {
	
	@GET
	public String getDisease() {
		return "diseases";
	}

}
