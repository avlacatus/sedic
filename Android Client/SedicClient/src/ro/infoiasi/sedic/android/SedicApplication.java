package ro.infoiasi.sedic.android;

import java.util.List;

import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.model.DrugBean;
import ro.infoiasi.sedic.android.model.PlantBean;
import ro.infoiasi.sedic.android.model.RemedyBean;
import android.app.Application;

public class SedicApplication extends Application {

	private static SedicApplication sInstance = null;
	private List<PlantBean> plants;
	private List<RemedyBean> remedies;
	private List<DrugBean> drugs;
	private List<DiseaseBean> diseases;

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}

	public static SedicApplication getInstance() {
		return sInstance;
	}

	public List<RemedyBean> getRemedies() {
		return remedies;
	}

	public void setRemedies(List<RemedyBean> remedies) {
		this.remedies = remedies;
	}

	public List<DrugBean> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<DrugBean> drugs) {
		this.drugs = drugs;
	}

	public List<DiseaseBean> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<DiseaseBean> diseases) {
		this.diseases = diseases;
	}

	public List<PlantBean> getPlants() {
		return plants;
	}

	public void setPlants(List<PlantBean> plants) {
		this.plants = plants;
	}

}
