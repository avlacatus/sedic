package ro.infoiasi.sedic.model;

import java.util.ArrayList;

import org.apache.jena.atlas.json.JsonArray;

import ro.infoiasi.sedic.OntologyUtils;
import ro.infoiasi.sedic.model.entity.DiseaseEntity;
import ro.infoiasi.sedic.model.entity.MedicalFactorEntity;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class MedicalConditionHelper extends EntityHelper {

	private static MedicalConditionHelper sInstance;

	private MedicalConditionHelper() {
		super();
	}

	public static MedicalConditionHelper getInstance() {
		if (sInstance == null) {
			sInstance = new MedicalConditionHelper();
		}
		return sInstance;
	}

	public JsonArray getMedicalFactorsArray() {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES 
				+ " SELECT distinct ?object WHERE { "
			+ "?subject sedic:has_medical_factor ?object } ";
		String response = "";
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this, sparqlQueryString);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		ArrayList <MedicalFactorEntity> medicalFactors = new ArrayList<MedicalFactorEntity>();
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response = soln.get("object").toString();
			Individual factorResource = getOntModel().getResource(response).as(Individual.class);
			Property hasId = getOntModel().getProperty(OntologyUtils.NS + "has_medical_factor_id");
			RDFNode propertyValue = factorResource.getPropertyValue(hasId);
			MedicalFactorEntity mf = new MedicalFactorEntity();
			String factorName = factorResource.getURI().substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			mf.setMedicalFactorURI(factorResource.getURI());
			mf.setMedicalFactorName(factorName);
			mf.setMedicalFactorId(Long.valueOf(propertyValue.toString()));
			medicalFactors.add(mf);
		}
			qexec.close();
			

		JsonArray factorsArray = new JsonArray();
		for (MedicalFactorEntity f : medicalFactors) {
			factorsArray.add(f.toJSONString());
		}
		return factorsArray;
	}

	public JsonArray getDiseasesArray() {
		String sparqlQueryString = OntologyUtils.SPARQL_PREFIXES 
				+ " SELECT distinct ?object WHERE { "
			+ "?subject sedic:is_affected_by ?object } ";
		String response = "";
		QueryExecution qexec = OntologyUtils.getSPARQLQuery(this, sparqlQueryString);
		com.hp.hpl.jena.query.ResultSet results = qexec.execSelect();
		ArrayList <DiseaseEntity> diseases = new ArrayList<DiseaseEntity>();
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			response = soln.get("object").toString();
			Individual factorResource = getOntModel().getResource(response).as(Individual.class);
			Property hasId = getOntModel().getProperty(OntologyUtils.NS + "has_disease_id");
			RDFNode propertyValue = factorResource.getPropertyValue(hasId);
			DiseaseEntity disease = new DiseaseEntity();
			String diseaseName = factorResource.getURI().substring(OntologyUtils.NS.length()).replaceAll("_", " ");
			disease.setDiseaseURI(factorResource.getURI());
			disease.setDiseaseName(diseaseName);
			disease.setDiseaseId(Long.valueOf(propertyValue.toString()));
			diseases.add(disease);
		}
			qexec.close();
			

		JsonArray diseasesArray = new JsonArray();
		for (DiseaseEntity d : diseases) {
			diseasesArray.add(d.toJSONString());
		}
		return diseasesArray;
	}

	

}