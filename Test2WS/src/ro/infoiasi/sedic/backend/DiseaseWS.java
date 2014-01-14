package ro.infoiasi.sedic.backend;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.model.Disease;

@Path("/disease")
public class DiseaseWS {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDiseases() {
		OntologyConstants.initSedicPath(context);
		Disease p = new Disease();
		String response = p.getAllDiseases();
		return "<?xml version=\"1.0\"?>" + "<h1> Diseases: " + response + "</h1>";
	}

}
