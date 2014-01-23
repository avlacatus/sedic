package ro.infoiasi.sedic.android.activity;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.communication.event.GetCompactRemediesEvent;
import ro.infoiasi.sedic.android.communication.event.GetDiseasesEvent;
import ro.infoiasi.sedic.android.communication.event.GetDrugsEvent;
import ro.infoiasi.sedic.android.communication.event.GetPlantsEvent;
import ro.infoiasi.sedic.android.communication.task.GetCompactRemedyListServiceTask;
import ro.infoiasi.sedic.android.communication.task.GetDiseaseServiceTask;
import ro.infoiasi.sedic.android.communication.task.GetDrugServiceTask;
import ro.infoiasi.sedic.android.communication.task.GetPlantsServiceTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import de.greenrobot.event.EventBus;

public class SplashActivity extends Activity {
	private static final String tag = SplashActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);

		EventBus.getDefault().register(this, GetPlantsEvent.class, GetCompactRemediesEvent.class, GetDrugsEvent.class,
				GetDiseasesEvent.class);
		new GetPlantsServiceTask().execute();
		new GetCompactRemedyListServiceTask().execute();
		new GetDrugServiceTask().execute();
		new GetDiseaseServiceTask().execute();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}

		}, 5000);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, GetPlantsEvent.class, GetCompactRemediesEvent.class,
				GetDrugsEvent.class, GetDiseasesEvent.class);
	}

	public void onEventMainThread(GetPlantsEvent e) {
		Log.e(tag, "GetPlantsEvent received");
	}

	public void onEventMainThread(GetCompactRemediesEvent e) {
		Log.e(tag, "GetCompactRemediesEvent received");
	}

	public void onEventMainThread(GetDrugsEvent e) {
		Log.e(tag, "GetDrugsEvent received");
	}

	public void onEventMainThread(GetDiseasesEvent e) {
		Log.e(tag, "GetDiseasesEvent received");
	}
}
