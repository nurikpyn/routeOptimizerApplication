package com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input;

import java.io.File;

public class JsonFileInput {
	private File locationFile;
	private int vehicleCount;
	private int vehicleCapacity;
	private int depotListSize;
	public JsonFileInput() {
		
	}
	
	public JsonFileInput(File locationFile, int vehicleCount, int vehicleCapacity, int depotListSize) {
		super();
		this.locationFile = locationFile;
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

	public File getLocationFile() {
		return locationFile;
	}
	public void setLocationFile(File locationFile) {
		this.locationFile = locationFile;
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
		return "JsonFileInput [locationFile=" + locationFile + ", vehicleCount=" + vehicleCount + ", vehicleCapacity="
				+ vehicleCapacity + ", depotListSize=" + depotListSize + "]";
	}
	

}
