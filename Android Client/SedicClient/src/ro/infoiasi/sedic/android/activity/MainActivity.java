package ro.infoiasi.sedic.android.activity;

import ro.infoiasi.sedic.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
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
		browseRoadsButton = (Button) findViewById(R.id.roads_button);
		browseIndicatorsButton = (Button) findViewById(R.id.indicators_button);
		browseMappingButton = (Button) findViewById(R.id.mapping_button);

	}

	private void setupListeners() {
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

}
