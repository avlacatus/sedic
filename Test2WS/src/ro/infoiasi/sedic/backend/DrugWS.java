package ro.infoiasi.sedic.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

	

}
