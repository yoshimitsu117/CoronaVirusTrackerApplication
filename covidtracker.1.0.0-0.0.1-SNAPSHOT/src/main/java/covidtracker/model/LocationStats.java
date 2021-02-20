package covidtracker.model;

import java.math.BigInteger;

public class LocationStats {
	private String state;
	private String region;
	private double latitude;
	private double longitude;
	private BigInteger totalCases;
	public BigInteger getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(BigInteger totalCases) {
		this.totalCases = totalCases;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
