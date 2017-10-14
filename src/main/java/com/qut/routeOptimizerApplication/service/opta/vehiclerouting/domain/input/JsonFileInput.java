package com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input;

public class JsonFileInput {
	private String locationFilePath;
	private int vehicleCount;
	private int vehicleCapacity;
	private int depotListSize;
	public JsonFileInput() {
		
	}
	
	public JsonFileInput(String locationFilePath, int vehicleCount, int vehicleCapacity, int depotListSize) {
		super();
		this.locationFilePath = locationFilePath;
		this.vehicleCount = vehicleCount;
		this.vehicleCapacity = vehicleCapacity;
		this.depotListSize = depotListSize;
	}

	public int getDepotListSize() {
		return depotListSize;
	}

	public void setDepotListSize(int depotListSize) {
		this.depotListSize = depotListSize;
	}

	
	public String getLocationFilePath() {
		return locationFilePath;
	}

	public void setLocationFilePath(String locationFilePath) {
		this.locationFilePath = locationFilePath;
	}

	public int getVehicleCount() {
		return vehicleCount;
	}
	public void setVehicleCount(int vehicleCount) {
		this.vehicleCount = vehicleCount;
	}
	public int getVehicleCapacity() {
		return vehicleCapacity;
	}
	public void setVehicleCapacity(int vehicleCapacity) {
		this.vehicleCapacity = vehicleCapacity;
	}

	@Override
	public String toString() {
		return "JsonFileInput [locationFile=" + locationFilePath + ", vehicleCount=" + vehicleCount + ", vehicleCapacity="
				+ vehicleCapacity + ", depotListSize=" + depotListSize + "]";
	}
	

}
