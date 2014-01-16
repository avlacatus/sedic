package ro.infoiasi.sedic.android.communication.task;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import ro.infoiasi.sedic.android.util.Constants;
import ro.infoiasi.sedic.android.util.MarshalDouble;

import android.os.AsyncTask;
import android.util.Log;

public class UpdatePositionTask extends AsyncTask<Double, Void, Boolean> {

	private static final String tag = UpdatePositionTask.class.getSimpleName();

	public static interface UpdatePositionResponder {
		public void onUpdateStarted();

		public void onUpdateFinished(boolean result);
	}

	private static final String METHOD_NAME = "saveCoordinates";
	private static final String NAMESPACE = "http://jaxws.tap.infoiasi.ro/";
	private static final String URL = Constants.BASE_URL + "/services/HandleCoordinates?wsdl";

	private UpdatePositionResponder responder;
	private int roadID;

	public UpdatePositionTask(int roadID, UpdatePositionResponder responder) {
		this.roadID = roadID;
		this.responder = responder;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		responder.onUpdateStarted();
	}

	@Override
	protected Boolean doInBackground(Double... params) {
		String msg = "roadID: " + roadID + " ";
		for (double val : params) {
			msg += " " + val;
		}
		Log.d(tag, msg);
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		MarshalDouble md = new MarshalDouble();
		md.register(envelope);

		request.addProperty("arg0", roadID);
		request.addProperty("arg1", params[0]);
		request.addProperty("arg2", params[1]);
		request.addProperty("arg3", params[2]);
		request.addProperty("arg4", params[3]);

		envelope.setOutputSoapObject(request);

		HttpTransportSE ht = new HttpTransportSE(URL);
		Boolean result = null;
		try {
			ht.call("", envelope);

			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			String strResponse = response.toString();
			result = Boolean.valueOf(strResponse);
			Log.d(tag, response.toString());

		} catch (Exception e) {
			Log.d(tag, e.toString());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		responder.onUpdateFinished(result);
	}

}
