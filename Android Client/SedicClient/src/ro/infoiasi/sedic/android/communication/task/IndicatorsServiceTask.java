package ro.infoiasi.sedic.android.communication.task;

import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;

import ro.infoiasi.sedic.android.communication.task.Response.ResponseStatus;
import ro.infoiasi.sedic.android.model.Indicator;
import ro.infoiasi.sedic.android.util.JSONHelper;
import ro.infoiasi.sedic.android.util.URLConstants;

public class IndicatorsServiceTask extends ServiceTask<Indicator> {

	@SuppressWarnings("unused")
	private static final String tag = IndicatorsServiceTask.class.getSimpleName();

	public IndicatorsServiceTask() {
	}

	@Override
	public HttpRequestMethod getHttpRequestMethod() {
		return HttpRequestMethod.GET;
	}

	@Override
	public String getURL() {
		return URLConstants.INDICATORS_CONTROLLER;
	}

	@Override
	public void prepareRequest(HttpRequestBase request) {
	}

	@Override
	public Response<Indicator> parseResponse(String response) {
		List<Indicator> output = JSONHelper.buildIndicatorsArray(response);
		if (output != null)
			return new Response<Indicator>(this, ResponseStatus.OK, output);
		else
			return new Response<Indicator>(this, ResponseStatus.FAILED);
	}

}
