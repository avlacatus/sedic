package ro.infoiasi.sedic.model;

import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.model.entity.RemedyEntity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class Remedy extends EntityHelper {

	public Remedy() {
		super();
	}
public String getSpecificRemedy(String id)
{
	String sparqlQueryString = OntologyConstants.SPARQL_PREFIXES
			+ "SELECT ?subject "
			+ "	WHERE { ?subject rdf:type sedic:Remedy . "
			+ "?subject sedic:has_remedy_id ?value ."
			+ "FILTER (STR(?value)= '" + id + "')" + "}";
	String response = "";
	Query query = QueryFactory.create(sparqlQueryString);
	ARQ.getContext().setTrue(ARQ.useSAX);
	QueryExecution qexec = QueryExecutionFactory.create(query,
			getOntModel());
	com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
if (results.hasNext())
{
	QuerySolution soln = results.nextSolution();
	response = soln.get("subject").toString();
	Individual remedyResource = getOntModel().getResource(response).as(
			Individual.class);
	Property hasRemedyId = getOntModel().getProperty(
			OntologyConstants.NS + "has_remedy_id");
	RDFNode propertyValue = remedyResource.getPropertyValue(hasRemedyId);
	RemedyEntity remedy = new RemedyEntity();
	String remedyName = remedyResource.getURI()
			.substring(OntologyConstants.NS.length()).replaceAll("_", " ");
	remedy.setRemedyURI(remedyResource.getURI());
	remedy.setRemedyName(remedyName);
	remedy.setRemedyId(Long.valueOf(propertyValue.toString()));

	qexec.close();
	JsonObject plantJson = remedy.toJSONString();
	
	return plantJson.toString();
}
else
	return "Remedy not found!";

}
}
