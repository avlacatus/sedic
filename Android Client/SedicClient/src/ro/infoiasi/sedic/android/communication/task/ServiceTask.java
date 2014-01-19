package ro.infoiasi.sedic.android.communication.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import ro.infoiasi.sedic.android.communication.task.Response.ResponseStatus;
import android.os.AsyncTask;
import android.util.Log;

public abstract class ServiceTask<E> extends AsyncTask<Void, Void, Response<E>> {

	private static final String tag = ServiceTask.class.getSimpleName();

	public static enum HttpRequestMethod {
		GET, POST, PUT, DELETE
	}

	public ServiceTask() {
	}

	public abstract HttpRequestMethod getHttpRequestMethod();

	public abstract String getURL();

	public abstract void prepareRequest(HttpRequestBase request);

	public abstract Response<E> parseResponse(String response);

	protected Response<E> doInBackground(Void... params) {
		HttpRequestBase request = buildHttpRequest();
		prepareRequest(request);

		if (request == null)
			return new Response<E>(this, ResponseStatus.FAILED);

		HttpResponse response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(request);
		} catch (Exception e) {
			return new Response<E>(this, ResponseStatus.FAILED);
		}

		if (response == null)
			return new Response<E>(this, ResponseStatus.FAILED);

		HttpEntity entity = response.getEntity();

		if (entity == null) {
			return new Response<E>(this, ResponseStatus.OK);
		}

		String strOutput = null;
		InputStream instream;
		try {
			instream = entity.getContent();
			strOutput = new Scanner(instream, "UTF-8").useDelimiter("\\A").next();
			Log.d(tag, request.getURI() + " " + strOutput);

			instream.close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return new Response<E>(this, ResponseStatus.FAILED);
		} catch (IOException e) {
			e.printStackTrace();
			return new Response<E>(this, ResponseStatus.FAILED);
		}

		if (strOutput == null)
			return new Response<E>(this, ResponseStatus.FAILED);

		return parseResponse(strOutput);
	}

	private HttpRequestBase buildHttpRequest() {
		HttpRequestBase request = null;
		switch (getHttpRequestMethod()) {
		case PUT:
			request = new HttpPut(getURL());
			break;
		case GET:
			request = new HttpGet(getURL());
			break;
		case POST:
			request = new HttpPost(getURL());
			break;
		case DELETE:
			request = new HttpDelete(getURL());
			break;
		}
		return request;
	}
}
