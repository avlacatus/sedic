package ro.infoiasi.sedic.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ro.infoiasi.sedic.model.Plant;


@Path("/plant")
public class PlantWS {


	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String getPlants() {
		
		Plant p = new Plant();
	
		String response = p.getPlantQuery();
		
		return "<?xml version=\"1.0\"?>" + "<hello> Hello " + response
				+ "</hello>";
	}


}