package com.qut.routeOptimizerApplication.service;

import com.qut.routeOptimizerApplication.Bean.Depot;
import com.qut.routeOptimizerApplication.Bean.Vehicle;

public class VehicleService {
	public Vehicle getDepotDetails(int capacity,Depot depot) {
	Vehicle vehicle=new Vehicle();
	vehicle.setCapacity(capacity);
	vehicle.setDepot(depot);
		return vehicle;
	}
}
