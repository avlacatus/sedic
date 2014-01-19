package ro.infoiasi.sedic.android.adapter;

import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.model.MappedIndicator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MappingsAdapter extends EntityAdapter<MappedIndicator> {

	public MappingsAdapter(Context context, List<MappedIndicator> data) {
		super(context, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MappingViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.item_mapping_layout, null);
			holder = new MappingViewHolder();
			holder.im_indicator_id = (TextView) convertView
					.findViewById(R.id.im_indicator_id);
			holder.im_observations = (TextView) convertView
					.findViewById(R.id.im_observations);
			holder.im_road_id = (TextView) convertView
					.findViewById(R.id.im_road_id);
			holder.im_km = (TextView) convertView.findViewById(R.id.im_km);

			convertView.setTag(holder);
		} else {
			holder = (MappingViewHolder) convertView.getTag();
		}

		MappedIndicator item = getItem(position);
		if (item != null) {
			holder.im_indicator_id.setText("Ind id: ");
			holder.im_observations.setText(item.getObservations());
			holder.im_road_id.setText("Road id: ");
			holder.im_km.setText("Km: " + item.getKm());
		}

		return convertView;
	}

	private class MappingViewHolder {
		TextView im_indicator_id;
		TextView im_observations;
		TextView im_road_id;
		TextView im_km;

	}

}
