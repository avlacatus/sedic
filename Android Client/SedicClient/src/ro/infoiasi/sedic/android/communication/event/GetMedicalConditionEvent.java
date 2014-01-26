package ro.infoiasi.sedic.android.communication.event;

import ro.infoiasi.sedic.android.communication.task.Response;
import ro.infoiasi.sedic.android.model.MedicalFactorBean;

public class GetMedicalConditionEvent extends ResponseEvent {

	public GetMedicalConditionEvent(Response<MedicalFactorBean> response) {
		super(response);
	}

}
