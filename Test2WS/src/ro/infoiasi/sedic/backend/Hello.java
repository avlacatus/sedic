package ro.infoiasi.sedic.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {

	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello plain text";
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		// String inputFileName = "D:\\facultate\\sedic\\sedic.owl";
		// Model m = ModelFactory.createDefaultModel();
		// InputStream in = FileManager.get().open(inputFileName);
		// if (in == null) {
		// throw new IllegalArgumentException("File: " + inputFileName
		// + " not found");
		// }

		// m.read(in, null);
		// m.write(System.out, "Turtle");
		String sparqlQueryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
				+ "PREFIX ifgi: <http://ifgi.uni-muenster.de/> "
				+ "SELECT ?friends WHERE { "
				+ "    ifgi:jones foaf:knows ?friends . " + "}";
		// Query query = QueryFactory.create(sparqlQueryString);
		// ARQ.getContext().setTrue(ARQ.useSAX);
		// QueryExecution qexec = QueryExecutionFactory.create(query, m);
		// ResultSet results = qexec.execSelect();
		String response = "atat";
		// while (results.hasNext()) {
		// QuerySolution soln = results.nextSolution();
		// System.out.println(soln.get("friends"));
		// response = soln.get("friends").toString();
		// }
		// qexec.close();
		return "<?xml version=\"1.0\"?>" + "<hello> Hello " + response
				+ "</hello>";
	}

	// This method is called if HTML is request
	// @GET
	// @Produces(MediaType.TEXT_HTML)
	// public String sayHtmlHello() {
	// return "<html> " + "<title>" + "Hello Jersey" + "</title>"
	// + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
	// }

}
