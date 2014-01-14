package ro.infoiasi.sedic.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import ro.infoiasi.sedic.URLConstants;


public class Plant {
	Model m;
	String inputFileName;
	public static final String NS = "http://www.infoiasi.ro/wad/ontologies/sedic#";

	public Plant() {
	 inputFileName = URLConstants.SEDIC_PATH + "sedic.owl";
		m = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		m.read(in, null);
		//m.write(System.out, "Turtle");

	}

	

	public String getPlantName() {

		String resp = m.write(System.out, "Turtle").toString();
		return resp;
	}

	public JsonArray getPlantQuery() {

		 OntModel om = createModelFromFile(inputFileName);
	        OntClass plantClass = om.getResource(NS + "Plant").as(OntClass.class);
	        Property hasPlantId = om.getProperty(NS + "has_plant_id");
	        ExtendedIterator<? extends OntResource> listInstances = plantClass.listInstances();
	        List<PlantEntity> plants = new ArrayList<PlantEntity>();
	        while (listInstances.hasNext()) {
	            Individual plantResource = (Individual) listInstances.next();
	            RDFNode propertyValue = plantResource.getPropertyValue(hasPlantId);
	            PlantEntity plant = new PlantEntity();
	            String plantName = plantResource.getURI().substring(NS.length()).replaceAll("_", " ");
	            plant.setPlantURI(plantResource.getURI());
	            plant.setPlantName(plantName);
	            plant.setPlantId(Long.valueOf(propertyValue.toString()));
	            plants.add(plant);
	            
	        }
	        
	        JsonObject output = new JsonObject();
	        JsonArray plantsArray = new JsonArray();
	        for (PlantEntity p : plants) {
	            plantsArray.add(p.toJSONString());
	        }
	        output.put("plants", plantsArray);
	        log(output);
	        return plantsArray;
	}
	public String getSpecificPlant(String id)
	{
//		String sparqlQueryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
//"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
//"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
//"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
//"PREFIX sedic: <http://www.infoiasi.ro/wad/ontologies/sedic#> " +
//"SELECT ?subject "+
//"	WHERE { ?subject rdf:type sedic:Plant  . "+
//"?subject sedic:has_plant_id  " + id  +
//"}";
	String response = "";
//		Query query = QueryFactory.create(sparqlQueryString);
//		ARQ.getContext().setTrue(ARQ.useSAX);
//		QueryExecution qexec = QueryExecutionFactory.create(query, m);
//		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
//
//		while (results.hasNext()) {
//			QuerySolution soln = results.nextSolution();
//			// System.out.println(soln.get("subject"));
//			response += soln.get("subject").toString();
//		}
//		qexec.close();
		
		
		return response;
	}
	private static void log(Object obj) {
        System.out.println(obj.toString());
    }
	
   

    private static OntModel createModelFromFile(String filename) {
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(filename);
        if (in == null) {
            throw new IllegalArgumentException("File: " + filename + " not found");
        }

        model.read(in, null);
        OntModel om = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
        return om;
    }

}
