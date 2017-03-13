package edu.girish.asu.CrimeData;

public class Crime {
	
	public String getCrimeType() {
		return crimeType;
	}
	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}
	public Integer getCrimeNumber() {
		return crimeNumber;
	}
	public void setCrimeNumber(Integer crimeNumber) {
		this.crimeNumber = crimeNumber;
	}
	private String crimeType;
	private Integer crimeNumber;

}
