package ro.infoiasi.sedic.model;

import java.io.InputStream;


import javax.ws.rs.core.Context;

import ro.infoiasi.sedic.URLConstants;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class Disease {
	Model m;


	public Disease(String inputFileNam) {
	String inputFileName = URLConstants.SEDIC_PATH + "sedic.owl";
		m = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		m.read(in, null);
		// m.write(System.out, "Turtle");

	}

	public static void main(String[] args) {
		String inputFileName = URLConstants.SEDIC_PATH + "sedic.owl";
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		// m.read(in, null);
		// m.write(System.out, "Turtle");

	}

	public String getAllDiseases() {

		
		String sparqlQueryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX sedic: <http://www.infoiasi.ro/wad/ontologies/sedic#> "
				+ "SELECT ?subject "
				+ "	WHERE { ?subject rdfs:subClassOf sedic:Diseases }";
		String response = "";
		Query query = QueryFactory.create(sparqlQueryString);
		ARQ.getContext().setTrue(ARQ.useSAX);
		QueryExecution qexec = QueryExecutionFactory.create(query, m);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response += soln.get("subject").toString().substring(44) + "\n";
		}
		qexec.close();
		return response;
	}

}
