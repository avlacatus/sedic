package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyUtils;
import ro.infoiasi.sedic.model.entity.DiseaseEntity;
import ro.infoiasi.sedic.model.entity.ParentEntity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

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
		StringBuffer selectSubClassesQuery = new StringBuffer(
				OntologyUtils.SPARQL_PREFIXES);
		selectSubClassesQuery
				.append("SELECT distinct ?class ?subclass (str(?id) as ?strId) where {  ");
		selectSubClassesQuery
				.append("{ ?class rdfs:subClassOf* sedic:Diseases .   ");
		selectSubClassesQuery.append("?subclass rdfs:subClassOf ?class . ");
		selectSubClassesQuery.append("?subclass sedic:has_disease_id ?id  }");
		selectSubClassesQuery.append("UNION ");
		selectSubClassesQuery
				.append("{?class rdfs:subClassOf* sedic:Diseases .  ");
		selectSubClassesQuery.append("?subclass a ?class .  ");
		selectSubClassesQuery.append("?subclass sedic:has_disease_id ?id  } }");
		selectSubClassesQuery.append("order by asc(?class)  ");
		QueryExecution query = OntologyUtils.getSPARQLQuery(this,
				selectSubClassesQuery.toString());
		ResultSet results = query.execSelect();
		String response = "";
		List<DiseaseEntity> diseases = new ArrayList<DiseaseEntity>();
		List<String> diseaseNames = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			response = soln.get("class").toString();
			Individual diseaseResource = getOntModel().getResource(response)
					.as(Individual.class);
			Property hasDiseaseId = getOntModel().getProperty(
					OntologyUtils.NS + "has_disease_id");
			RDFNode propertyValue = diseaseResource
					.getPropertyValue(hasDiseaseId);
			DiseaseEntity disease = new DiseaseEntity();
			String diseaseName = diseaseResource.getURI()
					.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			disease.setDiseaseURI(diseaseResource.getURI());
			disease.setDiseaseName(diseaseName);
			disease.setDiseaseId(Long.valueOf(propertyValue.toString()));
			if (diseaseNames.contains(diseaseName)) {

				String parent = soln.get("subclass").toString();
				DiseaseEntity diseaseEntity = new DiseaseEntity();
				for (DiseaseEntity d : diseases) {

					if (d.getDiseaseName().equals(diseaseName)) {
						diseaseEntity = d;
						break;
					}
				}
				// System.out.println("name=" +diseaseEntity.getDiseaseName());
				ArrayList<ParentEntity> parents = diseaseEntity.getParents();
				ParentEntity parentEntity = new ParentEntity();
				String id = soln.get("strId").toString();
				parentEntity.setParentURI(parent);
				parentEntity.setParentId(Long.valueOf(id));
				if (!parent.equals(diseaseResource.getURI()))
				parents.add(parentEntity);
				disease.setParents(parents);
			} else {
				diseaseNames.add(diseaseName);
				String parent = soln.get("subclass").toString();
				ArrayList<ParentEntity> parents = new ArrayList<ParentEntity>();
				ParentEntity parentEntity = new ParentEntity();
				String id = soln.get("strId").toString();
				parentEntity.setParentURI(parent);
				parentEntity.setParentId(Long.valueOf(id));
				if (!parent.equals(diseaseResource.getURI()))
				parents.add(parentEntity);
				disease.setParents(parents);
				diseases.add(disease);
			}
		}
		query.close();
		JsonArray diseaseArray = new JsonArray();
		for (DiseaseEntity d : diseases) {
			diseaseArray.add(d.toJSONString());
		}
		// JsonObject drugJson = drug.toJSONString();
		return diseaseArray;

	}

	public JsonObject getSpecificDisease(String Id) {

		StringBuffer selectSubClassesQuery = new StringBuffer(OntologyUtils.SPARQL_PREFIXES);
		selectSubClassesQuery.append("SELECT DISTINCT ?class ?subclass (str(?id) as ?strId) where { ");
		selectSubClassesQuery.append("{ ?class rdfs:subClassOf* sedic:Diseases .  ");
		selectSubClassesQuery.append("?subclass rdfs:subClassOf ?class . ");
		selectSubClassesQuery.append("?subclass sedic:has_disease_id ?id .");
		selectSubClassesQuery.append("?class  sedic:has_disease_id ?idclass .");
		selectSubClassesQuery.append("FILTER (STR(?idclass)= '" + Id + "') }");
		selectSubClassesQuery.append("UNION ");
		selectSubClassesQuery.append("{?class rdfs:subClassOf* sedic:Diseases .  ");
		selectSubClassesQuery.append("?subclass a ?class . ");
		selectSubClassesQuery.append("?subclass sedic:has_disease_id ?id .");
		selectSubClassesQuery.append("?class  sedic:has_disease_id ?idclass .");
		selectSubClassesQuery.append("FILTER (STR(?idclass)= '" + Id + "')" + "} }");
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this,
				selectSubClassesQuery.toString());
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		// System.out.println(sparqlQueryString);
		System.out.println("results:" + results.getRowNumber());

		if (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			String response = soln.get("class").toString();
			System.out.println(response.toString());
			Individual diseaseResource = getOntModel().getResource(response)
					.as(Individual.class);
			Property hasDiseaseId = getOntModel().getProperty(
					OntologyUtils.NS + "has_disease_id");
			RDFNode propertyValue = diseaseResource
					.getPropertyValue(hasDiseaseId);

			DiseaseEntity disease = new DiseaseEntity();
			String diseaseName = diseaseResource.getURI()
					.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			disease.setDiseaseURI(diseaseResource.getURI());
			disease.setDiseaseName(diseaseName);
			disease.setDiseaseId(Long.valueOf(propertyValue.toString()));
			String parent = soln.get("subclass").toString();
			ArrayList<ParentEntity> parents = new ArrayList<ParentEntity>();
			ParentEntity parentEntity = new ParentEntity();
			String id = soln.get("strId").toString();
			parentEntity.setParentURI(parent);
			parentEntity.setParentId(Long.valueOf(id));
			if (!parent.equals(diseaseResource.getURI()))
			parents.add(parentEntity);
			while (results.hasNext()) {
				soln = results.nextSolution();
				parent = soln.get("subclass").toString();
				parentEntity = new ParentEntity();
				id = soln.get("strId").toString();
				parentEntity.setParentURI(parent);
				parentEntity.setParentId(Long.valueOf(id));
				if (!parent.equals(diseaseResource.getURI()))
				parents.add(parentEntity);
			}
			disease.setParents(parents);
			qexec.close();
			return disease.toJSONString();
		} else {
			JsonObject jo = new JsonObject();
			jo.put("Error", "Disease not found!");
			return jo;
		}
	}

}
