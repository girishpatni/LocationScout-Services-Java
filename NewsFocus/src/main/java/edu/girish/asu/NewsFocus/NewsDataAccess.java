package edu.girish.asu.NewsFocus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class NewsDataAccess {
	private static final String accessToken = "96621b9b-a307-442c-a0a9-dbf4782ee639";
	private String connectionURL;

	protected void InitializeComponents(String query) throws UnsupportedEncodingException {

		String newsServiceURL = "https://webhose.io/search?";
		String tokenParam = "token=" + accessToken;
		String formatParam = "&format=xml";
		String sourceTypeParam = "&site_type=news";
		String langParam = "&language=english";
		String queryParam = "&q=" + "thread.title%3A(" + query + ")%20text%3A(" + query + ")";
		String articleTypeParam = "&is_first=true";
		this.connectionURL = newsServiceURL + tokenParam + formatParam + queryParam + langParam + sourceTypeParam
				+ articleTypeParam;
	}

	protected String getResponse() {
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
		System.out.println(conn.getResponseCode());
		return conn;
	}

}
