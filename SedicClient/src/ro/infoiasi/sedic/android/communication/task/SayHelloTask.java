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

public class SayHelloTask extends AsyncTask<String, Void, String> {

	private static final String tag = SayHelloTask.class.getSimpleName();

	public static interface SayHelloResponder {
		public void onSayStarted();

		public void onSayResponded(String result);
	}

	private static final String METHOD_NAME = "sayHelloWorldFrom";
	private static final String NAMESPACE = "http://jaxws.tap.infoiasi.ro/";
	private static final String URL = Constants.BASE_URL
			+ "/services/HandleCoordinates?wsdl";

	private SayHelloResponder responder;

	public SayHelloTask(SayHelloResponder responder) {
		this.responder = responder;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		responder.onSayStarted();
	}

	@Override
	protected String doInBackground(String... params) {

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		MarshalDouble md = new MarshalDouble();
		md.register(envelope);

		request.addProperty("arg0", params[0]);

		envelope.setOutputSoapObject(request);

		HttpTransportSE ht = new HttpTransportSE(URL);
		String result = null;
		try {
			ht.call("", envelope);

			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			result = response.toString();

		} catch (Exception e) {
			Log.d(tag, e.toString());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		responder.onSayResponded(result);
	}

}
