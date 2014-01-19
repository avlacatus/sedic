package ro.infoiasi.sedic.android.communication.task;

import org.apache.http.client.methods.HttpRequestBase;

import ro.infoiasi.sedic.android.model.RemedyBean;
import ro.infoiasi.sedic.android.util.URLConstants;

public class RemedyInfoServiceTask extends ServiceTask<RemedyBean> {

	@SuppressWarnings("unused")
	private static final String tag = RemedyInfoServiceTask.class.getSimpleName();

	public RemedyInfoServiceTask() {
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
	}

	@Override
	public Response<RemedyBean> parseResponse(String response) {
		return new Response<RemedyBean>(this, Response.ResponseStatus.OK);
	}

}
