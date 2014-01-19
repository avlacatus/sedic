package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;

import ro.infoiasi.sedic.android.adapter.EntityAdapter;
import ro.infoiasi.sedic.android.adapter.IndicatorsAdapter;
import ro.infoiasi.sedic.android.communication.task.Message.EntityType;
import ro.infoiasi.sedic.android.model.Indicator;

public class IndicatorsActivity extends EntityActivity<Indicator> {

	@SuppressWarnings("unused")
	private static final String tag = IndicatorsActivity.class.getSimpleName();
	private IndicatorsAdapter adapter = null;

	protected void onRefresh() {
//		new IndicatorsServiceTask().execute(new Message(RequestType.GET, getEntityType()));
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.INDICATOR;
	}

	@Override
	protected void onAdd() {

	}

	@Override
	protected EntityAdapter<Indicator> getAdapter() {
		if (adapter == null) {
			adapter = new IndicatorsAdapter(this, new ArrayList<Indicator>());
		}
		return adapter;
	}

	@Override
	protected void onOpenEntity(Indicator item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onEditEntity(Indicator item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRemoveEntity(Indicator item) {
		//TODO
//		new IndicatorsServiceTask().execute(String.valueOf(item
//				.getID());
	}

}
