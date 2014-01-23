package ro.infoiasi.sedic.android.adapter;

import java.util.List;
import java.util.Map;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.model.PlantBean;
import ro.infoiasi.sedic.android.model.RemedyBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RemedyListAdapter extends ArrayAdapter<RemedyBean> {
    private LayoutInflater mInflater;
    private Map<Long, PlantBean> mPlants;

    public RemedyListAdapter(Context context, int textViewResourceId, List<RemedyBean> objects) {
        super(context, textViewResourceId, objects);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPlants = SedicApplication.getInstance().getPlantList();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RemedyItemHolder tag = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_remedy_layout, null);
            tag = new RemedyItemHolder();
            tag.remedyName = (TextView) convertView.findViewById(R.id.ir_name);
            convertView.setTag(tag);
        } else {
            tag = (RemedyItemHolder) convertView.getTag();
        }

        RemedyBean bean = getItem(position);
        PlantBean plantBean = mPlants.get(bean.getRemedyPlantId());
        if (plantBean != null) {
            tag.remedyName.setText(plantBean.getPlantName());
        }
        return convertView;
    }

    private class RemedyItemHolder {
        TextView remedyName;
    }
}
