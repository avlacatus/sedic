package ro.infoiasi.sedic.android.adapter;

import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.model.Indicator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IndicatorsAdapter extends EntityAdapter<Indicator> {

	public IndicatorsAdapter(Context context, List<Indicator> data) {
		super(context, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IndicatorViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater
					.inflate(R.layout.item_indicator_layout, null);
			holder = new IndicatorViewHolder();
			holder.ii_name = (TextView) convertView.findViewById(R.id.ii_name);

			holder.ir_code = (TextView) convertView.findViewById(R.id.ii_code);

			convertView.setTag(holder);
		} else {
			holder = (IndicatorViewHolder) convertView.getTag();
		}

		Indicator item = getItem(position);
		if (item != null) {
			holder.ii_name.setText(item.getName());
			holder.ir_code.setText(item.getCode());
		}

		return convertView;
	}

	private class IndicatorViewHolder {
		TextView ii_name;
		TextView ir_code;

	}

}
