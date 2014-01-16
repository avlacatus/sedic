package ro.infoiasi.sedic.android.communication.task;

public class Response<E> {
	public static enum ResponseStatus {
		OK, FAILED
	}

	private Message originalMessage;
	private ResponseStatus status;
	private String errorMessage;
	private Object data;

	public Response(Message message, ResponseStatus status, Object data) {
		this.status = status;
		this.data = data;
		this.originalMessage = message;
	}

	public Response(Message message, ResponseStatus status) {
		this.status = status;
		this.originalMessage = message;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Message getOriginalMessage() {
		return originalMessage;
	}

	public void setOriginalMessage(Message originalMessage) {
		this.originalMessage = originalMessage;
	}

}
