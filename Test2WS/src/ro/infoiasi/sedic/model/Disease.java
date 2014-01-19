package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;

import ro.infoiasi.sedic.OntologyUtils;
import ro.infoiasi.sedic.model.entity.DiseaseEntity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class Disease extends EntityHelper {

	private static Disease sInstance;

	private Disease() {
		super();
	}

	public static Disease getInstance() {
		if (sInstance == null) {
			sInstance = new Disease();
		}
		return sInstance;
	}

	public JsonArray getAllDiseases() {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES + "SELECT DISTINCT ?subject ?class ?id WHERE "
				+ "{" + "?class rdfs:subClassOf* sedic:Diseases . " + "?subject a ?class . "
				+ "?class sedic:has_disease_id ?id " + "} " + "order by asc (?subject)";
		OntClass diseaseClass = getOntModel().getResource(OntologyUtils.NS + "Diseases").as(OntClass.class);
		ExtendedIterator<OntClass> iterator = diseaseClass.listSubClasses();
		List<DiseaseEntity> diseases = new ArrayList<DiseaseEntity>();
		while (iterator.hasNext()) {
			Property hasDiseaseId = getOntModel().getProperty(OntologyUtils.NS + "has_disease_id");
			ExtendedIterator<? extends OntResource> listInstances = iterator.next().listInstances();

			while (listInstances.hasNext()) {
				Individual diseaseResource = (Individual) listInstances.next();
				RDFNode propertyValue = diseaseResource.getPropertyValue(hasDiseaseId);
				DiseaseEntity disease = new DiseaseEntity();
				String diseaseName = diseaseResource.getURI().substring(OntologyUtils.NS.length())
						.replaceAll("_", " ");
				// disease.setDiseaseURI(diseaseResource.getURI());
				disease.setDiseaseName(diseaseName);
				disease.setDiseaseId(Long.valueOf(propertyValue.toString()));
				diseases.add(disease);
			}
		}

		JsonArray diseasesArray = new JsonArray();
		for (DiseaseEntity d : diseases) {
			diseasesArray.add(d.toJSONString());
		}
		return diseasesArray;

	}

	public String getSpecificDisease(String Id) {

		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES + "SELECT ?subject "
				+ "	WHERE {   ?subject a ?class . " + "?class  rdfs:subClassOf* sedic:Diseases ."
				+ "?subject sedic:has_disease_id ?value ." + "FILTER (STR(?value)= '" + Id + "')" + "}";
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this, sparqlQueryString);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		// System.out.println(sparqlQueryString);
		System.out.println("results:" + results.getRowNumber());

		if (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			String response = soln.get("subject").toString();
			System.out.println(response.toString());
			Individual diseaseResource = getOntModel().getResource(response).as(Individual.class);
			Property hasDiseaseId = getOntModel().getProperty(OntologyUtils.NS + "has_disease_id");
			RDFNode propertyValue = diseaseResource.getPropertyValue(hasDiseaseId);

			DiseaseEntity disease = new DiseaseEntity();
			String diseaseName = diseaseResource.getURI().substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			disease.setDiseaseURI(diseaseResource.getURI());
			disease.setDiseaseName(diseaseName);
			disease.setDiseaseId(Long.valueOf(propertyValue.toString()));

			qexec.close();
			// JsonObject diseaseJson = disease.toJSONString();
			return disease.toString();
		} else
			return "Disease not found!";
	}

}
