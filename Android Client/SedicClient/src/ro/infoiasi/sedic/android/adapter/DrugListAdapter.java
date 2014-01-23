package ro.infoiasi.sedic.android.adapter;

import pl.polidea.treeview.AbstractTreeViewAdapter;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeStateManager;
import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.model.DrugBean;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DrugListAdapter extends AbstractTreeViewAdapter<DrugBean> {

    private LayoutInflater mInflater;

    public DrugListAdapter(Activity activity, TreeStateManager<DrugBean> treeStateManager, int numberOfLevels) {
        super(activity, treeStateManager, numberOfLevels);
        mInflater = activity.getLayoutInflater();
    }

    @Override
    public long getItemId(int position) {
        return getTreeId(position).getBeanID();
    }

    @Override
    public View getNewChildView(TreeNodeInfo<DrugBean> treeNodeInfo) {
        View output = mInflater.inflate(R.layout.item_drug_disease_layout, null);
        DrugItemHolder tag = new DrugItemHolder();
        tag.name = (TextView) output.findViewById(R.id.idd_name);
        output.setTag(tag);
        return updateView(output, treeNodeInfo);
    }

    @Override
    public View updateView(View view, TreeNodeInfo<DrugBean> treeNodeInfo) {
        DrugItemHolder tag = (DrugItemHolder) view.getTag();
        tag.name.setText(treeNodeInfo.getId().getBeanName());
        return view;
    }

    private class DrugItemHolder {
        TextView name;
    }
}
