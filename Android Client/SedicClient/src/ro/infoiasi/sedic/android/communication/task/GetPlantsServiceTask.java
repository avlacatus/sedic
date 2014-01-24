package ro.infoiasi.sedic.android.communication.task;

import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.communication.event.GetPlantsEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.communication.task.Response.ResponseStatus;
import ro.infoiasi.sedic.android.model.PlantBean;
import ro.infoiasi.sedic.android.util.JSONHelper;
import ro.infoiasi.sedic.android.util.URLConstants;

public class GetPlantsServiceTask extends ServiceTask<PlantBean> {

	@SuppressWarnings("unused")
	private static final String tag = GetPlantsServiceTask.class.getSimpleName();

	public GetPlantsServiceTask() {

	}

	@Override
	protected void onPostExecute(Response<PlantBean> result) {
		super.onPostExecute(result);
	}

	@Override
	public HttpRequestMethod getHttpRequestMethod() {
		return HttpRequestMethod.GET;
	}

	@Override
	public String getURL() {
		return URLConstants.URL_RESOURCE_PLANT;
	}

	@Override
	public void prepareRequest(HttpRequestBase request) {
	}

	@Override
	public Response<PlantBean> parseResponse(String response) {
		Map<Long, PlantBean> output = null;
		try {
			JSONObject jsonResponse = new JSONObject(response);
			output = JSONHelper.parsePlantsArray(jsonResponse.getJSONArray("plants").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (output != null) {
			SedicApplication.getInstance().setPlants(output);
			return new Response<PlantBean>(this, ResponseStatus.OK, output);
		} else {
			return new Response<PlantBean>(this, ResponseStatus.FAILED);
		}
	}

	@Override
	public ResponseEvent getEvent(Response<PlantBean> response) {
		return new GetPlantsEvent(response);
	}

}
