package ro.infoiasi.sedic.android.communication.event;

import ro.infoiasi.sedic.android.communication.task.Response;
import ro.infoiasi.sedic.android.model.DrugBean;

public class GetDrugsEvent extends ResponseEvent {

	public GetDrugsEvent(Response<DrugBean> response) {
		super(response);
	}

}
