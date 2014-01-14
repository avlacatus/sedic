package ro.infoiasi.sedic.backend;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.URLConstants;
import ro.infoiasi.sedic.model.Plant;

@Path("/plant")
public class PlantWS {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecificPlant(@QueryParam(URLConstants.PARAM_PLANT_ID) String id) {
		OntologyConstants.initSedicPath(context);
		Plant p = new Plant();
		if (id != null) {
			return getPlantByID(p, id);
		} else {
			return getAllPlants(p);
		}
	}

	private String getAllPlants(Plant p) {
		JsonArray response = p.getPlantArray();
		JsonObject output = new JsonObject();
		output.put("plants", response);
		return output.toString();
	}

	private String getPlantByID(Plant p, String id) {
		return p.getSpecificPlant(id);
	}

}