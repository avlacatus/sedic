package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;
import java.util.List;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.adapter.MainPagerAdapter;
import ro.infoiasi.sedic.android.communication.event.GetCompactRemediesEvent;
import ro.infoiasi.sedic.android.communication.event.GetDrugsEvent;
import ro.infoiasi.sedic.android.communication.event.GetPlantsEvent;
import ro.infoiasi.sedic.android.communication.event.GetRemedyDetailsEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity {
	private ListView mLeftDrawer;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		findViews();
		setupListeners();
		EventBus.getDefault().register(this, GetPlantsEvent.class, GetCompactRemediesEvent.class,
				GetRemedyDetailsEvent.class, GetDrugsEvent.class);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this, GetPlantsEvent.class, GetCompactRemediesEvent.class,
				GetRemedyDetailsEvent.class, GetDrugsEvent.class);
	}

	private void findViews() {
		mLeftDrawer = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mViewPager = (ViewPager) findViewById(R.id.main_pager);
		mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
				mDrawerLayout.closeDrawer(GravityCompat.START);
			} else {
				mDrawerLayout.openDrawer(GravityCompat.START);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setupListeners() {
		ArrayList<String> menuOptions = new ArrayList<String>();
		menuOptions.add("Browse Remedies");
		menuOptions.add("Browse Adjuvants");
		menuOptions.add("Browse Therapeutics");
		DrawerAdapter adapter = new DrawerAdapter(this, R.layout.item_drawer_list_layout, R.id.idl_text, menuOptions);
		mLeftDrawer.setAdapter(adapter);
		mLeftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0: {
					Intent intent = new Intent(MainActivity.this, RemedyListActivity.class);
					startActivity(intent);
					mDrawerLayout.closeDrawer(GravityCompat.START);
				}
					break;
				case 1: {
					Intent intent = new Intent(MainActivity.this, DrugsListActivity.class);
					startActivity(intent);
					mDrawerLayout.closeDrawer(GravityCompat.START);
				}
					break;
				case 2: {
					Intent intent = new Intent(MainActivity.this, DiseasesListActivity.class);
					startActivity(intent);
					mDrawerLayout.closeDrawer(GravityCompat.START);
				}
					break;
				default:
					break;
				}

			}

		});

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_am, R.string.app_name,
				R.string.app_name) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle("Semantic Medicine");
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle("Select an option");
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	public void onEventMainThread(GetPlantsEvent e) {
		method(e);
	}

	public void onEventMainThread(GetDrugsEvent e) {
		method(e);
	}

	public void onEventMainThread(GetRemedyDetailsEvent e) {
		method(e);
	}

	public void onEventMainThread(GetCompactRemediesEvent e) {
		method(e);
	}

	private void method(ResponseEvent e) {
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onBackPressed() {
		if (mViewPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
		}
	}

	class DrawerAdapter extends ArrayAdapter<String> {

		public DrawerAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
			super(context, R.layout.item_drawer_list_layout, R.id.idl_text, objects);
		}

	}

}
