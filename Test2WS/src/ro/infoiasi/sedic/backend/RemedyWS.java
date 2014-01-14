package ro.infoiasi.sedic.backend;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.model.Remedy;

@Path("/remedy")
public class RemedyWS {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.TEXT_XML)
	public String getDiseases() {
		OntologyConstants.initSedicPath(context);
		Remedy r = new Remedy();
		String response = r.getAllRemedies();
		return "<?xml version=\"1.0\"?>" + "<h1> Remedies: " + response + "</h1>";
	}

}
