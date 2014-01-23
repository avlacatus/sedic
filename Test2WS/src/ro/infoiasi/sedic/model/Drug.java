package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyUtils;
import ro.infoiasi.sedic.model.entity.DrugEntity;
import ro.infoiasi.sedic.model.entity.ParentEntity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class Drug extends EntityHelper {

	private static Drug sInstance;

	private Drug() {
		super();
	}

	public static Drug getInstance() {
		if (sInstance == null) {
			sInstance = new Drug();
		}
		return sInstance;
	}

	public JsonArray getAllDrugs() {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES + "SELECT DISTINCT ?subject WHERE " + "{"
				+ "?class rdfs:subClassOf* sedic:Chemicals_and_Drugs . " + "?subject a ?class . " + "} "
				+ "order by asc (?subject)";
		QueryExecution getIndividuals = OntologyUtils.getSPARQLQuery(this, sparqlQueryString);
		ResultSet results = getIndividuals.execSelect();

		List<DrugEntity> drugs = new ArrayList<DrugEntity>();

		Property hasDrugId = getOntModel().getProperty(OntologyUtils.NS + "has_adjuvant_id");

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			String drugIndividualURI = soln.get("subject").toString();
			Individual drugResource = getOntModel().getResource(drugIndividualURI).as(Individual.class);
			Long drugIndividuaID = Long.valueOf(drugResource.getPropertyValue(hasDrugId).toString());
			String drugName = drugResource.getURI().substring(OntologyUtils.NS.length()).replaceAll("_", " ");

			DrugEntity drug = new DrugEntity();
			drug.setDrugURI(drugResource.getURI());
			drug.setDrugName(drugName);
			drug.setDrugId(drugIndividuaID);

			StringBuffer selectSubClassesQuery = new StringBuffer(OntologyUtils.SPARQL_PREFIXES);
			selectSubClassesQuery.append("SELECT DISTINCT ?subject (str(?id) as ?strId) WHERE { ");
			selectSubClassesQuery.append("{ ?subject rdfs:subClassOf <" + drugIndividualURI + "> . ");
			selectSubClassesQuery.append("?subject sedic:has_adjuvant_id ?id } ");
			selectSubClassesQuery.append("UNION ");
			selectSubClassesQuery.append("{ ?subject a <"  + drugIndividualURI + "> . ");
			selectSubClassesQuery.append("?subject sedic:has_adjuvant_id ?id } }");
			
			QueryExecution selectChildrenExec = OntologyUtils.getSPARQLQuery(this, selectSubClassesQuery.toString());
			ResultSet childrenSet = selectChildrenExec.execSelect();
			
			ArrayList<ParentEntity> parents = new ArrayList<ParentEntity>();
			while (childrenSet.hasNext()) {
				QuerySolution childrenRow = childrenSet.nextSolution();
				String childrenURI = childrenRow.get("subject").toString();
				Long childrenID = Long.valueOf(childrenRow.get("strId").toString()); 
				ParentEntity childrenEntity = new ParentEntity(childrenID, childrenURI);
				if (!childrenURI.equals(drugIndividualURI)) {
					parents.add(childrenEntity);
				}
			}
			drug.setParents(parents);
			selectChildrenExec.close();
			
			drugs.add(drug);
		}
		getIndividuals.close();
		JsonArray drugsArray = new JsonArray();
		for (DrugEntity d : drugs) {
			drugsArray.add(d.toJSONString());
		}
		return drugsArray;

	}

	public JsonObject getDrug(String drugId) {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES + "SELECT DISTINCT ?subject ?class ?id WHERE " + "{"
				+ "?class rdfs:subClassOf* sedic:Chemicals_and_Drugs . " + "?subject a ?class . "
				+ "?subject sedic:has_adjuvant_id ?value ." + "?class sedic:has_adjuvant_id ?id "
				+ "FILTER (STR(?value)= '" + drugId + "')" + "} ";
		String response = "";
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this, sparqlQueryString);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		if (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			response = soln.get("subject").toString();
			Individual drugResource = getOntModel().getResource(response).as(Individual.class);
			Property hasDrugId = getOntModel().getProperty(OntologyUtils.NS + "has_adjuvant_id");
			RDFNode propertyValue = drugResource.getPropertyValue(hasDrugId);
			DrugEntity drug = new DrugEntity();
			String drugName = drugResource.getURI().substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			drug.setDrugURI(drugResource.getURI());
			drug.setDrugName(drugName);
			drug.setDrugId(Long.valueOf(propertyValue.toString()));
			String parent = soln.get("class").toString();
			ArrayList<ParentEntity> parents = new ArrayList<ParentEntity>();
			ParentEntity parentEntity = new ParentEntity();
			String id = soln.get("id").toString();
			parentEntity.setParentURI(parent);
			parentEntity.setParentId(Long.valueOf(id));
			parents.add(parentEntity);
			while (results.hasNext()) {
				soln = results.nextSolution();
				parent = soln.get("class").toString();
				parentEntity = new ParentEntity();
				id = soln.get("id").toString();
				parentEntity.setParentURI(parent);
				parentEntity.setParentId(Long.valueOf(id));
				parents.add(parentEntity);
			}
			drug.setParents(parents);
			qexec.close();
			JsonObject drugJson = drug.toJSONString();
			return drugJson;
		} else {
			JsonObject jo = new JsonObject();
			jo.put("Error", "Drug not found!");
			return jo;
		}
	}

}
