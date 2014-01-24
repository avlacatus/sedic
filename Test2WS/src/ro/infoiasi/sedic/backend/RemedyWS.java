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

import ro.infoiasi.sedic.OntologyUtils;
import ro.infoiasi.sedic.URLConstants;
import ro.infoiasi.sedic.model.Remedy;

@Path("/remedy")
public class RemedyWS {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecificRemedy(@QueryParam(URLConstants.PARAM_REMEDY_ID) String id) {
		OntologyUtils.initSedicPath(context);
		Remedy remedy = Remedy.getInstance();
		if (id != null) {
			return remedy.getSpecificRemedy(id);
		} else {
			return getCompactRemedies(remedy);
		}

	}

	private String getCompactRemedies(Remedy remedy) {
		JsonArray response = remedy.getCompactRemedies();
		JsonObject output = new JsonObject();
		output.put("remedies", response);
		return output.toString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String performRemedySearch(String payload) {
		OntologyUtils.initSedicPath(context);
		if (payload == null || payload.isEmpty())
		{
			JsonObject output = new JsonObject();
			output.put("Error", "Payload empty");
			return output.toString();
		}
		JsonObject jsonPayload = JSON.parse(payload);
		ArrayList<String> adjuvantEffects = new ArrayList<String>();
		ArrayList<String> therapeuticalEffects = new ArrayList<String>();
		if (jsonPayload.hasKey("adjuvant_effect")) {
			JsonValue adjuvant = jsonPayload.get("adjuvant_effect");
			JsonArray array = adjuvant.getAsArray();
			// System.out.println(array);
			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = (JsonObject) array.get(i);
				long id = Long.parseLong(obj.get("id").toString());
				String uri = obj.get("uri").toString();
				
				String adjuvantUri = "<" + uri.replace('"', '>').substring(1);
				adjuvantEffects.add(adjuvantUri);
			}
		}
		if (jsonPayload.hasKey("therapeutical_effect")) {
			JsonValue therapeutical = jsonPayload.get("therapeutical_effect");
			JsonArray array = therapeutical.getAsArray();
			for (int i = 0; i < array.size(); i++) {
				// System.out.println(array.get(i).toString());
				JsonObject obj = (JsonObject) array.get(i);
				long id = Long.parseLong(obj.get("id").toString());
				String uri = obj.get("uri").toString();
				String thUri = "<" + uri.replace('"', '>').substring(1);
				therapeuticalEffects.add(thUri);
			}
		}
		JsonArray response = getQueryResult(adjuvantEffects, therapeuticalEffects);
		JsonObject output = new JsonObject();
		output.put("remedies", response);
		return output.toString();
	}

	private JsonArray getQueryResult(ArrayList<String> adjuvantEffects, ArrayList<String> therapeuticalEffects) {
		Remedy remedy = Remedy.getInstance();
		return remedy.getQueryResults(adjuvantEffects, therapeuticalEffects);

	}
}
