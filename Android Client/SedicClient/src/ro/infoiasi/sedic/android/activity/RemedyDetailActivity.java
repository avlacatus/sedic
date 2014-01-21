package ro.infoiasi.sedic.android.activity;

import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.model.RemedyBean;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class RemedyDetailActivity extends Activity {

	public static final String INTENT_EXTRA_REMEDY_ID = "intent_extra_remedy_id";

	@SuppressWarnings("unused")
	private static final String tag = RemedyDetailActivity.class.getSimpleName();
	private RemedyBean mRemedyBean;
	private long mRemedyId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
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
			List<RemedyBean> remedies = SedicApplication.getInstance().getRemedies();
			for (RemedyBean bean : remedies) {
				if (bean.getRemedyId() == mRemedyId) {
					mRemedyBean = bean;
				}
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
			if (mRemedyBean.getFrequentUsage() != null) {
				mRemedyFrequent.setText(mRemedyBean.getFrequentUsage().toString());
			} else {
				mRemedyFrequent.setText("empty frequent");

			}
			if (mRemedyBean.getReportedUsage() != null) {
				mRemedyReported.setText(mRemedyBean.getReportedUsage().toString());
			} else {
				mRemedyReported.setText("empty reported");

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
