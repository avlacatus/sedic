package ro.infoiasi.sedic.android.adapter;

import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.model.Road;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RoadsAdapter extends EntityAdapter<Road> {


	public RoadsAdapter(Context context, List<Road> data) {
		super(context, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RoadViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.item_road_layout, null);
			holder = new RoadViewHolder();
			holder.ir_indicative = (TextView) convertView
					.findViewById(R.id.ir_indicative);
			holder.ir_length = (TextView) convertView
					.findViewById(R.id.ir_length);
			holder.ir_region = (TextView) convertView
					.findViewById(R.id.ir_region);
			holder.ir_road_type = (TextView) convertView
					.findViewById(R.id.ir_road_type);
			convertView.setTag(holder);
		} else {
			holder = (RoadViewHolder) convertView.getTag();
		}

		Road item = getItem(position);
		if (item != null) {
			holder.ir_indicative.setText(item.getIndicative());
			holder.ir_length.setText(String.valueOf(item.getLength()));
			holder.ir_region.setText(item.getRegion());
			holder.ir_road_type.setText(item.getType());
		}

		return convertView;
	}

	private class RoadViewHolder {
		TextView ir_indicative;
		TextView ir_region;
		TextView ir_road_type;
		TextView ir_length;

	}

}
