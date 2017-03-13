package edu.girish.asu.CrimeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("myresources")

public class CrimeServiceLayer {
	protected CrimeDataAccess dataAccessLayer = new CrimeDataAccess();
	private Map<String, Integer> crimeDataMap;

	@Path("{q}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String getCrimeData(@PathParam("q") String zipcode) throws Exception {
		System.out.println(zipcode);
		String response = dataAccessLayer.InitializeComponents("85281");
		parseInput(response);
		//MapToXMLConverter.convertMapToXMLString(crimeDataMap);
		return MapToXmlConverter.ConvertMapToXMLString(crimeDataMap);
	}

	private void parseInput(String response) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		JSONArray jsonDataArray = (JSONArray) jsonObject.get("d");
		JSONObject innerObject = (JSONObject) jsonDataArray.get(0);
		crimeDataMap = new HashMap<>();
		crimeDataMap.put("ViolentCrime", Integer.parseInt(innerObject.get("ViolentCrime").toString()));
		crimeDataMap.put("MurderAndManslaughter",
				Integer.parseInt(innerObject.get("MurderAndManslaughter").toString()));
		crimeDataMap.put("ForcibleRape", Integer.parseInt(innerObject.get("ForcibleRape").toString()));
		crimeDataMap.put("Robbery", Integer.parseInt(innerObject.get("Robbery").toString()));
		crimeDataMap.put("AggravatedAssault", Integer.parseInt(innerObject.get("AggravatedAssault").toString()));
		crimeDataMap.put("PropertyCrime", Integer.parseInt(innerObject.get("PropertyCrime").toString()));
		crimeDataMap.put("Burglary", Integer.parseInt(innerObject.get("Burglary").toString()));
		crimeDataMap.put("LarcenyTheft", Integer.parseInt(innerObject.get("LarcenyTheft").toString()));
		crimeDataMap.put("MotorVehicleTheft", Integer.parseInt(innerObject.get("MotorVehicleTheft").toString()));
		crimeDataMap.put("Arson", Integer.parseInt(innerObject.get("Arson").toString()));

		List<String> keys = new ArrayList<String>(crimeDataMap.keySet());
		for (String str : keys) {
			System.out.println(str + "::" + crimeDataMap.get(str));
		}
	}
}
