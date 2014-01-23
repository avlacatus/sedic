package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;
import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.adapter.RemedyListAdapter;
import ro.infoiasi.sedic.android.communication.event.GetRemedyDetailsEvent;
import ro.infoiasi.sedic.android.communication.task.GetRemedyDetailsServiceTask;
import ro.infoiasi.sedic.android.communication.task.Response.ResponseStatus;
import ro.infoiasi.sedic.android.model.RemedyBean;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import de.greenrobot.event.EventBus;

public class RemedyListActivity extends Activity implements AdapterView.OnItemClickListener {
    @SuppressWarnings("unused")
    private static final String tag = RemedyListActivity.class.getSimpleName();
    private ListView mListView;
    private List<RemedyBean> mRemedyList;
    private RemedyListAdapter mRemedyListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.list_activity_layout);
        setupData();
        findViews();
        EventBus.getDefault().register(this, GetRemedyDetailsEvent.class);
    }

    private void setupData() {
        mRemedyList = new ArrayList<RemedyBean>(SedicApplication.getInstance().getRemedies().values());
    }

    private void findViews() {
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setEmptyView(findViewById(android.R.id.empty));
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
