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

import ro.infoiasi.sedic.OntologyUtils;
import ro.infoiasi.sedic.URLConstants;
import ro.infoiasi.sedic.model.Disease;

@Path("/disease")
public class DiseaseWS {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDiseases(@QueryParam(URLConstants.PARAM_DISEASE_ID) String id) {
		OntologyUtils.initSedicPath(context);
		Disease d = Disease.getInstance();
		if (id != null) {
			return getDiseaseByID(d, id);
		} else {
			return getAllDiseases(d);
		}
	}

	private String getAllDiseases(Disease d) {
		JsonArray response = d.getAllDiseases();
		JsonObject output = new JsonObject();
		output.put("diseases", response);
		return output.toString();
	}

	private String getDiseaseByID(Disease d, String id) {
		return d.getSpecificDisease(id).toString();
	}

}
