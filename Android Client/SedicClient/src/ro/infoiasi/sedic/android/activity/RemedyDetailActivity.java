package ro.infoiasi.sedic.android.activity;

import java.util.Map;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.model.RemedyBean;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class RemedyDetailActivity extends ActionBarActivity {

	public static final String INTENT_EXTRA_REMEDY_ID = "intent_extra_remedy_id";

	@SuppressWarnings("unused")
	private static final String tag = RemedyDetailActivity.class.getSimpleName();
	private RemedyBean mRemedyBean;
	private long mRemedyId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.remedy_detail_layout);
		if (getIntent().hasExtra(INTENT_EXTRA_REMEDY_ID)) {
			mRemedyId = getIntent().getLongExtra(INTENT_EXTRA_REMEDY_ID, -1);
		} else {
			mRemedyId = -1;
		}
		setupData();
		if (mRemedyBean != null) {
			setTitle(mRemedyBean.getRemedyName());
		}
		setupUI();
	}

	private void setupData() {
		if (mRemedyId != -1) {
			Map<Long, RemedyBean> remedies = SedicApplication.getInstance().getRemedies();
			if (remedies != null) {
				mRemedyBean = remedies.get(Long.valueOf(mRemedyId));
			}
		} else {
			Toast.makeText(this, "remedy could not be found", Toast.LENGTH_LONG).show();
			finish();
		}

	}

	private void setupUI() {
		TextView mRemedyDescription = (TextView) findViewById(R.id.rd_description);
		TextView mRemedyFrequent = (TextView) findViewById(R.id.rd_frequent);
		TextView mRemedyReported = (TextView) findViewById(R.id.rd_reported);
		if (mRemedyBean != null) {
			mRemedyDescription.setText(mRemedyBean.getRemedyName() + mRemedyBean.getRemedyURI());
			if (mRemedyBean.getAdjuvantUsages() != null) {
				mRemedyFrequent.setText(mRemedyBean.getAdjuvantUsages().toString());
			} else {
				mRemedyFrequent.setText("empty adjuvants");

			}
			if (mRemedyBean.getTherapeuticalUsages() != null) {
				mRemedyReported.setText(mRemedyBean.getTherapeuticalUsages().toString());
			} else {
				mRemedyReported.setText("empty therapeutical");

			}
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
