package ro.infoiasi.sedic.android;

import java.util.List;

import ro.infoiasi.sedic.android.model.Indicator;
import ro.infoiasi.sedic.android.model.MappedIndicator;
import ro.infoiasi.sedic.android.model.PlantBean;
import ro.infoiasi.sedic.android.model.Road;
import android.app.Application;

public class SedicApplication extends Application {

	private static SedicApplication sInstance = null;
	private List<PlantBean> plants;
	private List<Indicator> indicators;
	private List<MappedIndicator> mappings;

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}

	public static SedicApplication getInstance() {
		return sInstance;
	}

	public List<PlantBean> getPlants() {
		return plants;
	}

	public void setPlants(List<PlantBean> plants) {
		this.plants = plants;
	}

	public List<Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<Indicator> indicators) {
		this.indicators = indicators;
	}

	public List<MappedIndicator> getMappings() {
		return mappings;
	}

	public void setMappings(List<MappedIndicator> mappings) {
		this.mappings = mappings;
	}

}
