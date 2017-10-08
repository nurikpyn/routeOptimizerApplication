package com.qut.routeOptimizerApplication.Bean;

import java.util.Map;

public class RoadLocation extends Address{
	  protected Map<RoadLocation, Double> travelDistanceMap;
	  public RoadLocation() {
	    }

	    public RoadLocation(int id, double latitude, double longitude) {
	        super(id, latitude, longitude);
	    }

	    public Map<RoadLocation, Double> getTravelDistanceMap() {
	        return travelDistanceMap;
	    }

	    public void setTravelDistanceMap(Map<RoadLocation, Double> travelDistanceMap) {
	        this.travelDistanceMap = travelDistanceMap;
	    }
}
