package ro.infoiasi.sedic;

import javax.servlet.ServletContext;

import ro.infoiasi.sedic.model.EntityHelper;

import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;

public class OntologyUtils {
	private static String SEDIC_PATH = null;
	public static final String NS = "http://www.infoiasi.ro/wad/ontologies/sedic#";
	public static String SPARQL_PREFIXES = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX sedic: <http://www.infoiasi.ro/wad/ontologies/sedic#> ";

	public static void initSedicPath(ServletContext context) {
		SEDIC_PATH = context.getRealPath("WEB-INF/classes/files/sedic.owl");
	}

	public static String getOntologyFilePath() {
		return SEDIC_PATH;
	}

	public static QueryExecution getSPARQLQuery(EntityHelper helper, String queryString) {
		Query query = QueryFactory.create(queryString);
		ARQ.getContext().setTrue(ARQ.useSAX);
		return QueryExecutionFactory.create(query, helper.getOntModel());
	}

}
