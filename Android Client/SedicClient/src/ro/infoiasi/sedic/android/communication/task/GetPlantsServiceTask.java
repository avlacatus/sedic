package ro.infoiasi.sedic.android.communication.task;

import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.communication.event.GetPlantsEvent;
import ro.infoiasi.sedic.android.communication.task.Response.ResponseStatus;
import ro.infoiasi.sedic.android.model.PlantBean;
import ro.infoiasi.sedic.android.util.JSONHelper;
import ro.infoiasi.sedic.android.util.URLConstants;
import de.greenrobot.event.EventBus;

public class GetPlantsServiceTask extends ServiceTask<PlantBean> {

	@SuppressWarnings("unused")
	private static final String tag = GetPlantsServiceTask.class.getSimpleName();

	public GetPlantsServiceTask() {

	}

	@Override
	protected void onPostExecute(Response<PlantBean> result) {
		super.onPostExecute(result);
		EventBus.getDefault().post(new GetPlantsEvent(result));
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
		List<PlantBean> output = null;
		try {
			JSONObject jsonResponse = new JSONObject(response);
			output = JSONHelper.buildPlantsArray(jsonResponse.getJSONArray("plants").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (output != null) {
			return new Response<PlantBean>(this, ResponseStatus.OK, output);
		} else {
			return new Response<PlantBean>(this, ResponseStatus.FAILED);
		}
	}

}
