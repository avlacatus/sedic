package ro.infoiasi.sedic.android.communication.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import ro.infoiasi.sedic.android.communication.task.Message.EntityType;
import ro.infoiasi.sedic.android.communication.task.Response.ResponseStatus;
import ro.infoiasi.sedic.android.model.Road;
import ro.infoiasi.sedic.android.util.Constants;
import ro.infoiasi.sedic.android.util.EntityOperationsCallback;
import ro.infoiasi.sedic.android.util.JSONHelper;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class RoadsServiceTask extends AsyncTask<Message, Void, Response<Road>> {

	private static final String tag = RoadsServiceTask.class.getSimpleName();

	@SuppressWarnings("unused")
	private Context context;
	private EntityOperationsCallback<Road> callback = null;

	public RoadsServiceTask(Context context, EntityOperationsCallback<Road> responder) {
		this.context = context;
		this.callback = responder;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (callback != null) {
			callback.onEntityOperationStarted();
		}
	}

	@Override
	protected Response<Road> doInBackground(Message... params) {
		if (params.length == 0)
			// I don't have any message to process!
			return null;
		Message msg = params[0];

		HttpRequestBase request = buildHttpRequest(msg);

		if (request == null)
			return new Response<Road>(msg, ResponseStatus.FAILED);

		HttpResponse response = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(request);
		} catch (Exception e) {
			return new Response<Road>(msg, ResponseStatus.FAILED);
		}

		if (response == null)
			return new Response<Road>(msg, ResponseStatus.FAILED);

		HttpEntity entity = response.getEntity();

		if (entity == null) {
			return new Response<Road>(msg, ResponseStatus.OK);
		}

		String strOutput = null;
		InputStream instream;
		try {
			instream = entity.getContent();
			strOutput = new Scanner(instream, "UTF-8").useDelimiter("\\A")
					.next();
			Log.d(tag, request.getURI() + " " + strOutput);

			instream.close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return new Response<Road>(msg, ResponseStatus.FAILED);
		} catch (IOException e) {
			e.printStackTrace();
			return new Response<Road>(msg, ResponseStatus.FAILED);
		}

		if (strOutput == null)
			return new Response<Road>(msg, ResponseStatus.FAILED);

		List<Road> output = JSONHelper.buildRoadsArray(strOutput);

		if (output != null)
			return new Response<Road>(msg, ResponseStatus.OK, output);
		else
			return new Response<Road>(msg, ResponseStatus.FAILED);
	}

	@Override
	protected void onPostExecute(Response<Road> result) {
		super.onPostExecute(result);
		if (callback != null) {
			switch (result.getOriginalMessage().requestType) {
			case PUT:
				callback.onAddEntityOperationFinished();
				break;
			case GET:
				callback.onGetEntitiesOperationFinished(result);
				break;
			case POST:
				callback.onUpdateEntityOperationFinished();
				break;
			case DELETE:
				callback.onDeleteEntityOperationFinished();
				break;
			}

		}
	}

	private HttpRequestBase buildHttpRequest(Message msg) {
		if (!msg.entityType.equals(EntityType.ROAD))
			// I only handle Road operations!
			return null;

		HttpRequestBase request = null;
		switch (msg.requestType) {
		case PUT: {
			StringEntity entity;
			try {
				entity = new StringEntity(msg.extraData);
				entity.setContentType("application/json");
				request = new HttpPut(Constants.ROADS_CONTROLLER);
				((HttpPut) request).setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
			break;
		case GET:
			request = new HttpGet(Constants.ROADS_CONTROLLER);
			if (msg.extraData != null && !msg.extraData.equals("")) {
				HttpParams httpParams = new BasicHttpParams();
				httpParams.setParameter("id", msg.extraData);
			}
			break;
		case POST: {
			StringEntity entity;
			try {
				entity = new StringEntity(msg.extraData);
				entity.setContentType("application/json");
				request = new HttpPost(Constants.ROADS_CONTROLLER);
				((HttpPost) request).setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
			break;
		case DELETE: {
			request = new HttpDelete(Constants.ROADS_CONTROLLER + "?id="
					+ msg.extraData);
			// HttpParams httpParams = new BasicHttpParams();
			// httpParams.setParameter("id", msg.extraData);

			// request.setParams(httpParams);
		}
			break;
		}
		return request;
	}
}
