package ro.infoiasi.sedic.android.communication.task;

import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.communication.event.GetMedicalConditionEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.model.MedicalFactorBean;
import ro.infoiasi.sedic.android.util.JSONHelper;
import ro.infoiasi.sedic.android.util.URLConstants;

public class GetMedicalConditionServiceTask extends ServiceTask<MedicalFactorBean> {

	@SuppressWarnings("unused")
	private static final String tag = GetMedicalConditionServiceTask.class.getSimpleName();

	public GetMedicalConditionServiceTask() {
	}

	@Override
	public HttpRequestMethod getHttpRequestMethod() {
		return HttpRequestMethod.GET;
	}

	@Override
	public String getURL() {
		return URLConstants.URL_RESOURCE_MEDICAL_CONDITION;
	}

	@Override
	public void prepareRequest(HttpRequestBase request) {
	}

	@Override
	public Response<MedicalFactorBean> parseResponse(String response) {
		Map<Long, DiseaseBean> contraindicatedDiseases = null;
		Map<Long, MedicalFactorBean> medicalFactors = null;
		try {
			JSONObject jsonResponse = new JSONObject(response);
			if (jsonResponse.has("contraindicated_diseases")) {
				contraindicatedDiseases = JSONHelper.parseDiseaseArray(jsonResponse.getJSONArray(
						"contraindicated_diseases").toString());
			}

			if (jsonResponse.has("medical_factors")) {
				medicalFactors = JSONHelper.parseMedicalFactorArray(jsonResponse.getJSONArray("medical_factors"));
			}
		} catch (JSONException e) {

		}

		if (contraindicatedDiseases != null && medicalFactors != null) {
			SedicApplication.getInstance().setContraindicatedDiseases(contraindicatedDiseases);
			SedicApplication.getInstance().setMedicalFactors(medicalFactors);
			return new Response<MedicalFactorBean>(this, Response.ResponseStatus.OK, medicalFactors);
		} else {
			Response<MedicalFactorBean> output = new Response<MedicalFactorBean>(this, Response.ResponseStatus.FAILED);
			output.setErrorMessage("Could not parse json response");
			return output;
		}
	}

	@Override
	public ResponseEvent getEvent(Response<MedicalFactorBean> response) {
		return new GetMedicalConditionEvent(response);
	}

}
