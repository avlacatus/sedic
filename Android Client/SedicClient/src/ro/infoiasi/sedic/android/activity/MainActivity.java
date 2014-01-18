package ro.infoiasi.sedic.android.activity;

import java.util.ArrayList;
import java.util.List;

import ro.infoiasi.sedic.android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private ListView mLeftDrawer;
	private DrawerLayout mDrawerLayout;
	private Button browseRoadsButton;
	private Button browseIndicatorsButton;
	private Button browseMappingButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		findViews();
		setupListeners();
	}

	private void findViews() {
		mLeftDrawer = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		browseRoadsButton = (Button) findViewById(R.id.roads_button);
		browseIndicatorsButton = (Button) findViewById(R.id.indicators_button);
		browseMappingButton = (Button) findViewById(R.id.mapping_button);

	}

	private void setupListeners() {
		ArrayList<String> menuOptions = new ArrayList<String>();
		menuOptions.add("Remedy Lookup");
		menuOptions.add("Browse Plants");
		menuOptions.add("Browse Therapeutic and Adjuvant usages");
		DrawerAdapter adapter = new DrawerAdapter(this,
				R.layout.item_drawer_list_layout, R.id.idl_text, menuOptions);
		mLeftDrawer.setAdapter(adapter);
		mLeftDrawer
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Toast.makeText(MainActivity.this, arg2,
								Toast.LENGTH_SHORT).show();
					}

				});
		browseRoadsButton.setOnClickListener(this);
		browseIndicatorsButton.setOnClickListener(this);
		browseMappingButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.roads_button:
			break;
		case R.id.indicators_button:
			intent = new Intent(MainActivity.this, IndicatorsActivity.class);
			startActivity(intent);
			break;
		case R.id.mapping_button:
			intent = new Intent(MainActivity.this, MappingsActivity.class);
			startActivity(intent);
			break;

		}

	}

	class DrawerAdapter extends ArrayAdapter<String> {

		public DrawerAdapter(Context context, int resource,
				int textViewResourceId, List<String> objects) {
			super(context, R.layout.item_drawer_list_layout, R.id.idl_text,
					objects);
		}

	}

}
