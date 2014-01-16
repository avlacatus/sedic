package ro.infoiasi.sedic.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.mvc.Viewable;

@Path("/remedy")
public class RemedyWS {

	@GET
	public Viewable get() {
		return new Viewable("index.jsp", this);
	}

}
