package ro.infoiasi.sedic.android.util;

public class Constants {
	public static final String fake_url = "http://students.info.uaic.ro/~alexandra.lacatus";
	public static final String ralu_url = "http://192.168.1.9:8081";
	public static final String ana_url = "http://192.168.0.158:8081";
	public static final String real_url = "http://10.0.2.2:8081";
	public static final String uaic_url = "http://172.17.50.151:8081";
	public static final String BASE_URL = uaic_url;

	public static final String ROADS_CONTROLLER = BASE_URL + "/resources"
			+ "/roads";
	public static final String INDICATORS_CONTROLLER = BASE_URL + "/resources"
			+ "/indicators";
	public static final String MAPPING_CONTROLLER = BASE_URL + "/resources"
			+ "/mapping";

	public static final String INTENT_EXTRA_ROAD_FIRST_POSITION = "road_first_positions";
	public static final String INTENT_EXTRA_ROAD_SECOND_POSITION = "road_second_positions";
	public static final String INTENT_EXTRA_ROAD_ID = "road_id";
}
