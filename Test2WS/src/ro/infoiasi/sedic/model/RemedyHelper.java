package ro.infoiasi.sedic.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import ro.infoiasi.sedic.OntologyUtils;
import ro.infoiasi.sedic.model.entity.DiseaseEntity;
import ro.infoiasi.sedic.model.entity.MedicalConditionEntity;
import ro.infoiasi.sedic.model.entity.MedicalFactorEntity;
import ro.infoiasi.sedic.model.entity.RemedyEntity;
import ro.infoiasi.sedic.model.entity.RemedyPropertyEntity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class RemedyHelper extends EntityHelper {

	private static RemedyHelper sInstance;

	private RemedyHelper() {
		super();
	}

	public static RemedyHelper getInstance() {
		if (sInstance == null) {
			sInstance = new RemedyHelper();
		}
		return sInstance;
	}

	public String getSpecificRemedy(String id) {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES
				+ "SELECT ?subject "
				+ "	WHERE { ?subject rdf:type sedic:Remedy . "
				+ "?subject sedic:has_remedy_id ?value ."
				+ "FILTER (STR(?value)= '" + id + "')" + "}";
		String response = "";
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this,
				sparqlQueryString);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		if (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response = soln.get("subject").toString();
			Individual remedyResource = getOntModel().getResource(response).as(
					Individual.class);
			Property hasRemedyId = getOntModel().getProperty(
					OntologyUtils.NS + "has_remedy_id");
			Property hasAdjuvantUsage = getOntModel().getProperty(
					OntologyUtils.NS + "hasAdjuvantUsage");
			Property hasTherapeuticalUsage = getOntModel().getProperty(
					OntologyUtils.NS + "hasTherapeuticalUsage");
			Property hasFrequentUsage = getOntModel().getProperty(
					OntologyUtils.NS + "hasFrequentUsage");
			Property hasReportedUsage = getOntModel().getProperty(
					OntologyUtils.NS + "hasReportedUsage");
			Property usesPlant = getOntModel().getProperty(
					OntologyUtils.NS + "usesPlant");
			Property hasPlantId = getOntModel().getProperty(
					OntologyUtils.NS + "has_plant_id");
			Property hasPlantPartUsage = getOntModel().getProperty(
					OntologyUtils.NS + "hasPlantPartUsage");
			Property hasAdjuvantId = getOntModel().getProperty(
					OntologyUtils.NS + "has_adjuvant_id");
			Property hasTherapeuticalId = getOntModel().getProperty(
					OntologyUtils.NS + "has_disease_id");
			Property hasMedicalCondition = getOntModel().getProperty(
					OntologyUtils.NS + "has_medical_condition");
			RDFNode propertyValue = remedyResource
					.getPropertyValue(hasRemedyId);
			RDFNode usesPlantValue = remedyResource.getPropertyValue(usesPlant);
			Individual plantResource = getOntModel().getResource(
					usesPlantValue.toString()).as(Individual.class);
			RDFNode hasPlantIdValue = plantResource
					.getPropertyValue(hasPlantId);

			// medical condition
			RDFNode hasMedicalConditionValue = remedyResource
					.getPropertyValue(hasMedicalCondition);
			Individual medicalConditionResource = getOntModel().getResource(
					hasMedicalConditionValue.toString()).as(Individual.class);
			Property hasMedicalConditionId = getOntModel().getProperty(
					OntologyUtils.NS + "has_medical_condition_id");
			Property hasMedicalConditionMinAge = getOntModel().getProperty(
					OntologyUtils.NS + "has_min_age");
			RDFNode hasMedicalConditionIdValue = medicalConditionResource
					.getPropertyValue(hasMedicalConditionId);
			RDFNode hasMinAgeValue = medicalConditionResource
					.getPropertyValue(hasMedicalConditionMinAge);
			MedicalConditionEntity medicalCondition = new MedicalConditionEntity();
			medicalCondition.setMedicalConditionId(Long
					.valueOf(hasMedicalConditionIdValue.toString()));
			medicalCondition.setMedicalConditionMinAge(Integer
					.valueOf(hasMinAgeValue.toString()));
			String medicalConditionName = medicalConditionResource.getURI()
					.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			medicalCondition.setMedicalConditionName(medicalConditionName);
			medicalCondition.setMedicalconditionURI(medicalConditionResource
					.getURI());

			RemedyEntity remedy = new RemedyEntity();
			String remedyName = remedyResource.getURI()
					.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			remedy.setRemedyURI(remedyResource.getURI());
			remedy.setRemedyName(remedyName);
			remedy.setRemedyId(Long.valueOf(propertyValue.toString()));
			remedy.setRemedyPlantId(Long.valueOf(hasPlantIdValue.toString()));
			remedy.setMedicalCondition(medicalCondition);
			NodeIterator it = remedyResource
					.listPropertyValues(hasAdjuvantUsage);
			while (it.hasNext()) {
				RDFNode property = it.next();
				Individual adjuvantResource = getOntModel().getResource(
						property.toString()).as(Individual.class);
				RDFNode hasAdjuvantIdValue = adjuvantResource
						.getPropertyValue(hasAdjuvantId);
				RemedyPropertyEntity adjuvant = new RemedyPropertyEntity();
				adjuvant.setName(property.toString()
						.substring(OntologyUtils.NS.length())
						.replaceAll("_", " "));
				adjuvant.setId(Long.valueOf(hasAdjuvantIdValue.toString()));
				remedy.addAdjuvantUsage(adjuvant);
			}
			it = remedyResource.listPropertyValues(hasPlantPartUsage);
			while (it.hasNext()) {
				RDFNode property = it.next();
				remedy.addPlantPartUsage(property.toString()
						.substring(OntologyUtils.NS.length())
						.replaceAll("_", " "));
			}
			it = remedyResource.listPropertyValues(hasTherapeuticalUsage);
			while (it.hasNext()) {
				RDFNode property = it.next();
				Individual therapeuticalResource = getOntModel().getResource(
						property.toString()).as(Individual.class);
				RDFNode hasTherapeuticalIdValue = therapeuticalResource
						.getPropertyValue(hasTherapeuticalId);
				RemedyPropertyEntity therapeutic = new RemedyPropertyEntity();
				therapeutic.setName(property.toString()
						.substring(OntologyUtils.NS.length())
						.replaceAll("_", " "));
				therapeutic.setId(Long.valueOf(hasTherapeuticalIdValue
						.toString()));
				remedy.addTherapeuticalUsage(therapeutic);
			}
			it = remedyResource.listPropertyValues(hasFrequentUsage);
			while (it.hasNext()) {
				RDFNode property = it.next();
				Individual frequentUsageResource = getOntModel().getResource(
						property.toString()).as(Individual.class);
				RDFNode hasFrequentIdValue;
				if (frequentUsageResource.hasProperty(hasAdjuvantId))
					hasFrequentIdValue = frequentUsageResource
							.getPropertyValue(hasAdjuvantId);
				else
					hasFrequentIdValue = frequentUsageResource
							.getPropertyValue(hasTherapeuticalId);
				RemedyPropertyEntity frequentUsage = new RemedyPropertyEntity();
				frequentUsage.setName(property.toString()
						.substring(OntologyUtils.NS.length())
						.replaceAll("_", " "));
				if (hasFrequentIdValue != null)
					frequentUsage.setId(Long.valueOf(hasFrequentIdValue
							.toString()));
				remedy.addFrequentUsage(frequentUsage);
			}
			it = remedyResource.listPropertyValues(hasReportedUsage);
			while (it.hasNext()) {
				RDFNode property = it.next();
				Individual frequentUsageResource = getOntModel().getResource(
						property.toString()).as(Individual.class);
				RDFNode hasFrequentIdValue;
				if (frequentUsageResource.hasProperty(hasAdjuvantId))
					hasFrequentIdValue = frequentUsageResource
							.getPropertyValue(hasAdjuvantId);
				else
					hasFrequentIdValue = frequentUsageResource
							.getPropertyValue(hasTherapeuticalId);
				RemedyPropertyEntity frequentUsage = new RemedyPropertyEntity();
				frequentUsage.setName(property.toString()
						.substring(OntologyUtils.NS.length())
						.replaceAll("_", " "));
				if (hasFrequentIdValue != null)
					frequentUsage.setId(Long.valueOf(hasFrequentIdValue
							.toString()));
				remedy.addReportedUsage(frequentUsage);
			}
			qexec.close();
			JsonObject remedyJson = remedy.toJSONString();

			return remedyJson.toString();
		} else {
			JsonObject output = new JsonObject();
			output.put("error", "Remedy not found!");
			return output.toString();
		}

	}

	public JsonArray getCompactRemedies() {
		OntClass remedyClass = getOntModel().getResource(
				OntologyUtils.NS + "Remedy").as(OntClass.class);
		Property hasRemedyId = getOntModel().getProperty(
				OntologyUtils.NS + "has_remedy_id");
		Property usesPlant = getOntModel().getProperty(
				OntologyUtils.NS + "usesPlant");
		Property hasPlantId = getOntModel().getProperty(
				OntologyUtils.NS + "has_plant_id");

		ExtendedIterator<? extends OntResource> listInstances = remedyClass
				.listInstances();
		List<RemedyEntity> remedies = new ArrayList<RemedyEntity>();
		while (listInstances.hasNext()) {
			Individual remedyResource = (Individual) listInstances.next();
			RDFNode hasRemedyIdValue = remedyResource
					.getPropertyValue(hasRemedyId);
			RDFNode usesPlantValue = remedyResource.getPropertyValue(usesPlant);
			Individual plantResource = getOntModel().getResource(
					usesPlantValue.toString()).as(Individual.class);
			RDFNode hasPlantIdValue = plantResource
					.getPropertyValue(hasPlantId);

			RemedyEntity remedy = new RemedyEntity();
			String remedyName = remedyResource.getURI()
					.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			remedy.setRemedyURI(remedyResource.getURI());
			remedy.setRemedyName(remedyName);
			remedy.setRemedyId(Long.valueOf(hasRemedyIdValue.toString()));
			remedy.setRemedyPlantId(Long.valueOf(hasPlantIdValue.toString()));
			remedies.add(remedy);
		}

		JsonArray remediesArray = new JsonArray();
		for (RemedyEntity remedy : remedies) {
			JsonObject remedyJson = remedy.toCompactJSONString();
			remediesArray.add(remedyJson);
		}
		return remediesArray;
	}

	public JsonArray getQueryResults(ArrayList<String> adjuvantEffects,
			ArrayList<String> therapeuticalEffects,
			MedicalConditionEntity medicalCondition) {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES
				+ "SELECT distinct ?subject "
				+ "	WHERE { ?subject rdf:type sedic:Remedy . ?subject sedic:has_medical_condition ?mc . ";
		int i = 0;
		for (String adjuvant : adjuvantEffects) {
			String individual = "?ind" + i;
			String clas = "?class" + i;
			sparqlQueryString += "{ ?subject sedic:hasAdjuvantUsage "
					+ individual + " . " + individual + " a  " + clas + " . "
					+ clas + " rdfs:subClassOf* " + adjuvant + " . } UNION { "
					+ "?subject sedic:hasAdjuvantUsage " + adjuvant + " . } .";
			i++;
		}
		for (String th : therapeuticalEffects) {
			String individual = "?ind" + i;
			String clas = "?class" + i;
			sparqlQueryString += "{ ?subject sedic:hasTherapeuticalUsage "
					+ individual + " . " + individual + " a  " + clas + " . "
					+ clas + " rdfs:subClassOf* " + th + " . } UNION { "
					+ "?subject sedic:hasTherapeuticalUsage " + th + " . } .";
			i++;
		}

		String filterString = "FILTER NOT EXISTS { ";
		ArrayList<String> filterConditions = new ArrayList<String>();
		if (medicalCondition.getMedicalConditionMinAge() > 0)
			filterConditions.add ("{?mc sedic:has_min_age ?value . filter(xsd:decimal(?value) > "
					+ medicalCondition.getMedicalConditionMinAge() + ")  }");
		for (MedicalFactorEntity factor : medicalCondition.getMedicalFactors())
		{
			filterConditions.add ("?mc sedic:has_medical_factor " + factor.getMedicalFactorURI() + ". }");
		}
		for (DiseaseEntity d :medicalCondition.getDiseases())
		{
			filterConditions.add ("?mc sedic:is_affected_by " + d.getDiseaseURI() + ". }");
		}
		if (filterConditions.size() >0)
		{
		for (int j=0; j< filterConditions.size(); j++)
		{
			String cond = filterConditions.get(j);
			filterString += cond ;
			if (j< filterConditions.size() -1)
				filterString += "UNION";
		}
		filterString  += "}";
		sparqlQueryString += filterString ;
		}
		sparqlQueryString += "}";
		String response = "";
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this,
				sparqlQueryString);
		System.out.println(sparqlQueryString);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		ArrayList<RemedyEntity> result = new ArrayList<RemedyEntity>();
		Property usesPlant = getOntModel().getProperty(
				OntologyUtils.NS + "usesPlant");
		Property hasPlantId = getOntModel().getProperty(
				OntologyUtils.NS + "has_plant_id");
		Property hasRemedyId = getOntModel().getProperty(
				OntologyUtils.NS + "has_remedy_id");
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response = soln.get("subject").toString();
			Individual remedyResource = getOntModel().getResource(response).as(
					Individual.class);
			String remedyName = remedyResource.getURI()
					.substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			RemedyEntity remedyEntity = new RemedyEntity();
			remedyEntity.setRemedyName(remedyName);
			RDFNode hasRemedyIdValue = remedyResource
					.getPropertyValue(hasRemedyId);
			RDFNode usesPlantValue = remedyResource.getPropertyValue(usesPlant);
			Individual plantResource = getOntModel().getResource(
					usesPlantValue.toString()).as(Individual.class);
			RDFNode hasPlantIdValue = plantResource
					.getPropertyValue(hasPlantId);
			remedyEntity.setRemedyURI(remedyResource.getURI());
			remedyEntity.setRemedyName(remedyName);
			remedyEntity.setRemedyId(Long.valueOf(hasRemedyIdValue.toString()));
			remedyEntity.setRemedyPlantId(Long.valueOf(hasPlantIdValue
					.toString()));
			result.add(remedyEntity);
		}
		qexec.close();
		JsonArray resultArray = new JsonArray();
		for (RemedyEntity r : result) {
			System.out.println(r.toString());
			resultArray.add(r.toCompactJSONString());
		}
		return resultArray;

	}
}
