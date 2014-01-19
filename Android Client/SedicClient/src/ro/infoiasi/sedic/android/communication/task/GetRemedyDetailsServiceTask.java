package ro.infoiasi.sedic.android.communication.task;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpRequestBase;

import ro.infoiasi.sedic.android.communication.event.GetRemedyDetailsEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.model.RemedyBean;
import ro.infoiasi.sedic.android.util.URLConstants;
import android.net.Uri;
import android.util.Log;

public class GetRemedyDetailsServiceTask extends ServiceTask<RemedyBean> {

	@SuppressWarnings("unused")
	private static final String tag = GetRemedyDetailsServiceTask.class.getSimpleName();
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
		Uri.Builder builder = new Uri.Builder();
		return builder.appendPath(URLConstants.URL_RESOURCE_REMEDY).appendQueryParameter("remedyID", String.valueOf(remedyId))
				.build().toString();
	}

	@Override
	public void prepareRequest(HttpRequestBase request) {
		Uri.Builder builder = new Uri.Builder();
		String encodedURI = builder.appendPath(URLConstants.URL_RESOURCE_REMEDY)
				.appendQueryParameter("remedyID", "20000").build().toString();
		Log.e("debug", "encoded uri: " + encodedURI);
		try {
			request.setURI(new URI(encodedURI));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response<RemedyBean> parseResponse(String response) {
		return new Response<RemedyBean>(this, Response.ResponseStatus.OK);
	}

	@Override
	public ResponseEvent getEvent(Response<RemedyBean> response) {
		return new GetRemedyDetailsEvent(response);
	}

}
