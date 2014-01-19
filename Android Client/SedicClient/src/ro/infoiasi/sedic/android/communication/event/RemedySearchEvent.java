package ro.infoiasi.sedic.android.communication.event;

import ro.infoiasi.sedic.android.communication.task.Response;
import ro.infoiasi.sedic.android.model.RemedyBean;

public class RemedySearchEvent extends ResponseEvent {

	public RemedySearchEvent(Response<RemedyBean> response) {
		super(response);
	}

}
