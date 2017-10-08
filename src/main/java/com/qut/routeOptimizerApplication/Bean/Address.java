package com.qut.routeOptimizerApplication.Bean;

public class Address {
	// private final Logger logger = LoggerFactory.getLogger(getClass());
	private int id;
	private String latitude;
	private String longitude;

	public Address() {

	}

	public Address(int id, String latitude, String longitude) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "latitude=" + latitude + " longitude=" + longitude;
	}

}
