package pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDataAccess {
	protected static final int numDays = 7;
	private String zipCode="";
	protected void setZipCode(String zipcode){
		this.zipCode=zipcode;
	}
	public String IntializeWeather() throws IOException {
		String weatherURL = "http://graphical.weather.gov/xml/sample_products/browser_interface/ndfdBrowserClientByDay.php?";
		String parameters = "zipCodeList=" + zipCode + "&format=24+hourly&numDays=" + numDays;
		BufferedReader in = new BufferedReader(
				new InputStreamReader(createWebConnection(weatherURL + parameters).getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	private HttpURLConnection createWebConnection(String webURL) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(webURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "format=xml");
			conn.setDoOutput(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}

}