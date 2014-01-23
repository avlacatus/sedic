package ro.infoiasi.sedic.android.adapter;

import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DiseaseListAdapter extends ArrayAdapter<DiseaseBean> {
	private LayoutInflater mInflater;

	public DiseaseListAdapter(Context context, int textViewResourceId, List<DiseaseBean> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_remedy_layout, null);
		}
		DiseaseBean bean = getItem(position);
		if (bean != null) {
			TextView label = (TextView) convertView.findViewById(R.id.ir_name);
			label.setText(bean.getDiseaseName());
		}
		return convertView;
	}
}
