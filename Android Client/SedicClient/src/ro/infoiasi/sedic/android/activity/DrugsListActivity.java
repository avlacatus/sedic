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
import ro.infoiasi.sedic.android.adapter.DrugListAdapter;
import ro.infoiasi.sedic.android.communication.event.GetCompactRemediesEvent;
import ro.infoiasi.sedic.android.communication.event.RemedySearchEvent;
import ro.infoiasi.sedic.android.communication.task.RemedySearchServiceTask;
import ro.infoiasi.sedic.android.model.DrugBean;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;
import de.greenrobot.event.EventBus;

public class DrugsListActivity extends Activity {
    private static final String tag = DrugsListActivity.class.getSimpleName();
    private List<DrugBean> mDrugList;
    private Map<Long, DrugBean> mDrugMap;
    private TreeViewList treeView;

    private static final int LEVEL_NUMBER = 6;
    private TreeStateManager<DrugBean> manager = null;
    private DrugListAdapter drugAdapter;
    private boolean initialized = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.expandable_list_layout);
        EventBus.getDefault().register(this, GetCompactRemediesEvent.class, RemedySearchEvent.class);
        setupData();
        manager = new InMemoryTreeStateManager<DrugBean>();
        initialized = initTreeManager();
        setupUI();
        registerForContextMenu(treeView);
    }

    private void setupData() {
        mDrugMap = SedicApplication.getInstance().getDrugs();
        if (mDrugMap != null) {
            mDrugList = new ArrayList<DrugBean>(mDrugMap.values());
        }
    }

    private void setupUI() {
        treeView = (TreeViewList) findViewById(R.id.tree_view);
        drugAdapter = new DrugListAdapter(this, manager, LEVEL_NUMBER);
        treeView.setAdapter(drugAdapter);
        treeView.setCollapsible(true);
        manager.collapseChildren(null);
    }

    private boolean initTreeManager() {
        if (SedicApplication.getInstance().getDrugs() != null) {
            final TreeBuilder<DrugBean> treeBuilder = new TreeBuilder<DrugBean>(manager);
            if (mDrugList != null) {

                DrugBean root = null;
                for (DrugBean bean : mDrugList) {
                    if (bean.getBeanName().equalsIgnoreCase("chemicals and drugs")) {
                        root = bean;
                        break;
                    }
                }

                List<DrugBean> parents = new ArrayList<DrugBean>();
                for (DrugBean bean : root.getDrugChildren()) {
                    treeBuilder.sequentiallyAddNextNode(bean, 0);
                    parents.add(bean);
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
        EventBus.getDefault().unregister(this, GetCompactRemediesEvent.class, RemedySearchEvent.class);
    }

    public void onEventMainThread(RemedySearchEvent e) {
        Log.e(tag, "RemedySearchEvent received " + e.getResponse().getData());
        

    }

    public void onEventMainThread(GetCompactRemediesEvent e) {
        if (!initialized) {
            initTreeManager();
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
        DrugBean selectedBean = SedicApplication.getInstance().getDrugs().get(id);
        if (item.getItemId() == R.id.context_menu_browse_selected) {
            List<DrugBean> payload = new ArrayList<DrugBean>();
            payload.add(selectedBean);
            new RemedySearchServiceTask(payload, null).execute();

            Toast.makeText(this, "browsing for " + selectedBean.getBeanName(), Toast.LENGTH_LONG).show();
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
