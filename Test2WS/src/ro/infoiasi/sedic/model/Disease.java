package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.model.entity.DiseaseEntity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class Disease extends EntityHelper {

	public Disease() {
		super();
	}

	public JsonArray getAllDiseases() {
		OntClass diseaseClass = getOntModel().getResource(
				OntologyConstants.NS + "Diseases").as(OntClass.class);
		Property hasDiseaseId = getOntModel().getProperty(
				OntologyConstants.NS + "has_disease_id");
		ExtendedIterator<? extends OntResource> listInstances = diseaseClass
				.listInstances();
		List<DiseaseEntity> diseases = new ArrayList<DiseaseEntity>();
		while (listInstances.hasNext()) {
			Individual diseaseResource = (Individual) listInstances.next();
			RDFNode propertyValue = diseaseResource
					.getPropertyValue(hasDiseaseId);
			DiseaseEntity disease = new DiseaseEntity();
			String diseaseName = diseaseResource.getURI()
					.substring(OntologyConstants.NS.length())
					.replaceAll("_", " ");
			disease.setDiseaseURI(diseaseResource.getURI());
			disease.setDiseaseName(diseaseName);
			disease.setDiseaseId(Long.valueOf(propertyValue.toString()));
			diseases.add(disease);
		}

		JsonArray diseasesArray = new JsonArray();
		for (DiseaseEntity d : diseases) {
			diseasesArray.add(d.toJSONString());
		}
		return diseasesArray;
		// String sparqlQueryString = OntologyConstants.SPARQL_PREFIXES +
		// "SELECT ?subject "
		// + "	WHERE {   ?subject rdf:type ?object . "
		// + "?object  rdfs:subClassOf sedic:Diseases }";
		// String response = "";
		// Query query = QueryFactory.create(sparqlQueryString);
		// ARQ.getContext().setTrue(ARQ.useSAX);
		// QueryExecution qexec = QueryExecutionFactory.create(query,
		// getOntModel());
		// com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		//
		// while (results.hasNext()) {
		// QuerySolution soln = results.nextSolution();
		// response += soln.get("subject").toString().substring(44) + "\n";
		// }
		// qexec.close();
		// return response;

	}

	public String getSpecificDisease(String Id) {
		String sparqlQueryString = OntologyConstants.SPARQL_PREFIXES
				+ "SELECT ?subject "
				+ "	WHERE {   ?subject rdf:type ?object . "
				+ "?object  rdfs:subClassOf sedic:Diseases ."
				+ "?subject sedic:has_disease_id ?value ."
				+ "FILTER (STR(?value)= '" + Id + "')" + "}";
		String response = "";
		Query query = QueryFactory.create(sparqlQueryString);
		ARQ.getContext().setTrue(ARQ.useSAX);
		QueryExecution qexec = QueryExecutionFactory.create(query,
				getOntModel());
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		if (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response = soln.get("subject").toString();
			Individual diseaseResource = getOntModel().getResource(response)
					.as(Individual.class);
			Property hasDiseaseId = getOntModel().getProperty(
					OntologyConstants.NS + "has_disease_id");
			RDFNode propertyValue = diseaseResource
					.getPropertyValue(hasDiseaseId);
			DiseaseEntity disease = new DiseaseEntity();
			String diseaseName = diseaseResource.getURI()
					.substring(OntologyConstants.NS.length())
					.replaceAll("_", " ");
			disease.setDiseaseURI(diseaseResource.getURI());
			disease.setDiseaseName(diseaseName);
			disease.setDiseaseId(Long.valueOf(propertyValue.toString()));

			qexec.close();
			JsonObject diseaseJson = disease.toJSONString();
			return diseaseJson.toString();
		} else
			return "Disease not found!";
	}

}
