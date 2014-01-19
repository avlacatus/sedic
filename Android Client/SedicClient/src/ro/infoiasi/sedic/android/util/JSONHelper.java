package ro.infoiasi.sedic.android.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.model.Indicator;
import ro.infoiasi.sedic.android.model.MappedIndicator;
import ro.infoiasi.sedic.android.model.PlantBean;

public class JSONHelper {

	public static List<PlantBean> buildPlantsArray(String strOutput) {
		List<PlantBean> output = null;
		try {
			JSONArray jsonPlantsArray = new JSONArray(strOutput);
			output = new ArrayList<PlantBean>();

			for (int i = 0; i < jsonPlantsArray.length(); i++) {
				JSONObject jsonPlant = jsonPlantsArray.getJSONObject(i);
				PlantBean newRoad = JSONHelper.parseJSONPlant(jsonPlant);
				output.add(newRoad);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return output;
	}

	public static List<Indicator> buildIndicatorsArray(String strOutput) {
		List<Indicator> output = null;
		try {
			JSONArray jsonIndicatorsArray = new JSONArray(strOutput);
			output = new ArrayList<Indicator>();

			for (int i = 0; i < jsonIndicatorsArray.length(); i++) {
				JSONObject jsonIndicator = jsonIndicatorsArray.getJSONObject(i);
				Indicator newIndicator = JSONHelper.parseJSONIndicator(jsonIndicator.toString());
				output.add(newIndicator);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return output;
	}

	public static List<MappedIndicator> buildMappingsArray(String strOutput) {
		List<MappedIndicator> output = null;
		try {
			JSONArray jsonIndicatorsArray = new JSONArray(strOutput);
			output = new ArrayList<MappedIndicator>();

			for (int i = 0; i < jsonIndicatorsArray.length(); i++) {
				JSONObject jsonIndicator = jsonIndicatorsArray.getJSONObject(i);
				MappedIndicator newIndicator = JSONHelper.parseJSONMappedIndicator(jsonIndicator.toString());
				output.add(newIndicator);
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

	public static Indicator parseJSONIndicator(String strJSONIndicator) {
		Indicator indicator = null;
		try {
			JSONObject indicatorJSON = new JSONObject(strJSONIndicator);
			indicator = new Indicator();
			indicator.setName(indicatorJSON.getString("name"));
			indicator.setCode(indicatorJSON.getString("code"));
			if (indicatorJSON.has("id")) {
				indicator.setID(indicatorJSON.getInt("id"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return indicator;
	}

	public static MappedIndicator parseJSONMappedIndicator(String strJSONMapping) {
		MappedIndicator indicator = null;
		try {
			JSONObject mappingJSON = new JSONObject(strJSONMapping);
			indicator = new MappedIndicator();
			indicator.setKm(mappingJSON.getInt("km"));
			indicator.setObservations(mappingJSON.getString("observations"));
			if (mappingJSON.has("id")) {
				indicator.setID(mappingJSON.getInt("id"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return indicator;
	}

	public static JSONObject buildJSONCoordinatesObject(double[] first, double[] second) {
		JSONObject coordJSON = new JSONObject();
		JSONObject firstCoordJSON = new JSONObject();
		JSONObject secondCoordJSON = new JSONObject();
		try {
			firstCoordJSON.put("lat", first[0]);
			firstCoordJSON.put("long", first[1]);

			secondCoordJSON.put("lat", second[0]);
			secondCoordJSON.put("long", second[1]);

			coordJSON.put("first", firstCoordJSON);
			coordJSON.put("second", secondCoordJSON);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return coordJSON;
	}

	public static double[] parseCordinatesJSON(String strCoordinatesJSON) {
		double[] output = new double[4];
		try {
			JSONObject coordJSON = new JSONObject(strCoordinatesJSON);

			JSONObject first = coordJSON.getJSONObject("first");
			output[0] = first.getDouble("lat");
			output[1] = first.getDouble("long");
			JSONObject second = coordJSON.getJSONObject("second");
			output[2] = second.getDouble("lat");
			output[3] = second.getDouble("long");
		} catch (JSONException e) {
			System.out.println(e);
			return null;
		}

		return output;
	}
}
