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
		return remedy.getSpecificRemedy(id);
	}
}
