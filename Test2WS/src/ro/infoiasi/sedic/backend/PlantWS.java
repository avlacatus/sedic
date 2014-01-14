package ro.infoiasi.sedic.backend;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.jena.atlas.json.JsonArray;

import ro.infoiasi.sedic.URLConstants;
import ro.infoiasi.sedic.model.Drug;
import ro.infoiasi.sedic.model.Plant;

@Path("/plant")
public class PlantWS {

	// This method is called if XML is request
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPlants() {

		Plant p = new Plant();

		JsonArray response = p.getPlantQuery();
		String returnedResponse = "<?xml version=\"1.0\"?>" + "<hello> Plants: " + response.toString();
				//+ "</hello>";
//		for( String r: response)
//		{
//		returnedResponse +="";
//		}
		return returnedResponse;
	}
	@GET
	@Produces(MediaType.TEXT_XML)
	public String getSpecificPlant(@QueryParam(URLConstants.PARAM_PLANT_ID) String Id) {
		if (Id != null) {
			Plant p = new Plant();
			String response = p.getSpecificPlant(Id);
			return  response;
		} else {
			return URLConstants.PARAM_PLANT_ID + " cannot be null";
		}
	}

}