package com.qut.routeOptimizerApplication.Bean;

import java.util.List;

public class VRPSolution {
	 protected String name;
	    protected String distanceType="ROAD_DISTANCE";
	    protected String distanceUnitOfMeasurement;
	    protected List<Address> locationList;
	    protected List<Depot> depotList;
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
		public List<Depot> getDepotList() {
			return depotList;
		}
		public void setDepotList(List<Depot> depotList) {
			this.depotList = depotList;
		}
		public List<Vehicle> getVehicleList() {
			return vehicleList;
		}
		public void setVehicleList(List<Vehicle> vehicleList) {
			this.vehicleList = vehicleList;
		}
		protected List<Vehicle> vehicleList;
}
