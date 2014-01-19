package ro.infoiasi.sedic.android.communication.event;

import ro.infoiasi.sedic.android.communication.task.Response;
import ro.infoiasi.sedic.android.model.RemedyBean;

public class GetCompactRemediesEvent extends ResponseEvent {

	public GetCompactRemediesEvent(Response<RemedyBean> response) {
		super(response);
	}

}
