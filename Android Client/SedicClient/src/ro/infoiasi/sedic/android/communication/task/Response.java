package ro.infoiasi.sedic.android.communication.task;

public class Response<E> {
	public static enum ResponseStatus {
		OK, FAILED
	}

	private ServiceTask<E> originalTask;
	private ResponseStatus status;
	private String errorMessage;
	private Object data;

	public Response(ServiceTask<E> message, ResponseStatus status, Object data) {
		this.status = status;
		this.data = data;
		this.originalTask = message;
	}

	public Response(ServiceTask<E> message, ResponseStatus status) {
		this.status = status;
		this.originalTask = message;
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

	public ServiceTask<E> getOriginalMessage() {
		return originalTask;
	}

	public void setOriginalMessage(ServiceTask<E> originalMessage) {
		this.originalTask = originalMessage;
	}

}
