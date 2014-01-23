package ro.infoiasi.sedic.android;

import java.util.Map;

import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.model.DrugBean;
import ro.infoiasi.sedic.android.model.PlantBean;
import ro.infoiasi.sedic.android.model.RemedyBean;
import android.app.Application;

public class SedicApplication extends Application {

    private static SedicApplication sInstance = null;
    private Map<Long, PlantBean> plants;
    private Map<Long, RemedyBean> remedies;
    private Map<Long, DrugBean> drugs;
    private Map<Long, DiseaseBean> diseases;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static SedicApplication getInstance() {
        return sInstance;
    }

    public Map<Long, DrugBean> getDrugs() {
        return drugs;
    }

    public void setDrugs(Map<Long, DrugBean> drugs) {
        this.drugs = drugs;
    }

    public Map<Long, PlantBean> getPlantList() {
        return plants;
    }

    public void setPlants(Map<Long, PlantBean> plants) {
        this.plants = plants;
    }

    public Map<Long, DiseaseBean> getDiseases() {
        return diseases;
    }

    public void setDiseases(Map<Long, DiseaseBean> diseases) {
        this.diseases = diseases;
    }

    public Map<Long, RemedyBean> getRemedies() {
        return remedies;
    }

    public void setRemedies(Map<Long, RemedyBean> remedies) {
        this.remedies = remedies;
    }
}
