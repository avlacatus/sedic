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
import ro.infoiasi.sedic.model.Drug;

@Path("/drug")
public class DrugWS {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDrugs(@QueryParam(URLConstants.PARAM_DRUG_ID) String id) {
		OntologyConstants.initSedicPath(context);
		Drug d = new Drug();
		if (id != null) {
			return getDrugByID(d, id);
		} else {
			return getAllDrugs(d);
		}
	}

	private String getAllDrugs(Drug d) {
		JsonArray response = d.getAllDrugs();
		JsonObject output = new JsonObject();
		output.put("diseases", response);
		return output.toString();
	}

	private String getDrugByID(Drug d, String id) {
		return d.getDrug(id);
	}

}
