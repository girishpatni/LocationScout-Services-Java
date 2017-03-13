package pkg;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class QuakeService {

	public BigDecimal getQuakeIndex(BigDecimal lat, BigDecimal lon) throws Exception {
		QuakeDataAccess dataAccesslayer = new QuakeDataAccess();
		dataAccesslayer.set_Latitude_Longitude(lat.doubleValue(), lon.doubleValue());
		String response = dataAccesslayer.InitializeComponents();
		Logging(response);
		double output = parseMagnitude(response);
		return new BigDecimal(output).setScale(2, RoundingMode.DOWN);
	}

	private void Logging(String output) throws IOException {
		FileOutputStream out = new FileOutputStream("\\log.txt");
		PrintWriter writer = new PrintWriter(out);
		writer.println(output);
		out.close();
	}

	private double parseMagnitude(String response) throws ParseException {
		double sum = 0.0;
		int index = 1;
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		JSONArray jsonfeatureArray = (JSONArray) jsonObject.get("features");
		for (int i = 0; i < jsonfeatureArray.size(); i++) {
			JSONObject jsonInnerObject = (JSONObject) jsonfeatureArray.get(i);
			if (jsonInnerObject.containsKey("properties")) {
				JSONObject jsonPropertyObject = (JSONObject) jsonInnerObject.get("properties");
				Object magnitudeObject = jsonPropertyObject.get("mag");
				if (magnitudeObject != null) {
					sum += Double.parseDouble(magnitudeObject.toString());
					index++;
				}
			}
		}
		return (sum / index);
	}
}
