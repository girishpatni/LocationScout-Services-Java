package edu.girish.asu.NewsFocus;

import java.io.StringReader;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Path("myresources")
public class NewsServiceLayer {

	private String[] newsURL;

	protected void parseInputXML(String response) throws Exception {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
		InputSource iSource = new InputSource();
		iSource.setCharacterStream(new StringReader(response));

		Document doc = docBuilder.parse(iSource);
		NodeList urlList = doc.getElementsByTagName("url");
		newsURL = new String[urlList.getLength()];
		for (int i = 0; i < urlList.getLength(); i++) {
			Element element = (Element) urlList.item(i);
			newsURL[i] = element.getTextContent();
		}
	}

	@Path("{q}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public NewsURL getNewsData(@PathParam("q") String query) throws Exception {
		NewsDataAccess dataAccess = new NewsDataAccess();
		dataAccess.InitializeComponents(query);
		String response = dataAccess.getResponse();
		parseInputXML(response);
		NewsURL ouputXML = new NewsURL(Arrays.asList(newsURL));
		return ouputXML;
	}

}
