package ro.infoiasi.sedic.android.activity;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.communication.event.GetCompactRemediesEvent;
import ro.infoiasi.sedic.android.communication.event.GetPlantsEvent;
import ro.infoiasi.sedic.android.communication.task.GetCompactRemedyListServiceTask;
import ro.infoiasi.sedic.android.communication.task.GetPlantsServiceTask;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import de.greenrobot.event.EventBus;

public class DrugsListActivity extends Activity {
	private static final String tag = DrugsListActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);

		EventBus.getDefault().register(this, GetPlantsEvent.class, GetCompactRemediesEvent.class);
		new GetPlantsServiceTask().execute();
		new GetCompactRemedyListServiceTask().execute();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, GetPlantsEvent.class, GetCompactRemediesEvent.class);
	}

	public void onEventMainThread(GetPlantsEvent e) {
		Log.e(tag, "GetPlantsEvent received");
	}

	public void onEventMainThread(GetCompactRemediesEvent e) {
		Log.e(tag, "GetCompactRemediesEvent received");
	}
}
