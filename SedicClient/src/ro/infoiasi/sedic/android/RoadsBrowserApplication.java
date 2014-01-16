package ro.infoiasi.sedic.android;

import java.util.List;

import ro.infoiasi.sedic.android.model.Indicator;
import ro.infoiasi.sedic.android.model.MappedIndicator;
import ro.infoiasi.sedic.android.model.Road;
import android.app.Application;

public class RoadsBrowserApplication extends Application {

	private static RoadsBrowserApplication sInstance = null;
	private List<Road> roads;
	private List<Indicator> indicators;
	private List<MappedIndicator> mappings;

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}

	public static RoadsBrowserApplication getInstance() {
		return sInstance;
	}

	public List<Road> getRoads() {
		return roads;
	}

	public void setRoads(List<Road> roads) {
		this.roads = roads;
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
