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
import ro.infoiasi.sedic.android.communication.event.GetDiseasesEvent;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import de.greenrobot.event.EventBus;

public class SelectTherapiesFragment extends Fragment {

	private final Set<DiseaseBean> selected = new HashSet<DiseaseBean>();

	@SuppressWarnings("unused")
	private static final String TAG = SelectTherapiesFragment.class.getSimpleName();
	private TreeViewList treeView;
	private ProgressBar progress;

	private static final int LEVEL_NUMBER = 4;
	private TreeStateManager<DiseaseBean> manager = null;
	private BeanTreeAdapter<DiseaseBean> simpleAdapter;
	private boolean initialized = false;
	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = new InMemoryTreeStateManager<DiseaseBean>();
		initialized = initTreeManager();
		EventBus.getDefault().register(this, GetDiseasesEvent.class);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MainActivity) {
			((MainActivity) activity).registerTherapiesFragment(this);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, GetDiseasesEvent.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.expandable_list_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		treeView = (TreeViewList) view.findViewById(R.id.tree_view);
		progress = (ProgressBar) view.findViewById(R.id.progress);

		if (initialized) {
			simpleAdapter = new BeanTreeAdapter<DiseaseBean>(getActivity(), selected, manager, LEVEL_NUMBER, true);
			treeView.setAdapter(simpleAdapter);
			simpleAdapter.setCheckedChangedListener((MainActivity) getActivity());
			treeView.setCollapsible(true);
			manager.collapseChildren(null);
		}
	}

	public void onEventAsync(GetDiseasesEvent e) {
		Log.e("debug", "GetDiseasesEvent");
		if (!initialized) {
			handler.post(new Runnable() {

				@Override
				public void run() {
					progress.setVisibility(View.VISIBLE);
				}
			});
			initTreeManager();
			simpleAdapter = new BeanTreeAdapter<DiseaseBean>(getActivity(), selected, manager, LEVEL_NUMBER, true);
			handler.post(new Runnable() {

				@Override
				public void run() {
					treeView.setAdapter(simpleAdapter);
					simpleAdapter.setCheckedChangedListener((MainActivity) getActivity());
					treeView.setCollapsible(true);
					manager.collapseChildren(null);
					progress.setVisibility(View.GONE);
				}
			});
		} else {
			handler.post(new Runnable() {

				@Override
				public void run() {
					progress.setVisibility(View.GONE);
				}
			});
		}
	}

	private boolean initTreeManager() {
		if (SedicApplication.getInstance().getDiseases() != null) {
			final TreeBuilder<DiseaseBean> treeBuilder = new TreeBuilder<DiseaseBean>(manager);
			Collection<DiseaseBean> diseases = SedicApplication.getInstance().getDiseases().values();
			if (diseases != null) {

				DiseaseBean root = null;
				for (DiseaseBean bean : diseases) {
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

	public Set<DiseaseBean> getSelection() {
		return selected;
	}

}
