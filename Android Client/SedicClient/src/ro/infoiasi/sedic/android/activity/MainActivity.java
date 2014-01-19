package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;
import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.communication.event.GetCompactRemediesEvent;
import ro.infoiasi.sedic.android.communication.event.GetPlantsEvent;
import ro.infoiasi.sedic.android.communication.event.GetRemedyDetailsEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.communication.task.GetCompactRemedyListServiceTask;
import ro.infoiasi.sedic.android.communication.task.GetPlantsServiceTask;
import ro.infoiasi.sedic.android.communication.task.GetRemedyDetailsServiceTask;
import ro.infoiasi.sedic.android.communication.task.Response;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.greenrobot.event.EventBus;

public class MainActivity extends Activity {
	private ListView mLeftDrawer;
	private DrawerLayout mDrawerLayout;
	private TextView mTestView;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		findViews();
		setupListeners();
		EventBus.getDefault().register(this, GetPlantsEvent.class, GetCompactRemediesEvent.class,
				GetRemedyDetailsEvent.class);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, GetPlantsEvent.class, GetCompactRemediesEvent.class,
				GetRemedyDetailsEvent.class);
	}

	private void findViews() {
		mLeftDrawer = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mTestView = (TextView) findViewById(R.id.result_text);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void setupListeners() {
		ArrayList<String> menuOptions = new ArrayList<String>();
		menuOptions.add("GetPlants");
		menuOptions.add("GetCompactRemedyList");
		menuOptions.add("GetRemedyDetails");
		DrawerAdapter adapter = new DrawerAdapter(this, R.layout.item_drawer_list_layout, R.id.idl_text, menuOptions);
		mLeftDrawer.setAdapter(adapter);
		mLeftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					new GetPlantsServiceTask().execute();
					mDrawerLayout.closeDrawer(Gravity.START);
					break;
				case 1:
					new GetCompactRemedyListServiceTask().execute();
					mDrawerLayout.closeDrawer(Gravity.START);
					break;
				case 2:
					new GetRemedyDetailsServiceTask(20000).execute();
					mDrawerLayout.closeDrawer(Gravity.START);
					break;
				}

			}

		});

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_am, R.string.app_name,
				R.string.app_name) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle("Semantic Medicine");
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("Select an option");
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	public void onEventMainThread(GetPlantsEvent e) {
		method(e);
	}

	public void onEventMainThread(GetRemedyDetailsEvent e) {
		method(e);
	}

	public void onEventMainThread(GetCompactRemediesEvent e) {
		method(e);
	}

	private void method(ResponseEvent e) {
		Response<?> response = e.getResponse();
		if (response.getErrorMessage() != null) {
			Toast.makeText(this, e.getClass().toString() + e.getResponse().toString(), Toast.LENGTH_LONG).show();
			String text = "";
			text += e.getClass().getSimpleName() + " " + e.getResponse().getStatus() + " " + response.getErrorMessage()
					+ " " + e.getResponse().getData();
			mTestView.setText(text);
		} else {
			Toast.makeText(this, e.getClass().toString() + e.getResponse().toString(), Toast.LENGTH_LONG).show();
			String text = "";
			text += e.getClass().getSimpleName() + " " + e.getResponse().getStatus() + " " + e.getResponse().getData();
			mTestView.setText(text);
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	class DrawerAdapter extends ArrayAdapter<String> {

		public DrawerAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
			super(context, R.layout.item_drawer_list_layout, R.id.idl_text, objects);
		}

	}

}
