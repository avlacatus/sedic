package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;
import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.adapter.DiseaseListAdapter;
import ro.infoiasi.sedic.android.communication.event.RemedySearchEvent;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import de.greenrobot.event.EventBus;

public class DiseasesListActivity extends Activity implements AdapterView.OnItemClickListener {
	private static final String tag = DiseasesListActivity.class.getSimpleName();
	private ListView mListView;
	private List<DiseaseBean> mDiseaseList;
	private DiseaseListAdapter mDiseaseListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.list_activity_layout);
		setupData();
		findViews();
		EventBus.getDefault().register(this, RemedySearchEvent.class);
	}

	private void setupData() {
		mDiseaseList = new ArrayList<DiseaseBean>(SedicApplication.getInstance().getDiseases().values());
	}

	private void findViews() {
		mListView = (ListView) findViewById(android.R.id.list);
		mListView.setEmptyView(findViewById(android.R.id.empty));
		if (mDiseaseList != null) {
			mDiseaseListAdapter = new DiseaseListAdapter(this, 0, mDiseaseList);
			mListView.setAdapter(mDiseaseListAdapter);
		}
		mListView.setOnItemClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, RemedySearchEvent.class);
	}

	public void onEventMainThread(RemedySearchEvent e) {
		Log.e(tag, "RemedySearchEvent received");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		DiseaseBean selectedBean = mDiseaseListAdapter.getItem(position);
		Toast.makeText(this, selectedBean.toString(), Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
