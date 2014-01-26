package ro.infoiasi.sedic.android.fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.polidea.treeview.InMemoryTreeStateManager;
import pl.polidea.treeview.NodeAlreadyInTreeException;
import pl.polidea.treeview.TreeBuilder;
import pl.polidea.treeview.TreeStateManager;
import pl.polidea.treeview.TreeViewList;
import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.activity.MainActivity;
import ro.infoiasi.sedic.android.adapter.BeanTreeAdapter;
import ro.infoiasi.sedic.android.communication.event.GetDrugsEvent;
import ro.infoiasi.sedic.android.model.DrugBean;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.greenrobot.event.EventBus;

public class SelectAdjuvantsFragment extends Fragment {

	private final Set<DrugBean> selected = new HashSet<DrugBean>();

	@SuppressWarnings("unused")
	private static final String TAG = SelectAdjuvantsFragment.class.getSimpleName();
	private TreeViewList treeView;

	private static final int LEVEL_NUMBER = 6;
	private TreeStateManager<DrugBean> manager = null;
	private BeanTreeAdapter<DrugBean> simpleAdapter;
	private boolean initialized = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = new InMemoryTreeStateManager<DrugBean>();
		initialized = initTreeManager();
		EventBus.getDefault().register(this, GetDrugsEvent.class);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MainActivity) {
			((MainActivity) activity).registerAdjuvantsFragment(this);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, GetDrugsEvent.class);
	}

	private boolean initTreeManager() {
		if (SedicApplication.getInstance().getDrugs() != null) {
			Collection<DrugBean> drugs = SedicApplication.getInstance().getDrugs().values();
			final TreeBuilder<DrugBean> treeBuilder = new TreeBuilder<DrugBean>(manager);
			if (drugs != null) {

				DrugBean root = null;
				for (DrugBean bean : drugs) {
					if (bean.getBeanName().equalsIgnoreCase("chemical actions and uses")) {
						root = bean;
						break;
					}
				}

				List<DrugBean> parents = new ArrayList<DrugBean>();
				for (DrugBean bean : root.getDrugChildren()) {
					parents.add(bean);
				}

				for (DrugBean bean : drugs) {
					if (bean.getBeanName().equalsIgnoreCase("lipids")) {
						parents.add(bean);
						break;
					}
				}

				for (DrugBean bean : drugs) {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.expandable_list_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		treeView = (TreeViewList) view.findViewById(R.id.tree_view);

		if (initialized) {
			simpleAdapter = new BeanTreeAdapter<DrugBean>(getActivity(), selected, manager, LEVEL_NUMBER, true);
			simpleAdapter.setCheckedChangedListener((MainActivity) getActivity());
			treeView.setAdapter(simpleAdapter);
			treeView.setCollapsible(true);
			manager.collapseChildren(null);
		}
	}

	public void onEventMainThread(GetDrugsEvent e) {
		Log.e("debug", "GetDrugsEvent");
		if (!initialized) {
			initTreeManager();
			simpleAdapter = new BeanTreeAdapter<DrugBean>(getActivity(), selected, manager, LEVEL_NUMBER, true);
			treeView.setAdapter(simpleAdapter);
			treeView.setCollapsible(true);
			simpleAdapter.setCheckedChangedListener((MainActivity) getActivity());
			manager.collapseChildren(null);
		}
	}

	public Set<DrugBean> getSelection() {
		return selected;
	}

}
