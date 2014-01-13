package ro.infoiasi.sedic.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ro.infoiasi.sedic.URLConstants;
import ro.infoiasi.sedic.model.Drug;

@Path("/drug")
public class DrugWS {

	@GET
	@Produces(MediaType.TEXT_XML)
	public String getDiseases() {
		Drug d = new Drug();
		String response = d.getAllDrugs();
		return "<?xml version=\"1.0\"?>" + "<drug> Drugs: " + response
				+ "</drug>";
	}

	@GET
	@PathParam(URLConstants.PARAM_DRUG_ID)
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecificDisease(@PathParam(URLConstants.PARAM_DRUG_ID)String Id) {
		Drug d = new Drug();
		String response = d.getDrug(Id);
		return  response;
	}

}
