package com.qut.routeOptimizerApplication.Bean;

public class Customer {
	protected Address address;
	protected int demand;
	 protected Customer nextCustomer;
	  protected Vehicle vehicle;

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
	public Customer getNextCustomer() {
		return nextCustomer;
	}
	public void setNextCustomer(Customer nextCustomer) {
		this.nextCustomer = nextCustomer;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
}
