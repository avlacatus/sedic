package ro.infoiasi.sedic.android.activity;

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
import ro.infoiasi.sedic.android.adapter.BeanTreeAdapter;
import ro.infoiasi.sedic.android.model.Bean;
import ro.infoiasi.sedic.android.model.DrugBean;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Demo activity showing how the tree view can be used.
 * 
 */
public class TreeViewActivity extends Activity {

    private final Set<Bean> selected = new HashSet<Bean>();

    private static final String TAG = TreeViewActivity.class.getSimpleName();
    private TreeViewList treeView;

    private static final int LEVEL_NUMBER = 4;
    private TreeStateManager<Bean> manager = null;
    private BeanTreeAdapter simpleAdapter;
    private boolean collapsible;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean newCollapsible;
        if (savedInstanceState == null) {

            manager = new InMemoryTreeStateManager<Bean>();
            final TreeBuilder<Bean> treeBuilder = new TreeBuilder<Bean>(manager);

            Collection<DrugBean> drugs = SedicApplication.getInstance().getDrugs().values();
            if (drugs != null) {
                List<DrugBean> currentLevelBeans = new ArrayList<DrugBean>();

                for (DrugBean bean : drugs) {
                    if (bean.getDrugChildren().isEmpty()) {
                        treeBuilder.sequentiallyAddNextNode(bean, 0);
                        currentLevelBeans.add(bean);
                    }
                }

                for (DrugBean bean : currentLevelBeans) {
                    for (DrugBean bean2 : drugs) {
                        if (bean2.getDrugChildren().contains(bean)) {
                            try {
                                treeBuilder.addRelation(bean, bean2);
                            } catch (NodeAlreadyInTreeException e) {
                                Log.e("debug", "Could not add " + bean.getBeanName() + " -> " + bean2.getBeanName());
                            }
                        }
                    }
                }
            }
            Log.d(TAG, manager.toString());
            newCollapsible = true;
        } else {

            manager = (TreeStateManager<Bean>) savedInstanceState.getSerializable("treeManager");
            if (manager == null) {
                manager = new InMemoryTreeStateManager<Bean>();
            }
            newCollapsible = savedInstanceState.getBoolean("collapsible");
        }
        setContentView(R.layout.expandable_list_layout);
        treeView = (TreeViewList) findViewById(R.id.tree_view);
        simpleAdapter = new BeanTreeAdapter(this, selected, manager, LEVEL_NUMBER);
        treeView.setAdapter(simpleAdapter);
        setCollapsible(newCollapsible);
        registerForContextMenu(treeView);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putSerializable("treeManager", manager);
        outState.putBoolean("collapsible", this.collapsible);
        super.onSaveInstanceState(outState);
    }

    protected final void setCollapsible(final boolean newCollapsible) {
        this.collapsible = newCollapsible;
        treeView.setCollapsible(this.collapsible);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final MenuItem collapsibleMenu = menu.findItem(R.id.collapsible_menu_item);
        if (collapsible) {
            collapsibleMenu.setTitle(R.string.collapsible_menu_disable);
        } else {
            collapsibleMenu.setTitle(R.string.collapsible_menu_enable);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.collapsible_menu_item) {
            setCollapsible(!this.collapsible);
        } else if (item.getItemId() == R.id.expand_all_menu_item) {
            manager.expandEverythingBelow(null);
        } else if (item.getItemId() == R.id.collapse_all_menu_item) {
            manager.collapseChildren(null);
        } else {
            return false;
        }
        return true;
    }

}