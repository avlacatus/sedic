package ro.infoiasi.sedic.android.communication.event;

import ro.infoiasi.sedic.android.communication.task.Response;

public class ResponseEvent {

	protected Response<?> response;

	public ResponseEvent(Response<?> response) {
		this.response = response;
	}

	public Response<?> getResponse() {
		return response;
	}
}
