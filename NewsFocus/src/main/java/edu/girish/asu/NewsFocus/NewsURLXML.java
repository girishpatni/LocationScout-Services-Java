package edu.girish.asu.NewsFocus;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class NewsURL {

	private List<String> URLs;

	public NewsURL() {

	}

	public NewsURL(List<String> data) {
		this.URLs = new ArrayList<String>(data);
	}

	@XmlElement(name = "URL")
	public List<String> getURLs() {
		return URLs;
	}

	public void setURLs(List<String> data) {
		this.URLs = data;
	}
}