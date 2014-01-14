package ro.infoiasi.sedic.model;

import ro.infoiasi.sedic.OntologyConstants;

import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;

public class Remedy extends EntityHelper {

	public Remedy() {
		super();
	}

	public String getAllRemedies() {
		String sparqlQueryString = OntologyConstants.SPARQL_PREFIXES + "SELECT ?subject "
				+ "	WHERE { ?subject rdf:type sedic:Remedy}";
		String response = "";
		Query query = QueryFactory.create(sparqlQueryString);
		ARQ.getContext().setTrue(ARQ.useSAX);
		QueryExecution qexec = QueryExecutionFactory.create(query, getOntModel());
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response += soln.get("subject").toString().substring(44) + "\n";
		}
		qexec.close();
		return response;
	}

}
