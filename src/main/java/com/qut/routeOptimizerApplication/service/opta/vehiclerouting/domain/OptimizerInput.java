package com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain;

import java.io.File;

public class OptimizerInput {
private File locationFile;
private int vehicleCount;
private int vehicleCapacity;
public OptimizerInput() {
	
}
public OptimizerInput(File locationFile, int vehicleCount, int vehicleCapacity) {
	super();
	this.locationFile = locationFile;
	this.vehicleCount = vehicleCount;
	this.vehicleCapacity = vehicleCapacity;
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

}
