package ro.infoiasi.sedic.android.communication.task;

import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.communication.event.GetRemedyDetailsEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.model.RemedyBean;
import ro.infoiasi.sedic.android.util.JSONHelper;
import ro.infoiasi.sedic.android.util.URLConstants;

public class GetRemedyDetailsServiceTask extends ServiceTask<RemedyBean> {

	@SuppressWarnings("unused")
	private static final String tag = GetRemedyDetailsServiceTask.class
			.getSimpleName();
	private long remedyId;

	public GetRemedyDetailsServiceTask(long remedyId) {
		this.remedyId = remedyId;
	}

	@Override
	public HttpRequestMethod getHttpRequestMethod() {
		return HttpRequestMethod.GET;
	}

	@Override
	public String getURL() {
		return URLConstants.URL_RESOURCE_REMEDY + "?remedyID="
				+ String.valueOf(remedyId);
	}

	@Override
	public void prepareRequest(HttpRequestBase request) {
	}

	@Override
	public Response<RemedyBean> parseResponse(String response) {
		RemedyBean output = null;
		try {
			JSONObject jsonResponse = new JSONObject(response);
			output = JSONHelper.paseJSONRemedy(jsonResponse);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (output != null) {
		    SedicApplication.getInstance().getRemedies().put(output.getRemedyId(), output);
			return new Response<RemedyBean>(this, Response.ResponseStatus.OK,
					output);
		} else {
			return new Response<RemedyBean>(this, Response.ResponseStatus.OK);

		}
	}

	@Override
	public ResponseEvent getEvent(Response<RemedyBean> response) {
		return new GetRemedyDetailsEvent(response);
	}

}
