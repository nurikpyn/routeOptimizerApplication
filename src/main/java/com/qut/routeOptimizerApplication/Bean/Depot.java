package com.qut.routeOptimizerApplication.Bean;

public class Depot {
	protected int id;
protected Address address;
protected int demand;
public Address getAddress() {
	return address;
}

public void setAddress(Address address) {
	this.address = address;
}

public int getDemand() {
	return demand;
}

public void setDemand(int demand) {
	this.demand = demand;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

}
