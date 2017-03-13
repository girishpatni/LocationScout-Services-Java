package edu.girish.asu.CrimeData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class CrimeDataAccess {

	protected String InitializeComponents(String zipcode) {

		String crimeServiceURL = "http://azure.geodataservice.net/GeoDataService.svc/GetUSDemographics?includecrimedata=true&zipcode=";
		String dataFormat="&$format=json";
		String connectionURL=crimeServiceURL+zipcode+dataFormat;
		StringBuffer response = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(createWebConnection(connectionURL).getInputStream()));
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
		conn.setRequestProperty("Content-Type", "format=json");
		System.out.println(conn.getResponseCode());
		return conn;
	}

}

