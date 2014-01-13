package ro.infoiasi.sedic.model;

import java.io.InputStream;


import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class Plant {
	Model m;
	public Plant()
	{
		String inputFileName = "D:\\facultate\\sedic\\sedic.owl";
		m = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		m.read(in, null);
		m.write(System.out, "Turtle");
		
	
	}
	public static void main(String[] args) {
		String inputFileName = "D:\\facultate\\sedic\\sedic.owl";
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		//m.read(in, null);
		//m.write(System.out, "Turtle");
		
	
	}
public String getPlantName()
{

	String resp = m.write(System.out, "Turtle").toString();
	return resp;
}
public String getPlantQuery()
{
	
	// m.write(System.out, "Turtle");
	String sparqlQueryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
"PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
"SELECT ?subject ?object " +
	"WHERE { ?subject rdfs:subClassOf ?object }";
	String response = "";
	 Query query = QueryFactory.create(sparqlQueryString);
	 ARQ.getContext().setTrue(ARQ.useSAX);
	 QueryExecution qexec = QueryExecutionFactory.create(query, m);
	 com.hp.hpl.jena.query.ResultSet results =  qexec.execSelect();
	
	 while ( results.hasNext()) {
	 QuerySolution soln =  results.nextSolution();
	// System.out.println(soln.get("subject"));
	 response += soln.get("subject").toString();
	 }
	 qexec.close();
	 return response;
}

}

