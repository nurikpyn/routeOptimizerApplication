package com.qut.routeOptimizerApplication.Bean;

	import com.qut.routeOptimizerApplication.service.opta.DistanceType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamInclude;

	@XStreamAlias("VrpLocation")
	@XStreamInclude({
	        RoadLocation.class
	})
	public  abstract class Address{
protected int id;
	    protected String name = null;
	    protected double latitude;
	    protected double longitude;

	    public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Address() {
	    }

	    public Address(int id, double latitude, double longitude) {
	    	this.id=id;
	        this.latitude = latitude;
	        this.longitude = longitude;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
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

	    // ************************************************************************
	    // Complex methods
	    // ************************************************************************

	    /**
	     * The distance's unit of measurement depends on the {@link VehicleRoutingSolution}'s {@link DistanceType}.
	     * It can be in miles or km, but for most cases it's in the TSPLIB's unit of measurement.
	     * @param location never null
	     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
	     */
	   
	    public abstract long getDistanceTo(Address location);
	    public double getAirDistanceDoubleTo(Address location) {
	        // Implementation specified by TSPLIB http://www2.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/
	        // Euclidean distance (Pythagorean theorem) - not correct when the surface is a sphere
	        double latitudeDifference = location.latitude - latitude;
	        double longitudeDifference = location.longitude - longitude;
	        return Math.sqrt(
	                (latitudeDifference * latitudeDifference) + (longitudeDifference * longitudeDifference));
	    }

	    /**
	     * The angle relative to the direction EAST.
	     * @param location never null
	     * @return in Cartesian coordinates
	     */
	    public double getAngle(Address location) {
	        // Euclidean distance (Pythagorean theorem) - not correct when the surface is a sphere
	        double latitudeDifference = location.latitude - latitude;
	        double longitudeDifference = location.longitude - longitude;
	        return Math.atan2(latitudeDifference, longitudeDifference);
	    }


	    @Override
	    public String toString() {
	        if (name == null) {
	            return super.toString();
	        }
	        return name;
	    }


}
