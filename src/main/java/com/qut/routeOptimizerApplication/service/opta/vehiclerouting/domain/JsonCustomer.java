package com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain;

public class JsonCustomer {


	protected String locationName;
	
    protected double latitude;
	
    protected double longitude;

    protected int demand;

	public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

	public JsonCustomer() {
	
	}

	public JsonCustomer(String locationName, double latitude, double longitude, int demand) {
		super();
		this.locationName = locationName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.demand = demand;
	}

	@Override
	public String toString() {
		return "JsonCustomer [locationName=" + locationName + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", demand=" + demand + "]";
	}

	
    
}
