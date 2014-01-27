package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ro.infoiasi.sedic.android.R;
import ro.infoiasi.sedic.android.adapter.BeanTreeAdapter;
import ro.infoiasi.sedic.android.adapter.MainPagerAdapter;
import ro.infoiasi.sedic.android.communication.event.GetCompactRemediesEvent;
import ro.infoiasi.sedic.android.communication.event.GetDrugsEvent;
import ro.infoiasi.sedic.android.communication.event.GetPlantsEvent;
import ro.infoiasi.sedic.android.communication.event.GetRemedyDetailsEvent;
import ro.infoiasi.sedic.android.communication.event.RemedySearchEvent;
import ro.infoiasi.sedic.android.communication.event.ResponseEvent;
import ro.infoiasi.sedic.android.communication.task.RemedySearchServiceTask;
import ro.infoiasi.sedic.android.communication.task.Response.ResponseStatus;
import ro.infoiasi.sedic.android.communication.task.ServiceTask;
import ro.infoiasi.sedic.android.fragment.SelectAdjuvantsFragment;
import ro.infoiasi.sedic.android.fragment.SelectMedicalFactorFragment;
import ro.infoiasi.sedic.android.fragment.SelectTherapiesFragment;
import ro.infoiasi.sedic.android.model.Bean;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.model.DrugBean;
import ro.infoiasi.sedic.android.model.MedicalFactorBean;
import ro.infoiasi.sedic.android.model.RemedyBean;
import ro.infoiasi.sedic.android.util.DialogUtils;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity implements BeanTreeAdapter.TreeCellCheckedChangeListener {
	private ListView mLeftDrawer;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	private Button mLeftButton;
	private Button mRightButton;

	private SelectAdjuvantsFragment mAdjuvantsFragment;
	private SelectTherapiesFragment mTherapiesFragment;
	private SelectMedicalFactorFragment mMedicalFactorsFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		findViews();
		setupListeners();
	}

	@Override
	protected void onStart() {
		super.onStart();
		EventBus.getDefault().register(this, GetPlantsEvent.class, GetCompactRemediesEvent.class,
				GetRemedyDetailsEvent.class, GetDrugsEvent.class, RemedySearchEvent.class);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this, GetPlantsEvent.class, GetCompactRemediesEvent.class,
				GetRemedyDetailsEvent.class, GetDrugsEvent.class, RemedySearchEvent.class);
	}

	private void findViews() {
		mLeftDrawer = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mViewPager = (ViewPager) findViewById(R.id.main_pager);
		mLeftButton = (Button) findViewById(R.id.bottom_button_left);
		mRightButton = (Button) findViewById(R.id.bottom_button_right);
		mLeftButton.setVisibility(View.GONE);
		mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);

		mViewPager.setCurrentItem(0);
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
					Intent intent = new Intent(MainActivity.this, AdjuvantsListActivity.class);
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

		mLeftButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int currentItem = mViewPager.getCurrentItem();
				if (currentItem > 0) {
					mViewPager.setCurrentItem(currentItem - 1, true);
				}
			}
		});

		mRightButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int currentItem = mViewPager.getCurrentItem();
				if (currentItem == 2) {
					performQuery();

				} else if (currentItem >= 0 && currentItem < 2) {
					mViewPager.setCurrentItem(currentItem + 1, true);

				}
			}
		});
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					mLeftButton.setVisibility(View.GONE);
					mRightButton.setEnabled(true);
					mRightButton.setText("Next");
					break;
				case 1:
					mLeftButton.setVisibility(View.VISIBLE);
					mRightButton.setEnabled(true);
					mRightButton.setText("Next");
					break;
				case 2:
					mLeftButton.setVisibility(View.VISIBLE);
					mLeftButton.setEnabled(true);
					mRightButton.setText("Perform query");

					mRightButton.setEnabled(isPerformButtonEnabled());
					break;
				default:
					break;
				}
			}
		});
	}

	private boolean isPerformButtonEnabled() {
		boolean output = false;

		if (mAdjuvantsFragment != null && !mAdjuvantsFragment.getSelection().isEmpty()) {
			return true;
		}

		if (mTherapiesFragment != null && !mTherapiesFragment.getSelection().isEmpty()) {
			return true;
		}

		return output;
	}

	private void performQuery() {
		Set<DrugBean> adjuvantSelection = mAdjuvantsFragment.getSelection();
		Set<DiseaseBean> therapiesSelection = mTherapiesFragment.getSelection();
		Set<Bean> medicalfactorsSelection = mMedicalFactorsFragment.getSelection();
		Integer minAge = mMedicalFactorsFragment.getMinimumAge();

		List<MedicalFactorBean> mfs = new ArrayList<MedicalFactorBean>();
		List<DiseaseBean> contraindicated = new ArrayList<DiseaseBean>();
		for (Bean bean : medicalfactorsSelection) {
			if (bean instanceof MedicalFactorBean) {
				mfs.add((MedicalFactorBean) bean);
			} else if (bean instanceof DiseaseBean) {
				contraindicated.add((DiseaseBean) bean);
			}
		}

		ServiceTask<RemedyBean> queryTask = new RemedySearchServiceTask(new ArrayList<DrugBean>(adjuvantSelection),
				new ArrayList<DiseaseBean>(therapiesSelection), mfs, contraindicated, minAge != null ? minAge : -1);
		queryTask.execute();
		DialogUtils.showProgressDialog(this, "Please wait..");
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

	public void onEventMainThread(RemedySearchEvent e) {
		DialogUtils.hideProgressDialog(this);
		if (e.getResponse().getStatus().equals(ResponseStatus.OK)) {
			if (e.getResponse().getData() instanceof Map) {
				Map<Long, RemedyBean> responseData = (Map<Long, RemedyBean>) e.getResponse().getData();
				long[] ids = new long[responseData.size()];
				int i = 0;
				for (Long id : responseData.keySet()) {
					ids[i] = id;
					i++;
				}
				Intent intent = new Intent(this, RemedyListActivity.class);
				intent.putExtra(RemedyListActivity.INTENT_EXTRA_REMEDY_IDS, ids);
				intent.putExtra(RemedyListActivity.INTENT_EXTRA_ACTIVITY_TITLE, "Search Results");
				startActivity(intent);
			}
		} else {
			Toast.makeText(this, e.getResponse().getErrorMessage(), Toast.LENGTH_LONG).show();
		}
	}

	private void method(ResponseEvent e) {
	}

	public void registerAdjuvantsFragment(SelectAdjuvantsFragment frag) {
		this.mAdjuvantsFragment = frag;
	}

	public void registerTherapiesFragment(SelectTherapiesFragment frag) {
		this.mTherapiesFragment = frag;
	}

	public void registerMedicalFactorsFragment(SelectMedicalFactorFragment frag) {
		this.mMedicalFactorsFragment = frag;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onBackPressed() {
		if (mViewPager.getCurrentItem() == 0) {
			super.onBackPressed();
		} else {
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
		}
	}

	class DrawerAdapter extends ArrayAdapter<String> {

		public DrawerAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
			super(context, R.layout.item_drawer_list_layout, R.id.idl_text, objects);
		}

	}

	@Override
	public void onCheckedChanged(Object id, boolean isChecked) {
		if (mViewPager.getCurrentItem() == 2) {
			mRightButton.setEnabled(isPerformButtonEnabled());
		}
	}

}
