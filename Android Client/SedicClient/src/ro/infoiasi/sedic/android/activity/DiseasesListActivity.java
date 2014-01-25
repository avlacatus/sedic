package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;
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
import ro.infoiasi.sedic.android.adapter.DiseaseListAdapter;
import ro.infoiasi.sedic.android.communication.event.GetDiseasesEvent;
import ro.infoiasi.sedic.android.communication.event.GetDrugsEvent;
import ro.infoiasi.sedic.android.communication.event.RemedySearchEvent;
import ro.infoiasi.sedic.android.communication.task.RemedySearchServiceTask;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.model.RemedyBean;
import ro.infoiasi.sedic.android.util.DialogUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import de.greenrobot.event.EventBus;

public class DiseasesListActivity extends ActionBarActivity {

	@SuppressWarnings("unused")
	private static final String tag = DiseasesListActivity.class.getSimpleName();

	private List<DiseaseBean> mDiseaseList;
	private TreeViewList treeView;
	private static final int LEVEL_NUMBER = 6;
	private TreeStateManager<DiseaseBean> manager = null;
	private DiseaseListAdapter diseaseAdapter;
	private boolean initialized = false;
	private DiseaseBean selectedBean = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.expandable_list_layout);
		EventBus.getDefault().register(this, GetDiseasesEvent.class, RemedySearchEvent.class);
		setupData();
		manager = new InMemoryTreeStateManager<DiseaseBean>();
		initialized = initTreeManager();
		setupUI();
		registerForContextMenu(treeView);
	}

	private void setupData() {
		Map<Long, DiseaseBean> diseaseMap = SedicApplication.getInstance().getDiseases();
		if (diseaseMap != null) {
			mDiseaseList = new ArrayList<DiseaseBean>(diseaseMap.values());
		}

	}

	private void setupUI() {
		treeView = (TreeViewList) findViewById(R.id.tree_view);
		diseaseAdapter = new DiseaseListAdapter(this, manager, LEVEL_NUMBER);
		treeView.setAdapter(diseaseAdapter);
		treeView.setCollapsible(true);
		manager.collapseChildren(null);
	}

	private boolean initTreeManager() {
		if (SedicApplication.getInstance().getDrugs() != null) {
			final TreeBuilder<DiseaseBean> treeBuilder = new TreeBuilder<DiseaseBean>(manager);
			if (mDiseaseList != null) {

				DiseaseBean root = null;
				for (DiseaseBean bean : mDiseaseList) {
					if (bean.getBeanName().equalsIgnoreCase("diseases")) {
						root = bean;
						break;
					}
				}

				List<DiseaseBean> parents = new ArrayList<DiseaseBean>();
				for (DiseaseBean bean : root.getDiseaseChildren()) {
					treeBuilder.sequentiallyAddNextNode(bean, 0);
					parents.add(bean);
				}

				for (int i = 0; i < LEVEL_NUMBER; i++) {
					List<DiseaseBean> children = addNewLayer(parents, treeBuilder);
					parents = children;
				}

				return true;
			}
		}
		return false;
	}

	private List<DiseaseBean> addNewLayer(List<DiseaseBean> parents, TreeBuilder<DiseaseBean> treeBuilder) {
		List<DiseaseBean> output = new ArrayList<DiseaseBean>();
		for (DiseaseBean bean : parents) {
			for (DiseaseBean children : bean.getDiseaseChildren()) {
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
		EventBus.getDefault().unregister(this, GetDiseasesEvent.class, RemedySearchEvent.class);
	}

	public void onEventMainThread(RemedySearchEvent e) {
		DialogUtils.hideProgressDialog(this);
		if (e.getResponse().getData() instanceof Map) {
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
						"Remedies for " + selectedBean.getBeanName());
			} else {
				intent.putExtra(RemedyListActivity.INTENT_EXTRA_ACTIVITY_TITLE, "Search Results");
			}
			startActivity(intent);
		}
	}

	public void onEventMainThread(GetDrugsEvent e) {
		if (!initialized) {
			initTreeManager();
		}
	}

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenuInfo menuInfo) {
		final AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo) menuInfo;
		final long id = adapterInfo.id;
		final TreeNodeInfo<DiseaseBean> info = manager
				.getNodeInfo(SedicApplication.getInstance().getDiseases().get(id));
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
		selectedBean = SedicApplication.getInstance().getDiseases().get(id);
		if (item.getItemId() == R.id.context_menu_browse_selected) {
			List<DiseaseBean> payload = new ArrayList<DiseaseBean>();
			payload.add(selectedBean);
			DialogUtils.showProgressDialog(this, null);
			new RemedySearchServiceTask(null, payload).execute();
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
