package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;

import ro.infoiasi.sedic.android.adapter.EntityAdapter;
import ro.infoiasi.sedic.android.adapter.MappingsAdapter;
import ro.infoiasi.sedic.android.communication.task.RemedyInfoServiceTask;
import ro.infoiasi.sedic.android.communication.task.Message;
import ro.infoiasi.sedic.android.communication.task.Message.EntityType;
import ro.infoiasi.sedic.android.communication.task.Message.RequestType;
import ro.infoiasi.sedic.android.model.MappedIndicator;

public class MappingsActivity extends EntityActivity<MappedIndicator> {

	@SuppressWarnings("unused")
	private static final String tag = MappingsActivity.class.getSimpleName();
	private MappingsAdapter adapter = null;

	@Override
	public EntityType getEntityType() {
		return EntityType.MAPPING;
	}

	@Override
	protected EntityAdapter<MappedIndicator> getAdapter() {
		if (adapter == null) {
			adapter = new MappingsAdapter(this, new ArrayList<MappedIndicator>());
		}
		return adapter;
	}

	protected void onRefresh() {
		new RemedyInfoServiceTask(this).execute(new Message(RequestType.GET, getEntityType()));
	}

	@Override
	protected void onAdd() {

	}

	@Override
	protected void onOpenEntity(MappedIndicator item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onEditEntity(MappedIndicator item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRemoveEntity(MappedIndicator road) {
		new RemedyInfoServiceTask(this).execute(new Message(RequestType.DELETE, getEntityType(), String.valueOf(road
				.getID())));
	}

}
