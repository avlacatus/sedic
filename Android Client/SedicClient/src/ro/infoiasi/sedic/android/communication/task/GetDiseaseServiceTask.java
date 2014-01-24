package ro.infoiasi.sedic.android.communication.task;

import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.communication.event.GetDiseasesEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.util.JSONHelper;
import ro.infoiasi.sedic.android.util.URLConstants;

public class GetDiseaseServiceTask extends ServiceTask<DiseaseBean> {

	@SuppressWarnings("unused")
	private static final String tag = GetDiseaseServiceTask.class.getSimpleName();
	private long diseaseId = -1;

	public GetDiseaseServiceTask() {
	}

	public GetDiseaseServiceTask(long diseaseId) {
		this.diseaseId = diseaseId;
	}

	@Override
	public HttpRequestMethod getHttpRequestMethod() {
		return HttpRequestMethod.GET;
	}

	@Override
	public String getURL() {
		if (diseaseId != -1) {
			return URLConstants.URL_RESOURCE_DISEASE + "?diseaseID=" + diseaseId;
		} else {
			return URLConstants.URL_RESOURCE_DISEASE;
		}
	}

	@Override
	public void prepareRequest(HttpRequestBase request) {
	}

	@Override
	public Response<DiseaseBean> parseResponse(String response) {
		Map<Long, DiseaseBean> outputMap = null;
		try {
			JSONObject jsonResponse = new JSONObject(response);
			outputMap = JSONHelper.parseDiseaseArray(jsonResponse.getJSONArray("diseases").toString());
		} catch (JSONException e) {

		}

		if (outputMap != null) {
			SedicApplication.getInstance().setDiseases(outputMap);
			return new Response<DiseaseBean>(this, Response.ResponseStatus.OK, outputMap);
		} else {
			Response<DiseaseBean> output = new Response<DiseaseBean>(this, Response.ResponseStatus.FAILED);
			output.setErrorMessage("Could not parse json response");
			return output;
		}
	}

	@Override
	public ResponseEvent getEvent(Response<DiseaseBean> response) {
		return new GetDiseasesEvent(response);
	}

}
