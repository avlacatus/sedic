package ro.infoiasi.sedic.android.communication.event;

import ro.infoiasi.sedic.android.communication.task.Response;
import ro.infoiasi.sedic.android.model.DiseaseBean;

public class GetDiseasesEvent extends ResponseEvent{

	public GetDiseasesEvent(Response<DiseaseBean> response) {
		super(response);
	}

}
