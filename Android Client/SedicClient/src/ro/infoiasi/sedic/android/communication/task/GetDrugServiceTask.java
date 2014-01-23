package ro.infoiasi.sedic.android.communication.task;

import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.communication.event.GetDrugsEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.model.DrugBean;
import ro.infoiasi.sedic.android.util.JSONHelper;
import ro.infoiasi.sedic.android.util.URLConstants;

public class GetDrugServiceTask extends ServiceTask<DrugBean> {

	@SuppressWarnings("unused")
	private static final String tag = GetDrugServiceTask.class.getSimpleName();
	private long drugId = -1;

	public GetDrugServiceTask() {
	}

	public GetDrugServiceTask(long drugId) {
		this.drugId = drugId;
	}

	@Override
	public HttpRequestMethod getHttpRequestMethod() {
		return HttpRequestMethod.GET;
	}

	@Override
	public String getURL() {
		if (drugId != -1) {
			return URLConstants.URL_RESOURCE_DRUG + "?drugID=" + drugId;
		} else {
			return URLConstants.URL_RESOURCE_DRUG;
		}
	}

	@Override
	public void prepareRequest(HttpRequestBase request) {
	}

	@Override
	public Response<DrugBean> parseResponse(String response) {
		Map<Long, DrugBean> outputMap = null;
		try {
			JSONObject jsonResponse = new JSONObject(response);
			outputMap = JSONHelper.parseDrugArray(jsonResponse.getJSONArray("drugs").toString());
		} catch (JSONException e) {

		}

		if (outputMap != null) {
			SedicApplication.getInstance().setDrugs(outputMap);
			return new Response<DrugBean>(this, Response.ResponseStatus.OK, outputMap);
		} else {
			Response<DrugBean> output = new Response<DrugBean>(this, Response.ResponseStatus.FAILED);
			output.setErrorMessage("Could not parse json response");
			return output;
		}
	}

	@Override
	public ResponseEvent getEvent(Response<DrugBean> response) {
		return new GetDrugsEvent(response);
	}

}
