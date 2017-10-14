package com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input;

import java.util.List;

import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.JsonCustomer;

public class JsonManualInput {
List<JsonCustomer> customerList;
private int vehicleCount;
private int vehicleCapacity;
JsonCustomer depot;
public JsonManualInput() {
	super();
}

public JsonManualInput(List<JsonCustomer> customerList, int vehicleCount, int vehicleCapacity, JsonCustomer depot) {
	super();
	this.customerList = customerList;
	this.vehicleCount = vehicleCount;
	this.vehicleCapacity = vehicleCapacity;
	this.depot = depot;
}

public JsonCustomer getDepot() {
	return depot;
}

public void setDepot(JsonCustomer depot) {
	this.depot = depot;
}

public List<JsonCustomer> getCustomerList() {
	return customerList;
}
public void setCustomerList(List<JsonCustomer> customerList) {
	this.customerList = customerList;
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
	return "JsonManualInput [customerList=" + customerList + ", vehicleCount=" + vehicleCount + ", vehicleCapacity="
			+ vehicleCapacity + ", depot=" + depot + "]";
}



}
