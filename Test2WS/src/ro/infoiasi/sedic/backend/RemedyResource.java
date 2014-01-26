package ro.infoiasi.sedic.backend;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.jena.atlas.json.JSON;
import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.atlas.json.JsonValue;

import ro.infoiasi.sedic.OntologyUtils;
import ro.infoiasi.sedic.URLConstants;
import ro.infoiasi.sedic.model.RemedyHelper;
import ro.infoiasi.sedic.model.entity.DiseaseEntity;
import ro.infoiasi.sedic.model.entity.MedicalConditionEntity;
import ro.infoiasi.sedic.model.entity.MedicalFactorEntity;

@Path("/remedy")
public class RemedyResource {
	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecificRemedy(@QueryParam(URLConstants.PARAM_REMEDY_ID) String id) {
		OntologyUtils.initSedicPath(context);
		RemedyHelper remedy = RemedyHelper.getInstance();
		if (id != null) {
			return remedy.getSpecificRemedy(id);
		} else {
			return getCompactRemedies(remedy);
		}

	}

	private String getCompactRemedies(RemedyHelper remedy) {
		JsonArray response = remedy.getCompactRemedies();
		JsonObject output = new JsonObject();
		output.put("remedies", response);
		return output.toString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String performRemedySearch(String payload) {
		OntologyUtils.initSedicPath(context);
		if (payload == null || payload.isEmpty()) {
			JsonObject output = new JsonObject();
			output.put("Error", "Payload empty");
			return output.toString();
		}

		JsonObject jsonPayload = null;
		try {
			jsonPayload = JSON.parse(payload);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		if (jsonPayload == null)
		{
			JsonObject output = new JsonObject();
			output.put("Error", "Payload mallformed");
			return output.toString();
		}
		ArrayList<String> adjuvantEffects = new ArrayList<String>();
		ArrayList<String> therapeuticalEffects = new ArrayList<String>();
		boolean emptyAdjuvants = true;
		if (jsonPayload.hasKey("adjuvant_effect")) {
			JsonValue adjuvant = jsonPayload.get("adjuvant_effect");
			JsonArray array = adjuvant.getAsArray();
			if (array.size() > 0) {
				emptyAdjuvants = false;
			}
			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = (JsonObject) array.get(i);
				String uri = obj.get("uri").toString();
				String adjuvantUri = "<" + uri.replace('"', '>').substring(1);
				adjuvantEffects.add(adjuvantUri);
			}
		}
		if (jsonPayload.hasKey("therapeutical_effect")) {
			JsonValue therapeutical = jsonPayload.get("therapeutical_effect");
			JsonArray array = therapeutical.getAsArray();
			if (array.size() > 0) {
				emptyAdjuvants = false;
			}
			for (int i = 0; i < array.size(); i++) {
				JsonObject obj = (JsonObject) array.get(i);
				String uri = obj.get("uri").toString();
				String thUri = "<" + uri.replace('"', '>').substring(1);
				therapeuticalEffects.add(thUri);
			}
		}

		if (emptyAdjuvants) {
			JsonObject output = new JsonObject();
			output.put("Error", "Adjuvants & Therapeutical effects cannot be empty.");
			return output.toString();
		}
		MedicalConditionEntity medicalCondition = new MedicalConditionEntity();
		if (jsonPayload.hasKey("medical_condition")) {
			JsonValue medical = jsonPayload.get("medical_condition");
			JsonObject medicalConditionObject = medical.getAsObject();
			if (medicalConditionObject.hasKey("age")) {
				JsonValue minAge = medicalConditionObject.get("age");
				medicalCondition.setMedicalConditionMinAge(Integer.valueOf(minAge.toString()));

			}
			if (medicalConditionObject.hasKey("medical_factors")) {
				JsonValue factors = medicalConditionObject.get("medical_factors");
				JsonArray factorsArray = factors.getAsArray();
				List<MedicalFactorEntity> medicalFactors = new ArrayList<MedicalFactorEntity>();
				for (int i = 0; i < factorsArray.size(); i++) {
					JsonObject obj = (JsonObject) factorsArray.get(i);
					String uri = obj.get("uri").toString();
					String mUri = "<" + uri.replace('"', '>').substring(1);
					String id = obj.get("id").toString();
					MedicalFactorEntity factor = new MedicalFactorEntity();
					factor.setMedicalFactorId(Long.valueOf(id));
					factor.setMedicalFactorURI(mUri);
					medicalFactors.add(factor);
				}
				medicalCondition.setMedicalFactors(medicalFactors);
			}
			if (medicalConditionObject.hasKey("diseases")) {
				JsonValue diseases = medicalConditionObject.get("diseases");
				JsonArray diseasesArray = diseases.getAsArray();
				List<DiseaseEntity> medicalConditionDiseases = new ArrayList<DiseaseEntity>();
				for (int i = 0; i < diseasesArray.size(); i++) {
					JsonObject obj = (JsonObject) diseasesArray.get(i);
					String uri = obj.get("uri").toString();
					String mUri = "<" + uri.replace('"', '>').substring(1);
					String id = obj.get("id").toString();
					DiseaseEntity disease = new DiseaseEntity();
					disease.setDiseaseId(Long.valueOf(id));
					disease.setDiseaseURI(mUri);
					medicalConditionDiseases.add(disease);
				}
				medicalCondition.setDiseases(medicalConditionDiseases);
			}

		}
		JsonArray response = getQueryResult(adjuvantEffects, therapeuticalEffects, medicalCondition);
		JsonObject output = new JsonObject();
		output.put("remedies", response);
		return output.toString();

	}

	private JsonArray getQueryResult(ArrayList<String> adjuvantEffects, ArrayList<String> therapeuticalEffects,
			MedicalConditionEntity medicalCondition) {
		RemedyHelper remedy = RemedyHelper.getInstance();
		return remedy.getQueryResults(adjuvantEffects, therapeuticalEffects, medicalCondition);

	}
}
