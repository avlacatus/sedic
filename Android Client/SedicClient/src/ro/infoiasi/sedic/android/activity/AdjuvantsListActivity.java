package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import pl.polidea.treeview.InMemoryTreeStateManager;
import pl.polidea.treeview.NodeAlreadyInTreeException;
import pl.polidea.treeview.TreeBuilder;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeStateManager;
import pl.polidea.treeview.TreeViewList;
import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.adapter.DrugListAdapter;
import ro.infoiasi.sedic.android.communication.event.GetDrugsEvent;
import ro.infoiasi.sedic.android.communication.event.RemedySearchEvent;
import ro.infoiasi.sedic.android.communication.task.RemedySearchServiceTask;
import ro.infoiasi.sedic.android.model.DrugBean;
import ro.infoiasi.sedic.android.model.RemedyBean;
import ro.infoiasi.sedic.android.util.DialogUtils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.AdapterView.AdapterContextMenuInfo;
import de.greenrobot.event.EventBus;

public class AdjuvantsListActivity extends ActionBarActivity {
	@SuppressWarnings("unused")
	private static final String tag = AdjuvantsListActivity.class.getSimpleName();
	private TreeViewList treeView;
	private ProgressBar progress;
	private static final int LEVEL_NUMBER = 6;
	private TreeStateManager<DrugBean> manager = null;
	private DrugListAdapter drugAdapter;
	private boolean initialized = false;
	private DrugBean selectedBean = null;
	private InitTreeAsyncTask initTask = null;

	private class InitTreeAsyncTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!initialized) {
				progress.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			if (!initialized) {
				initialized = initTreeManager();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (initialized) {
				drugAdapter = new DrugListAdapter(AdjuvantsListActivity.this, manager, LEVEL_NUMBER);
				treeView.setAdapter(drugAdapter);
				treeView.setCollapsible(true);
				manager.collapseChildren(null);
				progress.setVisibility(View.GONE);
			} else {
				progress.setVisibility(View.VISIBLE);
			}
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.expandable_list_layout);
		EventBus.getDefault().register(this, GetDrugsEvent.class, RemedySearchEvent.class);
		manager = new InMemoryTreeStateManager<DrugBean>();
		findViews();
		registerForContextMenu(treeView);
		initTask = new InitTreeAsyncTask();
		initTask.execute();
	}

	private void findViews() {
		treeView = (TreeViewList) findViewById(R.id.tree_view);
		progress = (ProgressBar) findViewById(R.id.progress);
	}

	private boolean initTreeManager() {
		if (SedicApplication.getInstance().getDrugs() != null) {
			Collection<DrugBean> drugList = new ArrayList<DrugBean>(SedicApplication.getInstance().getDrugs().values());
			final TreeBuilder<DrugBean> treeBuilder = new TreeBuilder<DrugBean>(manager);
			if (drugList != null) {

				DrugBean root = null;
				for (DrugBean bean : drugList) {
					if (bean.getBeanName().equalsIgnoreCase("chemical actions and uses")) {
						root = bean;
						break;
					}
				}

				List<DrugBean> parents = new ArrayList<DrugBean>();
				for (DrugBean bean : root.getDrugChildren()) {
					parents.add(bean);
				}

				for (DrugBean bean : drugList) {
					if (bean.getBeanName().equalsIgnoreCase("lipids")) {
						parents.add(bean);
						break;
					}
				}

				for (DrugBean bean : drugList) {
					if (bean.getBeanName().equalsIgnoreCase("hormones, hormone substitutes, and hormone antagonists")) {
						parents.add(bean);
						break;
					}
				}
				for (DrugBean bean : parents) {
					treeBuilder.sequentiallyAddNextNode(bean, 0);
				}

				for (int i = 0; i < LEVEL_NUMBER; i++) {
					List<DrugBean> children = addNewLayer(parents, treeBuilder);
					parents = children;
				}

				return true;
			}
		}
		return false;
	}

	private List<DrugBean> addNewLayer(List<DrugBean> parents, TreeBuilder<DrugBean> treeBuilder) {
		List<DrugBean> output = new ArrayList<DrugBean>();
		for (DrugBean bean : parents) {
			for (DrugBean children : bean.getDrugChildren()) {
				try {
					treeBuilder.addRelation(bean, children);
					output.add(children);
				} catch (NodeAlreadyInTreeException e) {

				}
			}
		}
		return output;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, GetDrugsEvent.class, RemedySearchEvent.class);
	}

	public void onEventMainThread(RemedySearchEvent e) {
		DialogUtils.hideProgressDialog(this);
		Map<Long, RemedyBean> responseData = (Map<Long, RemedyBean>) e.getResponse().getData();
		long[] ids = new long[responseData.size()];
		int i = 0;
		for (Long id : responseData.keySet()) {
			ids[i] = id;
			i++;
		}

		Intent intent = new Intent(this, RemedyListActivity.class);
		intent.putExtra(RemedyListActivity.INTENT_EXTRA_REMEDY_IDS, ids);
		if (selectedBean != null) {
			intent.putExtra(RemedyListActivity.INTENT_EXTRA_ACTIVITY_TITLE,
					"Remedies with " + selectedBean.getBeanName());
		} else {
			intent.putExtra(RemedyListActivity.INTENT_EXTRA_ACTIVITY_TITLE, "Search Results");
		}
		startActivity(intent);
	}

	public void onEventMainThread(GetDrugsEvent e) {
		Log.e("debug", "adjuvants list get drug event");
		if (!initialized && (initTask == null || initTask.getStatus() == Status.FINISHED)) {
			initTask = new InitTreeAsyncTask();
			initTask.execute();
		}
	}

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenuInfo menuInfo) {
		final AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo) menuInfo;
		final long id = adapterInfo.id;
		final TreeNodeInfo<DrugBean> info = manager.getNodeInfo(SedicApplication.getInstance().getDrugs().get(id));
		final MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.context_menu, menu);
		if (info.isWithChildren()) {
			if (info.isExpanded()) {
				menu.findItem(R.id.context_menu_expand_item).setVisible(false);
				menu.findItem(R.id.context_menu_expand_all).setVisible(false);
			} else {
				menu.findItem(R.id.context_menu_collapse).setVisible(false);
			}
		} else {
			menu.findItem(R.id.context_menu_expand_item).setVisible(false);
			menu.findItem(R.id.context_menu_expand_all).setVisible(false);
			menu.findItem(R.id.context_menu_collapse).setVisible(false);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final long id = info.id;
		selectedBean = SedicApplication.getInstance().getDrugs().get(id);
		if (item.getItemId() == R.id.context_menu_browse_selected) {
			List<DrugBean> payload = new ArrayList<DrugBean>();
			payload.add(selectedBean);
			DialogUtils.showProgressDialog(this, null);
			new RemedySearchServiceTask(payload, null, null, null, -1).execute();
			return true;
		} else if (item.getItemId() == R.id.context_menu_collapse) {
			manager.collapseChildren(selectedBean);
			return true;
		} else if (item.getItemId() == R.id.context_menu_expand_all) {
			manager.expandEverythingBelow(selectedBean);
			return true;
		} else if (item.getItemId() == R.id.context_menu_expand_item) {
			manager.expandDirectChildren(selectedBean);
			return true;
		} else {
			return super.onContextItemSelected(item);
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
