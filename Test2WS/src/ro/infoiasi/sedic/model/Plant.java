package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.model.entity.PlantEntity;

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

public class Plant extends EntityHelper {

	public Plant() {
		super();
	}

	public JsonArray getPlantArray() {
		OntClass plantClass = getOntModel().getResource(OntologyConstants.NS + "Plant").as(OntClass.class);
		Property hasPlantId = getOntModel().getProperty(OntologyConstants.NS + "has_plant_id");
		ExtendedIterator<? extends OntResource> listInstances = plantClass.listInstances();
		List<PlantEntity> plants = new ArrayList<PlantEntity>();
		while (listInstances.hasNext()) {
			Individual plantResource = (Individual) listInstances.next();
			RDFNode propertyValue = plantResource.getPropertyValue(hasPlantId);
			PlantEntity plant = new PlantEntity();
			String plantName = plantResource.getURI().substring(OntologyConstants.NS.length()).replaceAll("_", " ");
			plant.setPlantURI(plantResource.getURI());
			plant.setPlantName(plantName);
			plant.setPlantId(Long.valueOf(propertyValue.toString()));
			plants.add(plant);
		}

		JsonArray plantsArray = new JsonArray();
		for (PlantEntity p : plants) {
			plantsArray.add(p.toJSONString());
		}
		return plantsArray;
	}

	public String getSpecificPlant(String id) {
		String sparqlQueryString = OntologyConstants.SPARQL_PREFIXES + "SELECT ?subject "
				+ "	WHERE { ?subject rdf:type sedic:Plant . " + "?subject sedic:has_plant_id " + id + "}";
		String response = "";
		Query query = QueryFactory.create(sparqlQueryString);
		ARQ.getContext().setTrue(ARQ.useSAX);
		QueryExecution qexec = QueryExecutionFactory.create(query, getOntModel());
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response += soln.get("subject").toString();
		}
		qexec.close();

		return response;
	}

}
