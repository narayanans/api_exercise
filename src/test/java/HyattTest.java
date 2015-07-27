package test.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HyattTest {

	static final String API_KEY = "Qi1KTZKTqsjwTSTUs3NrNuKksOmk39eyFeMAINLp";
	static String station_id;
	
	@Test
	public void stationIDTest() {

		String URL = "https://api.data.gov/nrel/alt-fuel-stations/v1/nearest.json?api_key="
					+ API_KEY 
					+ "&location=Austin+TX";

		JsonObject jsonObject = getJSONObject(URL);
		JsonArray jsonArray = jsonObject.get("fuel_stations").getAsJsonArray();

		String station_id = null;
		Iterator<JsonElement> l = jsonArray.iterator();
		while (l.hasNext()) {
			JsonObject j = (JsonObject) l.next();
			String station_name = j.get("station_name").getAsString();
			if (station_name.equals("HYATT AUSTIN")) {
				station_id = j.get("id").getAsString();
				break;
			}
		}

		Assert.assertNotNull("Station ID should not be null if Hyatt's in the response", station_id);
		HyattTest.station_id = station_id;

	}

	@Test(dependsOnMethods="stationIDTest")
	public void addressTest(){
		String URL = "https://api.data.gov/nrel/alt-fuel-stations/v1/" + station_id + ".json?api_key="
				+ API_KEY;
		
		JsonObject jsonObject = getJSONObject(URL);
		
		Assert.assertEquals(jsonObject.getAsJsonObject("alt_fuel_station").get("street_address").getAsString(), "208 Barton Springs Rd");
		
	}
	
	
	public JsonObject getJSONObject(String urlToRead) {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		JsonParser parser = new JsonParser();
		JsonObject object = null;
		try {
			url = new URL(urlToRead);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));

			object = parser.parse(rd).getAsJsonObject();
			// while ((line = rd.readLine()) != null) {
			// result.append(line);
			// }
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

}
