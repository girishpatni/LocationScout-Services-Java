package pkg;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WeatherServiceLayer {

	private String maxTempData = "";
	private String minTempData = "";
	private String ranifallData = "";
	private String delimitter = ":";
	private WeatherDataAccess dataAccessLayer = new WeatherDataAccess();

	public String getMaxTempData(String zipCode) throws Exception {
		dataAccessLayer.setZipCode(zipCode);
		String response = dataAccessLayer.IntializeWeather();
		parseDocument(response);
		return maxTempData.toString();
	}

	public String getMinTempData(String zipCode) throws Exception {
		dataAccessLayer.setZipCode(zipCode);
		String response = dataAccessLayer.IntializeWeather();
		parseDocument(response);
		return minTempData.toString();
	}

	public String getRainfallData(String zipCode) throws Exception {
		dataAccessLayer.setZipCode(zipCode);
		String response = dataAccessLayer.IntializeWeather();
		parseDocument(response);
		return ranifallData.toString();
	}

	private Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	private void parseDocument(String response) throws Exception {
		Document doc = loadXMLFromString(response);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("data");
		for (int i = 0; i < nList.getLength(); i++) {
			NodeList dataList = nList.item(i).getChildNodes();
			for (int count = 0; count < dataList.getLength(); count++) {
				if (dataList.item(count).getNodeName().equals("parameters")) {
					Node weatherParameters = dataList.item(count);
					NodeList weatherList = weatherParameters.getChildNodes();
					for (int j = 0; j < weatherList.getLength(); j++) {
						Node node = weatherList.item(j);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) node;
							if (node.getNodeName().equals("temperature")
									&& eElement.getAttribute("type").equals("maximum")) {
								for (int index2 = 0; index2 < WeatherDataAccess.numDays; index2++)
									maxTempData += eElement.getElementsByTagName("value").item(index2).getTextContent()
											+ delimitter;
							}
							if (node.getNodeName().equals("temperature")
									&& eElement.getAttribute("type").equals("minimum")) {
								for (int index2 = 0; index2 < WeatherDataAccess.numDays; index2++)
									minTempData += eElement.getElementsByTagName("value").item(index2).getTextContent()
											+ delimitter;
							}
							if (node.getNodeName().equals("probability-of-precipitation")) {
								for (int index2 = 0; index2 < WeatherDataAccess.numDays * 2; index2++)
									ranifallData += eElement.getElementsByTagName("value").item(index2).getTextContent()
											+ delimitter;
							}
						}

					}

				}

			}

		}
	}
}
