package ro.infoiasi.sedic.android.adapter;

import java.util.Set;

import pl.polidea.treeview.AbstractTreeViewAdapter;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeStateManager;
import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.model.Bean;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This is a very simple adapter that provides very basic tree view with a checkboxes and simple item description.
 * 
 */
public class BeanTreeAdapter<E extends Bean> extends AbstractTreeViewAdapter<E> {

    private final Set<E> selected;

    private final OnCheckedChangeListener onCheckedChange = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
            final E id = (E) buttonView.getTag();
            changeSelected(isChecked, id);
        }

    };

    private void changeSelected(final boolean isChecked, final E id) {
        if (isChecked) {
            selected.add(id);
        } else {
            selected.remove(id);
        }
    }

    public BeanTreeAdapter(Activity treeViewListDemo, final Set<E> selected,
            final TreeStateManager<E> treeStateManager, final int numberOfLevels) {
        super(treeViewListDemo, treeStateManager, numberOfLevels);
        this.selected = selected;
    }

    @Override
    public View getNewChildView(final TreeNodeInfo<E> treeNodeInfo) {
        final LinearLayout viewLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(
                R.layout.demo_list_item, null);
        return updateView(viewLayout, treeNodeInfo);
    }

    @Override
    public LinearLayout updateView(final View view, final TreeNodeInfo<E> treeNodeInfo) {
        final LinearLayout viewLayout = (LinearLayout) view;
        final TextView descriptionView = (TextView) viewLayout.findViewById(R.id.demo_list_item_description);
        descriptionView.setText(treeNodeInfo.getId().getBeanName());
        final CheckBox box = (CheckBox) viewLayout.findViewById(R.id.demo_list_checkbox);
        box.setTag(treeNodeInfo.getId());
        box.setChecked(selected.contains(treeNodeInfo.getId()));
        box.setOnCheckedChangeListener(onCheckedChange);
        return viewLayout;
    }

    @Override
    public void handleItemClick(final View view, final Object id) {
        final E longId = (E) id;
        final TreeNodeInfo<E> info = getManager().getNodeInfo(longId);
        if (info.isWithChildren()) {
            super.handleItemClick(view, id);
        } else {
            final ViewGroup vg = (ViewGroup) view;
            final CheckBox cb = (CheckBox) vg.findViewById(R.id.demo_list_checkbox);
            cb.performClick();
        }
    }

    @Override
    public long getItemId(final int position) {
        return getTreeId(position).getBeanID();
    }
}