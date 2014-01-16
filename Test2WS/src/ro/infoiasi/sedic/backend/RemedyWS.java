package ro.infoiasi.sedic.backend;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.jena.atlas.json.JSON;
import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.atlas.json.JsonValue;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.URLConstants;
import ro.infoiasi.sedic.model.Remedy;

@Path("/remedy")
public class RemedyWS {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecificRemedy(
			@QueryParam(URLConstants.PARAM_REMEDY_ID) String id) {
		OntologyConstants.initSedicPath(context);
		Remedy remedy = new Remedy();
		if (id != null) {
			return remedy.getSpecificRemedy(id);
		} else {
			return getAllRemedies(remedy);
		}
		
	}
	private String getAllRemedies(Remedy r) {
		JsonArray response = r.getCompactRemedies();
		JsonObject output = new JsonObject();
		output.put("remedies", response);
		return output.toString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String performRemedySearch(String payload) {
		JsonObject jsonPayload = JSON.parse(payload);
		ArrayList<String> adjuvantEffects = new ArrayList<String>();
		JsonObject adjuvant = (JsonObject) jsonPayload.get("adjuvant_effect");
		JsonArray array = adjuvant.getAsArray();
		//System.out.println(array);
		for(int i = 0 ; i < array.size() ; i++){
			JsonObject obj = (JsonObject) array.get(i);
			long id = Long.parseLong(obj.get("id").toString());
			String uri = obj.get("uri").toString();
			
//			System.out.println(().toString());
			adjuvantEffects.add(uri);
		}
		ArrayList<String> therapeuticalEffects = new ArrayList<String>();
		JsonObject therapeutical = (JsonObject) jsonPayload.get("therapeutical_effect");
		array = therapeutical.getAsArray();
		for(int i = 0 ; i < array.size() ; i++){
			//System.out.println(array.get(i).toString());
			JsonObject obj = (JsonObject) array.get(i);
			long id = Long.parseLong(obj.get("id").toString());
			String uri = obj.get("uri").toString();
			therapeuticalEffects.add(array.get(i).toString());
		}
		return "Hello from post + " + jsonPayload.toString();
	}
}
