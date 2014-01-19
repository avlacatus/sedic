package ro.infoiasi.sedic.android.communication.event;

import ro.infoiasi.sedic.android.communication.task.Response;
import ro.infoiasi.sedic.android.model.RemedyBean;

public class GetRemedyDetailsEvent extends ResponseEvent {

	public GetRemedyDetailsEvent(Response<RemedyBean> response) {
		super(response);
	}

}
