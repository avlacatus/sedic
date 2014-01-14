package ro.infoiasi.sedic.backend;

import java.io.File;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ro.infoiasi.sedic.model.Disease;

@Path("/disease")
public class DiseaseWS {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.TEXT_XML)
	public String getDiseases() {
		String fileName = context.getRealPath("/classes/files/sedic.owl");
		System.out.println(context.toString() + " " + fileName);
		Disease p = new Disease(fileName);
		String response = p.getAllDiseases();
		return "<?xml version=\"1.0\"?>" + "<h1> Diseases: " + response
				+ "</h1>";
	}

}
