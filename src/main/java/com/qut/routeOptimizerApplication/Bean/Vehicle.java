package com.qut.routeOptimizerApplication.Bean;

public class Vehicle {
	protected int capacity;
    protected Depot depot;
    protected Customer nextCustomer;

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public Customer getNextCustomer() {
		return nextCustomer;
	}

	public void setNextCustomer(Customer nextCustomer) {
		this.nextCustomer = nextCustomer;
	}
    
}
