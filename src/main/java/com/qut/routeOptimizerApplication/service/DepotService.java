package com.qut.routeOptimizerApplication.service;

import com.qut.routeOptimizerApplication.Bean.Address;
import com.qut.routeOptimizerApplication.Bean.Depot;

public class DepotService {
public Depot getDepotDetails() {
	Address depotAddress = new Address();
	depotAddress.setLatitude(-27.453311);
	depotAddress.setLongitude(153.034910);
	Depot depot=new Depot();
	depot.setAddress(depotAddress);
	depot.setDemand(1);
	return depot;
}
}
