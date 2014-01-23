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
import ro.infoiasi.sedic.android.activity.TreeViewActivity;
import ro.infoiasi.sedic.android.adapter.BeanTreeAdapter;
import ro.infoiasi.sedic.android.communication.event.GetDiseasesEvent;
import ro.infoiasi.sedic.android.communication.event.GetDrugsEvent;
import ro.infoiasi.sedic.android.model.Bean;
import ro.infoiasi.sedic.android.model.DrugBean;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.greenrobot.event.EventBus;

public class SelectAdjuvantsFragment extends Fragment {

    private final Set<Bean> selected = new HashSet<Bean>();

    @SuppressWarnings("unused")
    private static final String TAG = TreeViewActivity.class.getSimpleName();
    private TreeViewList treeView;

    private static final int LEVEL_NUMBER = 4;
    private TreeStateManager<Bean> manager = null;
    private BeanTreeAdapter simpleAdapter;
    private boolean initialized = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new InMemoryTreeStateManager<Bean>();
        initialized = initTreeManager();
        EventBus.getDefault().register(this, GetDrugsEvent.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this, GetDrugsEvent.class);
    }

    private boolean initTreeManager() {
        if (SedicApplication.getInstance().getDrugs() != null) {
            final TreeBuilder<Bean> treeBuilder = new TreeBuilder<Bean>(manager);
            Collection<DrugBean> drugs = SedicApplication.getInstance().getDrugs().values();
            if (drugs != null) {

                DrugBean root = null;
                for (DrugBean bean : drugs) {
                    if (bean.getBeanName().equalsIgnoreCase("chemicals and drugs")) {
                        root = bean;
                        break;
                    }
                }
                treeBuilder.sequentiallyAddNextNode(root, 0);

                List<DrugBean> currentLevelBeans = new ArrayList<DrugBean>();
                for (DrugBean bean : root.getDrugChildren()) {
                    treeBuilder.addRelation(root, bean);
                    currentLevelBeans.add(bean);
                }
                
                for (DrugBean bean : currentLevelBeans) {
                    for (DrugBean children : bean.getDrugChildren()) {
                        try {
                            treeBuilder.addRelation(bean, children);
                        } catch (NodeAlreadyInTreeException e) {
                            
                        }
                    }
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.expandable_list_layout, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        treeView = (TreeViewList) view.findViewById(R.id.tree_view);
        simpleAdapter = new BeanTreeAdapter(getActivity(), selected, manager, LEVEL_NUMBER);
        treeView.setAdapter(simpleAdapter);
        treeView.setCollapsible(true);
        manager.collapseChildren(null);
        registerForContextMenu(treeView);
    }

    public void onEventMainThread(GetDiseasesEvent e) {
        if (!initialized) {
            initTreeManager();
        }
    }

}
