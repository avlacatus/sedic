package ro.infoiasi.sedic.android.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ro.infoiasi.sedic.android.model.Indicator;
import ro.infoiasi.sedic.android.model.MappedIndicator;
import ro.infoiasi.sedic.android.model.Road;

public class JSONHelper {

	public static String buildMappingJSONArray(List<MappedIndicator> mappings,
			int roadID, int indicatorID) {
		JSONArray mappingsArray = new JSONArray();
		for (MappedIndicator mapping : mappings) {
			mappingsArray.put(buildJSONObject(mapping));
		}
		return mappingsArray.toString();

	}

	public static String buildRoadsJSONArray(List<Road> roads) {

		JSONArray roadsArray = new JSONArray();
		for (Road road : roads) {
			roadsArray.put(JSONHelper.buildJSONObject(road));
		}
		return roadsArray.toString();

	}

	public static String buildIndicatorsJSONArray(List<Indicator> indicators) {
		JSONArray indicatorsArray = new JSONArray();
		for (Indicator indicator : indicators) {
			indicatorsArray.put(JSONHelper.buildJSONObject(indicator));
		}
		return indicatorsArray.toString();
	}

	public static List<Road> buildRoadsArray(String strOutput) {
		List<Road> output = null;
		try {
			JSONArray jsonRoadsArray = new JSONArray(strOutput);
			output = new ArrayList<Road>();

			for (int i = 0; i < jsonRoadsArray.length(); i++) {
				JSONObject jsonRoad = jsonRoadsArray.getJSONObject(i);
				Road newRoad = JSONHelper.parseJSONRoad(jsonRoad.toString());
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
				Indicator newIndicator = JSONHelper
						.parseJSONIndicator(jsonIndicator.toString());
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
				MappedIndicator newIndicator = JSONHelper
						.parseJSONMappedIndicator(jsonIndicator.toString());
				output.add(newIndicator);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return output;
	}

	public static JSONObject buildJSONObject(MappedIndicator mappedIndicator) {
		JSONObject mappingJSON = new JSONObject();
		try {
			mappingJSON.put("id", mappedIndicator.getID());
			mappingJSON.put("km", mappedIndicator.getKm());
			mappingJSON.put("observations", mappedIndicator.getObservations());
			mappingJSON.put("indicatorID", mappedIndicator.getIndicator()
					.getID());
			mappingJSON.put("roadID", mappedIndicator.getRoad().getID());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mappingJSON;
	}

	public static JSONObject buildJSONObject(Indicator road) {
		JSONObject indicatorJSON = new JSONObject();
		try {
			indicatorJSON.put("id", road.getID());
			indicatorJSON.put("name", road.getName());
			indicatorJSON.put("code", road.getCode());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return indicatorJSON;
	}

	public static JSONObject buildJSONObject(Road road) {
		JSONObject roadJSON = new JSONObject();
		try {
			roadJSON.put("id", road.getID());
			roadJSON.put("indicative", road.getIndicative());
			roadJSON.put("length", road.getLength());
			roadJSON.put("region", road.getRegion());
			roadJSON.put("type", road.getType());
			if (road.getFirstCoordinates() != null
					&& road.getSecondCoordinates() != null) {
				JSONObject jsonCoords = buildJSONCoordinatesObject(
						road.getFirstCoordinates(), road.getSecondCoordinates());
				roadJSON.put("coordinates", jsonCoords);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return roadJSON;
	}

	public static Road parseJSONRoad(String strJSONRoad) {
		Road road = null;
		try {
			JSONObject roadJSON = new JSONObject(strJSONRoad);
			road = new Road();
			road.setIndicative(roadJSON.getString("indicative"));
			road.setLength(roadJSON.getInt("length"));
			road.setRegion(roadJSON.getString("region"));
			road.setType(roadJSON.getString("type"));
			if (roadJSON.has("id"))
				road.setID(roadJSON.getInt("id"));
			if (roadJSON.has("coordinates")) {
				double[] coordsArray = parseCordinatesJSON(roadJSON
						.getString("coordinates"));
				if (coordsArray != null) {
					double[] first = new double[2];
					first[0] = coordsArray[0];
					first[1] = coordsArray[1];

					double[] second = new double[2];
					second[0] = coordsArray[2];
					second[1] = coordsArray[3];
					road.setFirstCoordinates(first);
					road.setSecondCoordinates(second);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return road;
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

	public static JSONObject buildJSONCoordinatesObject(double[] first,
			double[] second) {
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
