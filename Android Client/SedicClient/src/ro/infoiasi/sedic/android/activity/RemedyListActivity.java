package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.adapter.RemedyListAdapter;
import ro.infoiasi.sedic.android.communication.event.GetRemedyDetailsEvent;
import ro.infoiasi.sedic.android.communication.task.GetRemedyDetailsServiceTask;
import ro.infoiasi.sedic.android.communication.task.Response.ResponseStatus;
import ro.infoiasi.sedic.android.model.RemedyBean;
import ro.infoiasi.sedic.android.util.DialogUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.greenrobot.event.EventBus;

public class RemedyListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
	@SuppressWarnings("unused")
	private static final String tag = RemedyListActivity.class.getSimpleName();

	public static final String INTENT_EXTRA_REMEDY_IDS = "intent_extra_remedy_ids";
	public static final String INTENT_EXTRA_ACTIVITY_TITLE = "intent_extra_activity_title";

	private ListView mListView;
	private List<RemedyBean> mRemedyList;
	private RemedyListAdapter mRemedyListAdapter;

	private long[] remedyIdArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.list_activity_layout);
		if (getIntent().hasExtra(INTENT_EXTRA_REMEDY_IDS)) {
			remedyIdArray = getIntent().getLongArrayExtra(INTENT_EXTRA_REMEDY_IDS);
		} else {
			remedyIdArray = null;
		}

		if (getIntent().hasExtra(INTENT_EXTRA_ACTIVITY_TITLE)) {
			getSupportActionBar().setTitle(getIntent().getStringExtra(INTENT_EXTRA_ACTIVITY_TITLE));
		}
		setupData();
		findViews();
		EventBus.getDefault().register(this, GetRemedyDetailsEvent.class);
	}

	private void setupData() {
		Map<Long, RemedyBean> remedyMap = SedicApplication.getInstance().getRemedies();
		if (remedyMap != null ) {
			if (remedyIdArray == null) {
				mRemedyList = new ArrayList<RemedyBean>(remedyMap.values());
			} else {
				mRemedyList = new ArrayList<RemedyBean>();
				for (long id : remedyIdArray) {
					RemedyBean remedy = remedyMap.get(id);
					if (remedy != null) {
						mRemedyList.add(remedy);
					}
				}
			}
		}
	}

	private void findViews() {
		mListView = (ListView) findViewById(android.R.id.list);
		TextView emptyView = (TextView) findViewById(android.R.id.empty);
		emptyView.setText("No results found");
		mListView.setEmptyView(emptyView);
		if (mRemedyList != null) {
			mRemedyListAdapter = new RemedyListAdapter(this, 0, mRemedyList);
			mListView.setAdapter(mRemedyListAdapter);
		}
		mListView.setOnItemClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, GetRemedyDetailsEvent.class);
	}

	public void onEventMainThread(GetRemedyDetailsEvent e) {
		DialogUtils.hideProgressDialog(this);
		if (e.getResponse().getStatus() == ResponseStatus.OK) {
			RemedyBean bean = (RemedyBean) e.getResponse().getData();
			Intent intent = new Intent(this, RemedyDetailActivity.class);
			intent.putExtra(RemedyDetailActivity.INTENT_EXTRA_REMEDY_ID, bean.getRemedyId());
			startActivity(intent);
		} else {
			Toast.makeText(this, "could not retrieve remedy info", Toast.LENGTH_LONG).show();

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		RemedyBean selectedBean = mRemedyListAdapter.getItem(position);
		if (selectedBean != null) {
			DialogUtils.showProgressDialog(this, "Please wait..");
			new GetRemedyDetailsServiceTask(selectedBean.getRemedyId()).execute();
		} else {
			Toast.makeText(this, "could not open remedy", Toast.LENGTH_SHORT).show();
		}
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
