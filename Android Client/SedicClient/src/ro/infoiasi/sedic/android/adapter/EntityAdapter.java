package ro.infoiasi.sedic.android.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class EntityAdapter<E> extends BaseAdapter {

	private Context context;
	private List<E> data;

	public EntityAdapter(Context context, List<E> data) {
		this.context = context;
		this.data = data;
	}

	public void addAll(List<E> result) {
		if (data == null)
			data = new ArrayList<E>();
		data.addAll(result);
		notifyDataSetChanged();
	}

	public void clear() {
		if (data != null) {
			data.clear();
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public E getItem(int position) {
		return data == null ? null : data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public Context getContext() {
		return context;
	}

}
