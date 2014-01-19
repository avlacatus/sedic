package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;

import ro.infoiasi.sedic.android.adapter.EntityAdapter;
import ro.infoiasi.sedic.android.adapter.RoadsAdapter;
import ro.infoiasi.sedic.android.communication.task.Message;
import ro.infoiasi.sedic.android.communication.task.RoadsServiceTask;
import ro.infoiasi.sedic.android.communication.task.Message.EntityType;
import ro.infoiasi.sedic.android.communication.task.Message.RequestType;
import ro.infoiasi.sedic.android.model.Road;
import android.view.View;
import android.widget.ListView;

public class RoadsActivity extends EntityActivity<Road> {

	@SuppressWarnings("unused")
	private static final String tag = RoadsActivity.class.getSimpleName();
	private RoadsAdapter adapter = null;

	protected void onRefresh() {
		new RoadsServiceTask(this, this).execute(new Message(RequestType.GET,
				getEntityType()));
	}

	@Override
	protected void onAdd() {

	}

	@Override
	public EntityType getEntityType() {
		return EntityType.ROAD;
	}

	@Override
	protected EntityAdapter<Road> getAdapter() {
		if (adapter == null) {
			adapter = new RoadsAdapter(this, new ArrayList<Road>());
		}
		return adapter;
	}

	@Override
	protected void onOpenEntity(Road item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onEditEntity(Road item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRemoveEntity(Road road) {
		new RoadsServiceTask(this, this).execute(new Message(
				RequestType.DELETE, getEntityType(), String.valueOf(road
						.getID())));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}

}
