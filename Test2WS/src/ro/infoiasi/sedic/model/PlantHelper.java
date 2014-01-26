package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyUtils;
import ro.infoiasi.sedic.model.entity.PlantEntity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class PlantHelper extends EntityHelper {

	private static PlantHelper sInstance;

	private PlantHelper() {
		super();
	}

	public static PlantHelper getInstance() {
		if (sInstance == null) {
			sInstance = new PlantHelper();
		}
		return sInstance;
	}

	public JsonArray getPlantArray() {
		OntClass plantClass = getOntModel().getResource(OntologyUtils.NS + "Plant").as(OntClass.class);
		Property hasPlantId = getOntModel().getProperty(OntologyUtils.NS + "has_plant_id");
		Property hasPlantDescription = getOntModel().getProperty(OntologyUtils.NS + "plantDescription");
		ExtendedIterator<? extends OntResource> listInstances = plantClass.listInstances();
		List<PlantEntity> plants = new ArrayList<PlantEntity>();
		while (listInstances.hasNext()) {
			Individual plantResource = (Individual) listInstances.next();
			RDFNode propertyValue = plantResource.getPropertyValue(hasPlantId);
			RDFNode plantDescription = plantResource.getPropertyValue(hasPlantDescription);
			PlantEntity plant = new PlantEntity();
			String plantName = plantResource.getURI().substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			plant.setPlantURI(plantResource.getURI());
			plant.setPlantName(plantName);
			plant.setPlantId(Long.valueOf(propertyValue.toString()));
			plant.setPlantDescription(plantDescription.toString());
			plants.add(plant);
		}

		JsonArray plantsArray = new JsonArray();
		for (PlantEntity p : plants) {
			plantsArray.add(p.toJSONString());
		}
		return plantsArray;
	}

	public String getSpecificPlant(String id) {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES + "SELECT ?subject "
				+ "WHERE { ?subject rdf:type sedic:Plant . " + "?subject sedic:has_plant_id ?value ."
				+ "FILTER (STR(?value)= '" + id + "')" + "}";
		String response = "";
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this, sparqlQueryString);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		if (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response = soln.get("subject").toString();
			Individual plantResource = getOntModel().getResource(response).as(Individual.class);
			Property hasPlantId = getOntModel().getProperty(OntologyUtils.NS + "has_plant_id");
			Property hasPlantDescription = getOntModel().getProperty(OntologyUtils.NS + "plantDescription");
			RDFNode propertyValue = plantResource.getPropertyValue(hasPlantId);
			RDFNode plantDescription = plantResource.getPropertyValue(hasPlantDescription);
			PlantEntity plant = new PlantEntity();
			String plantName = plantResource.getURI().substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			plant.setPlantURI(plantResource.getURI());
			plant.setPlantName(plantName);
			plant.setPlantId(Long.valueOf(propertyValue.toString()));
			plant.setPlantDescription(plantDescription.toString());
			qexec.close();
			JsonObject plantJson = plant.toJSONString();
			return plantJson.toString();
		} else {
			JsonObject output = new JsonObject();
			output.put("error", "Plant not found!");
			return output.toString();
		}

	}

}
