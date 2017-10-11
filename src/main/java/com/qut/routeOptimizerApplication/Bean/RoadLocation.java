package com.qut.routeOptimizerApplication.Bean;

import java.util.Map;

public class RoadLocation extends Address{

		    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			// Prefer Map over array or List because customers might be added and removed in real-time planning.
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

	
		    public long getDistanceTo(Address location) {
		        if (this == location) {
		            return 0L;
		        }
		        double distance = travelDistanceMap.get((RoadLocation) location);
		        // Multiplied by 1000 to avoid floating point arithmetic rounding errors
		        return (long) (distance * 1000.0 + 0.5);
		    }

}
