package ro.infoiasi.sedic.android.activity;

import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.RoadsBrowserApplication;
import ro.infoiasi.sedic.android.adapter.EntityAdapter;
import ro.infoiasi.sedic.android.communication.task.Response;
import ro.infoiasi.sedic.android.communication.task.SayHelloTask;
import ro.infoiasi.sedic.android.communication.task.Message.EntityType;
import ro.infoiasi.sedic.android.communication.task.Response.ResponseStatus;
import ro.infoiasi.sedic.android.communication.task.SayHelloTask.SayHelloResponder;
import ro.infoiasi.sedic.android.util.EntityOperationsCallback;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

public abstract class EntityActivity<E> extends ListActivity implements
		EntityOperationsCallback<E>, SayHelloResponder {

	protected RoadsBrowserApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (RoadsBrowserApplication) getApplication();

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.list_activity_layout);
		findViews();
		setupListeners();

	}

	private void findViews() {
	}

	private void setupListeners() {
		setListAdapter(getAdapter());
		registerForContextMenu(getListView());
	}

	@Override
	protected void onResume() {
		super.onResume();
		onRefresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.mi_add) {
			onAdd();
		}
		if (item.getItemId() == R.id.mi_refresh) {
			onRefresh();
		}
		if (item.getItemId() == R.id.mi_say_hello) {
			new SayHelloTask(this).execute("Ana");
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == android.R.id.list) {
			menu.setHeaderTitle(getEntityType().toString() + " options");
			String[] menuItems = { "Open", "Edit", "Remove" };
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem menuItem) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuItem
				.getMenuInfo();

		E item = getAdapter().getItem(info.position);
		if (item != null) {
			int menuItemIndex = menuItem.getItemId();
			switch (menuItemIndex) {
			case 0:
				onOpenEntity(item);
				break;
			case 1:
				onEditEntity(item);
				break;
			case 2:
				onRemoveEntity(item);
				break;
			default:
				return false;
			}
		}
		return true;
	}

	protected abstract EntityType getEntityType();

	protected abstract EntityAdapter<E> getAdapter();

	protected abstract void onRefresh();

	protected abstract void onAdd();

	protected abstract void onRemoveEntity(E item);

	protected abstract void onOpenEntity(E item);

	protected abstract void onEditEntity(E item);

	@Override
	public void onEntityOperationStarted() {
		setProgressBarIndeterminateVisibility(true);
	}

	@Override
	public void onAddEntityOperationFinished() {
		setProgressBarIndeterminateVisibility(false);
		Toast.makeText(this, "addition ok", Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onGetEntitiesOperationFinished(Response<E> result) {
		if (result.getStatus().equals(ResponseStatus.OK)) {
			if (result.getData() != null) {
				List<E> data = (List<E>) result.getData();
				getAdapter().clear();
				getAdapter().addAll(data);
			}
			setProgressBarIndeterminateVisibility(false);
		} else {
			setProgressBarIndeterminateVisibility(false);
			Toast.makeText(this, "An error ocurred", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onUpdateEntityOperationFinished() {
		setProgressBarIndeterminateVisibility(false);
		Toast.makeText(this, "update ok", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDeleteEntityOperationFinished() {
		setProgressBarIndeterminateVisibility(false);
		onRefresh();
	}

	private ProgressDialog progressDialog = null;

	public void onSayStarted() {
		progressDialog = ProgressDialog.show(this, "Wait",
				"Sending your greetings..");
	}

	public void onSayResponded(String result) {
		progressDialog.dismiss();
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}

}
