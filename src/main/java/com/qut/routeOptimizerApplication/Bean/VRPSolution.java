package com.qut.routeOptimizerApplication.Bean;

import java.util.List;

public class VRPSolution {
	 protected String name;
	   
	    protected String distanceUnitOfMeasurement;
	    protected List<Address> locationList;
	    protected Depot depot;
	    public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDistanceUnitOfMeasurement() {
			return distanceUnitOfMeasurement;
		}
		public void setDistanceUnitOfMeasurement(String distanceUnitOfMeasurement) {
			this.distanceUnitOfMeasurement = distanceUnitOfMeasurement;
		}
		public List<Address> getLocationList() {
			return locationList;
		}
		public void setLocationList(List<Address> locationList) {
			this.locationList = locationList;
		}
		public Depot getDepot() {
			return depot;
		}
		public void setDepot(Depot depot) {
			this.depot = depot;
		}
		public List<Vehicle> getVehicleList() {
			return vehicleList;
		}
		public void setVehicleList(List<Vehicle> vehicleList) {
			this.vehicleList = vehicleList;
		}
		protected List<Vehicle> vehicleList;
}
