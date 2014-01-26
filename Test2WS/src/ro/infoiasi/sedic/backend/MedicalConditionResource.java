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
import ro.infoiasi.sedic.model.MedicalConditionHelper;
import ro.infoiasi.sedic.model.PlantHelper;


@Path("/medical_condition")
public class MedicalConditionResource {
	@Context
	private ServletContext context;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMedicalConditions() {
		OntologyUtils.initSedicPath(context);
		MedicalConditionHelper mc = MedicalConditionHelper.getInstance();
		
			return getAllFactorsAndDiseases(mc);
		
	}
	private String getAllFactorsAndDiseases(MedicalConditionHelper mc) {
		JsonArray factors = mc.getMedicalFactorsArray();
		JsonArray diseases = mc.getDiseasesArray();
		JsonObject output = new JsonObject();
		output.put("medical_factors", factors);
		output.put("contraindicated_diseases", diseases);
		return output.toString();
	}
}
