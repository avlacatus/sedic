package ro.infoiasi.sedic.android.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.model.PlantBean;
import ro.infoiasi.sedic.android.model.RemedyBean;

public class JSONHelper {

	public static List<PlantBean> parsePlantsArray(String strOutput) {
		List<PlantBean> output = null;
		try {
			JSONArray jsonPlantsArray = new JSONArray(strOutput);
			output = new ArrayList<PlantBean>();

			for (int i = 0; i < jsonPlantsArray.length(); i++) {
				JSONObject jsonPlant = jsonPlantsArray.getJSONObject(i);
				PlantBean newPlant = JSONHelper.parseJSONPlant(jsonPlant);
				output.add(newPlant);
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
	
	public static List<RemedyBean> parseCompactRemedyArray(String strOutput) {
		List<RemedyBean> output = null;
		try {
			JSONArray jsonRemedyArray = new JSONArray(strOutput);
			output = new ArrayList<RemedyBean>();

			for (int i = 0; i < jsonRemedyArray.length(); i++) {
				JSONObject jsonRemedy = jsonRemedyArray.getJSONObject(i);
				RemedyBean newRemedy = JSONHelper.parseJSONCompactRemedy(jsonRemedy);
				output.add(newRemedy);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return output;
	}
	
	public static RemedyBean parseJSONCompactRemedy(JSONObject jsonCompactRemedy) throws NumberFormatException, JSONException {
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

}
