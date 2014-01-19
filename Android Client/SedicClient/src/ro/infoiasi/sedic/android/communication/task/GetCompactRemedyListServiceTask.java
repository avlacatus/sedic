package ro.infoiasi.sedic.android.communication.task;

import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.communication.event.GetCompactRemediesEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.model.RemedyBean;
import ro.infoiasi.sedic.android.util.JSONHelper;
import ro.infoiasi.sedic.android.util.URLConstants;

public class GetCompactRemedyListServiceTask extends ServiceTask<RemedyBean> {

	@SuppressWarnings("unused")
	private static final String tag = GetCompactRemedyListServiceTask.class.getSimpleName();

	public GetCompactRemedyListServiceTask() {
	}

	@Override
	public HttpRequestMethod getHttpRequestMethod() {
		return HttpRequestMethod.GET;
	}

	@Override
	public String getURL() {
		return URLConstants.URL_RESOURCE_REMEDY;
	}

	@Override
	public void prepareRequest(HttpRequestBase request) {
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter("compact", 1);
		request.setParams(httpParams);
	}

	@Override
	public Response<RemedyBean> parseResponse(String response) {
		List<RemedyBean> outputList = null;
		try {
			JSONObject jsonResponse = new JSONObject(response);
			outputList = JSONHelper.parseCompactRemedyArray(jsonResponse.getJSONArray("remedies").toString());
		} catch (JSONException e) {

		}

		if (outputList != null) {
			SedicApplication.getInstance().setRemedies(outputList);
			return new Response<RemedyBean>(this, Response.ResponseStatus.OK, outputList);
		} else {
			Response<RemedyBean> output = new Response<RemedyBean>(this, Response.ResponseStatus.FAILED);
			output.setErrorMessage("Could not parse json response");
			return output;
		}
	}

	@Override
	public ResponseEvent getEvent(Response<RemedyBean> response) {
		return new GetCompactRemediesEvent(response);
	}

}
