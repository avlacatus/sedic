package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyConstants;
import ro.infoiasi.sedic.model.entity.DrugEntity;

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

public class Drug extends EntityHelper {

	public Drug() {
		super();
	}

	public JsonArray getAllDrugs() {
		OntClass drugClass = getOntModel().getResource(
				OntologyConstants.NS + "Chemicals_and_Drugs")
				.as(OntClass.class);
		ExtendedIterator<OntClass> iterator = drugClass.listSubClasses();
		List<DrugEntity> drugs = new ArrayList<DrugEntity>();
		Property hasDiseaseId = getOntModel().getProperty(
				OntologyConstants.NS + "has_adjuvant_id");
		while (iterator.hasNext()) {
			OntClass myclass = iterator.next();
			ExtendedIterator<? extends OntResource> listInstances = myclass.listInstances();
			//System.out.println(myclass.getURI());
			ArrayList<String>parents = new ArrayList<String>();
			List<OntClass> superClasses = myclass.listSuperClasses().toList();
			for (OntClass o :superClasses)
			{
			String id = "property";
			parents.add(id);
			}
			while (listInstances.hasNext()) {
				Individual drugResource = (Individual) listInstances.next();
				RDFNode propertyValue = drugResource
						.getPropertyValue(hasDiseaseId);
				DrugEntity drug = new DrugEntity();
				String drugName = drugResource.getURI()
						.substring(OntologyConstants.NS.length())
						.replaceAll("_", " ");
				
				drug.setDrugURI(drugResource.getURI());
				drug.setDrugName(drugName);
				drug.setDrugId(Long.valueOf(propertyValue.toString()));
				drug.setParents(parents);
				drugs.add(drug);
			}
		}

		JsonArray drugsArray = new JsonArray();
		for (DrugEntity d : drugs) {
			drugsArray.add(d.toJSONString());
		}
		return drugsArray;
	}

	public String getDrug(String drugId) {

		String sparqlQueryString = OntologyConstants.SPARQL_PREFIXES
				+ "SELECT ?subject " + "	WHERE {   ?subject a ?class . "
				+ "?class  rdfs:subClassOf* sedic:Chemicals_and_Drugs ."
				+ "?subject sedic:has_adjuvant_id ?value ."
				+ "FILTER (STR(?value)= '" + drugId + "')" + "}";
		String response = "";
		Query query = QueryFactory.create(sparqlQueryString);
		ARQ.getContext().setTrue(ARQ.useSAX);
		QueryExecution qexec = QueryExecutionFactory.create(query,
				getOntModel());
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		if (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			response = soln.get("subject").toString();
			Individual drugResource = getOntModel().getResource(response).as(
					Individual.class);
			Property hasDrugId = getOntModel().getProperty(
					OntologyConstants.NS + "has_adjuvant_id");
			RDFNode propertyValue = drugResource.getPropertyValue(hasDrugId);
			DrugEntity drug = new DrugEntity();
			String drugName = drugResource.getURI()
					.substring(OntologyConstants.NS.length())
					.replaceAll("_", " ");
			drug.setDrugURI(drugResource.getURI());
			drug.setDrugName(drugName);
			drug.setDrugId(Long.valueOf(propertyValue.toString()));

			qexec.close();
			JsonObject drugJson = drug.toJSONString();
			return drugJson.toString();
		} else
			return "Drug not found!";

	}

}
