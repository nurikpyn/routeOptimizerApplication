package com.qut.routeOptimizerApplication.Bean;

public class Address {
	private int id;
	private Double latitude;
	private Double longitude;
	private String name;

	public Address(int id,Double latitude, Double longitude) {
		super();
		this.id=id;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Address() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
