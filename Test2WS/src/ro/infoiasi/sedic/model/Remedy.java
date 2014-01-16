package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.model.entity.RemedyEntity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

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
	Property hasAdjuvantUsage = getOntModel().getProperty(
			OntologyConstants.NS + "hasAdjuvantUsage");
	Property hasTherapeuticalUsage = getOntModel().getProperty(
			OntologyConstants.NS + "hasTherapeuticalUsage");
	Property hasFrequentUsage = getOntModel().getProperty(
			OntologyConstants.NS + "hasFrequentUsage");
	Property hasReportedUsage = getOntModel().getProperty(
			OntologyConstants.NS + "hasReportedUsage");
	RDFNode propertyValue = remedyResource.getPropertyValue(hasRemedyId);
	RemedyEntity remedy = new RemedyEntity();
	String remedyName = remedyResource.getURI()
			.substring(OntologyConstants.NS.length()).replaceAll("_", " ");
	remedy.setRemedyURI(remedyResource.getURI());
	remedy.setRemedyName(remedyName);
	remedy.setRemedyId(Long.valueOf(propertyValue.toString()));
	NodeIterator it = remedyResource.listPropertyValues(hasAdjuvantUsage);
	while (it.hasNext())
	{
		RDFNode property = it.next();
		remedy.setAdjuvantUsage(property.toString().substring(OntologyConstants.NS.length()).replaceAll("_", " "));
	}
	it = remedyResource.listPropertyValues(hasTherapeuticalUsage);
	while (it.hasNext())
	{
		RDFNode property = it.next();
		remedy.setTherapeuticalUsage(property.toString().substring(OntologyConstants.NS.length()).replaceAll("_", " "));
	}
	it = remedyResource.listPropertyValues(hasFrequentUsage);
	while (it.hasNext())
	{
		RDFNode property = it.next();
		remedy.setFrequentUsage(property.toString().substring(OntologyConstants.NS.length()).replaceAll("_", " "));
	}
	it = remedyResource.listPropertyValues(hasReportedUsage);
	while (it.hasNext())
	{
		RDFNode property = it.next();
		remedy.setReportedUsage(property.toString().substring(OntologyConstants.NS.length()).replaceAll("_", " "));
	}
	qexec.close();
	JsonObject remedyJson = remedy.toJSONString();
	
	return remedyJson.toString();
}
else
	return "Remedy not found!";

}
public JsonArray getCompactRemedies() {
	OntClass remedyClass = getOntModel().getResource(
			OntologyConstants.NS + "Remedy").as(OntClass.class);
	Property hasRemedyId = getOntModel().getProperty(
			OntologyConstants.NS + "has_remedy_id");
	ExtendedIterator<? extends OntResource> listInstances = remedyClass
			.listInstances();
	List<RemedyEntity> remedies = new ArrayList<RemedyEntity>();
	while (listInstances.hasNext()) {
		Individual remedyResource = (Individual) listInstances.next();
		RDFNode propertyValue = remedyResource.getPropertyValue(hasRemedyId);
		RemedyEntity remedy = new RemedyEntity();
		String remedyName = remedyResource.getURI()
				.substring(OntologyConstants.NS.length())
				.replaceAll("_", " ");
		remedy.setRemedyURI(remedyResource.getURI());
		remedy.setRemedyName(remedyName);
		remedy.setRemedyId(Long.valueOf(propertyValue.toString()));
		remedies.add(remedy);
	}

	JsonArray remediesArray = new JsonArray();
	for (RemedyEntity r : remedies) {
		remediesArray.add(r.toString());
	}
	return remediesArray;
}
}
