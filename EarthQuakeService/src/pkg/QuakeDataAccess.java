package pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuakeDataAccess {
	private static final int seismicDuration = 30;
	private double _Latitude;
	private double _Longitude;
	private double _maxradiuskm = 100;

	protected void set_Latitude_Longitude(double Latitude, double Longitude) {
		this._Latitude = Latitude;
		this._Longitude = Longitude;
	}

	protected String InitializeComponents() {

		LocalDateTime dateTime = LocalDateTime.now();
		String currentTime = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
		String oldTime = dateTime.minusYears(seismicDuration).format(DateTimeFormatter.ISO_LOCAL_DATE);
		String earthQuakeServiceURL = "http://earthquake.usgs.gov/fdsnws/event/1/query?";
		StringBuffer response = new StringBuffer();
		try {
			String parameterURL = "format=geojson&starttime=" + oldTime + "&endtime=" + currentTime + "&latitude="
					+ _Latitude + "&longitude=" + _Longitude + "&maxradiuskm=" + _maxradiuskm;
			BufferedReader in = new BufferedReader(
					new InputStreamReader(createWebConnection(earthQuakeServiceURL + parameterURL).getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}

	private HttpURLConnection createWebConnection(String webURL) throws IOException {
		URL url = new URL(webURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "format=geojson");
		conn.setDoOutput(true);
		return conn;
	}
}