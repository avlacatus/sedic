package ro.infoiasi.sedic.android.communication.event;

import ro.infoiasi.sedic.android.communication.task.Response;
import ro.infoiasi.sedic.android.model.PlantBean;

public class GetPlantsEvent extends ResponseEvent {

	public GetPlantsEvent(Response<PlantBean> response) {
		super(response);
	}

}
