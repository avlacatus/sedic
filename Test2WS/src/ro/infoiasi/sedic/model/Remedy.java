package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyUtils;
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

	private static Remedy sInstance;

	private Remedy() {
		super();
	}

	public static Remedy getInstance() {
		if (sInstance == null) {
			sInstance = new Remedy();
		}
		return sInstance;
	}

	public String getSpecificRemedy(String id) {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES
				+ "SELECT ?subject "
				+ "	WHERE { ?subject rdf:type sedic:Remedy . "
				+ "?subject sedic:has_remedy_id ?value ."
				+ "FILTER (STR(?value)= '" + id + "')" + "}";
		String response = "";
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this,
				sparqlQueryString);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		if (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response = soln.get("subject").toString();
			Individual remedyResource = getOntModel().getResource(response).as(
					Individual.class);
			Property hasRemedyId = getOntModel().getProperty(
					OntologyUtils.NS + "has_remedy_id");
			Property hasAdjuvantUsage = getOntModel().getProperty(
					OntologyUtils.NS + "hasAdjuvantUsage");
			Property hasTherapeuticalUsage = getOntModel().getProperty(
					OntologyUtils.NS + "hasTherapeuticalUsage");
			Property hasFrequentUsage = getOntModel().getProperty(
					OntologyUtils.NS + "hasFrequentUsage");
			Property hasReportedUsage = getOntModel().getProperty(
					OntologyUtils.NS + "hasReportedUsage");
			Property usesPlant = getOntModel().getProperty(
					OntologyUtils.NS + "usesPlant");
			Property hasPlantId = getOntModel().getProperty(
					OntologyUtils.NS + "has_plant_id");
			RDFNode propertyValue = remedyResource
					.getPropertyValue(hasRemedyId);
			RDFNode usesPlantValue = remedyResource.getPropertyValue(usesPlant);
			Individual plantResource = getOntModel().getResource(
					usesPlantValue.toString()).as(Individual.class);
			RDFNode hasPlantIdValue = plantResource
					.getPropertyValue(hasPlantId);

			RemedyEntity remedy = new RemedyEntity();
			String remedyName = remedyResource.getURI()
					.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			remedy.setRemedyURI(remedyResource.getURI());
			remedy.setRemedyName(remedyName);
			remedy.setRemedyId(Long.valueOf(propertyValue.toString()));
			remedy.setPlantId(Long.valueOf(hasPlantIdValue.toString()));
			NodeIterator it = remedyResource
					.listPropertyValues(hasAdjuvantUsage);
			while (it.hasNext()) {
				RDFNode property = it.next();
				remedy.addAdjuvantUsage(property.toString()
						.substring(OntologyUtils.NS.length())
						.replaceAll("_", " "));
			}
			it = remedyResource.listPropertyValues(hasTherapeuticalUsage);
			while (it.hasNext()) {
				RDFNode property = it.next();
				remedy.addTherapeuticalUsage(property.toString()
						.substring(OntologyUtils.NS.length())
						.replaceAll("_", " "));
			}
			it = remedyResource.listPropertyValues(hasFrequentUsage);
			while (it.hasNext()) {
				RDFNode property = it.next();
				remedy.addFrequentUsage(property.toString()
						.substring(OntologyUtils.NS.length())
						.replaceAll("_", " "));
			}
			it = remedyResource.listPropertyValues(hasReportedUsage);
			while (it.hasNext()) {
				RDFNode property = it.next();
				remedy.addReportedUsage(property.toString()
						.substring(OntologyUtils.NS.length())
						.replaceAll("_", " "));
			}
			qexec.close();
			JsonObject remedyJson = remedy.toJSONString();

			return remedyJson.toString();
		} else
			return "Remedy not found!";

	}

	public JsonArray getCompactRemedies() {
		OntClass remedyClass = getOntModel().getResource(
				OntologyUtils.NS + "Remedy").as(OntClass.class);
		Property hasRemedyId = getOntModel().getProperty(
				OntologyUtils.NS + "has_remedy_id");
		Property usesPlant = getOntModel().getProperty(
				OntologyUtils.NS + "usesPlant");
		Property hasPlantId = getOntModel().getProperty(
				OntologyUtils.NS + "has_plant_id");

		ExtendedIterator<? extends OntResource> listInstances = remedyClass
				.listInstances();
		List<RemedyEntity> remedies = new ArrayList<RemedyEntity>();
		while (listInstances.hasNext()) {
			Individual remedyResource = (Individual) listInstances.next();
			RDFNode hasRemedyIdValue = remedyResource
					.getPropertyValue(hasRemedyId);
			RDFNode usesPlantValue = remedyResource.getPropertyValue(usesPlant);
			Individual plantResource = getOntModel().getResource(
					usesPlantValue.toString()).as(Individual.class);
			RDFNode hasPlantIdValue = plantResource
					.getPropertyValue(hasPlantId);

			RemedyEntity remedy = new RemedyEntity();
			String remedyName = remedyResource.getURI()
					.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			remedy.setRemedyURI(remedyResource.getURI());
			remedy.setRemedyName(remedyName);
			remedy.setRemedyId(Long.valueOf(hasRemedyIdValue.toString()));
			remedy.setPlantId(Long.valueOf(hasPlantIdValue.toString()));
			remedies.add(remedy);
		}

		JsonArray remediesArray = new JsonArray();
		for (RemedyEntity remedy : remedies) {
			JsonObject remedyJson = remedy.toCompactJSONString();
			remediesArray.add(remedyJson);
		}
		return remediesArray;
	}

	public JsonArray getQueryResults(ArrayList<String> adjuvantEffects,
			ArrayList<String> therapeuticalEffects) {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES
				+ "SELECT ?subject "
				+ "	WHERE { ?subject rdf:type sedic:Remedy . ";
		for (String adjuvant : adjuvantEffects) {
			sparqlQueryString += " ?subject sedic:hasAdjuvantUsage" + adjuvant
					+ ".";
		}
		for (String th : therapeuticalEffects) {
			sparqlQueryString += " ?subject sedic:hasTherapeuticalUsage " + th
					+ ".";
		}
		sparqlQueryString += " }";
		String response = "";
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this,
				sparqlQueryString);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		ArrayList<RemedyEntity> result = new ArrayList<RemedyEntity>();
		Property usesPlant = getOntModel().getProperty(
				OntologyUtils.NS + "usesPlant");
		Property hasPlantId = getOntModel().getProperty(
				OntologyUtils.NS + "has_plant_id");
		Property hasRemedyId = getOntModel().getProperty(
				OntologyUtils.NS + "has_remedy_id");
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response = soln.get("subject").toString();
			Individual remedyResource = getOntModel().getResource(response).as(
					Individual.class);
			String remedyName = remedyResource.getURI()
					.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			RemedyEntity remedyEntity = new RemedyEntity();
			remedyEntity.setRemedyName(remedyName);
			RDFNode hasRemedyIdValue = remedyResource
					.getPropertyValue(hasRemedyId);
			RDFNode usesPlantValue = remedyResource.getPropertyValue(usesPlant);
			Individual plantResource = getOntModel().getResource(
					usesPlantValue.toString()).as(Individual.class);
			RDFNode hasPlantIdValue = plantResource
					.getPropertyValue(hasPlantId);
			remedyEntity.setRemedyURI(remedyResource.getURI());
			remedyEntity.setRemedyName(remedyName);
			remedyEntity.setRemedyId(Long.valueOf(hasRemedyIdValue.toString()));
			remedyEntity.setPlantId(Long.valueOf(hasPlantIdValue.toString()));
			result.add(remedyEntity);
		}
		qexec.close();
		JsonArray resultArray = new JsonArray();
		for (RemedyEntity r : result) {
			System.out.println(r.toString());
			resultArray.add(r.toCompactJSONString());
		}
		return resultArray;

	}
}
