package ro.infoiasi.sedic.backend;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.URLConstants;
import ro.infoiasi.sedic.model.Drug;

@Path("/drug")
public class DrugWS {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.TEXT_XML)
	public String getDiseases() {
		OntologyConstants.initSedicPath(context);
		Drug d = new Drug();
		String response = d.getAllDrugs();
		return "<?xml version=\"1.0\"?>" + "<drug> Drugs: " + response
				+ "</drug>";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecificDisease(@QueryParam(URLConstants.PARAM_DRUG_ID) String Id) {
		OntologyConstants.initSedicPath(context);
		if (Id != null) {
			Drug d = new Drug();
			String response = d.getDrug(Id);
			return  response;
		} else {
			return URLConstants.PARAM_DRUG_ID + " cannot be null";
		}
	}

}
