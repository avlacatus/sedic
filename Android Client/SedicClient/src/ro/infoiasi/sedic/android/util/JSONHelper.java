package ro.infoiasi.sedic.android.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.SedicApplication;
import ro.infoiasi.sedic.android.model.DiseaseBean;
import ro.infoiasi.sedic.android.model.DrugBean;
import ro.infoiasi.sedic.android.model.PlantBean;
import ro.infoiasi.sedic.android.model.RemedyBean;
import android.util.Log;

public class JSONHelper {

    public static Map<Long, PlantBean> parsePlantsArray(String strOutput) {
        Map<Long, PlantBean> output = null;
        try {
            JSONArray jsonPlantsArray = new JSONArray(strOutput);
            output = new HashMap<Long, PlantBean>();

            for (int i = 0; i < jsonPlantsArray.length(); i++) {
                JSONObject jsonPlant = jsonPlantsArray.getJSONObject(i);
                PlantBean newPlant = JSONHelper.parseJSONPlant(jsonPlant);
                output.put(newPlant.getPlantId(), newPlant);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return output;
    }

    public static PlantBean parseJSONPlant(JSONObject jsonPlant) throws NumberFormatException, JSONException {
        PlantBean output = new PlantBean();
        if (jsonPlant.has("plant_id")) {
            output.setPlantId(Long.parseLong(jsonPlant.getString("plant_id")));
        }

        if (jsonPlant.has("plant_name")) {
            output.setPlantName(jsonPlant.getString("plant_name"));
        }

        if (jsonPlant.has("plant_uri")) {
            output.setPlantURI(jsonPlant.getString("plant_uri"));
        }

        return output;
    }

    public static Map<Long, RemedyBean> parseCompactRemedyArray(String strOutput) {
        Map<Long, RemedyBean> output = null;
        try {
            JSONArray jsonRemedyArray = new JSONArray(strOutput);
            output = new HashMap<Long, RemedyBean>();

            for (int i = 0; i < jsonRemedyArray.length(); i++) {
                JSONObject jsonRemedy = jsonRemedyArray.getJSONObject(i);
                RemedyBean newRemedy = JSONHelper.parseJSONCompactRemedy(jsonRemedy);
                output.put(newRemedy.getRemedyId(), newRemedy);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return output;
    }

    public static Map<Long, DrugBean> parseDrugArray(String strOutput) {
        Map<Long, DrugBean> output = null;
        try {
            JSONArray jsonDrugArray = new JSONArray(strOutput);
            output = new HashMap<Long, DrugBean>();

            // parse initial drug beans
            for (int i = 0; i < jsonDrugArray.length(); i++) {
                JSONObject jsonDrug = jsonDrugArray.getJSONObject(i);
                DrugBean newDrug = JSONHelper.parseJSONDrug(jsonDrug);
                output.put(newDrug.getDrugId(), newDrug);
            }

            // add the links to the children
            for (int i = 0; i < jsonDrugArray.length(); i++) {
                JSONObject jsonDrug = jsonDrugArray.getJSONObject(i);
                long drugId = -1;
                if (jsonDrug.has("drug_id")) {
                    drugId = Long.parseLong(jsonDrug.getString("drug_id"));
                }

                if (drugId != -1) {
                    DrugBean selectedDrug = output.get(drugId);
                    if (selectedDrug != null) {
                        JSONArray drugChildrenArray = null;
                        if (jsonDrug.has("drug_children")) {
                            drugChildrenArray = jsonDrug.getJSONArray("drug_children");
                            if (drugChildrenArray != null) {

                                List<DrugBean> children = new ArrayList<DrugBean>();

                                for (int j = 0; j < drugChildrenArray.length(); j++) {
                                    long childId = -1;
                                    String childURI = "";

                                    JSONObject childJson = drugChildrenArray.getJSONObject(j);
                                    if (childJson.has("child_id")) {
                                        childId = childJson.getLong("child_id");
                                    }
                                    if (childJson.has("child_uri")) {
                                        childURI = childJson.getString("child_uri");
                                    }

                                    DrugBean childBean = output.get(childId);
                                    if (childBean != null && !childURI.equals(selectedDrug.getDrugURI())) {
                                        children.add(childBean);
                                    }
                                }

                                selectedDrug.setDrugChildren(children);
                                output.put(selectedDrug.getDrugId(), selectedDrug);

                            } else {
                                Log.e("debug", "drug_children null for " + selectedDrug);
                            }

                        } else {
                            Log.e("debug", "no drug_children for " + selectedDrug);
                        }
                    } else {
                        Log.e("debug", "no drugBean for " + jsonDrug.toString());
                    }
                }
            }

        } catch (JSONException e) {
            Log.e("debug", "error in parsing drug array", e);
        }

        return output;
    }

    public static Map<Long, DiseaseBean> parseDiseaseArray(String strOutput) {
        Map<Long, DiseaseBean> output = null;
        try {
            JSONArray jsonDiseaseArray = new JSONArray(strOutput);
            output = new HashMap<Long, DiseaseBean>();

            for (int i = 0; i < jsonDiseaseArray.length(); i++) {
                JSONObject jsonDisease = jsonDiseaseArray.getJSONObject(i);
                DiseaseBean newDisease = JSONHelper.parseJSONDisease(jsonDisease);
                output.put(newDisease.getDiseaseId(), newDisease);
            }

            // add the links to the parents
            for (int i = 0; i < jsonDiseaseArray.length(); i++) {
                JSONObject jsonDisease = jsonDiseaseArray.getJSONObject(i);
                long diseaseId = -1;
                if (jsonDisease.has("disease_id")) {
                    diseaseId = Long.parseLong(jsonDisease.getString("disease_id"));
                }

                if (diseaseId != -1) {
                    DiseaseBean selectedDisease = output.get(diseaseId);
                    if (selectedDisease != null) {
                        JSONArray diseaseChildrenArray = null;
                        if (jsonDisease.has("disease_children")) {
                            diseaseChildrenArray = jsonDisease.getJSONArray("disease_children");
                            if (diseaseChildrenArray != null) {

                                List<DiseaseBean> children = new ArrayList<DiseaseBean>();

                                for (int j = 0; j < diseaseChildrenArray.length(); j++) {
                                    long childId = -1;
                                    String childURI = "";

                                    JSONObject childJson = diseaseChildrenArray.getJSONObject(j);
                                    if (childJson.has("child_id")) {
                                        childId = childJson.getLong("child_id");
                                    }
                                    if (childJson.has("child_uri")) {
                                        childURI = childJson.getString("child_uri");
                                    }

                                    DiseaseBean childBean = output.get(childId);
                                    if (childBean != null && !childURI.equals(selectedDisease.getDiseaseURI())) {
                                        children.add(childBean);
                                    }
                                }

                                selectedDisease.setDiseaseChildren(children);
                                output.put(selectedDisease.getDiseaseId(), selectedDisease);

                            } else {
                                Log.e("debug", "disease_children null for " + selectedDisease);
                            }

                        } else {
                            Log.e("debug", "no disease_children for " + selectedDisease);
                        }
                    } else {
                        Log.e("debug", "no diseaseBean for " + jsonDisease.toString());
                    }
                }
            }

        } catch (JSONException e) {
        	Log.e("debug", "error in parsing disease array", e);
        }

        return output;
    }

    public static RemedyBean parseJSONCompactRemedy(JSONObject jsonCompactRemedy) throws NumberFormatException,
            JSONException {
        RemedyBean output = new RemedyBean();
        if (jsonCompactRemedy.has("remedy_id")) {
            output.setRemedyId(Long.parseLong(jsonCompactRemedy.getString("remedy_id")));
        }

        if (jsonCompactRemedy.has("remedy_plant_id")) {
            output.setRemedyPlantId(Long.parseLong(jsonCompactRemedy.getString("remedy_plant_id")));
        }

        if (jsonCompactRemedy.has("remedy_name")) {
            output.setRemedyName(jsonCompactRemedy.getString("remedy_name"));
        }

        if (jsonCompactRemedy.has("remedy_uri")) {
            output.setRemedyURI(jsonCompactRemedy.getString("remedy_uri"));
        }

        return output;
    }

    public static DrugBean parseJSONDrug(JSONObject jsonDrug) throws NumberFormatException, JSONException {
        DrugBean output = new DrugBean();
        if (jsonDrug.has("drug_id")) {
            output.setDrugId(Long.parseLong(jsonDrug.getString("drug_id")));
        }

        if (jsonDrug.has("drug_name")) {
            output.setDrugName(jsonDrug.getString("drug_name"));
        }

        if (jsonDrug.has("drug_uri")) {
            output.setDrugURI(jsonDrug.getString("drug_uri"));
        }

        return output;
    }

    public static DiseaseBean parseJSONDisease(JSONObject jsonDrug) throws NumberFormatException, JSONException {
        DiseaseBean output = new DiseaseBean();
        if (jsonDrug.has("disease_id")) {
            output.setDiseaseId(Long.parseLong(jsonDrug.getString("disease_id")));
        }

        if (jsonDrug.has("disease_name")) {
            output.setDiseaseName(jsonDrug.getString("disease_name"));
        }

        if (jsonDrug.has("disease_uri")) {
            output.setDiseaseURI(jsonDrug.getString("disease_uri"));
        }

        return output;
    }

    public static RemedyBean paseJSONRemedy(JSONObject jsonRemedy) throws NumberFormatException, JSONException {
        RemedyBean output = parseJSONCompactRemedy(jsonRemedy);
        if (jsonRemedy.has("adjuvant_usages")) {
            Object obj = jsonRemedy.get("adjuvant_usages");
            if (obj != null && obj instanceof JSONArray) {
                output.setAdjuvantUsages(parseAdjuvantUsagesJSONArray((JSONArray) obj));
            }
        }

        if (jsonRemedy.has("therapeutical_usages")) {
            Object obj = jsonRemedy.get("therapeutical_usages");
            if (obj != null && obj instanceof JSONArray) {
                output.setTherapeuticalUsages(parseTherapeuticalUsagesJSONArray((JSONArray) obj));
            }
        }

        // if (jsonRemedy.has("frequent_usages")) {
        // Object obj = jsonRemedy.get("frequent_usages");
        // if (obj != null && obj instanceof JSONArray) {
        // output.setFrequentUsages(parseStringJSONArray((JSONArray) obj));
        // }
        // }
        //
        // if (jsonRemedy.has("reported_usages")) {
        // Object obj = jsonRemedy.get("reported_usages");
        // if (obj != null && obj instanceof JSONArray) {
        // output.setReportedUsages(parseStringJSONArray((JSONArray) obj));
        // }
        // }

        return output;
    }

    private static List<DrugBean> parseAdjuvantUsagesJSONArray(JSONArray usagesArray) {
        Map<Long, DrugBean> drugBeanMap = SedicApplication.getInstance().getDrugs();
        List<DrugBean> output = new ArrayList<DrugBean>();
        for (int i = 0; i < usagesArray.length(); i++) {
            JSONObject child = null;
            try {
                child = usagesArray.getJSONObject(i);
                long usageId = -1;
                if (child.has("remedy_usage_id")) {
                    try {
                        usageId = child.getLong("remedy_usage_id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (usageId != -1) {
                    DrugBean usageBean = drugBeanMap.get(usageId);
                    if (usageBean != null) {
                        output.add(usageBean);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    private static List<DiseaseBean> parseTherapeuticalUsagesJSONArray(JSONArray usagesArray) {
        Map<Long, DiseaseBean> diseaseBeanMap = SedicApplication.getInstance().getDiseases();
        List<DiseaseBean> output = new ArrayList<DiseaseBean>();
        for (int i = 0; i < usagesArray.length(); i++) {
            JSONObject child = null;
            try {
                child = usagesArray.getJSONObject(i);
                long usageId = -1;
                if (child.has("remedy_usage_id")) {
                    try {
                        usageId = child.getLong("remedy_usage_id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (usageId != -1) {
                    DiseaseBean usageBean = diseaseBeanMap.get(usageId);
                    if (usageBean != null) {
                        output.add(usageBean);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    private static List<String> parseStringJSONArray(JSONArray usagesArray) {
        List<String> output = new ArrayList<String>();
        for (int i = 0; i < usagesArray.length(); i++) {
            Object child = null;
            try {
                child = usagesArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (child != null) {
                if (child instanceof String) {
                    output.add((String) child);
                }
            }
        }
        return output;
    }

}
